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
public class SpringBootRestJpaApplicationTests {

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
        customer1.setFirstName("Taro");
        customer1.setLastName("Yamada");
        customer1.setAddress("Tokyo");
        customerRepository.save(customer1);

        customer2 = new Customer();
        customer2.setFirstName("花子");
        customer2.setLastName("山田");
        customer2.setAddress("千葉");
        customerRepository.save(customer2);

        customer3 = new Customer();
        customer3.setFirstName("Taku");
        customer3.setLastName("Suzuki");
        customerRepository.save(customer3);
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
                customer1.getId());

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

        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Tayler");
        newCustomer.setLastName("Swift");
        newCustomer.setAddress("New York");

        int expectId = customer3.getId() + 1; // 最後に追加したもの+1

        {
            ResponseEntity<Customer> response =
                    restTemplate.postForEntity("/api/customers", newCustomer, Customer.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            assertThat(response.getBody())
                    .hasFieldOrPropertyWithValue("id", expectId)
                    .isEqualToComparingOnlyGivenFields(newCustomer, "firstName", "lastName", "address");
        }

        {
            ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/{id}", Customer.class,
                    expectId);

            assertThat(response.getBody())
                    .hasFieldOrPropertyWithValue("id", expectId)
                    .isEqualToComparingOnlyGivenFields(newCustomer, "firstName", "lastName", "address");
        }

    }

    @Test
    public void updateCustomer() {

        customer1.setFirstName("New Name");

        {
            ResponseEntity<Customer> response =
                    restTemplate.postForEntity("/api/customers/{id}", customer1, Customer.class,
                            customer1.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(customer1);
        }

        {
            ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/{id}", Customer.class,
                    customer1.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(customer1);
        }
    }

    @Test
    public void deleteCustomer() {

        {
            ResponseEntity<Void> response = restTemplate.exchange("/api/customers/{id}", HttpMethod.DELETE, null,
                    Void.class, customer1.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        {
            ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/{id}", Customer.class,
                    customer1.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNull();
        }
    }
}
