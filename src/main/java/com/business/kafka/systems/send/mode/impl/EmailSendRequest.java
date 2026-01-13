package com.business.kafka.systems.send.mode.impl;

import com.business.kafka.systems.send.mode.SendRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
public class EmailSendRequest extends SendRequest {

    private String subject;
}
