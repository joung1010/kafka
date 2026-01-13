package com.business.kafka.systems.send.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessage {
    private String from;
    private String to;
    @Setter
    private String content;
    
    // Builder 패턴을 사용할 때 content를 설정할 수 있도록 유지
}
