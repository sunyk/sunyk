import com.sunyk.vip.orm.demo.dao.MemberDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Create by sunyang on 2018/6/9 18:47
 * For me:One handred lines of code every day,make myself stronger.
 */

@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class Test {

    @Autowired
    MemberDao memberDao;


    @org.junit.Test
    public void test(){
        System.out.println(memberDao);
    }
}
