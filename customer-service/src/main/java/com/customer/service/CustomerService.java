package com.customer.service;

import com.customer.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private RestTemplate restTemplate = new RestTemplate();

    public Order getOrder() {
        var response = restTemplate.getForObject("http://localhost:8090/retail/order", Order.class);
        System.out.println("Response Name: " + response.getCustomer());
        return response;
    }

    public Order createOrder(Order order) {
        var response = restTemplate.postForObject("http://localhost:8090/retail/order/create", order, Order.class);

        return response;
    }
}
