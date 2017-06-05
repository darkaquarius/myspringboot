package boot;

import boot.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by huishen on 16/9/24.
 *
 */

@EnableAutoConfiguration     //启动内置自动配置
@Configuration     //表示Application作为spring的配置文件存在
@SpringBootApplication
@Import(SpringConfig.class)
public class Application {

    public static void main(String args[]){
        SpringApplication.run(Application.class);
    }

    // //@Profile在不同环境下使用不同配置
    // public static void main(String[] args) {
    //     AnnotationConfigApplicationContext context =
    //         new AnnotationConfigApplicationContext(SpringConfig.class);
    //     User user = (User) context.getBean(User.class);
    //     System.out.println(user);
    // }

}
