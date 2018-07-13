package com.pattern.template;

import java.sql.ResultSet;

/**
 * Create by sunyang on 2018/6/24 12:44
 * For me:One handred lines of code every day,make myself stronger.
 */
public interface RowMapper<T> {

    public T mapRow(ResultSet rs, int rowNum) throws Exception;
}
