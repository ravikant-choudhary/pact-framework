package com.retailer.controller;

import au.com.dius.pact.provider.junit.Consumer;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.retailer.model.Item;
import com.retailer.service.RetailService;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Provider("retailer")
@Consumer("product-service")
@PactBroker(host = "localhost", port = "8888")
public class ProductPactTest {

    @TestTarget
    Target target = new SpringBootHttpTarget();

    @State("Get Item Details")
    public void getItemTest() {
        RetailService retailService = Mockito.mock(RetailService.class);
        var item = new Item("Apple", "Iphone", 139000.00);
        Mockito.when(retailService.getItem()).thenReturn(item);

    }
}
