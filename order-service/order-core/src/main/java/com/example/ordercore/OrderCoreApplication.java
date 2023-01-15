package com.example.ordercore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.ordercore"})
public class OrderCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderCoreApplication.class, args);
    }

}
