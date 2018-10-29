package com.sunyk.springbootrabbitmq;

import com.sunyk.springbootrabbitmq.provider.MyProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRabbitmqApplicationTests {

	@Autowired
	MyProvider myProvider;

	@Test
	public void contextLoads() {
		myProvider.send();
	}

}
