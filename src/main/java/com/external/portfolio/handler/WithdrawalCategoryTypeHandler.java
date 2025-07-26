package com.external.portfolio.handler;

import com.external.portfolio.domain.WithdrawalCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(WithdrawalCategory.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class WithdrawalCategoryTypeHandler extends BaseTypeHandler<WithdrawalCategory> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, WithdrawalCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toDbValue());
    }

    @Override
    public WithdrawalCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return WithdrawalCategory.fromDbValue(rs.getString(columnName));
    }

    @Override
    public WithdrawalCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return WithdrawalCategory.fromDbValue(rs.getString(columnIndex));
    }

    @Override
    public WithdrawalCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return WithdrawalCategory.fromDbValue(cs.getString(columnIndex));
    }
}
