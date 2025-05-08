package com.microservicewebclient.controller;

import com.microservicewebclient.entity.Product;
import com.microservicewebclient.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {



    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }


    // Get products by list of IDs
    @PostMapping("/byIds")
    public List<Product> getProductsByIds(@RequestBody List<Long> ids) {
        return productService.getProductsByIds(ids);
    }
}
