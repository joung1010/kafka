package com.business.kafka.controller.user;

import com.business.kafka.controller.user.model.SignUpRequestDto;
import com.business.kafka.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;


    @PostMapping
    public ResponseEntity<String> signUp(
            @RequestBody SignUpRequestDto signUpRequestDto
    ) {
        service.signUp(signUpRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

}

