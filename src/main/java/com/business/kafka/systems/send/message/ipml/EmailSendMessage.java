package com.business.kafka.systems.send.message.ipml;


import com.business.kafka.systems.send.message.SendMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class EmailSendMessage extends SendMessage {

    private String subject;

}
