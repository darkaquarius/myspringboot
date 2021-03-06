package test.event;

import org.springframework.context.ApplicationListener;

/**
 * Created by huishen on 17/6/4.
 *
 */
// @Component
public class DemoListener implements ApplicationListener<DemoEvent> {

    @Override
    public void onApplicationEvent(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("我收到事件:".concat(msg));
    }
}
