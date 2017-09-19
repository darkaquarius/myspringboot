package boot;

import boot.config.SpringConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by huishen on 16/10/10.
 *
 */
@ContextConfiguration(classes = {SpringConfig.class})
// @SpringApplicationConfiguration(classes = SpringConfig.class)
public class BaseTest extends AbstractJUnit4SpringContextTests {

}