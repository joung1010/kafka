package com.business.kafka.controller.user.model;

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

}