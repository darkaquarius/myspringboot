package boot.order;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Created by huishen on 16/11/1.
 *
 */
@Data
public class OrderEvent extends ApplicationEvent{
    private int orderId;

    public OrderEvent(Object source, int id){
        super(source);
        this.orderId = id;
        System.out.println("Order Event Created!!");
    }
}
