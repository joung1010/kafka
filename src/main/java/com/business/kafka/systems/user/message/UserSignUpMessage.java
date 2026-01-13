package com.business.kafka.systems.user.message;

import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpMessage {
    private Long userId;
    private String email;
    private String name;
}
