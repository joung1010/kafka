package com.business.kafka.systems.send;

import com.business.kafka.systems.send.message.SendMessage;
import com.business.kafka.systems.send.mode.SendRequest;
import com.business.kafka.systems.send.mode.SendResultModel;

/**
 * 메시지 발송을 담당하는 인터페이스
 * 
 * @param <TRequest> 발송 요청 타입
 * @param <TMessage> 발송 메시지 타입
 */
public interface Sender<TRequest extends SendRequest, TMessage extends SendMessage> {

    /**
     * 발송 타입을 반환합니다.
     * 
     * @return 발송 타입 코드
     */
    String type();

    /**
     * Kafka 토픽을 반환합니다.
     * 
     * @return 토픽 이름
     */
    String getTopic();

    /**
     * 메시지를 발송합니다.
     * 
     * @param request 발송 요청
     * @return 발송 결과
     */
    SendResultModel<Void> send(TRequest request);

    /**
     * 발송 요청을 메시지로 변환합니다.
     * 
     * @param request 발송 요청
     * @return 변환된 메시지와 결과 상태
     */
    SendResultModel<TMessage> process(TRequest request);
}
