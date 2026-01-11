package com.business.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalKTableTestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topics.address-v2}")
    private String addressV2Topic;

    @Value("${topics.order}")
    private String orderTopic;

    /**
     * address_v2 토픽에 주소 전송 (동기)
     */
    public void sendAddressV2(String name, String address) {
        try {
            kafkaTemplate.send(addressV2Topic, name, address).get();
            log.info("✅ Address V2 sent - name: {}, address: {}", name, address);
        } catch (Exception e) {
            log.error("❌ Failed to send address V2 - name: {}, address: {}", 
                name, address, e);
            throw new RuntimeException("Failed to send address V2", e);
        }
    }

    /**
     * order 토픽에 주문 전송 (동기)
     */
    public void sendOrder(String name, String product) {
        try {
            kafkaTemplate.send(orderTopic, name, product).get();
            log.info("✅ Order sent - name: {}, product: {}", name, product);
        } catch (Exception e) {
            log.error("❌ Failed to send order - name: {}, product: {}", 
                name, product, e);
            throw new RuntimeException("Failed to send order", e);
        }
    }
}
