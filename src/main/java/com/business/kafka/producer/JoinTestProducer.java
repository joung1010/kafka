package com.business.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JoinTestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topics.address}")
    private String addressTopic;

    @Value("${topics.order}")
    private String orderTopic;

    /**
     * 주소 전송 (동기)
     */
    public void sendAddress(String name, String address) {
        try {
            SendResult<String, String> result = kafkaTemplate.send(addressTopic, name, address).get();
            log.info("✅ Address sent successfully - name: {}, address: {}, offset: {}", 
                name, address, result.getRecordMetadata().offset());
        } catch (Exception e) {
            log.error("❌ Failed to send address - name: {}, address: {}", name, address, e);
            throw new RuntimeException("Failed to send address", e);
        }
    }

    /**
     * 주문 전송 (동기)
     */
    public void sendOrder(String name, String product) {
        try {
            SendResult<String, String> result = kafkaTemplate.send(orderTopic, name, product).get();
            log.info("✅ Order sent successfully - name: {}, product: {}, offset: {}", 
                name, product, result.getRecordMetadata().offset());
        } catch (Exception e) {
            log.error("❌ Failed to send order - name: {}, product: {}", name, product, e);
            throw new RuntimeException("Failed to send order", e);
        }
    }
}
