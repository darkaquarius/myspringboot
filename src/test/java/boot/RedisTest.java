package boot;

import boot.chuangqi.base.EntityUtils;
import boot.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/2/22.
 *
 */

@WebAppConfiguration
public class RedisTest extends BaseTest{

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testOpsForValue(){
        ValueOperations<String, User> valueOperations = redisTemplate.opsForValue();

        User user = User.builder().id(1).name("zhangsan").build();

        valueOperations.set("user1", user, 30, TimeUnit.MINUTES);
        User user1 = valueOperations.get("user1");
        System.out.println(user1);
    }

    @Test
    public void testSetOperations() {
        SetOperations<String, User> setOperations = redisTemplate.opsForSet();

        List<User> users = Arrays.asList(
            User.builder().id(1).name("zhangsan").build(),
            User.builder().id(2).name("lisi").build());

        setOperations.add("users", users.toArray(new User[users.size()]));
        redisTemplate.expire("users", 30, TimeUnit.MINUTES);

        Set<User> userSet = setOperations.members("users");

        for (User user : userSet) {
            System.out.println(user);
        }

    }

    @Test
    public void test() {
        HashOperations hashOperations = redisTemplate.opsForHash();

        User user = User.builder().id(1).name("zhangsan").build();

        Map<String, String> map = EntityUtils.beanToHash(user);

        hashOperations.putAll("user:hash", map);
        redisTemplate.expire("user:hash", 30, TimeUnit.MINUTES);

        List<String> keys = Arrays.asList("id", "name");
        List list = hashOperations.multiGet("user:hash", keys);
    }

    // 插入1000条数据
    @Test
    public void test1() {
        for (int i = 0; i < 1000; i++) {
            stringRedisTemplate.opsForHash().put("com.loan.muyi", "123456789012", "12356");
            System.out.println(i);

        }
    }

    // zset
    @Test
    public void test2() {
        ZSetOperations zSetOperations = stringRedisTemplate.opsForZSet();
        Set set = zSetOperations.rangeWithScores("test-zset", 0, -1);
    }

    @Test
    public void test3() {
        // todo redisTempdate pipeline
        // stringRedisTemplate.
    }

    @Test
    public void test4() {
        BoundSetOperations<String, Long> operations = redisTemplate.boundSetOps("mobilePhone");

        long mobilePhone = 10000000000L;

        for (int i = 0; i < 100; i++) {
            operations.add(mobilePhone);
            mobilePhone += 1;
        }

        System.out.println("done");
    }

}
