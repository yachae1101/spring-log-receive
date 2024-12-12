package com.bh.logreceive;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final S3Operations s3Operations;

    public S3Service(S3Operations s3Operations) {
        this.s3Operations = s3Operations;
    }

    @Transactional
    public void uploadLogFile(File file) throws IOException {
        // 고유한 파일명 생성 (타임스탬프 포함)
        String uniqueFileName = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_"))
                + file.getName();

        try (InputStream is = new FileInputStream(file)) {
            s3Operations.upload(bucketName, uniqueFileName, is,
                    ObjectMetadata.builder()
                            .contentType("text/plain")
                            .build());
        }
    }
}