package com.business.kafka.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Slf4j
@Configuration
@EnableKafkaStreams
public class StreamsFilterConfig {

    @Value("${topics.stream-log}")
    private String streamLogTopic;

    @Value("${topics.stream-log-filter}")
    private String streamLogFilterTopic;

    @Bean
    public KStream<String, String> kStreamFilter(StreamsBuilder streamsBuilder) {
        log.info("Creating Stream Filter Topology");
        log.info("Input topic: {}", streamLogTopic);
        log.info("Output topic: {}", streamLogFilterTopic);

        // 소스 프로세서: stream_log 토픽에서 데이터 읽기
        KStream<String, String> sourceStream = streamsBuilder.stream(streamLogTopic);

        // 스트림 프로세서: 길이가 5보다 큰 값만 필터링
        KStream<String, String> filteredStream = sourceStream
                .filter((key, value) -> {
                    boolean pass = value != null && value.length() > 5;
                    log.info("Filter check - key: {}, value: {}, length: {}, pass: {}",
                            key, value, value != null ? value.length() : 0, pass);
                    return pass;
                });

        // 싱크 프로세서: stream_log_filter 토픽으로 전송
        filteredStream.to(streamLogFilterTopic);

        log.info("Stream Filter topology created: {} -> filter(length > 5) -> {}",
                streamLogTopic, streamLogFilterTopic);

        return filteredStream;
    }
}