package com.banking.chef.controller;

import com.banking.chef.service.JsonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String getCHFexchangeRate(Model model) throws ParseException {
        Map<String, Double> exchangeTable = JsonService.readMonthlyExchangeRateFromJson();
        double currentCHFrate = JsonService.readCurrentExchangeRateFromJson();
        model.addAttribute("table", exchangeTable);
        model.addAttribute("rate", currentCHFrate);
        model.addAttribute("chfamount", System.getenv("amountInCHF"));
        return "chf";
    }
}
