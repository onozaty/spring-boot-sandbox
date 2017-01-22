package com.example

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.SelectKey
import org.springframework.stereotype.Repository

import com.example.typehandler.JsonTypeHandler


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
    public void insert(Information data)
}

class JsonDataTypeHandler extends JsonTypeHandler<JsonData> {

    JsonDataTypeHandler() {
        super(JsonData.class)
    }
}

