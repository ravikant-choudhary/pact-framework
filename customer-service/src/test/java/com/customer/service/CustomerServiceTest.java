package com.customer.service;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.customer.model.Order;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    private static final String HOST = "localhost";
    private static final Integer PORT = 8090;

    @Autowired
    private CustomerService customerService;

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("retailer", HOST, PORT, this);

    @Pact(consumer = "customer-service", provider = "retailer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("customer", "Ravikant")
                .decimalType("total", 20000.0)
                .integerType("noOfItems", 2);

        Map<String,String> headers = new HashMap();
        headers.put("Content-Type","application/json");

        return builder.given("Order is available")
                .uponReceiving("Get Order details")
                .path("/retail/order")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .headers(headers)
                .body(body)
                .toPact();
    }

    @Pact(consumer = "customer-service", provider = "retailer")
    public RequestResponsePact createPactForCreateOrder(PactDslWithProvider builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("customer", "Ravikant Choudhary")
                .decimalType("total", 20000.0)
                .integerType("noOfItems", 3);

        Map<String,String> headers = new HashMap();
        headers.put("Content-Type","application/json");

        return builder.given("Order is Created")
                .uponReceiving("Going to create the order")
                .path("/retail/order/create")
                .method(HttpMethod.POST.name())
                .headers(headers)
                .body(body)
                .willRespondWith()
                .status(HttpStatus.CREATED.value())
                .body(body)
                .toPact();
    }

    @Test
    @PactVerification(value = "retailer")
    public void createOrderTest() {
        var order = new Order();
        order.setCustomer("Ravikant Choudhary");
        order.setTotal(20000.0);
        order.setNoOfItems(3);
        var result = customerService.createOrder(order);
        assertNotNull(result);
        assertEquals(3, result.getNoOfItems());
    }

    @Test
    @PactVerification(value = "retailer")
    public void testGetOrder() {
        var result = customerService.getOrder();
        assertNotNull(result);
        assertEquals(result.getCustomer(), "Ravikant");
    }

}
