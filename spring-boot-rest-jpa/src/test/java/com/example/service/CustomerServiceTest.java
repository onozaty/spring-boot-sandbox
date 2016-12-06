package com.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CustomerServiceTest.Config.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void findAll() {

        List<Customer> customers =
                Arrays.asList(
                        new Customer(1, "name1", "address1"),
                        new Customer(2, "name2", "address2"));

        when(customerRepository.findAll()).thenReturn(customers);

        assertThat(customerService.findAll()).isEqualTo(customers);
    }

    @Configuration
    @ComponentScan
    static class Config {
    }
}
