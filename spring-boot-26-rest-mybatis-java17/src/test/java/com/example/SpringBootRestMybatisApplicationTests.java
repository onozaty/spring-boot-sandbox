package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE" })
public class SpringBootRestMybatisApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @BeforeEach
    public void setup() {

        customerRepository.deleteAll();

        customerRepository.insert(new Customer(1, "Taro", "Yamada", "Tokyo"));
        customer1 = customerRepository.findLast();

        customerRepository.insert(new Customer(2, "花子", "山田", "千葉"));
        customer2 = customerRepository.findLast();

        customerRepository.insert(new Customer(3, "Taku", "Suzuki", null));
        customer3 = customerRepository.findLast();
    }

    @Test
    public void getCustomers() {

        ResponseEntity<List<Customer>> response = restTemplate.exchange("/api/customers", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Customer>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsOnly(customer1, customer2, customer3);
    }

    @Test
    public void getCustomer() {

        ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/{id}", Customer.class,
                customer1.id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(customer1);
    }

    @Test
    public void getCustomersByName() {

        ResponseEntity<List<Customer>> response =
                restTemplate.exchange("/api/customers/search?firstName=a", HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<Customer>>() {
                        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsOnly(customer1, customer3);
    }

    @Test
    public void createCustomer() {

        Customer newCustomer = new Customer(null, "Tayler", "Swift", "New York");

        int expectId = customer3.id() + 1; // 最後に追加したもの+1

        {
            ResponseEntity<Customer> response =
                    restTemplate.postForEntity("/api/customers", newCustomer, Customer.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            assertThat(response.getBody())
                    .returns(expectId, Customer::id)
                    .returns(newCustomer.firstName(), Customer::firstName)
                    .returns(newCustomer.lastName(), Customer::lastName)
                    .returns(newCustomer.address(), Customer::address);
        }

        {
            ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/{id}", Customer.class,
                    expectId);

            assertThat(response.getBody())
                    .returns(expectId, Customer::id)
                    .returns(newCustomer.firstName(), Customer::firstName)
                    .returns(newCustomer.lastName(), Customer::lastName)
                    .returns(newCustomer.address(), Customer::address);
        }

    }

    @Test
    public void updateCustomer() {

        Customer editedCustomer = new Customer(customer1.id(), "New Name", customer1.lastName(), customer1.address());

        {
            ResponseEntity<Customer> response =
                    restTemplate.postForEntity("/api/customers/{id}", editedCustomer, Customer.class,
                            editedCustomer.id());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(editedCustomer);
        }

        {
            ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/{id}", Customer.class,
                    editedCustomer.id());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(editedCustomer);
        }
    }

    @Test
    public void deleteCustomer() {

        {
            ResponseEntity<Void> response = restTemplate.exchange("/api/customers/{id}", HttpMethod.DELETE, null,
                    Void.class, customer1.id());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        {
            ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/{id}", Customer.class,
                    customer1.id());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNull();
        }
    }
}
