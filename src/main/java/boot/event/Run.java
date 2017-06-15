package boot.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by huishen on 17/6/5.
 *
 */

@SpringBootApplication
public class Run {

    // //event
    // public static void main(String[] args) {
    //     AnnotationConfigApplicationContext context =
    //         new AnnotationConfigApplicationContext(EventConfig.class);
    //     DemoPublisher publisher = context.getBean(DemoPublisher.class);
    //     // publisher.publish("hello application event");
    // }

    public static void main(String [] args) {
        SpringApplication application = new SpringApplication(EventConfig.class);
        application.addListeners(new DemoListener());
        application.addListeners(new MyApplicationStartedEventListener());
        application.run(args);
    }

}
