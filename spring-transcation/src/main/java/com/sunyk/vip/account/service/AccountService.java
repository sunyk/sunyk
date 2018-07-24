package com.sunyk.vip.account.service;

import com.sunyk.vip.account.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by sunyang on 2018/7/23 23:01
 * For me:One handred lines of code every day,make myself stronger.
 */
@Service("accountService")
public class AccountService {
    @Autowired
    AccountDao accountDao;

    /**
     * 转账逻辑
     * @param out 由谁转出
     * @param in 转给谁
     * @param money 转多少钱
     * @throws Exception
     */
    public void transfer(final String out,final String in,final Double money) throws Exception{
        Double account = accountDao.selectAccount(out);
        System.out.println(account);
        if (account.compareTo(money) < 0){
            throw new Exception("余额不足");
        }

        int outCount = accountDao.updateForOut(out,money);
        int inCount = accountDao.updateForIn(in,money);
        if (outCount == 0 || inCount == 0){
            throw new Exception("转账失败");
        }
    }
}
