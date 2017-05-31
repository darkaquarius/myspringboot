package boot.chuangqi.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author libo.ding
 * @since 2016-08-17
 */
public class EntityHelper {
    private static EntityHelper ourInstance = new EntityHelper();
    private List<String> entityBasePackages;
    private List<String> mapperBasePackages;
    private Map<Class<?>, Class<?>> entityMapperMap;
    private Map<Class<?>, EntityMetaBean> map = new HashMap<>();

    public Collection<EntityMetaBean> getEntityMetas() {
        return map.values();
    }

    public Map<Class<?>, Class<?>> getEntityMapperMap() {
        return entityMapperMap;
    }

    public void setEntityMapperMap(Map<Class<?>, Class<?>> entityMapperMap) {
        this.entityMapperMap = entityMapperMap;
    }

    public EntityMetaBean getEntityMeta(Class<?> clazz) {
        return map.get(clazz);
    }

    public void addEntityMeta(Class<?> clazz, EntityMetaBean bean) {
        map.put(clazz, bean);
    }

    public List<String> getEntityBasePackages() {
        return entityBasePackages;
    }

    public void setEntityBasePackages(List<String> entityBasePackages) {
        this.entityBasePackages = entityBasePackages;
    }

    public List<String> getMapperBasePackages() {
        return mapperBasePackages;
    }

    public void setMapperBasePackages(List<String> mapperBasePackages) {
        this.mapperBasePackages = mapperBasePackages;
    }

    public static EntityHelper getInstance() {
        return ourInstance;
    }

    private EntityHelper() {
    }
}
