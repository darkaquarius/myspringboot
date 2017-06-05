package boot.event;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huishen on 17/6/5.
 *
 */

@EnableAutoConfiguration     //启动内置自动配置
@Configuration     //表示Application作为spring的配置文件存在
@SpringBootApplication
public class Run {

    //event
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(EventConfig.class);
        DemoPublisher publisher = context.getBean(DemoPublisher.class);
        publisher.publish("hello application event");
    }

}
