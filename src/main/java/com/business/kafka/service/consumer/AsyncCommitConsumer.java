package com.business.kafka.service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnProperty(
        name = "spring.kafka.consumer.type",
        value = "async",
        matchIfMissing = false
)
@Component
public class AsyncCommitConsumer {

    private static final String TOPIC_NAME = "test";

    /**
     * 비동기 오프셋 커밋
     * Spring Kafka는 기본적으로 비동기 방식으로 커밋 처리
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = "async-group")
    public void listenWithAsyncCommit(
            ConsumerRecord<String, String> record,
            Acknowledgment ack) {
        
        log.info("Received record: {}", record);
        
        try {
            // 레코드 처리
            processRecord(record);
            
            // 비동기 커밋 (내부적으로 비동기 처리)
            ack.acknowledge();
            
        } catch (Exception e) {
            log.error("Failed to process record", e);
        }
    }

    private void processRecord(ConsumerRecord<String, String> record) {
        // 실제 비즈니스 로직 처리
        log.info("Processing: key={}, value={}", record.key(), record.value());
    }
}
