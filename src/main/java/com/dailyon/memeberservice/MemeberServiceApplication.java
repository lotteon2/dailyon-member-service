package com.dailyon.memeberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MemeberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemeberServiceApplication.class, args);
    }

}
