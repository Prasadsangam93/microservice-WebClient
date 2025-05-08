package com.microservicewebclient.service;

import com.microservicewebclient.dto.OrderRequest;
import com.microservicewebclient.entity.Order;
import com.microservicewebclient.model.CustomerDTO;
import com.microservicewebclient.model.ProductDTO;
import com.microservicewebclient.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

        @Override
        public Order placeOrder(OrderRequest orderRequest) {
            // Call customer service
            // ✅ Correct Customer Service URL
            CustomerDTO customer = webClientBuilder.baseUrl("http://localhost:9091")
                    .build()
                    .get()
                    .uri("/api/customers/" + orderRequest.getCustomerId())
                    .retrieve()
                    .bodyToMono(CustomerDTO.class)
                    .block();

// ✅ Correct Product Service URL
            List<ProductDTO> products = webClientBuilder.baseUrl("http://localhost:9092")
                    .build()
                    .post()
                    .uri("/api/products/byIds")
                    .bodyValue(orderRequest.getProducts())
                    .retrieve()
                    .bodyToFlux(ProductDTO.class)
                    .collectList()
                    .block();


            if (products == null || products.isEmpty()) {
                throw new RuntimeException("Products not found");
            }

            // Calculate total price
            double totalPrice = products.stream()
                    .mapToDouble(ProductDTO::getPrice)
                    .sum();

            // Save order
            Order order = new Order();
            order.setCustomerId(orderRequest.getCustomerId());
            order.setProductIds(orderRequest.getProducts());
            order.setTotalPrice(totalPrice);

            return orderRepository.save(order);
        }
    }
//
//@Service
//public class OrderServiceImpl implements OrderService {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private WebClient.Builder webClientBuilder;
//
//    // Define WebClients for different services at the class level
//    private final WebClient customerWebClient = webClientBuilder.baseUrl("http://localhost:9091").build();
//    private final WebClient productWebClient = webClientBuilder.baseUrl("http://localhost:9092").build();
//
//    @Override
//    public Order placeOrder(OrderRequest orderRequest) {
//        // Call Customer Service to fetch customer data
//        CustomerDTO customer = customerWebClient.get()
//                .uri("/api/customers/{id}", orderRequest.getCustomerId())
//                .retrieve()
//                .bodyToMono(CustomerDTO.class)
//                .block();
//
//        if (customer == null) {
//            throw new RuntimeException("Customer not found");
//        }
//
//        // Call Product Service to fetch product data
//        List<ProductDTO> products = productWebClient.post()
//                .uri("/api/products/byIds")
//                .bodyValue(orderRequest.getProducts()) // Pass the list of product IDs
//                .retrieve()
//                .bodyToFlux(ProductDTO.class)
//                .collectList()
//                .block();
//
//        if (products == null || products.isEmpty()) {
//            throw new RuntimeException("Products not found");
//        }
//
//        // Calculate the total price for the order
//        double totalPrice = products.stream()
//                .mapToDouble(ProductDTO::getPrice)
//                .sum();
//
//        // Create an order and save it to the database
//        Order order = new Order();
//        order.setCustomerId(orderRequest.getCustomerId());
//        order.setProductIds(orderRequest.getProducts());
//        order.setTotalPrice(totalPrice);
//
//        return orderRepository.save(order);
//    }
//}


//
//@Service
//public class OrderServiceImpl implements OrderService {
//
//    private final WebClient webClient = WebClient.create("http://localhost:9091");
//
//    @Override
//    public Order placeOrder(OrderRequest orderRequest) {
//        // Fetch customer data
//        CustomerDTO customer = webClient.get()
//                .uri("/api/customers/{id}", orderRequest.getCustomerId())
//                .retrieve()
//                .bodyToMono(CustomerDTO.class)
//                .block();
//
//        if (customer == null) {
//            throw new RuntimeException("Customer not found");
//        }
//
//        // Fetch products using a POST request
//        List<ProductDTO> products = webClient.post()
//                .uri("http://localhost:9092/api/products/byIds")
//                .bodyValue(orderRequest.getProducts()) // Pass the product IDs to the request body
//                .retrieve()
//                .bodyToFlux(ProductDTO.class)
//                .collectList()
//                .block();
//
//        if (products == null || products.isEmpty()) {
//            throw new RuntimeException("Products not found");
//        }
//
//        // Calculate total price
//        double totalPrice = products.stream()
//                .mapToDouble(ProductDTO::getPrice)
//                .sum();
//
//        // Create and save order (you should integrate with your repository)
//        Order order = new Order();
//        order.setCustomerId(orderRequest.getCustomerId());
//        order.setProductIds(orderRequest.getProducts());
//        order.setTotalPrice(totalPrice);
//
//        return orderRepository.save(order);
//    }


//
//@Service
//public class OrderServiceImpl implements OrderService {
//
//    // WebClient for Customer Service (Server 1)
//    private final WebClient customerWebClient = WebClient.create("http://localhost:9091");
//
//    // WebClient for Product Service (Server 2)
//    private final WebClient productWebClient = WebClient.create("http://localhost:9092");
//
//    @Override
//    public Order placeOrder(OrderRequest orderRequest) {
//        // Call Customer Service (Server 1) to fetch customer details
//        CustomerDTO customer = customerWebClient.get()
//                .uri("/api/customers/{id}", orderRequest.getCustomerId())
//                .retrieve()
//                .bodyToMono(CustomerDTO.class)
//                .block();
//
//        if (customer == null) {
//            throw new RuntimeException("Customer not found");
//        }
//
//        // Call Product Service (Server 2) to fetch products by IDs
//        List<ProductDTO> products = productWebClient.post()
//                .uri("/api/products/byIds")
//                .bodyValue(orderRequest.getProducts()) // Pass the list of product IDs
//                .retrieve()
//                .bodyToFlux(ProductDTO.class)
//                .collectList()
//                .block();
//
//        if (products == null || products.isEmpty()) {
//            throw new RuntimeException("Products not found");
//        }
//
//        // Calculate the total price for the order
//        double totalPrice = products.stream()
//                .mapToDouble(ProductDTO::getPrice)
//                .sum();
//
//        // Create an order and save it to the database (you need to implement this logic)
//        Order order = new Order();
//        order.setCustomerId(orderRequest.getCustomerId());
//        order.setProductIds(orderRequest.getProducts());
//        order.setTotalPrice(totalPrice);
//
//        return orderRepository.save(order);
//    }
//}