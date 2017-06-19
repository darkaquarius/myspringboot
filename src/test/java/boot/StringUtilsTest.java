package boot;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * Created by huishen on 17/6/19.
 *
 */
public class StringUtilsTest {

    @Test
    public void testCommaDelimitedListToStringArray() {
        String str = "zhangsan,lisi,wangwu";
        Set<String> set = StringUtils.commaDelimitedListToSet(str);
    }

}
