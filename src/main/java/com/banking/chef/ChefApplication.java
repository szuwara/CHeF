package com.banking.chef;

import com.banking.chef.service.SMSSenderScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;

@SpringBootApplication
public class ChefApplication {

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(ChefApplication.class, args);
        SMSSenderScheduler.setTimer();
    }
}
