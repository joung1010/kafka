package com.business.kafka.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class KafkaConsumerShutdownHandler implements ApplicationListener<ContextClosedEvent> {

    private final Optional<KafkaListenerEndpointRegistry> registry;

    public KafkaConsumerShutdownHandler(Optional<KafkaListenerEndpointRegistry> registry) {
        this.registry = registry;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (registry.isEmpty()) {
            log.debug("KafkaListenerEndpointRegistry is not available, skipping shutdown");
            return;
        }

        log.info("Gracefully shutting down Kafka consumers...");
        
        try {
            // 모든 리스너 컨테이너를 안전하게 종료
            registry.get().getAllListenerContainers().forEach(container -> {
                try {
                    log.info("Stopping listener: {}", container.getListenerId());
                    container.stop();
                } catch (Exception e) {
                    log.warn("Failed to stop listener: {}", container.getListenerId(), e);
                }
            });
            
            log.info("All Kafka consumers stopped gracefully");
        } catch (Exception e) {
            log.error("Failed to shutdown Kafka consumers", e);
        }
    }
}
