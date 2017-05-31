package boot.chuangqi.base;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;


/**
 * @author libo.ding
 * @since 2016-08-17
 */
public class EntityScannerRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private final static Logger logger = LoggerFactory.getLogger(EntityScannerRegistrar.class);

    private BeanFactory beanFactory;

    @Override
    @SuppressWarnings("unchecked")
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        EntityHelper.getInstance().setEntityBasePackages(getBasePackages(importingClassMetadata, EntityScan.class.getName()));
        EntityHelper.getInstance().setMapperBasePackages(getBasePackages(importingClassMetadata, MapperScan.class.getName()));

        Configuration configuration = beanFactory.getBean(Configuration.class);
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        // mapping Entity to Mapper
        Map<Class<?>, Class<?>> mapEntityMapper = new HashMap<>();
        for (Class<?> clazz : PackageUtils.getClasses(EntityHelper.getInstance().getMapperBasePackages())) {
            if (clazz.getGenericInterfaces().length == 0)
                continue;
            ParameterizedType type = (ParameterizedType) clazz.getGenericInterfaces()[0];
            if (type.getRawType().equals(BaseMapper.class))
                mapEntityMapper.put((Class<?>) type.getActualTypeArguments()[0], clazz);
        }
        EntityHelper.getInstance().setEntityMapperMap(mapEntityMapper);
        // @Entity classes
        for (Class<?> entityClass : PackageUtils.getClasses(EntityHelper.getInstance().getEntityBasePackages())) {
            if (entityClass.getDeclaredAnnotation(Entity.class) == null)
                continue;
            Class<?> mapperClass = mapEntityMapper.get(entityClass);
            if (mapperClass == null) {
                logger.warn("no mybatis Mapper found for entity class: {}", entityClass.getCanonicalName());
                continue;
            }

            EntityMetaBean bean = new EntityMetaBean();
            bean.setEntityClass(entityClass);
            bean.setMapperClass(mapperClass);

            Table table = entityClass.getDeclaredAnnotation(Table.class);
            bean.setTableName(table != null ? table.name() :
                CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName()));

            List<ResultMapping> resultMappings = new ArrayList<>();
            Map<String, String> propertyColumnMap = new HashMap<>();
            Set<EntityMetaBean.PrimaryKeyBean> primaryKeySet = new HashSet<>();
            for (Field field : entityClass.getDeclaredFields()) {
                // ignore final, static fields
                if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))
                    continue;
                String property = field.getName();
                // Column annotation
                Column columnAnnotation = field.getAnnotation(Column.class);
                String column = columnAnnotation != null ? columnAnnotation.name() :
                    CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                String columnWithQuote = "`".concat(column).concat("`");
                propertyColumnMap.put(property, columnWithQuote);
                ResultMapping.Builder resultMappingBuilder =
                    new ResultMapping.Builder(configuration, property, column, field.getType());
                // Id annotation
                Id idAnnotation = field.getAnnotation(Id.class);
                if (idAnnotation != null) {
                    resultMappingBuilder.flags(Collections.singletonList(ResultFlag.ID));
                    GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                    primaryKeySet.add(new EntityMetaBean.PrimaryKeyBean(property, columnWithQuote, generatedValue != null));
                }

                // enum
                if (field.getType().isEnum()) { // 枚举
                    Class<?> type = field.getType();
                    // typeHandlerRegistry.register(type, JdbcType.TINYINT, new EnumOrdinalTypeHandler(type));
                    resultMappingBuilder.typeHandler(new EnumOrdinalTypeHandler(type));
                }
                resultMappings.add(resultMappingBuilder.build());
            }
            // add BaseResultMap to Configuration
            String resultMapId = mapperClass.getCanonicalName() + ".BaseResultMap";
            ResultMap resultMap = new ResultMap.Builder(configuration, resultMapId, entityClass, resultMappings).build();
            configuration.addResultMap(resultMap);

            bean.setPropertyColumnMap(propertyColumnMap);
            bean.setPrimaryKeySet(primaryKeySet);
            EntityHelper.getInstance().addEntityMeta(entityClass, bean);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private List<String> getBasePackages(AnnotationMetadata annotationMetadata, String annotationName) {
        AnnotationAttributes entityScanAnnotation = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(annotationName));
        List<String> basePackages = new ArrayList<>();
        for (String pkg : entityScanAnnotation.getStringArray("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : entityScanAnnotation.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        return basePackages;
    }
}
