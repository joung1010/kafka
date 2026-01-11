package com.business.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamLogProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topics.stream-log}")
    private String streamLog;

    public void sendMessage(String key, String value) {
        kafkaTemplate.send(streamLog, key, value);
        log.info("Sent message - key: {}, value: {}", key, value);
    }
}
