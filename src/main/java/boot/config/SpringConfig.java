package boot.config;

import boot.consts.Jobs;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by huishen on 16/10/8.
 *
 */
@org.springframework.context.annotation.Configuration
@PropertySource("classpath:app.properties")
@ComponentScan(basePackages = {"boot.controller", "boot.service.impl", "boot.domain", "boot.order", "boot.util"})
@MapperScan(basePackages = "boot.mapper")
@Import({CachingConfig.class})
@EnableAspectJAutoProxy
public class SpringConfig {

    @Bean
    @Scope
    public JedisPoolConfig poolConfig(
        @Value("${redis.maxIdle}") int maxIdle,
        @Value("${redis.pool.maxTotal}") int maxTotal,
        @Value("${redis.maxWaitMillis}") int maxWaitMillis,
        @Value("${redis.testOnBorrow}") boolean testOnBorrow) {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(
        @Value("${redis.host}") String host,
        @Value("${redis.port}") int port,
        @Qualifier("poolConfig") JedisPoolConfig poolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPoolConfig(poolConfig);
        return factory;
    }

    @Bean
    public RedisTemplate redisTemplate(@Qualifier("jedisConnectionFactory") RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        // redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = getObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //value,字节码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        //hashKey
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    //使用redis作为Spring缓存抽象机制
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }

    // //使用多个缓存管理器
    // @Bean
    // public CacheManager cacheManager(net.sf.ehcache.CacheManager cm, RedisTemplate redisTemplate) {
    //     CompositeCacheManager cacheManager = new CompositeCacheManager();
    //     List<CacheManager> managers = new ArrayList<CacheManager>();
    //     // managers.add(new JCacheCacheManager(jcm));        //javax.cache.CacheManager jcm 在哪里?
    //     managers.add(new EhCacheCacheManager(cm));
    //     managers.add(new RedisCacheManager(redisTemplate));
    //     cacheManager.setCacheManagers(managers);
    //     return cacheManager;
    // }

    private ObjectMapper getObjectMapper() {
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
        builder.timeZone("GMT+8");
        builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //enum的映射
        builder.featuresToEnable(new Object[]{SerializationFeature.WRITE_ENUMS_USING_INDEX});
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.propertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        builder.serializerByType(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });
        builder.deserializerByType(LocalDateTime.class, new JsonDeserializer() {
            public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
                return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        });

        ObjectMapper objectMapper = builder.build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //Jackson序列化类型信息!!
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return objectMapper;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public static Configuration configuration() {
        Configuration configuration = new Configuration();
        //把对应的enum类型和所使用的TypeHandler注册进来
        configuration.getTypeHandlerRegistry().register(Jobs.class, EnumOrdinalTypeHandler.class);
        return configuration;
    }

    @Bean
    public DataSource dataSource(
        @Value("${jdbc.url}") String jdbcUrl,
        @Value("${jdbc.username}") String username,
        @Value("${jdbc.password}") String password,
        @Value("${jdbc.maxPoolSize}") int maxPoolSize) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setCheckoutTimeout(10000);    //timeout 10s
        dataSource.setIdleConnectionTestPeriod(1800);    //check idle connections every 30 minutes
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, Configuration configuration) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(configuration);

        //为了配置ResultMap
        // Resource resource = new ClassPathResource("mybatis-config.xml");
        //attention
        // Property 'configuration' and 'configLocation' can not specified with together
        // sqlSessionFactoryBean.setConfigLocation(resource);
        // Resource userMapperResource = new ClassPathResource("mapper/UserMapper.xml");
        // Resource addressMapperResource = new ClassPathResource("mapper/AddressMapper.xml");
        // sqlSessionFactoryBean.setMapperLocations(new Resource[]{userMapperResource, addressMapperResource});
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public MapperScannerConfigurer setMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("boot.mapper");
        return mapperScannerConfigurer;
    }

    @Bean
    public StandardServletMultipartResolver setStandardServletMultipartResolver() {
        return new StandardServletMultipartResolver();
    }

    //研究下面这些配置为什么不起作用??  关于上传文件的
    // @Bean
    // public CommonsMultipartResolver commonsMultipartResolver() {
    //     CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    //     resolver.setDefaultEncoding("UTF-8");
    //     resolver.setMaxUploadSize(5400000L);
    //     Resource fileResource = new FileSystemResource("/Users/huishen/upload");
    //     resolver.setUploadTempDir(fileResource);
    //     return resolver;
    // }

    // @Bean
    // public CommonsMultipartResolver setCommonsMultipartResolver() {
    //     CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
    //     commonsMultipartResolver.setMaxUploadSize(1000000000);
    //     return commonsMultipartResolver;
    // }

    // @Bean
    // public MultipartResolver multipartResolver() {
    //     return new StandardServletMultipartResolver();
    // }

}
