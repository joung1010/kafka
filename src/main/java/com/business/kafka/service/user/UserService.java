package com.business.kafka.service.user;

import com.business.kafka.controller.user.model.SignUpRequestDto;
import com.business.kafka.systems.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor

@Service
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public void signUp(SignUpRequestDto signUpRequestDto) {

    }
}
