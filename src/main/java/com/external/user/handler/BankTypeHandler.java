package com.external.user.handler;

import com.external.user.domain.Bank;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Bank.class)
public class BankTypeHandler extends BaseTypeHandler<Bank> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Bank bank, JdbcType jdbcType) throws SQLException {
        ps.setString(i, bank.toDbValue()); // DB ENUM 문자열 저장
    }

    @Override
    public Bank getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Bank.fromDbValue(rs.getString(columnName));
    }

    @Override
    public Bank getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Bank.fromDbValue(rs.getString(columnIndex));
    }

    @Override
    public Bank getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Bank.fromDbValue(cs.getString(columnIndex));
    }
}
