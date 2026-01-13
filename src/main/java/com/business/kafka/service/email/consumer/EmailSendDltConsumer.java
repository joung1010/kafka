package com.business.kafka.service.email.consumer;

import com.business.kafka.configuration.annotation.Consumer;
import com.business.kafka.systems.send.constant.EmailConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@RequiredArgsConstructor

@Consumer
public class EmailSendDltConsumer {

    @KafkaListener(
            topics = EmailConstant.DLT_TOPIC
            , groupId = EmailConstant.DLT_GROUP_ID
    )
    public void handler(String message) {

        // 로그 시스템 전송
        log.info("로그 시스템 전송: {}", message);
        // 알림 방송
        log.info("관리자 알림 발송: {}", message);

    }
}
