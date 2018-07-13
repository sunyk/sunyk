package com.pattern.template;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by sunyang on 2018/6/24 11:34
 * For me:One handred lines of code every day,make myself stronger.
 */
public class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private PreparedStatement createPrepareStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    private Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    private ResultSet executeQuery(PreparedStatement pstmt,Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i,values[i]);
        }
        return pstmt.executeQuery();
    }

    private void closeStatement(Statement stmt) throws SQLException {
        stmt.close();
    }

    private void closeResult(ResultSet rs) throws SQLException {
        rs.close();
    }

    private void closeConnection (Connection conn) throws SQLException {
        conn.close();
    }

    private List<Object> parseResultSet(ResultSet rs, RowMapper rowMapper)throws Exception{
        List<Object> result = new ArrayList<Object>();
        int rowNum = 1;
        while (rs.next()){
            result.add(rowMapper.mapRow(rs, rowNum++));
        }
        return result;
    }

    public List<Object> executeQuery(String sql, RowMapper<?> rowMapper ,Object [] values) throws Exception {

        Connection  connection = this.getConnection();

        PreparedStatement pstmt = this.createPrepareStatement(connection,sql);

        ResultSet rs= this.executeQuery(pstmt, values);


        List<Object> result = this.parseResultSet(rs,rowMapper);

        closeResult(rs);
        closeStatement(pstmt);
        connection.close();
        return result;
    }


//    public abstract Object processResult(ResultSet resultSet, int rowNum) throws SQLException;

}
