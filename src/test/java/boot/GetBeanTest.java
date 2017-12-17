package boot;

import boot.service.GetBeanServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by huishen on 17/11/17.
 *
 */

@WebAppConfiguration
public class GetBeanTest extends BaseTest {

    @Autowired
    private GetBeanServiceImpl getBeanServiceImpl;

    /**
     * 测试：
     * 一个类只能被注册一次，不能被注册2次
     * 但是如果2个类都继承同一个接口，通过getBean(接口.class), 会报错
     */
    @Test
    public void getBeanTest() {
        getBeanServiceImpl.getBean();
    }

}
