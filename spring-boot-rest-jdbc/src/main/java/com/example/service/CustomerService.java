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
    private CustomerRepository userRepository;

    public List<Customer> findAll() {
        return userRepository.findAll();
    }

    public Customer findOne(Integer id) {
        return userRepository.findOne(id);
    }

    public Customer create(Customer customer) {
        return userRepository.save(customer);
    }

    public Customer update(Customer customer) {
        return userRepository.save(customer);
    }

    public void delete(Integer id) {
        userRepository.delete(id);
    }
}
