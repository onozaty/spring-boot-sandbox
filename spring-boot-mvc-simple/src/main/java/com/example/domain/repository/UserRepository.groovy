package com.example.domain.repository

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

import com.example.domain.model.Customer
import com.example.domain.model.User

@Repository
@Mapper
public interface UserRepository {

    @Select('SELECT * FROM users WHERE username = #{username}')
    public User findOne(@Param('username') String username)
}
