package boot.order;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by huishen on 16/11/1.
 *
 */
@Component
public class OrderService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void createOrder(int orderId){
        System.out.println("Order Created");
        OrderEvent event = new OrderEvent(this, orderId);
        System.out.println("Publishing Order Event");
        publisher.publishEvent(event);
    }

}
