package com.example.servicedesk.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
    classes = ServiceDeskApplication.class,
    loader = AnnotationConfigContextLoader.class)
@SpringBootTest
class ServiceDeskApplicationTests {

  @Test
  void contextLoads() {}
}
