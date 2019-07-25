package com.banking.chef.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.TimerTask;

public class SMSSenderService extends TimerTask {

    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String NUMBER_FROM = System.getenv("TWILIO_NUMBER_FROM");
    private static final String NUMBER_TO = System.getenv("TWILIO_NUMBER_TO");
    private static double currentCHFRate = JsonService.readValuesFromJson();
    private static String smsBody = "Today's CHF exchange rate: " + currentCHFRate;

    public void sendSMS() {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(NUMBER_TO),
                new PhoneNumber(NUMBER_FROM),
                smsBody)
                .create();

        System.out.println(message.getSid());
    }

    @Override
    public void run() {
        sendSMS();
    }
}
