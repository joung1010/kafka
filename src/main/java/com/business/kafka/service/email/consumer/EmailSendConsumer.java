package com.business.kafka.service.email.consumer;

import com.business.kafka.configuration.annotation.Consumer;
import com.business.kafka.systems.send.constant.EmailConstant;
import com.business.kafka.systems.send.converter.MessageConverter;
import com.business.kafka.systems.send.message.impl.EmailSendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;

@Slf4j
@RequiredArgsConstructor

@Consumer
public class EmailSendConsumer {

    private final MessageConverter converter;

    @KafkaListener(
            topics = EmailConstant.TOPIC,
            groupId = EmailConstant.GROUP_ID
    )
    @RetryableTopic(
            attempts = "5"
            , backoff = @Backoff(delay = 1000, multiplier = 2) //처음에는 1초 그다음 부터 곱하기 2배만큼 간격을 줌
            , dltTopicSuffix = ".dlt"
    )
    public void send(String message) {
        log.info("Kafka Message : {}", message);
        EmailSendMessage emailSendMessage = (EmailSendMessage) converter.fromJson(message, EmailSendMessage.class);
        if (emailSendMessage.getSubject().equals("fail")) {
            log.info("이메일 발송 처리 실패");
            throw new RuntimeException("Wrong Email Address");
        }
        //발송 로직

        log.info("발송 완료!!!");
    }

}
