package com.business.kafka.service.email;

import com.business.kafka.service.email.model.SendEmailCriteria;
import com.business.kafka.systems.send.constant.EmailConstant;
import com.business.kafka.common.utils.MessageConverter;
import com.business.kafka.systems.send.impl.AbstractEmail;
import com.business.kafka.systems.send.message.impl.EmailSendMessage;
import com.business.kafka.systems.send.mode.SendResultModel;
import com.business.kafka.systems.send.mode.impl.EmailSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService extends AbstractEmail {


    public EmailService(KafkaTemplate<String, String> kafkaTemplate, MessageConverter messageConverter) {
        super(kafkaTemplate, messageConverter);
    }

    @Override
    public String getTopic() {
        return EmailConstant.TOPIC;
    }

    public SendResultModel<Void> sendEmail(final SendEmailCriteria criteria) {
        EmailSendRequest request = toEmailSendRequest(criteria);
        return super.send(request);
    }

    @Override
    public SendResultModel<EmailSendMessage> process(EmailSendRequest request) {
        EmailSendMessage message = EmailSendMessage.builder()
                .from(request.getFrom())
                .to(request.getTo())
                .subject(request.getSubject())
                .content(request.getContent())
                .build();
        
        return SendResultModel.success(message);
    }

    private EmailSendRequest toEmailSendRequest(SendEmailCriteria criteria) {
        return EmailSendRequest.builder()
                .from(criteria.getFrom())
                .to(criteria.getTo())
                .subject(criteria.getSubject())
                .content(criteria.getBody())
                .build();
    }
}
