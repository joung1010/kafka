package com.business.kafka.controller;

import com.business.kafka.producer.JoinTestProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/join")
@RequiredArgsConstructor
public class JoinTestController {

    private final JoinTestProducer producer;

    @PostMapping("/address")
    public String sendAddress(@RequestParam String name, @RequestParam String address) {
        log.info("API Request - sendAddress: name={}, address={}", name, address);
        producer.sendAddress(name, address);
        return String.format("Address sent - %s: %s", name, address);
    }

    @PostMapping("/order")
    public String sendOrder(@RequestParam String name, @RequestParam String product) {
        log.info("API Request - sendOrder: name={}, product={}", name, product);
        producer.sendOrder(name, product);
        return String.format("Order sent - %s: %s", name, product);
    }

    @PostMapping("/test-scenario")
    public Map<String, String> testScenario() {
        log.info("=== Starting Join Test Scenario ===");
        
        // 시나리오 1: 주소 먼저 등록 (중요!)
        log.info("Step 1: Registering addresses...");
        producer.sendAddress("wonyoung", "Seoul");
        producer.sendAddress("somin", "Busan");
        
        // 잠시 대기 (KTable이 데이터를 받을 시간)
        try {
            log.info("Step 2: Waiting for KTable to process...");
            Thread.sleep(2000);  // 2초 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Sleep interrupted", e);
        }
        
        // 시나리오 2: 주문 발생
        log.info("Step 3: Placing orders...");
        producer.sendOrder("somin", "iPhone");
        producer.sendOrder("wonyoung", "Galaxy");
        
        log.info("=== Join Test Scenario Completed ===");
        
        return Map.of(
            "status", "Test scenario executed",
            "step1", "Addresses registered: wonyoung->Seoul, somin->Busan",
            "step2", "Orders placed: somin->iPhone, wonyoung->Galaxy",
            "expected", "Check order_join topic for: 'iPhone send to Busan' and 'Galaxy send to Seoul'"
        );
    }

    @PostMapping("/test-update-scenario")
    public Map<String, String> testUpdateScenario() {
        log.info("=== Starting Address Update Test ===");
        
        // 1. 초기 주소 등록
        producer.sendAddress("wonyoung", "Seoul");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 2. 첫 주문
        producer.sendOrder("wonyoung", "iPhone");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 3. 주소 변경
        producer.sendAddress("wonyoung", "Jeju");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 4. 두 번째 주문
        producer.sendOrder("wonyoung", "Galaxy");
        
        return Map.of(
            "status", "Address update test completed",
            "expected1", "First order: iPhone send to Seoul",
            "expected2", "Second order: Galaxy send to Jeju"
        );
    }
}
