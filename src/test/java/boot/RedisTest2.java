package boot;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huishen on 17/12/3.
 * 测试Spring data redis中使用pipeline  写数据  的几种情况：
 * testPipelineWrite1(): 没有使用pipeline
 * testPipelineWrite2(): pipeline  executePipelined()
 * testPipelineWrite3(): pipeline  execute(RedisCallback<T> action)
 * testPipelineWrite4(): pipeline execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline)
 *
 * 使用pineline，速度提高了2个数量级
 *
 *
 * 使用pipeline  读数据  的几种情况：
 *
 *
 * testMembers1
 *
 *
 */

@EnableAutoConfiguration
public class RedisTest2 extends BaseTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SetOperations setOperations;

    /**
     * 没有使用pipeline写数据
     */
    @Test
    public void testPipelineWrite1() {
        long s = System.currentTimeMillis();
        ValueOperations operations = redisTemplate.opsForValue();
        for (int i = 0; i < 100_000; i++) {
            operations.set("test" + i, i);
        }
        System.out.println("spend： " + (System.currentTimeMillis() - s));   // 11566
    }

    /**
     * pipeline 写数据
     * executePipelined()
     */
    @Test
    public void testPipelineWrite2() {
        long s = System.currentTimeMillis();
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (int i = 100_001; i < 200_000; i++) {
                connection.set(("test" + i).getBytes(), String.valueOf(i).getBytes());
            }
            return null;
        });
        System.out.println("spend： " + (System.currentTimeMillis() - s));   // 697
    }

    /**
     * pipeline 写数据
     * execute(RedisCallback<T> action)
     * 需要手动openPipeline()  和 closePipeline();
     */
    @Test
    public void testPipelineWrite3() {
        long s = System.currentTimeMillis();
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.openPipeline();
            for (int i = 200_001; i < 300_000; i++) {
                connection.set(("test" + i).getBytes(), String.valueOf(i).getBytes());
            }
            return connection.closePipeline();
        });

        System.out.println("spend： " + (System.currentTimeMillis() - s));  // 726
    }

    /**
     * pipeline 写数据
     * execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline)
     */
    @Test
    public void testPipelineWrite4() {
        long s = System.currentTimeMillis();
        redisTemplate.execute((RedisCallback<Object>)connection -> {
            for (int i = 300_001; i < 400_000; i++) {
                connection.set(("test" + i).getBytes(), String.valueOf(i).getBytes());
            }
            return null;
        }, false, true);
        System.out.println("spend： " + (System.currentTimeMillis() - s));  // 756
    }

    /**
     * pipeline 读数据
     * execute()
     * 可以自定义返回的类型
     */
    @Test
    public void testPipelineRead1() {
        Set<String> keys = redisTemplate.keys("idfa:new:".concat(LocalDate.now().toString()).concat("*"));

        long s = System.nanoTime();
        Map<String, Set<String>> execute = redisTemplate.execute((RedisCallback<Map<String, Set<String>>>) connection -> {
            Map<String, Set<String>> map = new HashMap<>();
            connection.openPipeline();
            for (String key : keys) {
                Set<String> members = setOperations.members(key);
                map.put(key, members);
            }
            connection.closePipeline();
            return map;
        });

        System.out.println(System.nanoTime() - s);
    }

    /**
     * pipeline 读数据
     * executePipelined()
     *
     * note: 1. lambda中必须返回null
     *       2. executePipelined()的返回值类型肯定是一个List，不是很灵活
     */
    @Test
    public void testPipelineRead2() {
        Set<String> keys = redisTemplate.keys("idfa:new:".concat(LocalDate.now().toString()).concat("*"));

        long s = System.nanoTime();
        List list = redisTemplate.executePipelined(
            (RedisCallback<Object>) connection -> {
                for (String key : keys) {
                    connection.sMembers(key.getBytes());
                }
                return null;
            }
        );
        System.out.println(System.nanoTime() - s);
    }


}
