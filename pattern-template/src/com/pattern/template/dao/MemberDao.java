package com.pattern.template.dao;

import com.pattern.template.JdbcTemplate;
import com.pattern.template.RowMapper;
import com.pattern.template.entity.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Create by sunyang on 2018/6/24 11:45
 * For me:One handred lines of code every day,make myself stronger.
 */
public class MemberDao{

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(null);


    public List<Object> query() throws Exception {
        String sql = "select * from t_member";
        return jdbcTemplate.executeQuery(sql, new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws Exception {
                Member member = new Member();
                member.setUserName(rs.getString("userName"));
                member.setPassword(rs.getString("password"));
                member.setNickName(rs.getString("nickName"));
                return member;
            }
        },null);
    }

}
