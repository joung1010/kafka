package com.business.kafka.systems.send;

import com.business.kafka.systems.send.message.SendMessage;
import com.business.kafka.systems.send.mode.SendRequest;
import com.business.kafka.systems.send.mode.SendResultModel;

public interface Sender<T extends SendRequest> {

    String type();

    String getTopic();

    SendResultModel<?> send(T request);

    SendResultModel<? extends SendMessage> process(T request);

}
