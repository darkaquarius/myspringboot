package boot.order;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by huishen on 16/11/1.
 */
@Data
public class OrderNotification {
    private int notificationId;

    @EventListener
    public void processOrderEvent(OrderEvent event) {
        System.out.println("OrderEvent for Order Id : " + event.getOrderId());
    }

}
