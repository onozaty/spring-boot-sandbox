package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<User> userRowMapper = (rs, i) -> {

        Integer id = rs.getInt("id");
        String name = rs.getString("name");

        return new User(id, name);
    };

    public List<User> findAll() {

        return jdbcTemplate.query("SELECT * FROM users ORDER BY id", userRowMapper);
    }
}
