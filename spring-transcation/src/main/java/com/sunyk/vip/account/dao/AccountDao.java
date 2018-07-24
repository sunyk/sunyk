package com.sunyk.vip.account.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Create by sunyang on 2018/7/23 22:58
 * For me:One handred lines of code every day,make myself stronger.
 */
@Repository
public class AccountDao {

    private JdbcTemplate template;

    @Resource(name="dataSource")
    protected void setDataSource(DataSource dataSource){
        template = new JdbcTemplate(dataSource);
    }

    public Double selectAccount(String name){
        String sql = "select money from t_account where name = ?";
        return template.queryForObject(sql,new Object[]{name},Double.class);
    }

    public int updateForOut(String out,Double money) throws Exception{
        String sql = "update t_account set money = money-? where name = ?";
        return template.update(sql,money,out);
    }

    public int  updateForIn(String in,Double money) throws Exception{
        String sql = "update t_account set money = money+? where name = ?";
        return template.update(sql,money,in);
    }
}
