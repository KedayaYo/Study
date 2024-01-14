package com.tth.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tth.service.*"})
public class JetcacheServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JetcacheServiceApplication.class, args);
    }

}
