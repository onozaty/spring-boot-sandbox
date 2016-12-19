package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
