package test;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

/**
 * Created by huishen on 17/6/3.
 *
 */
public class SpELDemo {

    //普通字符串
    @Value("hello world")
    private String normal;

    //系统属性
    @Value("#{systemProperties['os.name']}")
    private String osName;

    //系统属性
    @Value("#{systemEnvironment['os.version']}")
    private String osName2;

    //表达式结果
    @Value("#{T(java.lang.Math).random() * 100.0}")
    private double randomNumber;

    //其它bean属性
    @Value("#{cachingUserServiceImpl.redisTemplate}")
    private String redisTemplate;

    //资源文件
    @Value("#{classpath:logback.xml}")
    // TODO: 17/6/3 ?????
    private Resource testFile;

    //url
    @Value("http://www.baidu.com")
    private Resource testUrl;

    //配置文件属性,这里注意是'$',不是'#'
    @Value("${redis.host}")
    private String bookName;

    @Value("#{'Hello world'.concat('!')}")
    private String helloWorld;

    @Value("#{'Hello world'.bytes}")
    private String helloWorldBytes;

    @Autowired
    private Environment environment;

    public void test() {
        try {
            System.out.println(normal);
            System.out.println(osName);
            System.out.println(osName2);
            System.out.println(randomNumber);
            System.out.println(redisTemplate);
            System.out.println(IOUtils.toString(testFile.getInputStream()));
            System.out.println(IOUtils.toString(testUrl.getInputStream()));
            System.out.println(bookName);
            //配置文件属性
            System.out.println(environment.getProperty("redis.port"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
