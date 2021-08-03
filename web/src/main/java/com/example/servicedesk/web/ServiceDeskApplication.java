package com.example.servicedesk.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories({"com.example.servicedesk.web", "com.example.servicedesk.core"})
@ComponentScan({"com.example.servicedesk.web", "com.example.servicedesk.core"})
@EntityScan({"com.example.servicedesk.core"})
@EnableConfigurationProperties
public class ServiceDeskApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceDeskApplication.class, args);
  }
}
