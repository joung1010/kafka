package com.business.kafka.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component
public class ConsumerLagMonitor {

    private final Optional<KafkaListenerEndpointRegistry> registry;

    public ConsumerLagMonitor(Optional<KafkaListenerEndpointRegistry> registry) {
        this.registry = registry;
    }

    /**
     * 주기적으로 컨슈머 상태 모니터링 (5초마다)
     * 실제 메트릭은 Kafka Admin API나 kafka-consumer-groups.sh를 통해 확인하는 것을 권장
     */
    @Scheduled(fixedDelay = 5000)
    public void monitorConsumerLag() {
        if (registry.isEmpty()) {
            log.debug("KafkaListenerEndpointRegistry is not available");
            return;
        }

        try {
            Collection<MessageListenerContainer> containers = registry.get().getAllListenerContainers();
            if (containers == null || containers.isEmpty()) {
                log.debug("No listener containers found");
                return;
            }

            containers.forEach(this::logConsumerStatus);
        } catch (Exception e) {
            log.error("Failed to monitor consumer lag", e);
        }
    }

    private void logConsumerStatus(MessageListenerContainer container) {
        if (container == null) {
            log.warn("Container is null");
            return;
        }

        try {
            boolean isRunning = container.isRunning();
            String listenerId = container.getListenerId();
            log.debug("Consumer Status - Listener: {}, Running: {}", listenerId, isRunning);
        } catch (Exception e) {
            log.warn("Failed to get consumer status: {}", e.getMessage(), e);
        }
    }
}

