package com.business.kafka.service.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;


@Slf4j
@RequiredArgsConstructor

@Service
public class KafkaProducerWithCallbackService {

    private static final String TOPIC_NAME = "test";
    private final KafkaTemplate<String, String> kafkaTemplate;


    /**
     * 비동기 전송 + Callback
     *
     * @param key     키
     * @param message 메시지
     */
    public void sendMessageAsync(String key, String message) {
        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(TOPIC_NAME, key, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]",
                        message,
                        result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message=[{}] due to : {}",
                        message,
                        ex.getMessage());
            }
        });
    }

    /**
     * 동기 전송 (Blocking)
     *
     * @param key     키
     * @param message 메시지
     */
    public void sendMessageSync(String key, String message) {
        try {
            SendResult<String, String> result =
                    kafkaTemplate.send(TOPIC_NAME, key, message).get();

            log.info("Sent message=[{}] with offset=[{}], partition=[{}]",
                    message,
                    result.getRecordMetadata().offset(),
                    result.getRecordMetadata().partition());

        } catch (Exception e) {
            log.error("Failed to send message", e);
        }
    }
}
