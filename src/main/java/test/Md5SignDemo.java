package test;

import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 类MD5Sign.java的实现描述：MD5签名和验签
 *
 * Created by huishen on 17/10/20.
 */
public class Md5SignDemo {

    /**
     * 方法描述:将字符串MD5加码 生成32位md5码
     *
     * @author leon 2016年10月10日 下午3:02:30
     * @param inStr
     * @return
     */
    public static String md5(String inStr) {
        try {
            return DigestUtils.md5DigestAsHex(inStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }
    }

    /**
     * 方法描述:签名字符串
     *
     * @author leon 2016年10月10日 下午2:54:47
     * @param params 需要签名的参数
     * @param appSecret 签名密钥
     * @return
     */
    public static String sign(HashMap<String, String> params, String appSecret) {
        StringBuilder valueSb = new StringBuilder();
        params.put("appSecret", appSecret);
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<>(params);
        Set<Map.Entry<String, String>> entrys = sortParams.entrySet();
        // 遍历排序的字典,并拼接value1+value2......格式
        for (Map.Entry<String, String> entry : entrys) {
            valueSb.append(entry.getValue());
        }
        params.remove("appSecret");
        return md5(valueSb.toString());
    }

    /**
     * 方法描述:验证签名
     *
     * @author leon 2016年10月10日 下午2:31:23
     * @param appSecret 加密秘钥
     * @param request
     * @return
     * @throws Exception
     */
    public static boolean verify(String appSecret, HttpServletRequest request) throws Exception {

        String sign = request.getParameter("sign");
        if (sign == null) {
            throw new Exception(URLEncoder.encode("请求中没有带签名", "UTF-8"));
        }
        if (request.getParameter("timestamp") == null) {
            throw new Exception(URLEncoder.encode("请求中没有带时间戳", "UTF-8"));
        }

        HashMap<String, String> params = new HashMap<>();

        // 获取url参数
        @SuppressWarnings("unchecked")
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign")) {
                // 拼接参数值字符串并进行utf-8解码，防止中文乱码产生
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
            }
        }

        params.put("appSecret", appSecret);

        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<>(params);
        Set<Map.Entry<String, String>> entrys = sortParams.entrySet();

        // 遍历排序的字典,并拼接value1+value2......格式
        StringBuilder valueSb = new StringBuilder();
        for (Map.Entry<String, String> entry : entrys) {
            valueSb.append(entry.getValue());
        }

        String mysign = md5(valueSb.toString());
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }

    }

}
