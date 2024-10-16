package com.product.service;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
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
public class ProductServiceTest {

    private static final String HOST = "localhost";
    private static final Integer PORT = 8090;

    @Autowired
    private ProductService productService;

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("retailer", HOST, PORT, this);

    @Pact(consumer = "product-service", provider = "retailer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("brand", "Apple")
                .decimalType("name", "Iphone 16 Pro")
                .integerType("price", 139000);

        Map<String,String> headers = new HashMap();
        headers.put("Content-Type","application/json");

        return builder.given("Getting Item detail")
                .uponReceiving("Get Item details from Retailer")
                .path("/retail/item")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .headers(headers)
                .body(body)
                .toPact();
    }

    @Test
    @PactVerification(value = "retailer")
    public void getItemTest() {
        var result = productService.getItem();
        System.out.println("Item Name: " + result.getBrand());
        assertNotNull(result);
        assertEquals("Apple", result.getBrand());
    }
}
