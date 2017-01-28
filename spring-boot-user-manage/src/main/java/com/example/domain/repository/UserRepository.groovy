package com.example.domain.repository

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectKey
import org.apache.ibatis.annotations.Update
import org.springframework.stereotype.Repository

import com.example.domain.model.User

@Repository
@Mapper
interface UserRepository {

    @Insert('''
      INSERT INTO users(
        login_id, encoded_password, first_name, last_name, mail_address)
      VALUES(
        #{loginId}, #{encodedPassword}, #{firstName}, #{lastName}, #{mailAddress})
    ''')
    @SelectKey(statement = "call identity()", keyProperty = "id", before = false, resultType = int.class)
    void create(User user)

    @Update('''
      UPDATE users
        SET
          login_id = #{loginId},
          encoded_password = #{encodedPassword},
          first_name = #{firstName},
          last_name = #{lastName},
          mail_address = #{mailAddress}
        WHERE
          id = #{id}
    ''')
    void update(User user)

    @Update('''
      UPDATE users
        SET
          login_id = #{loginId},
          first_name = #{firstName},
          last_name = #{lastName},
          mail_address = #{mailAddress}
        WHERE
          id = #{id}
    ''')
    void updateWithoutPassword(User user)

    @Select('''
      SELECT * FROM users WHERE id = #{id}
    ''')
    User find(int id)

    @Select('''
      SELECT * FROM users ORDER BY login_id
    ''')
    List<User> findAll()

    @Delete('DELETE FROM users WHERE id = #{id}')
    void delete(int id)
}
