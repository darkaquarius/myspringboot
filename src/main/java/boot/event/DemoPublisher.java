package boot.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by huishen on 17/6/4.
 *
 */
@Component
public class DemoPublisher {

    @Autowired
    private ApplicationContext context;

    public void publish(String msg) {
        context.publishEvent(new DemoEvent(this, msg));
    }

}
