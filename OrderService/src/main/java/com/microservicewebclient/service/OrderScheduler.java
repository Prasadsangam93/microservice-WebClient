package com.microservicewebclient.service;


import com.microservicewebclient.dto.OrderRequest;
import com.microservicewebclient.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderScheduler {


    @Autowired
    private  OrderService orderService;

    @Scheduled(fixedRate = 60000)
    public  void autoPlaceSampleOrder(){

        OrderRequest request = new OrderRequest();
        request.setCustomerId(206L);
        request.setProducts(List.of(503L,504L));
        try {

            Order order = orderService.placeOrder(request);
            System.out.println("Scheduling order placed : "+ order.getId());
        } catch (Exception e) {
            System.err.println("Scheduling order failed  :"+ e.getMessage());
        }

    }

}
