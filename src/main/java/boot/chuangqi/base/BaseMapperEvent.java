package boot.chuangqi.base;

import org.springframework.context.ApplicationEvent;

/**
 * @author libo.ding
 * @since 2016-08-23
 */
public class BaseMapperEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BaseMapperEvent(Object source) {
        super(source);
    }
}
