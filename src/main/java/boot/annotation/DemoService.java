package boot.annotation;

import org.springframework.stereotype.Service;

/**
 * Created by huishen on 17/6/6.
 *
 */
@Service
public class DemoService {

    public void outputResult() {
        System.out.println("从组合注解配置中照样获得bean");
    }

}
