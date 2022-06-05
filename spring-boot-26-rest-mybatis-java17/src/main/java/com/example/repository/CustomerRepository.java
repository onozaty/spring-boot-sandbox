package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.example.domain.Customer;

@Repository
@Mapper
public interface CustomerRepository {

    @Select("""
SELECT
  *
FROM
  customers
ORDER BY
id
    """)
    public List<Customer> findAll();

    @Select("""
SELECT
  *
FROM
  customers
WHERE
  id = #{id}
    """)
    public Customer findOne(@Param("id") int id);

    @Insert("""
INSERT INTO customers(
  first_name
  , last_name
  , address
)
VALUES(
  #{firstName}
  , #{lastName}
  , #{address}
)
""")
    public void insert(Customer customer);

    @Select("""
SELECT
  *
FROM
  customers
WHERE
  id = SCOPE_IDENTITY()
    """)
    public Customer findLast();

    @Update("""
UPDATE customers
  SET
    first_name = #{customer.firstName}
    , last_name = #{customer.lastName}
    , address = #{customer.address}
WHERE
  id = #{id}
""")
    public void update(@Param("id") int id, @Param("customer") Customer customer);

    @Delete("""
DELETE FROM customers
WHERE
  id = #{id}
    """)
    public void delete(@Param("id") Integer id);

    @Select("""
SELECT
  *
FROM
  customers
WHERE
  first_name LIKE '%' || #{firstName} || '%'
ORDER BY
  id
    """)
    public List<Customer> findByFirstName(@Param("firstName") String firstName);

    @Delete("""
DELETE FROM customers
    """)
    public void deleteAll();
}
