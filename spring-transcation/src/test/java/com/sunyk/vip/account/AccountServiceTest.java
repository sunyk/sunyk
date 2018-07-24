package com.sunyk.vip.account;

import com.sunyk.vip.account.service.AccountService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    public void testTransfer(){
        try {
            accountService.transfer("sunyk", "ff", 531D);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
