package com.business.kafka.systems.send.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConverter {

    private final ObjectMapper objectMapper;

    public String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[메시지 변환] JSON 변환 실패: {}", e.getMessage(), e);
            throw new MessageConversionException("메시지를 JSON 문자열로 변환하는데 실패했습니다.", e);
        }
    }

    public static class MessageConversionException extends RuntimeException {
        public MessageConversionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public <T> T fromJson(String json, Class<T> clsz) {
        try {
            return objectMapper.readValue(json, clsz);
        } catch (Exception e) {
            throw new IllegalArgumentException("JSON 역직렬화 실패", e);
        }
    }

}
