package project.buysellservice.global.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties
public class MinioConfig {

    private String minioUrl;

    private String accessKey;

    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9000") // 실제 Minio 서버의 endpoint를 입력해야 합니다.
                .credentials("minioadmin", "minioadmin123")
                .build();
    }

}
