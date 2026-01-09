package com.business.kafka.service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnProperty(
        name = "spring.kafka.consumer.type",
        value = "partition",
        matchIfMissing = false
)
@Component
public class PartitionAssignConsumer {

    /**
     * 특정 파티션만 구독
     */
    @KafkaListener(
        groupId = "partition-assign-group",
        topicPartitions = @TopicPartition(
            topic = "test",
            partitions = {"0"}  // 파티션 0만 구독
        )
    )
    public void listenPartition0(ConsumerRecord<String, String> record) {
        log.info("Partition 0 - Received: key={}, value={}, offset={}", 
            record.key(), 
            record.value(), 
            record.offset());
    }

    /**
     * 여러 파티션 지정
     */
    @KafkaListener(
        groupId = "multi-partition-group",
        topicPartitions = @TopicPartition(
            topic = "test",
            partitions = {"0", "1", "2"}
        )
    )
    public void listenMultiplePartitions(ConsumerRecord<String, String> record) {
        log.info("Partition {} - Received: {}", record.partition(), record.value());
    }
}
