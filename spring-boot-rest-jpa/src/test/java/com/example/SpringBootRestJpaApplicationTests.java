package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:log4jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE" })
public class SpringBootRestJpaApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;

    @Before
    public void setup() {

        customerRepository.deleteAll();

        customer1 = new Customer();
        customer1.setName("Taro");
        customer1.setAddress("Tokyo");

        customer2 = new Customer();
        customer2.setName("花子");

        customerRepository.save(Arrays.asList(customer1, customer2));
    }

    @Test
    public void getCustomers() {

        ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
                "/api/customers", HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {
                });

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).containsOnly(customer1, customer2);
    }
}
