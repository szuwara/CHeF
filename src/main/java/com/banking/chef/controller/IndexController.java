package com.banking.chef.controller;

import com.banking.chef.service.JsonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    @GetMapping(value = "/home")
    public String hello() {
        return "home";
    }

    @GetMapping(value = "/chf")
    public String getCHFexchangeRate(Model model) {
        double currentCHFrate = JsonService.readValuesFromJson();
        model.addAttribute("rate", currentCHFrate);
        return "chf";
    }

    /*@GetMapping(value = "/sent")
    public String invokeSendSMSMethod() {
        SMSSenderService.sendSMS();
        return "sent";
    }*/

}
