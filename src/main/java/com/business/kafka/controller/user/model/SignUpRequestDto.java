package com.business.kafka.controller.user.model;

import com.business.kafka.systems.user.message.UserSignUpMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpRequestDto {
  private String email;
  private String name;
  private String password;

  public UserSignUpMessage toMessage(Long id) {
    return UserSignUpMessage.builder()
            .userId(id)
            .name(getName())
            .email(getEmail())
            .build();
  }

}