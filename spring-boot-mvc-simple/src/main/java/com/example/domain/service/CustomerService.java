package com.example.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.Customer;
import com.example.domain.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findOne(Integer id) {
        return customerRepository.findOne(id);
    }

    public Customer create(Customer customer) {
        customerRepository.insert(customer);
        return customer;
    }

    public Customer update(Customer customer) {
        customerRepository.update(customer);
        return customer;
    }

    public void delete(Integer id) {
        customerRepository.delete(id);
    }
}
