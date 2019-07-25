package com.banking.chef.service;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class SMSSenderScheduler {

    public static void setTimer() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 9);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long delay = TimeUnit.HOURS.toMillis(24);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SMSSenderService(), today.getTime(), delay);
    }
}
