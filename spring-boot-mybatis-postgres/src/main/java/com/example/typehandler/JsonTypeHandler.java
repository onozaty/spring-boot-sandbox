package com.example.typehandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonTypeHandler<T> extends BaseTypeHandler<T> {

    private final ObjectMapper mapper = new ObjectMapper();

    private final Class<T> jsonType;

    public JsonTypeHandler(Class<T> jsonType) {
        this.jsonType = jsonType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
            throws SQLException {

        try {
            ps.setObject(i, mapper.writeValueAsString(parameter), Types.OTHER);
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {

        return toTargetObject(rs.getObject(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return toTargetObject(rs.getObject(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        return toTargetObject(cs.getObject(columnIndex));
    }

    private T toTargetObject(Object value) throws SQLException {

        if (value == null) {
            return null;
        }

        try {
            return mapper.readValue(value.toString(), jsonType);
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

}
