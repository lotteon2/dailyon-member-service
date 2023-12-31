package com.dailyon.memeberservice;

import java.time.LocalDateTime;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableEurekaClient
@EnableJpaAuditing
@SpringBootApplication
public class MemberServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MemberServiceApplication.class, args);
  }

  @PostConstruct
  public void setTimezoneToSeoul() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    log.info("현재 시각={}", LocalDateTime.now());
  }
}
