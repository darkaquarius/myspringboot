package boot.chuangqi.base;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @author libo.ding
 * @since 2016-04-24
 */
public class EntityUtils {

    private final static Logger logger = LoggerFactory.getLogger(EntityUtils.class);

    private static List<String> EXCLUDE_PROPERTIES = Arrays.asList("createTime", "updateTime", "class");

    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unchecked")
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static BeanUtilsBean beanUtilsBean;

    static {
        beanUtilsBean = new BeanUtilsBean(new ConvertUtilsBean() {
            @Override
            public Object convert(String value, Class clazz) {
                if (clazz.isEnum()) {
                    return Enum.valueOf(clazz, value);
                } else if (clazz == Date.class) {
                    try {
                        return threadLocal.get().parse(value);
                    } catch (ParseException ex) {
                        logger.error("parse exception, value: {}, clazz: {}", value, clazz, ex);
                        return null;
                    }
                } else if (clazz == LocalDate.class) {
                    return LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
                } else if (clazz == LocalDateTime.class) {
                    return LocalDateTime.parse(value, TIME_FORMATTER);
                } else {
                    return super.convert(value, clazz);
                }
            }
        });
    }

    public static Map<String, String> beanToHash(Object obj) {
        try {
            Map<String, String> map = new HashMap<>();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                if (EXCLUDE_PROPERTIES.contains(property.getName()))
                    continue;
                Object valObj = property.getReadMethod().invoke(obj);
                if (valObj == null)
                    continue;

                String val;
                if (property.getPropertyType() == Date.class)
                    val = threadLocal.get().format(valObj);
                else if (property.getPropertyType() == LocalDate.class)
                    val = ((LocalDate) valObj).format(DateTimeFormatter.ISO_DATE);
                else if (property.getPropertyType() == LocalDateTime.class)
                    val = ((LocalDateTime) valObj).format(TIME_FORMATTER);
                else
                    val = String.valueOf(valObj);
                map.put(property.getName(), val);
            }
            return map;
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void hashToBean(Map<String, ?> map, Object obj) {
        try {
            beanUtilsBean.populate(obj, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T hashToBean(Map<String, ?> map, Class c) {
        if (map == null || map.size() == 0) {
            return null;
        }
        try {
            Object obj = c.newInstance();
            hashToBean(map, obj);
            return (T) obj;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
