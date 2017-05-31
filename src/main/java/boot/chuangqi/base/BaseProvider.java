package boot.chuangqi.base;

import com.google.common.collect.ImmutableSet;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * @author libo.ding
 * @since 2016-08-15
 */
public class BaseProvider<T> extends TypeReference<T> {

    private final static Logger logger = LoggerFactory.getLogger(BaseProvider.class);

    protected final static Set<String> EXCLUDE_PROPERTIES = ImmutableSet.of("class", "createTime", "updateTime");

    protected EntityMetaBean entityMeta;

    public BaseProvider() {
        if (this.getRawType() instanceof Class) {
            this.entityMeta = EntityHelper.getInstance().getEntityMeta((Class) this.getRawType());
        }
    }

    public String selectByPrimaryKey(T record) {
        EntityMetaBean bean = EntityHelper.getInstance().getEntityMeta(record.getClass());
        SQL sql = new SQL();
        sql.SELECT(bean.getColumns()).FROM(bean.getTableName());
        wherePrimaryKeys(sql, bean.getPrimaryKeySet());
        return sql.toString();
    }

    public String insertSelective(T record) {
        EntityMetaBean bean = EntityHelper.getInstance().getEntityMeta(record.getClass());
        SQL sql = new SQL();
        sql.INSERT_INTO(bean.getTableName());
        insertUpdate(sql, record, bean, true, false);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(T record) {
        return update(record, false);
    }

    public String updateByPrimaryKey(T record) {
        return update(record, true);
    }

    private String update(T record, boolean needNull) {
        EntityMetaBean bean = EntityHelper.getInstance().getEntityMeta(record.getClass());
        SQL sql = new SQL();
        sql.UPDATE(bean.getTableName());
        insertUpdate(sql, record, bean, false, needNull);
        wherePrimaryKeys(sql, bean.getPrimaryKeySet());
        return sql.toString();
    }

    public String selectByEntity(T record) {
        EntityMetaBean bean = EntityHelper.getInstance().getEntityMeta(record.getClass());
        SQL sql = new SQL();
        sql.SELECT(bean.getColumns()).FROM(bean.getTableName());
        try {
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(record.getClass());
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String property = propertyDescriptor.getName();
                if (EXCLUDE_PROPERTIES.contains(property))
                    continue;
                Object value = propertyDescriptor.getReadMethod().invoke(record);

                if (value == null)
                    continue;
                String column = bean.getPropertyColumnMap().get(property);
                String val = "#{".concat(property).concat("}");
                sql.AND().WHERE(column.concat("=").concat(val));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.warn("selectByEntity exception", e);
        }

        return sql.toString();
    }

    protected void insertUpdate(SQL sql, T record, EntityMetaBean bean, boolean isInsert, boolean needNull) {
        try {
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(record.getClass());
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String property = propertyDescriptor.getName();
                if (EXCLUDE_PROPERTIES.contains(property) ||
                    (!isInsert && bean.getPrimaryKeySet().stream().filter(key -> key.getProperty().equals(property)).findAny().isPresent()))
                    continue;
                Object val = propertyDescriptor.getReadMethod().invoke(record);

                if (val == null && !needNull)
                    continue;
                String column = bean.getPropertyColumnMap().get(property);
                String value = "#{".concat(property).concat("}");
                if (isInsert)
                    sql.VALUES(column, value);
                else
                    sql.SET(column.concat(" = ").concat(value));
            }
        } catch (BeansException | InvocationTargetException | IllegalAccessException e) {
            logger.warn("insertUpdate exception", e);
        }
    }

    private void wherePrimaryKeys(SQL sql, Set<EntityMetaBean.PrimaryKeyBean> primaryKeyBeanSet) {
        primaryKeyBeanSet.forEach(keyBean ->
            sql.WHERE(keyBean.getColumn().concat(" = #{").concat(keyBean.getProperty()).concat("}")));
    }
}
