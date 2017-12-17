package boot.service;

import boot.service.typetest.CustomInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by huishen on 17/11/17.
 *
 */

@Service
public class GetBeanServiceImpl implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void getBean() {
        CustomInterface bean = applicationContext.getBean(CustomInterface.class);
        System.out.println(bean);
    }

}
