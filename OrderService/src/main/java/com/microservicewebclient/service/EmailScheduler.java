package com.microservicewebclient.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired

    private EmailService emailService;

    @Scheduled(fixedRate = 60000)
    public void sendDailyReminder(){
        emailService.sendSimpleMail(
                "rames.balagoni@eidiko-india.com,",
                "Order Status",
                "Order is ready for delivery "
        );
    }
}
