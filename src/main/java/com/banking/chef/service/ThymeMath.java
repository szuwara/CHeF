package com.banking.chef.service;

import org.springframework.stereotype.Component;

@Component
public class ThymeMath {
    public double calculateAmountAndRound(double rate) {
        double amountInCHF = Double.parseDouble(System.getenv("amountInCHF")) * rate;
        return Math.round(amountInCHF * 100.0) / 100.0;
    }
}

//methods to use in Thymeleaf template
