package com.microservicewebclient.service;

import com.microservicewebclient.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product saveProduct(Product product);




    List<Product> getProductsByIds(List<Long> ids);
}
