package boot.mapper;

import boot.BaseTest;
import boot.consts.Jobs;
import boot.domain.User;
import org.junit.Test;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**c
 * Created by huishen on 16/10/10.
 */
@Transactional
@TestExecutionListeners({TransactionalTestExecutionListener.class})
public class UserMapperTest extends BaseTest {
    @Resource
    private UserMapper userMapper;

    /**
     * @Rollback  @Commit  可以放在方法或者类上
     *
     * 还有@BeforeTransaction    @AfterTransaction
     *
     */
    // @Rollback
    // @Commit
    @Test
    public void testAddUser(){
        User user = new User();
        user.setName("asan1");
        user.setPassword("123");
        user.setAge(21);
        user.setSex(false);
        user.setJob(Jobs.BOSS);
        user.setRemark("asanremark");
        userMapper.addUser(user);
    }

}
