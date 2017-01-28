package com.example

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectKey
import org.apache.ibatis.annotations.Update
import org.springframework.stereotype.Repository

import com.example.typehandler.IntegerListTypeHandler
import com.example.typehandler.StringListTypeHandler
import com.example.typehandler.JsonTypeHandler
import com.fasterxml.jackson.annotation.PropertyAccessor


@Repository
@Mapper
interface InformationRepository {

    @Insert('''
       INSERT INTO informations(text, int_array, text_array, json)
         VALUES(
           #{text},
           #{intArray, typeHandler=com.example.typehandler.IntegerListTypeHandler},
           #{textArray, typeHandler=com.example.typehandler.StringListTypeHandler},
           #{json, typeHandler=com.example.JsonDataTypeHandler})
    ''')
    @SelectKey(
    statement = "SELECT currval('informations_id_seq')",
    keyProperty = "id", before = false, resultType = int.class)
    void insert(Information data)

    @Update('''
      UPDATE informations
        SET text = #{information.text},
            int_array = #{information.intArray, typeHandler=com.example.typehandler.IntegerListTypeHandler},
            text_array = #{information.textArray, typeHandler=com.example.typehandler.StringListTypeHandler},
            json = #{information.json, typeHandler=com.example.JsonDataTypeHandler}
        WHERE id = #{information.id}
    ''')
    void update(@Param('information') Information information)

    @Results([
        @Result(property = 'intArray', column = 'int_array', typeHandler = IntegerListTypeHandler),
        @Result(property = 'textArray', column = 'text_array', typeHandler = StringListTypeHandler),
        @Result(property = 'json', column = 'json', typeHandler = JsonDataTypeHandler),
    ])
    @Select('SELECT * FROM informations WHERE id = #{id}')
    Information find(int id)
}

class JsonDataTypeHandler extends JsonTypeHandler<JsonData> {

    JsonDataTypeHandler() {
        super(JsonData.class)
    }
}

