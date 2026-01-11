package com.business.kafka.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Slf4j
@Configuration
@EnableKafkaStreams
public class KStreamJoinKTableConfig {

    @Value("${topics.address}")
    private String addressTopic;

    @Value("${topics.order}")
    private String orderTopic;

    @Value("${topics.order-join}")
    private String orderJoinTopic;

    @Bean
    public KStream<String, String> kStreamJoinKTable(StreamsBuilder streamsBuilder) {
        log.info("=== Creating KStream-KTable Join Topology ===");
        log.info("KTable topic: {}", addressTopic);
        log.info("KStream topic: {}", orderTopic);
        log.info("Output topic: {}", orderJoinTopic);
        
        // KTable 생성: address 토픽 (이름 -> 주소)
        KTable<String, String> addressTable = streamsBuilder.table(addressTopic);
        
        // KTable 변경사항 로깅
        addressTable.toStream().foreach((key, value) ->
            log.info("📍 KTable Updated - key: [{}], address: [{}]", key, value)
        );
        
        // KStream 생성: order 토픽 (이름 -> 주문상품)
        KStream<String, String> orderStream = streamsBuilder.stream(orderTopic);
        
        // KStream 입력 로깅
        orderStream.peek((key, value) ->
            log.info("📦 Order Received - key: [{}], product: [{}]", key, value)
        );
        
        // 조인 수행: 같은 키(이름)를 기준으로 조인
        KStream<String, String> joinedStream = orderStream.join(
            addressTable,
            (order, address) -> {
                String result = order + " send to " + address;
                log.info("🔗 Join Success - product: [{}], address: [{}] => [{}]", 
                    order, address, result);
                return result;
            }
        );
        
        // 조인 결과 로깅
        joinedStream.peek((key, value) ->
            log.info("✅ Join Output - key: [{}], result: [{}]", key, value)
        );
        
        // 조인 결과를 order_join 토픽으로 전송
        joinedStream.to(orderJoinTopic);
        
        log.info("=== KStream-KTable Join Topology Created ===");
        
        return joinedStream;
    }
}
