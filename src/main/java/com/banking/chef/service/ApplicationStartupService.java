package com.banking.chef.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ApplicationStartupService implements ApplicationListener<ApplicationReadyEvent> {
    private long[] values = SMSSenderService.getTimes();

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        long startGetRate = values[0];
        long startSendNotif = values[1];
        long delay = values[2];
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(SMSSenderService::getRate, startGetRate, delay, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(SMSSenderService::sendSMS, startSendNotif, delay, TimeUnit.MILLISECONDS);

        //SMSSenderService.sendSMS();
    }
}
