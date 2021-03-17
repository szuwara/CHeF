package com.banking.chef.controller;

import com.banking.chef.service.JsonService;
import com.banking.chef.service.SMSSenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.util.Map;

@Controller
public class IndexController {

    final boolean SWITCHON = false;
    String buttonMessage;

    @GetMapping(value = "/")
    public String getCHFexchangeRate(Model model) throws ParseException {
        Map<String, Double> exchangeTable = JsonService.readMonthlyExchangeRateFromJson();
        double currentCHFrate = JsonService.readCurrentExchangeRateFromJson();
        model.addAttribute("table", exchangeTable);
        model.addAttribute("rate", currentCHFrate);
        model.addAttribute("chfamount", System.getenv("amountInCHFnew"));
        return "chf";
    }

    @GetMapping(value = "/sent")
    public String getSentMessage(Model model) throws ParseException {
        getOrSetButtonMessage();
        model.addAttribute("message", buttonMessage);
        if (SWITCHON) {
            SMSSenderService.sendSMS();
            getOrSetButtonMessage();
        } else {
            getOrSetButtonMessage();
        }
        return "sent";
    }

    String getOrSetButtonMessage() {
        if (SWITCHON) {
            return buttonMessage = "SMS notification sent!";
        } else {
            return buttonMessage = "Sorry, button function disabled for maintenance";
        }
    }
}
