package com.banking.chef.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SMSSenderService extends TimerTask {

    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String NUMBER_FROM = System.getenv("TWILIO_NUMBER_FROM");
    private static final String NUMBER_TO = System.getenv("TWILIO_NUMBER_TO");
    private static final TimeZone MY_TIME_ZONE = TimeZone.getTimeZone(System.getenv("TZ"));
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

    public void setTimer() {
        Calendar today = Calendar.getInstance();
        today.setTimeZone(MY_TIME_ZONE);
        today.set(Calendar.HOUR_OF_DAY, 9);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long period = TimeUnit.HOURS.toMillis(24);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SMSSenderService(), today.getTime(), period);
    }

    @Override
    public void run() {
        sendSMS();
    }
}
