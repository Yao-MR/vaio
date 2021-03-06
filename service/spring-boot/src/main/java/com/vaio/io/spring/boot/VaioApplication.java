package com.vaio.io.spring.boot;

import com.vaio.io.spring.boot.listener.MyApplicationStartedEventListener;
import com.vaio.io.spring.boot.repository.JpaUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaioApplication implements {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(VaioApplication.class);
        app.addListeners(new MyApplicationStartedEventListener());
        app.run(args);
    }
}