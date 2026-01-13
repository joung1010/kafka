package com.business.kafka.service.email;

import com.business.kafka.controller.email.model.SendEmailCriteria;
import com.business.kafka.systems.send.impl.AbstractEmail;
import com.business.kafka.systems.send.message.ipml.EmailSendMessage;
import com.business.kafka.systems.send.mode.SendResultModel;
import com.business.kafka.systems.send.mode.impl.EmailSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService extends AbstractEmail {


    public EmailService(KafkaTemplate<String, String> template) {
        super(template);
    }

    @Override
    public String getTopic() {
        return "email.send";
    }

    public SendResultModel<Void> sendEmail(final SendEmailCriteria criteria) {
        return super.send(
                EmailSendRequest.builder()
                        .from(criteria.getFrom())
                        .to(criteria.getTo())
                        .subject(criteria.getSubject())
                        .content(criteria.getBody())
                        .build()
        );
    }


    @Override
    public SendResultModel<EmailSendMessage> process(EmailSendRequest request) {
        return SendResultModel.success(
                EmailSendMessage.builder()
                        .from(request.getFrom())
                        .to(request.getTo())
                        .subject(request.getSubject())
                        .content(request.getContent())
                        .build()
        );
    }
}
