package boot.service;

import boot.BaseTest;
import boot.consts.Jobs;
import boot.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huishen on 16/10/10.
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends BaseTest{

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser(){
        User user= new User();
        user.setName("wangwu2");
        user.setPassword("789");
        user.setAge(30);
        user.setSex(false);
        user.setJob(Jobs.EMPLOYEE);
        user.setRemark("asansremark");
        userService.addUser(user);
    }

    @Test
    public void testUpdateUser() {
        User user = User.builder().id(9).name("lisi2").password("10").age(31).sex(false).job(Jobs.EMPLOYEE).remark("update").build();
        userService.updateUser(user);
        System.out.println(user);
    }

    @Test
    public void testGetUserById() {
        User user = userService.getUserById(9);
        System.out.println(user);
    }

    @Test
    public void testDelAllUserCache() {
        userService.delAllUserCache();
    }

}
