package com.blink.springboot;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import com.blink.springboot.entities.Customer;
import com.blink.springboot.services.CustomersManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class Server1Test {

    @Autowired
    private WebTestClient webClient;

   	@Autowired
	CustomersManager customersManager;
	
	Long customerIdTest = 1l;

	@Test
	public void testRepopsitory() {
		Assertions.assertEquals(customerIdTest, customersManager.get(customerIdTest).getId());
	}

	@Test
	void testServer2() {
		  Customer customerGetFromServer1 = customersManager.get(customerIdTest);
		  Customer customerSavedtoServer2 = webClient.post()
		  		   .uri("/customer")
		  		   .contentType(MediaType.APPLICATION_JSON)
				   .accept(MediaType.APPLICATION_JSON)
				   .bodyValue(customerGetFromServer1)
				   .exchange()
				   .expectStatus().isOk()
				   .expectHeader().contentType(MediaType.APPLICATION_JSON)
				   .expectBody(Customer.class)
				   .returnResult().getResponseBody();
		  
		  Assertions.assertEquals(customerGetFromServer1, customerSavedtoServer2);
		  
	      Customer customerGetFromServer2 = 
	    		  webClient.get().uri("/customer/%d".formatted(customerIdTest))
	      				.exchange()
	      				.expectStatus().isOk()
	      				.expectBody(Customer.class)
	      				.returnResult()
	      				.getResponseBody();

	      Assertions.assertEquals(customerGetFromServer1, customerGetFromServer2);
	
	}
	
}
