package com.business.kafka.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Slf4j
@Configuration
@EnableKafkaStreams
public class KStreamJoinGlobalKTableConfig {

    @Value("${topics.address-v2}")
    private String addressV2Topic;

    @Value("${topics.order}")
    private String orderTopic;

    @Value("${topics.order-join-global}")
    private String orderJoinGlobalTopic;

    @Bean
    public KStream<String, String> kStreamJoinGlobalKTable(StreamsBuilder streamsBuilder) {
        log.info("=== Creating KStream-GlobalKTable Join Topology ===");
        log.info("GlobalKTable topic: {}", addressV2Topic);
        log.info("KStream topic: {}", orderTopic);
        log.info("Output topic: {}", orderJoinGlobalTopic);
        
        // GlobalKTable 생성: address_v2 토픽
        // 코파티셔닝 불필요, 모든 데이터가 각 태스크에 복제됨
        GlobalKTable<String, String> addressGlobalTable = 
            streamsBuilder.globalTable(addressV2Topic);
        
        // GlobalKTable 데이터 로깅
        // 주의: GlobalKTable은 toStream() 메서드가 없으므로 별도 로깅 불가
        
        // KStream 생성: order 토픽
        KStream<String, String> orderStream = streamsBuilder.stream(orderTopic);
        
        // KStream 입력 로깅
        orderStream.peek((key, value) ->
            log.info("📦 Order Received (for Global Join) - key: [{}], product: [{}]", 
                key, value)
        );
        
        // GlobalKTable과 조인 (3개 파라미터 필요)
        KStream<String, String> joinedStream = orderStream.join(
            addressGlobalTable,
            // 첫 번째 파라미터: 키 매핑 함수 (KStream의 키를 GlobalKTable의 키로 매핑)
            (orderKey, orderValue) -> {
                log.info("🔑 Key Mapping - orderKey: [{}], orderValue: [{}] => lookupKey: [{}]", 
                    orderKey, orderValue, orderKey);
                return orderKey;  // orderKey를 그대로 사용
            },
            // 두 번째 파라미터: 조인 로직 (값 조합)
            (order, address) -> {
                if (address == null) {
                    log.warn("⚠️ Address not found in GlobalKTable for order: {}", order);
                    return order + " send to UNKNOWN";
                }
                String result = order + " send to " + address;
                log.info("🔗 GlobalKTable Join Success - product: [{}], address: [{}] => [{}]", 
                    order, address, result);
                return result;
            }
        );
        
        // 조인 결과 로깅
        joinedStream.peek((key, value) ->
            log.info("✅ Global Join Output - key: [{}], result: [{}]", key, value)
        );
        
        // 결과를 order_join_global 토픽으로 전송
        joinedStream.to(orderJoinGlobalTopic);
        
        log.info("=== KStream-GlobalKTable Join Topology Created ===");
        log.info("📌 Note: Co-partitioning NOT required for GlobalKTable");
        
        return joinedStream;
    }
}
