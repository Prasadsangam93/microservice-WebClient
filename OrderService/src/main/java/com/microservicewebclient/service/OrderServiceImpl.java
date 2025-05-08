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