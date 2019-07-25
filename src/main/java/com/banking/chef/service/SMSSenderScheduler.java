package com.banking.chef.service;

import java.util.Calendar;
import java.util.Timer;

public class SMSSenderScheduler {

    public static void setTimer() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 9);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        int period = 86_400_000; //1000*60*60*24 = 1 day

        Timer timer = new Timer();
        timer.schedule(new SMSSenderService(), today.getTime(), period);
    }
}