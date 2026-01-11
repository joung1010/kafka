package com.business.kafka.controller;

import com.business.kafka.producer.StreamLogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stream")
@RequiredArgsConstructor
public class StreamTestController {

    private final StreamLogProducer producer;

    @PostMapping("/send")
    public String sendMessage(
        @RequestParam(required = false) String key,
        @RequestParam String value
    ) {
        producer.sendMessage(key, value);
        return "Message sent: " + value;
    }
}
