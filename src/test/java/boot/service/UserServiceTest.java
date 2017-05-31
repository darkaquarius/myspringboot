package boot.service;

import boot.BaseTest;
import boot.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huishen on 16/10/10.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends BaseTest{

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser(){
        User user= new User();
        user.setName("asan");
        user.setPassword("123");
        user.setAge(21);
        user.setSex(true);
        //TODO boolean类型怎么加入数据库
//        user.setJob(Jobs.TEACHER);
//        user.setRemark("asansremark");
        userService.addUser(user);
    }

    @Test
    public void testGetUserById(){
        User user = userService.getUserById(6);
        System.out.println(user);
    }

}
