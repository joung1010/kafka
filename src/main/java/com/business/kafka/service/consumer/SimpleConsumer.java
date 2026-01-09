package com.business.kafka.service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnProperty(
        name = "spring.kafka.consumer.type",
        value = "simple",
        matchIfMissing = false
)
@Component
public class SimpleConsumer {

    private static final String TOPIC_NAME = "test";

    /**
     * 기본 컨슈머 - 자동 커밋
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = "test-group")
    public void listen(String message) {
        log.info("Received message: {}", message);
    }
}
