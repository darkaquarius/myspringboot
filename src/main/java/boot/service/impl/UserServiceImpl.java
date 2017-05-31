package boot.service.impl;

import boot.domain.User;
import boot.mapper.UserMapper;
import boot.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 16/10/8.
 *
 */
@Service
public class UserServiceImpl implements UserService {


//    @Resource(name = "redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, User> valueOperations;

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user){
        return userMapper.addUser(user);
    }

    @Override
    public int updateUser(User user){
        int row = userMapper.updateUser(user);
        if(0 == row)
            throw new RuntimeException("update error");
        return row;
    }

    @Override
    public User getUserById(int id){
       ValueOperations<String, User> valueOperations = redisTemplate.opsForValue();
        User user = valueOperations.get(String.valueOf(id));
        if(null == user){
            user = userMapper.selectByPrimaryKey(id);
            if(null == user){
                throw new RuntimeException("do not find this user in the db!");
            }
            valueOperations.set(String.valueOf(id), user, 30, TimeUnit.MINUTES);
        }
        return user;
    }

    public void addHashToRedis(User user){
        //chuangqi包
        // Map<String, String> userMap = EntityUtils.beanToHash(user);
        Map<String, String> userMap = new HashMap<>();
        try {
            BeanUtils.populate(user, userMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll("2", userMap);
    }

    public String getHashFromRedis(int id){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return (String)hashOperations.get(String.valueOf(id), "name");
    }

}
