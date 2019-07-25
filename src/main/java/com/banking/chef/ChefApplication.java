package com.banking.chef;

import com.banking.chef.service.SMSSenderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChefApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChefApplication.class, args);
        SMSSenderService smsSenderService = new SMSSenderService();
        smsSenderService.setTimer();
    }
}
