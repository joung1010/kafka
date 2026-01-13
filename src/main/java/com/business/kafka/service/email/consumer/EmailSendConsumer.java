package com.business.kafka.service.email.consumer;

import com.business.kafka.configuration.annotation.Consumer;
import com.business.kafka.systems.send.constant.EmailConstant;
import com.business.kafka.systems.send.converter.MessageConverter;
import com.business.kafka.systems.send.message.impl.EmailSendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@RequiredArgsConstructor

@Consumer
public class EmailSendConsumer {

    private final MessageConverter converter;

    @KafkaListener(
            topics = EmailConstant.TOPIC,
            groupId = EmailConstant.GROUP_ID
    )
    public void send(String message) {
        log.info("Kafka Message : {}", message);
        EmailSendMessage emailSendMessage = (EmailSendMessage) converter.fromJson(message, EmailSendMessage.class);
        //발송 로직

        log.info("발송 완료!!!");
    }

}
