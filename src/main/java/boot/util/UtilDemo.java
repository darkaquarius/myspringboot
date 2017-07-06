package boot.util;

import boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * Created by huishen on 17/4/21.
 * 测试static代码块，@Autowired方法，@PostConstruct方法的先后顺序
 */
@Component
public class UtilDemo {

    private static JedisPoolConfig jedisPoolConfig;

    private static UserService userService;

    @Autowired
    private JedisPoolConfig jc;

    @Autowired
    private UserService us;

    @PostConstruct
    public void init() {
        jedisPoolConfig = jc;                               //step3
        userService = us;
    }

    @Autowired
    public void set(JedisPoolConfig jc, UserService us) {
        jedisPoolConfig = jc;                               //step2
        userService = us;
    }

    static {
        System.out.println(jedisPoolConfig);                //step1
        System.out.println(userService);

    }

    public static void func() {
        System.out.println(jedisPoolConfig);
        System.out.println(userService);
    }

}
