package com.external.portfolio.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MapTypeHandler extends BaseTypeHandler<Map<String, Double>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Double> parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public Map<String, Double> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toMap(rs.getString(columnName));
    }

    @Override
    public Map<String, Double> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toMap(rs.getString(columnIndex));
    }

    @Override
    public Map<String, Double> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toMap(cs.getString(columnIndex));
    }

    private String toJson(Map<String, Double> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert map to JSON string", e);
        }
    }

    private Map<String, Double> toMap(String json) {
        if (json == null || json.isEmpty()) return new HashMap<>();
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON string to map", e);
        }
    }
}