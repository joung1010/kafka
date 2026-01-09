package com.business.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class CustomRebalanceListener implements ConsumerAwareRebalanceListener {

    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        log.warn("Partitions assigned: {}", partitions);
        partitions.forEach(partition -> 
            log.info("Assigned - topic: {}, partition: {}", 
                partition.topic(), 
                partition.partition())
        );
    }

    @Override
    public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        log.warn("Partitions revoked: {}", partitions);
        partitions.forEach(partition -> 
            log.info("Revoked - topic: {}, partition: {}", 
                partition.topic(), 
                partition.partition())
        );
        
        // 리밸런스 전 현재 오프셋 커밋
        if (consumer != null) {
            try {
                consumer.commitSync();
            } catch (Exception e) {
                log.error("Failed to commit offsets during rebalance", e);
            }
        }
    }
}
