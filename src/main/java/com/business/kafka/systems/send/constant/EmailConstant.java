package com.business.kafka.systems.send.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailConstant {
    public static final String TOPIC = "email.send";
    public static final String GROUP_ID = "email-send-group";

}
