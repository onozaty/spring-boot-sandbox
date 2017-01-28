package com.example.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.User;
import com.example.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class UserService {

    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User find(int id) {
        return repository.find(id);
    }

    public void create(User user) {
        repository.create(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
