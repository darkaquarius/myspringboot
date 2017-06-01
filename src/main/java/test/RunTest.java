package test;

import boot.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by huishen on 17/6/1.
 *
 */
public class RunTest {

    //run什么时候能不停
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        System.out.println("wait");
    }

}
