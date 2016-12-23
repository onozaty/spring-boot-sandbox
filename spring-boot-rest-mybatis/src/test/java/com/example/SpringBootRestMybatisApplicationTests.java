package com.example;

import static org.assertj.core.api.Assertions.assertThat;

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
public class SpringBootRestMybatisApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @Before
    public void setup() {

        customerRepository.deleteAll();

        customer1 = new Customer();
        customer1.setName("Taro");
        customer1.setAddress("Tokyo");
        customerRepository.save(customer1);

        customer2 = new Customer();
        customer2.setName("花�?");
        customer2.setAddress("�?�?");
        customerRepository.save(customer2);

        customer3 = new Customer();
        customer3.setName("Taku");
        customerRepository.save(customer3);
    }

    @Test
    public void getCustomers() {

        ResponseEntity<List<Customer>> response = restTemplate.exchange(
                "/api/customers", HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsOnly(customer1, customer2, customer3);
    }

    @Test
    public void getCustomer() {

        ResponseEntity<Customer> response = restTemplate.getForEntity(
                "/api/customers/{id}", Customer.class, customer1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(customer1);
    }

    @Test
    public void getCustomersByName() {

        ResponseEntity<List<Customer>> response = restTemplate.exchange(
                "/api/customers/search?name=a", HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsOnly(customer1, customer3);
    }

    @Test
    public void createCustomer() {

        Customer newCustomer = new Customer();
        newCustomer.setName("Tayler");
        newCustomer.setAddress("New York");

        ResponseEntity<Customer> response = restTemplate.postForEntity(
                "/api/customers", newCustomer, Customer.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Customer result = response.getBody();
        assertThat(result.getId()).isEqualTo(customer3.getId() + 1); // �?後に追�?したも�?�+1
        assertThat(result.getName()).isEqualTo("Tayler");
        assertThat(result.getAddress()).isEqualTo("New York");
    }

    @Test
    public void upateCustomer() {

        customer1.setName("New Name");

        ResponseEntity<Customer> response = restTemplate.postForEntity(
                "/api/customers/{id}", customer1, Customer.class, customer1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(customer1);
    }

    @Test
    public void deleteCustomer() {

        {
            ResponseEntity<Void> response = restTemplate.exchange(
                    "/api/customers/{id}", HttpMethod.DELETE, null, Void.class, customer1.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        {
            ResponseEntity<Customer> response = restTemplate.getForEntity(
                    "/api/customers/{id}", Customer.class, customer1.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNull();
        }
    }
}
