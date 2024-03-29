package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findOne(int id) {
        return customerRepository.findOne(id);
    }

    public List<Customer> findByFirstName(String firstName) {
        return customerRepository.findByFirstName(firstName);
    }

    public Customer create(Customer customer) {
        customerRepository.insert(customer);
        return customerRepository.findLast();
    }

    public Customer update(int id, Customer customer) {
        customerRepository.update(id, customer);
        return customer;
    }

    public void delete(Integer id) {
        customerRepository.delete(id);
    }
}
