package boot;

import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by huishen on 16/10/25.
 *
 */
public class RestTemplateTest {

    @Test
    public void testGetForObject1(){
        RestTemplate restTemplate = new RestTemplate();
        Map resultMap = restTemplate.getForObject("http://120.26.233.25:8080/api/apps/1502", Map.class);
    }

    @Test
    public void testGetForObject2(){
        RestTemplate restTemplate = new RestTemplate();
        //返回的头中,"Content-Type →text/json;charset=UTF-8"
        String res = restTemplate.getForObject("http://114.55.114.54/getApps?app_id=1502", String.class);
        List list = new GsonBuilder().create().fromJson(res, List.class);
    }

    //还可以用数组的形式接收
    @Test
    public void testGetForObject3(){
        RestTemplate restTemplate = new RestTemplate();
        //返回的头中,"Content-Type →application/json;charset=UTF-8"
        List resultList = restTemplate.getForObject("http://dc.chuangqish.cn/launches/outer?app_id=&time_begin=2016-10-25&time_end=2016-10-25", List.class);
    }


}
