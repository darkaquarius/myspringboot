package boot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by huishen on 17/7/27.
 *
 */

@Component
public class ObjectMapperUtil {

    private static final Logger logger = LoggerFactory.getLogger(ObjectMapperUtil.class);

    private static ObjectMapper om;

    @Autowired
    @Qualifier("getObjectMapper")
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        ObjectMapperUtil.om = objectMapper;
    }

    public static ObjectMapper getOm() {
        return om;
    }

    public static <T> T readValue(String content, TypeReference<T> tf) {

        T ret = null;
        try {
            ret = om.readValue(content, tf);
        } catch (IOException e) {
            logger.error("object-mapper-readValue-exception:content:{}", content, e);
        }
        return ret;
    }

    public static <T> T readValue(String content, Class<T> valueType) {

        T ret = null;
        try {
            ret = om.readValue(content, valueType);
        } catch (IOException e) {
            logger.error("object-mapper-readValue-exception:content:{}", content, e);
        }
        return ret;
    }

    public static <T> T readValue(String content, JavaType tf) {

        T ret = null;
        try {
            ret = om.readValue(content, tf);
        } catch (IOException e) {
            logger.error("object-mapper-readValue-exception:content:{}", content, e);
        }
        return ret;
    }

    public static String writeValueAsString(Object object){
        String ret = null;
        try {
            ret = om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("object-mapper-writeValueAsString-exception:object:{}",object, e);
        }
        return ret;
    }

}
