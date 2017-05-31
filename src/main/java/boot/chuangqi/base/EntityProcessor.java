package boot.chuangqi.base;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author libo.ding
 * @since 2016-08-17
 */
public class EntityProcessor implements ApplicationListener<ContextRefreshedEvent>, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    private static volatile boolean isRefresh = false;

    /**
     * 1. add @SelectKey to entities which contains @GeneratedValue
     * 2. replace selectAll
     * 3. replace selectByConditions
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isRefresh) return;

        ApplicationContext applicationContext = event.getApplicationContext();
        SqlSessionFactory sessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        Configuration configuration = sessionFactory.getConfiguration();
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();

        for (EntityMetaBean bean : EntityHelper.getInstance().getEntityMetas()) {
            selectKey(bean, configuration);
            select(bean, configuration, "selectAll");
            select(bean, configuration, "selectByConditions");
            registerTypeHandler(registry, bean.getEntityClass());
        }

        publisher.publishEvent(new BaseMapperEvent(this));
        isRefresh = true;
    }

    private void selectKey(EntityMetaBean bean, Configuration configuration) {
        Set<EntityMetaBean.PrimaryKeyBean> primaryKeyBeanSet = bean.getPrimaryKeySet();
        if (primaryKeyBeanSet.size() != 1) return;
        EntityMetaBean.PrimaryKeyBean primaryKeyBean = primaryKeyBeanSet.iterator().next();
        if (!primaryKeyBean.isGenerated()) return;
        Class<?> mapperClass = bean.getMapperClass();
        String property = primaryKeyBean.getProperty();
        String id = mapperClass.getCanonicalName() + ".insertSelective!selectKey";
        String defaultId = mapperClass.getCanonicalName() + ".insertSelective!selectKey-Inline";
        SqlSource sqlSource = new RawSqlSource(configuration, "SELECT LAST_INSERT_ID()", null);
        ResultMap resultMap = new ResultMap.Builder(configuration, defaultId, Integer.class, Collections.emptyList()).build();
        MappedStatement keyStatement = new MappedStatement.Builder(configuration, id, sqlSource, SqlCommandType.SELECT)
            .keyProperty(property)
            // .parameterMap(new ParameterMap.Builder(configuration, defaultId, Object.class, Collections.emptyList()).build())
            .resultMaps(Collections.singletonList(resultMap))
            .statementType(StatementType.PREPARED)
            .build();
        configuration.addMappedStatement(keyStatement);
        SelectKeyGenerator selectKeyGenerator = new SelectKeyGenerator(keyStatement, false);
        configuration.addKeyGenerator(id, selectKeyGenerator);
        // modify insertSelective MappedStatement
        MappedStatement ms = configuration.getMappedStatement(mapperClass.getCanonicalName() + ".insertSelective");
        MetaObject metaObject = SystemMetaObject.forObject(ms);
        metaObject.setValue("keyGenerator", selectKeyGenerator);
        metaObject.setValue("keyProperties", new String[]{property});
    }

    private void select(EntityMetaBean bean, Configuration configuration, String methodName) {
        String msId = bean.getMapperClass().getCanonicalName() + "." + methodName;
        MappedStatement ms = configuration.getMappedStatement(msId);
        SqlSource sqlSource = ms.getSqlSource();
        if (sqlSource instanceof DynamicSqlSource) { // selectByConditions
            MetaObject metaObject = SystemMetaObject.forObject(SystemMetaObject.forObject(sqlSource).getValue("rootSqlNode"));
            String sql = String.valueOf(metaObject.getValue("text"))
                .replace("$COLUMNS$", bean.getColumns()).replace("$TABLE_NAME$", bean.getTableName());
            metaObject.setValue("text", sql);
        } else if (sqlSource instanceof RawSqlSource) { // selectALl
            StaticSqlSource staticSqlSource = (StaticSqlSource) SystemMetaObject.forObject(ms.getSqlSource()).getValue("sqlSource");
            MetaObject metaObject = SystemMetaObject.forObject(staticSqlSource);
            String sql = String.valueOf(metaObject.getValue("sql"))
                .replace("$COLUMNS$", bean.getColumns()).replace("$TABLE_NAME$", bean.getTableName());
            metaObject.setValue("sql", sql);
        } else {
            throw new RuntimeException("invalid sql source");
        }
    }

    @SuppressWarnings("unchecked")
    private void registerTypeHandler(TypeHandlerRegistry registry, Class<?> entityClass) {
        Stream.of(entityClass.getDeclaredFields()).forEach(field -> {
            if (field.getType().isEnum()) { // 枚举
                Class<?> type = field.getType();
                registry.register(type, new EnumOrdinalTypeHandler(type));
            }
        });
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
