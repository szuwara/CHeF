package com.banking.chef.service;

import org.springframework.stereotype.Component;

@Component
public class ThymeMath {
    public static double calculateAmountAndRound(double rate) {
        double amount = Double.parseDouble(System.getenv("amountInCHF")) * rate;
        return Math.round(amount * 100.0) / 100.0;
    }
}

//methods to use in Thymeleaf template
