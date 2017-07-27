package boot.util;

import boot.BaseTest;
import boot.domain.User;
import org.junit.Test;

/**
 * Created by huishen on 17/7/27.
 *
 */
public class ObjectMapperUtilTest extends BaseTest {

    @Test
    public void writeValueAsString() {
        User user = User.builder()
            .id(1)
            .name("zhangsan")
            .build();
        String s = ObjectMapperUtil.writeValueAsString(user);
        System.out.println(s);
    }

}