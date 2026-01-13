package com.business.kafka.controller.email.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SendEmailCriteria {
    private String from;
    private String to;
    private String subject;
    private String body;

}
