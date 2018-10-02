package com.sunyk.springbootjdbc.repository;

import com.sunyk.springbootjdbc.domian.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * {@link com.sunyk.springbootjdbc.domian.User}
 * Create by sunyang on 2018/9/24 17:45
 * For me:One handred lines of code every day,make myself stronger.
 */
@Repository
public class UserRepository {

   private final DataSource dataSource;
   private final DataSource masterDataSource;
   private final DataSource slaveDataSource;

   private final JdbcTemplate jdbcTemplate;

    /**
     * api方式控制事务
     */
   private final PlatformTransactionManager platformTransactionManager;


    public UserRepository(DataSource dataSource,
                          @Qualifier("masterDataSource") DataSource masterDataSource,
                          @Qualifier("slaveDataSource") DataSource slaveDataSource, JdbcTemplate jdbcTemplate, PlatformTransactionManager platformTransactionManager) {
        this.dataSource = dataSource;
        this.masterDataSource = masterDataSource;
        this.slaveDataSource = slaveDataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Transactional
    public boolean save(User user) {
        boolean success = false;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getName());
                return preparedStatement;
            }
        }, keyHolder);
        int uid = keyHolder.getKey().intValue();
        String context = "try it transaction,one by one";
        success = saveUserLog(uid, context);
        return success;
    }
/*  change before or after;
    public boolean save(User user) {
        boolean success = false;
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        success = jdbcTemplate.execute("INSERT INTO users (name) VALUES (?)", new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1, user.getName());
                return preparedStatement.executeUpdate() > 0;
            }
        });
        try {
            platformTransactionManager.commit(transactionStatus);
            throw new Exception("就是让回滚事务");
        }catch (Exception e){
            platformTransactionManager.rollback(transactionStatus);
        }


        return success;
    }*/

    public boolean jdbcSave(User user){
        Connection connection = null;
        boolean success = false;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name) VALUES (?)");
            preparedStatement.setString(1, user.getName());
            success = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.commit();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    @Transactional
    public boolean saveUserLog(int uid,String context){
        boolean success = false;
        success = jdbcTemplate.execute("INSERT INTO users_log (uid,study_context) VALUES (?,?)", new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setInt(1, uid);
                preparedStatement.setString(2, context);
                return preparedStatement.executeUpdate() > 0;
            }
        });

        return success;
    }


}
