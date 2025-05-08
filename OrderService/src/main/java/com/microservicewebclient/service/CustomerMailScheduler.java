package com.microservicewebclient.service;

import com.microservicewebclient.entity.Order;
import com.microservicewebclient.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMailScheduler {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(fixedRate = 60000) // Every 60 seconds
    public void sendOrderDeliveryNotification() {

        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if (order.getEmail() != null && order.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(order.getEmail());
                message.setSubject("Order Status");
                message.setText("Order is ready for delivery");

                try {
                    mailSender.send(message);
                    System.out.println("Mail sent to: " + order.getEmail());
                } catch (Exception e) {
                    System.err.println("Failed to send mail to " + order.getEmail() + ": " + e.getMessage());
                }
            }
        }
    }
}




