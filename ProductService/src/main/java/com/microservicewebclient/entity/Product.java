package com.microservicewebclient.entity;

import jakarta.persistence.*; // ✅ Correct

import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product_tableWebclient")
@Entity

public class    Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
//
//    @ManyToMany(mappedBy = "products")
//    private List<Order> orders;

}
