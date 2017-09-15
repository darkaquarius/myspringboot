package boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huishen on 17/9/7.
 */

@RestController
@RequestMapping("/redisController")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/redistest", method = RequestMethod.GET)
    public void test1() {

        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForHash().put("com.loan.muyi", String.valueOf(i), "12356");
            System.out.println(i);

        }
    }

}
