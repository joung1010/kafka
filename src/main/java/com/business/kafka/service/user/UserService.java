package com.business.kafka.service.user;

import com.business.kafka.common.entity.UserEntity;
import com.business.kafka.controller.user.model.SignUpRequestDto;
import com.business.kafka.systems.send.converter.MessageConverter;
import com.business.kafka.systems.user.constant.UserConst;
import com.business.kafka.systems.user.message.UserSignUpMessage;
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
    private final MessageConverter converter;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public void signUp(SignUpRequestDto dto) {
        UserEntity entity = UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
        userRepository.save(entity);

        UserSignUpMessage message = dto.toMessage(entity.getId());
        kafkaTemplate.send(UserConst.SIGN_UP_TOPIC, converter.toJsonString(message));

    }
}
