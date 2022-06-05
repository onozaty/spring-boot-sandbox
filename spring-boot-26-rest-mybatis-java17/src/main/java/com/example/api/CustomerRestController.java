package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Customer;
import com.example.service.CustomerService;

@RestController
@RequestMapping("api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }

    @GetMapping(path = "{id}")
    public Customer getCustomer(@PathVariable Integer id) {
        return customerService.findOne(id);
    }

    @GetMapping(path = "/search")
    public List<Customer> getCustomersByFirstName(@RequestParam String firstName) {
        return customerService.findByFirstName(firstName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer craeteCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PostMapping(path = "{id}")
    public Customer upateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        return customerService.update(id, customer);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.delete(id);
    }
}
