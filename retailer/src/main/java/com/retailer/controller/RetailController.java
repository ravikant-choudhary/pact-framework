package com.retailer.controller;

import com.retailer.model.Item;
import com.retailer.model.Order;
import com.retailer.service.RetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retail")
public class RetailController {

    @Autowired
    private RetailService retailService;

    @GetMapping("/order")
    public Order getOrder() {
        return retailService.getOrder();
    }

    @GetMapping("/item")
    public Item getItem() {
        return retailService.getItem();
    }

    @PostMapping("/order/create")
    public Order createOrder(@RequestBody Order order) {
        return retailService.createOrder(order);
    }
}
