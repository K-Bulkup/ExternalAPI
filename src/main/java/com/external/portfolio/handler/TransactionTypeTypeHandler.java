package com.external.portfolio.handler;

import com.external.portfolio.domain.TransactionCategory;
import com.external.portfolio.domain.TransactionType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(TransactionCategory.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class TransactionTypeTypeHandler extends BaseTypeHandler<TransactionType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TransactionType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toDbValue());
    }

    @Override
    public TransactionType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dbValue = rs.getString(columnName);
        return dbValue != null ? TransactionType.fromDbValue(dbValue) : null;
    }

    @Override
    public TransactionType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String dbValue = rs.getString(columnIndex);
        return dbValue != null ? TransactionType.fromDbValue(dbValue) : null;
    }

    @Override
    public TransactionType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String dbValue = cs.getString(columnIndex);
        return dbValue != null ? TransactionType.fromDbValue(dbValue) : null;
    }
}
