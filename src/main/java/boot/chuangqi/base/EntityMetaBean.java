package boot.chuangqi.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author libo.ding
 * @since 2016-08-17
 */
@Data
public class EntityMetaBean {
    private String tableName;
    private Class<?> entityClass;
    private Class<?> mapperClass;
    private Map<String, String> propertyColumnMap;
    private Set<PrimaryKeyBean> primaryKeySet;
    private String columns;

    public String getColumns() {
        if (this.columns != null)
            return this.columns;
        this.columns = propertyColumnMap.values().stream().collect(Collectors.joining(", "));
        return this.columns;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrimaryKeyBean {
        private String property;
        private String column;
        private boolean isGenerated;
    }
}
