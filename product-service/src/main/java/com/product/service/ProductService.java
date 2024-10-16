package com.product.service;

import com.product.model.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    private RestTemplate restTemplate = new RestTemplate();

    public Item getItem() {
        var item = restTemplate.getForObject("http://localhost:8090/retail/item", Item.class);
        return item;
    }
}
