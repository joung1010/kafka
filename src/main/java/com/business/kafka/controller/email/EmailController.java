package com.business.kafka.controller.email;

import com.business.kafka.controller.email.model.SendEmailCriteria;
import com.business.kafka.service.email.EmailService;
import com.business.kafka.systems.send.mode.SendResultModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> send(@RequestBody SendEmailCriteria criteria) {
        SendResultModel<Void> result = emailService.sendEmail(criteria);
        
        if (result.status() == SendResultModel.ResultStatus.SUCCESS) {
            return ResponseEntity.ok("이메일 발송이 성공적으로 요청되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이메일 발송 요청 처리 중 오류가 발생했습니다.");
        }
    }
}
