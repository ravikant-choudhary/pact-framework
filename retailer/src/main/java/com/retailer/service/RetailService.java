package com.retailer.service;

import com.retailer.model.Item;
import com.retailer.model.Order;
import org.springframework.stereotype.Service;

@Service
public class RetailService {

    public Item getItem() {
        return new Item("Apple", "Iphone", 100000.0);
    }

    public Order getOrder() {
        return new Order("Ravikant", 10000.00, 2);
    }

    public Order createOrder(Order order) {
        return order;
    }
}
