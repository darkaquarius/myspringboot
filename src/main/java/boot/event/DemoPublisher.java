package boot.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by huishen on 17/6/4.
 *
 */
@Component
//TODO ApplicationEventPublisherAware来实现
public class DemoPublisher implements ApplicationEventPublisherAware {

    // @Autowired
    // private ApplicationContext context;
    //
    // public void publish(String msg) {
    //     context.publishEvent(new DemoEvent(this, msg));
    // }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        applicationEventPublisher.publishEvent(new DemoEvent(this, "hello application event"));
    }

}
