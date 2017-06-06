package boot;

import boot.config.SpringConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huishen on 17/6/6.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)          // SpringJUnit4ClassRunner
@ContextConfiguration(classes = {SpringConfig.class})    // ContextConfiguration
@ActiveProfiles(value = {"test"})      // 激活的profile
public class DemoTest {
}
