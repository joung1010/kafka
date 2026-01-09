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
        havingValue = "sync-commit",
        matchIfMissing = false
)
@Component
public class SyncCommitConsumer {

    private static final String TOPIC_NAME = "test";

    /**
     * 동기 오프셋 커밋 (배치 단위)
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = "sync-group")
    public void listenWithSyncCommit(
            ConsumerRecord<String, String> record,
            Acknowledgment ack) {
        
        log.info("Received record: {}", record);
        
        // 레코드 처리 후 동기 커밋
        ack.acknowledge();
    }

    /**
     * 개별 레코드 단위 동기 커밋
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = "sync-individual-group")
    public void listenWithIndividualCommit(
            ConsumerRecord<String, String> record,
            Acknowledgment ack) {
        
        try {
            // 레코드 처리
            log.info("Processing record: key={}, value={}, offset={}, partition={}", 
                record.key(), 
                record.value(), 
                record.offset(), 
                record.partition());
            
            // 각 레코드 처리 후 즉시 커밋
            ack.acknowledge();
            
        } catch (Exception e) {
            log.error("Failed to process record", e);
            // 커밋하지 않음 - 재처리됨
        }
    }
}
