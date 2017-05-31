package boot.order;

import boot.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by huishen on 16/11/1.
 */
public class MainApp {
    public static void main(String args[]){
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //注解bean可以通过AnnotationConfigApplicationContext来获得applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        OrderService orderService = (OrderService)applicationContext.getBean("orderService");
        orderService.createOrder(001);
        applicationContext.close();
    }
}
