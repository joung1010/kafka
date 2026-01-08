package com.business.kafka.service.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor

@Service
public class SimpleKafkaProducerService {

    private static final String TOPIC_NAME = "test";

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 키 없이 메시지 전송
     *
     * @param message 메세지
     */
    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
        log.info("Sent message: {}", message);
    }


    /**
     * 메세지 키와 함께 전송
     *
     * @param key     키
     * @param message 메시지
     */
    public void sendMessageWithKey(String key, String message) {
        kafkaTemplate.send(TOPIC_NAME, key, message);
        log.info("Sent message with key: {} - {}", key, message);
    }


    /**
     * 파티션 번호를 지정하여 전송
     *
     * @param partition 파티션 번호
     * @param key       키
     * @param message   메시지
     */
    public void sendMessageToPartition(int partition, String key, String message) {
        kafkaTemplate.send(TOPIC_NAME, partition, key, message);
        log.info("Sent message to partition {}: {} - {}", partition, key, message);
    }
}
