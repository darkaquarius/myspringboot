package boot.mapper;

import boot.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**c
 * Created by huishen on 16/10/10.
 */
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setName("asan");
        user.setPassword("123");
        user.setAge(21);
        user.setRemark("asanremark");
        userMapper.addUser(user);
    }

}
