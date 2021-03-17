package com.banking.chef.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SMSSenderService {

    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String NUMBER_FROM = System.getenv("TWILIO_NUMBER_FROM");
    private static final String NUMBER_TO = System.getenv("TWILIO_NUMBER_TO");
    private static final TimeZone MY_TIME_ZONE = TimeZone.getTimeZone(System.getenv("TZ"));
    private static final String appProdURL = "https://chefrank.herokuapp.com/chf";
    private static final String monthlyAmountCHF = System.getenv("amountInCHFnew");


    public static void sendSMS() {
        double currentCHFRate = getRate();
        String smsBody = String.format("CHF exchange rate: %s (PLN: %s, CHF: %s)\nClick for more: %s", currentCHFRate, getMonthlyAmountPLN(), monthlyAmountCHF, appProdURL);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(NUMBER_TO),
                new PhoneNumber(NUMBER_FROM),
                smsBody)
                .create();
        System.out.println(message.getSid() + "\nNOTIFICATION SENT with value of rate: " + currentCHFRate);
    }

    static long[] getTimes() {
        long[] timesArray = new long[3];

        Calendar timeOfGettingRate = Calendar.getInstance();
        timeOfGettingRate.setTimeZone(MY_TIME_ZONE);
        timeOfGettingRate.set(Calendar.HOUR_OF_DAY, 8);
        timeOfGettingRate.set(Calendar.MINUTE, 30);
        timeOfGettingRate.set(Calendar.SECOND, 0);
        Calendar timeOfSendingNotif = Calendar.getInstance();
        timeOfSendingNotif.setTimeZone(MY_TIME_ZONE);
        timeOfSendingNotif.set(Calendar.HOUR_OF_DAY, 9);
        timeOfSendingNotif.set(Calendar.MINUTE, 0);
        timeOfSendingNotif.set(Calendar.SECOND, 0);


        Calendar currentDay = Calendar.getInstance();
        currentDay.setTimeZone(MY_TIME_ZONE);

        long currentTime = currentDay.getTimeInMillis();
        long delay = TimeUnit.DAYS.toMillis(1);

        if (currentDay.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && currentDay.get(Calendar.HOUR_OF_DAY) > 11) {
            timeOfGettingRate.add(Calendar.DATE, 3);
            timeOfSendingNotif.add(Calendar.DATE, 3);

        } else if (currentDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            timeOfGettingRate.add(Calendar.DATE, 2);
            timeOfSendingNotif.add(Calendar.DATE, 2);
            System.out.println("Occurrence delayed to " + timeOfGettingRate.getTime());
        } else if (currentDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            timeOfGettingRate.add(Calendar.DATE, 1);
            timeOfSendingNotif.add(Calendar.DATE, 1);
            System.out.println("Occurrence delayed to " + timeOfGettingRate.getTime());
        }

        if (timeOfSendingNotif.getTime().getTime() < currentTime && timeOfGettingRate.getTime().getTime() < currentTime) {
            timeOfSendingNotif.add(Calendar.DATE, 1);
            timeOfGettingRate.add(Calendar.DATE, 1);
            System.out.println("Notification and getting rate delayed to next day \n" +
                    "Next occurrence of notification is: " + timeOfSendingNotif.getTime() +
                    "\nNext occurrence of getting rate is: " + timeOfGettingRate.getTime());
        }


        long startNotifScheduler = timeOfSendingNotif.getTime().getTime() - currentTime;
        long startGetRateScheduler = timeOfGettingRate.getTime().getTime() - currentTime;

        {
            int rateDays = getDays(startGetRateScheduler);
            int rateHours = getHours(startGetRateScheduler);
            int rateMinutes = getMinutes(startGetRateScheduler);
            int notifDays = getDays(startNotifScheduler);
            int notifHours = getHours(startNotifScheduler);
            int notifMinutes = getMinutes(startNotifScheduler);

            System.out.println(rateDays + " days " + rateHours + " hours " + rateMinutes + " minutes to get rate");
            System.out.println(notifDays + " days " + notifHours + " hours " + notifMinutes + " minutes to send notification");
        }

        timesArray[0] = startGetRateScheduler;
        timesArray[1] = startNotifScheduler;
        timesArray[2] = delay;

        return timesArray;
    }

    private static int getMinutes(long source) {
        return (int) ((source / 1000 / 60) % 60);
    }

    private static int getHours(long source) {
        return (int) ((source / 1000 / 60 / 60) % 24);
    }

    private static int getDays(long source) {
        return (int) (source / 1000 / 60 / 60 / 24);
    }

    static double getRate() {
        double rate = JsonService.readCurrentExchangeRateFromJson();
        System.out.println("Rate downloaded [" + rate + "]");
        return rate;
    }

    private static double getMonthlyAmountPLN() {
        return ThymeMath.calculateAmountAndRound(getRate());
    }
}
