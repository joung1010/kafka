package com.business.kafka.systems.send.impl;

import com.business.kafka.common.enums.SendTypeEnums;
import com.business.kafka.systems.send.Sender;
import com.business.kafka.systems.send.message.ipml.EmailSendMessage;
import com.business.kafka.systems.send.mode.SendResultModel;
import com.business.kafka.systems.send.mode.impl.EmailSendRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public abstract class AbstractEmail implements Sender<EmailSendRequest> {
    private final KafkaTemplate<String, String> template;

    protected AbstractEmail(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @Override
    public abstract String getTopic();

    @Override
    public String type() {
        return SendTypeEnums.EMAIL.getCode();
    }

    @Override
    public SendResultModel<Void> send(EmailSendRequest request) {
        SendResultModel<EmailSendMessage> sendModel = process(request);
        EmailSendMessage messageBody = sendModel.body();
        template.send(getTopic(),this.toJsonString(messageBody));

        return SendResultModel.success();
    }

    @Override
    public abstract SendResultModel<EmailSendMessage> process(EmailSendRequest request);

    private String toJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String message = null;
        try {
            message = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {

            log.error("[이메일 발송] String 변환 실패:{}", e.getMessage(), e);
            return "";
        }
        return message;
    }
}
