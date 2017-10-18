package boot.service.impl;

/**
 * Created by huishen on 17/5/31.
 *
 */

import boot.domain.User;
import boot.mapper.UserMapper;
import boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CachingUserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    @CachePut(value = "user", key = "#result.id.toString()")
    public User addUser(User user) {
        int i = userMapper.addUser(user);
        // throw new RuntimeException("anyway");
        return user;
    }

    @Override
    @CachePut(value = "user", key = "#user.id.toString()")
    public User updateUser(User user) {
        int row = userMapper.updateUser(user);
        if(0 == row) {
            throw new RuntimeException("update error");
        }
        return user;
    }

    @Override
    @Cacheable(value = "user", key = "#id.toString()")
    public User getUserById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(null == user){
            throw new RuntimeException("do not find this user in the db!");
        }
        return user;
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void delAllUserCache() {

    }

}
