package com.banking.chef.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class SMSSenderService {

    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String NUMBER_FROM = System.getenv("TWILIO_NUMBER_FROM");
    private static final String NUMBER_TO = System.getenv("TWILIO_NUMBER_TO");
    private static final TimeZone MY_TIME_ZONE = TimeZone.getTimeZone(System.getenv("TZ"));
    private static double currentCHFRate = JsonService.readValuesFromJson();
    private static String smsBody = "Today's CHF exchange rate: " + currentCHFRate;


    public static void sendSMS() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(NUMBER_TO),
                new PhoneNumber(NUMBER_FROM),
                smsBody)
                .create();
        System.out.println(message.getSid() + "\nNOTIFICATION SENT!");
    }

    public static void setTimer() {
        Calendar dayOfExecution = Calendar.getInstance();
        dayOfExecution.setTimeZone(MY_TIME_ZONE);
        dayOfExecution.set(Calendar.HOUR_OF_DAY, 10);
        dayOfExecution.set(Calendar.MINUTE, 0);
        dayOfExecution.set(Calendar.SECOND, 0);

        Calendar currentDay = Calendar.getInstance();
        currentDay.setTimeZone(MY_TIME_ZONE);

        long currentTime = currentDay.getTimeInMillis();
        long delayOfNextNotification = TimeUnit.HOURS.toMillis(6); // set interval to 6 hours for tests -> goal is to 24 hours
        if (dayOfExecution.getTime().getTime() < currentTime) {
            dayOfExecution.add(Calendar.DATE, 1);
            System.out.println("Notification delayed to next day \n" +
                    "Next occurrence of notification is: " + dayOfExecution.getTime());
        }

        long startScheduler = dayOfExecution.getTime().getTime() - currentTime;
        {
            int hours = (int) (startScheduler / 1000 / 60 / 60);
            int minutes = (int) ((startScheduler / 1000 / 60) % 60);
            System.out.println(hours + " hours " + minutes + " minutes to pass");
        }

        final ScheduledExecutorService executeSendingNotification = Executors.newSingleThreadScheduledExecutor();
        executeSendingNotification.scheduleAtFixedRate(SMSSenderService::sendSMS, startScheduler, delayOfNextNotification, TimeUnit.MILLISECONDS);
    }
}
