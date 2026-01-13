package com.business.kafka.systems.send.impl;

import com.business.kafka.common.enums.SendTypeEnums;
import com.business.kafka.systems.send.Sender;
import com.business.kafka.systems.send.converter.MessageConverter;
import com.business.kafka.systems.send.message.impl.EmailSendMessage;
import com.business.kafka.systems.send.mode.SendResultModel;
import com.business.kafka.systems.send.mode.impl.EmailSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.Assert;

@Slf4j
public abstract class AbstractEmail implements Sender<EmailSendRequest, EmailSendMessage> {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageConverter messageConverter;

    protected AbstractEmail(KafkaTemplate<String, String> kafkaTemplate, MessageConverter messageConverter) {
        Assert.notNull(kafkaTemplate, "KafkaTemplate must not be null");
        Assert.notNull(messageConverter, "MessageConverter must not be null");
        this.kafkaTemplate = kafkaTemplate;
        this.messageConverter = messageConverter;
    }

    @Override
    public abstract String getTopic();

    @Override
    public String type() {
        return SendTypeEnums.EMAIL.getCode();
    }

    @Override
    public SendResultModel<Void> send(EmailSendRequest request) {
        try {
            SendResultModel<EmailSendMessage> processResult = process(request);
            
            if (processResult.status() == SendResultModel.ResultStatus.FAIL) {
                log.error("[이메일 발송] 처리 실패: 요청 처리 중 오류가 발생했습니다.");
                return SendResultModel.fail();
            }

            EmailSendMessage messageBody = processResult.body();
            if (messageBody == null) {
                log.error("[이메일 발송] 메시지 본문이 null입니다.");
                return SendResultModel.fail();
            }

            String jsonMessage = messageConverter.toJsonString(messageBody);
            kafkaTemplate.send(getTopic(), jsonMessage);
            
            log.info("[이메일 발송] 성공: topic={}, from={}, to={}", 
                    getTopic(), messageBody.getFrom(), messageBody.getTo());
            
            return SendResultModel.success();
        } catch (MessageConverter.MessageConversionException e) {
            log.error("[이메일 발송] JSON 변환 실패: {}", e.getMessage(), e);
            return SendResultModel.fail();
        } catch (Exception e) {
            log.error("[이메일 발송] 예상치 못한 오류 발생: {}", e.getMessage(), e);
            return SendResultModel.fail();
        }
    }

    @Override
    public abstract SendResultModel<EmailSendMessage> process(EmailSendRequest request);
}
