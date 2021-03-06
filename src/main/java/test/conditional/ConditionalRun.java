package test.conditional;

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
public class ConditionalRun {

    //condition
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(ConditionConfig.class);

        ListService listService = context.getBean(ListService.class);
        String listCmd = listService.showListCmd();
        System.out.println(listCmd);
    }

}
