package com.microservicewebclient.service;

import com.microservicewebclient.dto.OrderRequest;
import com.microservicewebclient.entity.Order;

public interface OrderService  {


    Order placeOrder(OrderRequest orderRequest);
}
