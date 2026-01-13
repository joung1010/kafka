package com.business.kafka.consumer.email.user;

import com.business.kafka.common.utils.MessageConverter;
import com.business.kafka.systems.send.constant.EmailConstant;
import com.business.kafka.systems.user.constant.UserConst;
import com.business.kafka.systems.user.message.UserSignUpMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor

@Component
public class UserSignUpEnventConsumer {

    private final MessageConverter converter;

    @KafkaListener(
            topics = UserConst.SIGN_UP_TOPIC,
            groupId = EmailConstant.GROUP_ID,
            concurrency = "3"
    )
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            dltTopicSuffix = ".dlt"
    )
    public void consume(String message) throws InterruptedException {
        UserSignUpMessage userSignedUpEvent = converter.fromJson(message, UserSignUpMessage.class);

        // 실제 이메일 발송 로직은 생략
        String receiverEmail = userSignedUpEvent.getEmail();
        String subject = userSignedUpEvent.getName() + "님, 회원 가입을 축하드립니다!";
        Thread.sleep(3000); // 이메일 발송에 3초 정도 시간이 걸리는 걸 가정
        System.out.println("이메일 발송 완료");
    }
}
