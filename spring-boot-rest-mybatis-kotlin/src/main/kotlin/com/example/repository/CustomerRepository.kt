package com.example.repository

import com.example.domain.Customer
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectKey
import org.apache.ibatis.annotations.Update

@Mapper
interface CustomerRepository {

	@Select("SELECT * FROM customers ORDER BY id")
	fun findAll(): List<Customer>

	@Select("SELECT * FROM customers WHERE id = #{id}")
	fun findOne(@Param("id") id: Int): Customer

	@Insert("INSERT INTO customers(name, address) VALUES(#{name}, #{address})")
	@SelectKey(statement = arrayOf("call identity()"), keyProperty = "id", before = false, resultType = Int::class)
	fun insert(customer: Customer)

	@Update("UPDATE customers SET name = #{name}, address = #{address} WHERE id = #{id}")
	fun update(customer: Customer)

	@Delete("DELETE FROM customers WHERE id = #{id}")
	fun delete(@Param("id") id: Int);

	@Select("""
	    SELECT
	      *
	    FROM
	      customers
	    WHERE
	      name LIKE '%${'$'}{name}%'
	    ORDER BY id
	""")
	fun findByName(@Param("name") name: String): List<Customer>

	@Delete("DELETE FROM customers")
	fun deleteAll()
}