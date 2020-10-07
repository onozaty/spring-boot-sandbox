package com.example.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domain.Customer;
import com.example.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerRestController.class)
public class CustomerRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getCustomers() throws Exception {

        List<Customer> customers = Arrays.asList(
                new Customer(1, "firstName1", "lastName1", "address"),
                new Customer(2, "firstName2", "lastName2", null));

        when(customerService.findAll()).thenReturn(customers);

        mvc.perform(get("/api/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(customers)));
    }

    @Test
    public void getCustomer() throws Exception {

        Customer customer = new Customer(1, "firstName1", "lastName1", "address");

        when(customerService.findOne(1)).thenReturn(Optional.of(customer));

        mvc.perform(get("/api/customers/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(customer)));
    }

}
