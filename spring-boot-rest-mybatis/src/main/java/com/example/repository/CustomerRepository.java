package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.example.domain.Customer;

@Repository
@Mapper
public interface CustomerRepository {

    @Select("SELECT * FROM customers ORDER BY id")
    public List<Customer> findAll();

    @Select("SELECT * FROM customers WHERE id = #{id}")
    public Customer findOne(@Param("id") Integer id);

    @Insert("INSERT INTO customers(name, address) VALUES(#{name}, #{address})")
    @SelectKey(statement = "call identity()", keyProperty = "id", before = false, resultType = int.class)
    public void insert(Customer customer);

    @Update("UPDATE customers SET name = #{name}, address = #{address} WHERE id = #{id}")
    public void update(Customer customer);

    @Delete("DELETE FROM customers WHERE id = #{id}")
    public void delete(@Param("id") Integer id);

    @Select("SELECT * FROM customers WHERE name LIKE '%${name}%' ORDER BY id")
    public List<Customer> findByName(@Param("name") String name);

    @Delete("DELETE FROM customers")
    public void deleteAll();
}
