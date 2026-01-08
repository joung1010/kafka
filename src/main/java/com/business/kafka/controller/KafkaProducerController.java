package com.business.kafka.controller;

import com.business.kafka.service.producer.KafkaProducerWithCallbackService;
import com.business.kafka.service.producer.SimpleKafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {

    private final SimpleKafkaProducerService simpleProducerService;
    private final KafkaProducerWithCallbackService callbackProducerService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        simpleProducerService.sendMessage(message);
        return "Message sent: " + message;
    }

    @PostMapping("/send-with-key")
    public String sendMessageWithKey(
            @RequestParam String key,
            @RequestParam String message) {
        simpleProducerService.sendMessageWithKey(key, message);
        return "Message sent with key: " + key;
    }

    @PostMapping("/send-to-partition")
    public String sendToPartition(
            @RequestParam int partition,
            @RequestParam String key,
            @RequestParam String message) {
        simpleProducerService.sendMessageToPartition(partition, key, message);
        return "Message sent to partition: " + partition;
    }

    @PostMapping("/send-async")
    public String sendAsync(
            @RequestParam String key,
            @RequestParam String message) {
        callbackProducerService.sendMessageAsync(key, message);
        return "Async message sent";
    }

    @PostMapping("/send-sync")
    public String sendSync(
            @RequestParam String key,
            @RequestParam String message) {
        callbackProducerService.sendMessageSync(key, message);
        return "Sync message sent";
    }


}