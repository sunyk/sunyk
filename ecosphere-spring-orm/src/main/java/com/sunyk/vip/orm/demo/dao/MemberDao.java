package com.sunyk.vip.orm.demo.dao;


import com.sunyk.vip.orm.framework.BaseDaoSupport;
import org.springframework.stereotype.Repository;


import javax.annotation.Resource;
import javax.sql.DataSource;

@Repository
public class MemberDao extends BaseDaoSupport {

	@Resource(name = "dataSource")
	protected void setDataSource(DataSource dataSource){
		System.out.println(dataSource);
	}

}
