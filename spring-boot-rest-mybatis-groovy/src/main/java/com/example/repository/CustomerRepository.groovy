package com.example.repository

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectKey
import org.apache.ibatis.annotations.Update
import org.springframework.stereotype.Repository

import com.example.domain.Customer

@Repository
@Mapper
public interface CustomerRepository {

    @Select("SELECT * FROM customers ORDER BY id")
    public List<Customer> findAll()

    @Select("SELECT * FROM customers WHERE id = #{id}")
    public Customer findOne(@Param("id") Integer id)

    @Insert("INSERT INTO customers(first_name, last_name, address) VALUES(#{firstName}, #{lastName}, #{address})")
    @SelectKey(statement = "call identity()", keyProperty = "id", before = false, resultType = int.class)
    public void insert(Customer customer)

    @Update("UPDATE customers SET first_name = #{firstName}, last_name = #{lastName}, address = #{address} WHERE id = #{id}")
    public void update(Customer customer)

    @Delete("DELETE FROM customers WHERE id = #{id}")
    public void delete(@Param("id") Integer id)

    @Select('''
      SELECT
        *
      FROM
        customers
      WHERE
        first_name LIKE '%${firstName}%'
      ORDER BY id''')
    public List<Customer> findByFirstName(@Param("firstName") String firstName)

    @Delete("DELETE FROM customers")
    public void deleteAll()
}
