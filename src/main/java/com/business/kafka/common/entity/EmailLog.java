package com.business.kafka.common.entity;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "email_logs")
public class EmailLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long receiverUserId;

  private String receiverEmail;

  private String subject;


}