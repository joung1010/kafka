package com.business.kafka.systems.send.message.impl;


import com.business.kafka.systems.send.message.SendMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendMessage extends SendMessage {

    private String subject;

}
