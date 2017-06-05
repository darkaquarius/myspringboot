package boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huishen on 16/9/24.
 *
 */

@EnableAutoConfiguration     //启动内置自动配置
@Configuration     //表示Application作为spring的配置文件存在
@SpringBootApplication
// @Import(SpringConfig.class)
public class Application {

    // public static void main(String args[]){
    //     SpringApplication.run(Application.class);
    // }

    // //@Profile在不同环境下使用不同配置
    // public static void main(String[] args) {
    //     AnnotationConfigApplicationContext context =
    //         new AnnotationConfigApplicationContext(SpringConfig.class);
    //     User user = (User) context.getBean(User.class);
    //     System.out.println(user);
    // }

    // //event
    // public static void main(String[] args) {
    //     AnnotationConfigApplicationContext context =
    //         new AnnotationConfigApplicationContext(EventConfig.class);
    //     DemoPublisher publisher = context.getBean(DemoPublisher.class);
    //     publisher.publish("hello application event");
    // }

    // //aware
    // public static void main(String[] args) {
    //     AnnotationConfigApplicationContext context =
    //         new AnnotationConfigApplicationContext(AwareConfig.class);
    //     AwareService bean = context.getBean(AwareService.class);
    //     bean.outputResult();
    // }

}
