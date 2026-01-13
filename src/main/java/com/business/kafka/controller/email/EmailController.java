package com.business.kafka.controller.email;


import com.business.kafka.controller.email.model.SendEmailCriteria;
import com.business.kafka.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> send(
            @RequestBody SendEmailCriteria criteria) {


        return ResponseEntity.ok("Send Email Success");
    }
}
