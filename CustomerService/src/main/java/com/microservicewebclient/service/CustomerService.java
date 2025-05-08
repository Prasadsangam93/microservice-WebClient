package com.microservicewebclient.service;

import com.microservicewebclient.entity.Customer;

public interface CustomerService {


    Customer saveCustomer(Customer customer);

    Customer getCustomerById(Long id);
}
