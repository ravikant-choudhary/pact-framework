package com.retailer.controller;

import au.com.dius.pact.provider.junit.Consumer;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.retailer.model.Order;
import com.retailer.service.RetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Provider("retailer")
@Consumer("customer-service")
@PactBroker(host = "localhost", port = "8888")
public class CustomerPactTest {

    @TestTarget
    public Target target = new SpringBootHttpTarget();

    @State("Get Order Detail")
    public void getOrderTest() {
        RetailService retailService = Mockito.mock(RetailService.class);
        var order = new Order("Ravikant", 20000.0, 2);
        Mockito.when(retailService.getOrder()).thenReturn(order);
    }
}
