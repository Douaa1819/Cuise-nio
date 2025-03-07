package com.youcode.cuisenio;

import com.youcode.cuisenio.features.auth.util.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class CuisenioApplication {
    public static void main(String[] args) {
        SpringApplication.run(CuisenioApplication.class, args);
    }
}


