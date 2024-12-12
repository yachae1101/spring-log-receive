package com.bh.logreceive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final S3Service s3Service;

    @KafkaListener(topics="log-topic", groupId="bh")
    public void listen(String message) {
        log.info(message);
        try {
            // 로그 파일에 메시지 기록
            String filename = "log.txt";
            appendToFile(filename, message);

            // S3에 파일 업로드
            File file = new File(filename);
            s3Service.uploadLogFile(file);
        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
        }
    }

    private void appendToFile(String filename, String message) throws IOException {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(message + "\n");
        }
    }
}