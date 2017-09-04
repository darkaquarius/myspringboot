package boot.config;

import boot.consts.Jobs;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TimeZone;

/**
 * Created by huishen on 16/10/8.
 *
 */
@org.springframework.context.annotation.Configuration
@PropertySource("classpath:app.properties")
@ComponentScan(basePackages = {"boot.controller", "boot.service.impl", "boot.domain", "boot.order", "boot.util", "boot.config"})
@MapperScan(basePackages = "boot.mapper")
@Import({CachingConfig.class, JMXConfig.class})
@EnableAspectJAutoProxy
@EnableRedisHttpSession
public class SpringConfig {

    @Bean
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
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
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

    @Bean
    @Primary
    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk7Module());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(LocalDateTime.class,
            new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        simpleModule.addSerializer(LocalDateTime.class,
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(simpleModule);
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        // jackson序列化时，默认不带类信息，这里设置序列化时，把类的类型信息带上
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // enum类型，序列化时，用index
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        return objectMapper;
    }

    // 可以用"${key}"来获取properties文件中的属性
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public static Configuration configuration() {
        Configuration configuration = new Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
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

    // 上传文件
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

    // //Profile在不同环境下使用不同的配置
    // @Bean
    // @Profile({"test"})
    // public User getUser1() {
    //     return new User(1);
    // }

    // 发送邮件
    @Bean
    public JavaMailSenderImpl mainSender(@Value("${mail.host}") String mailHost,
                                 @Value("${mail.username}") String mailUsername,
                                 @Value("${mail.port}") int mailPort,
                                 @Value("${mail.password}") String mailPassowrd) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassowrd);

        // Properties properties = new Properties();
        // properties.put("mail.smtp.auth", true);
        // properties.put("mail.smtp.ssl.enable", true);
        // properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        // properties.put("mail.smtp.timeout", 25000);
        // mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

    // 模板解析器
    @Bean
    public ClassLoaderTemplateResolver classLoaderTemplateResolver() {
        ClassLoaderTemplateResolver resolver =
            new ClassLoaderTemplateResolver();
        resolver.setPrefix("mail/");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(1);
        return resolver;
    }

    // 模板解析器
    @Bean
    public ServletContextTemplateResolver servletContextTemplateResolver() {
        ServletContextTemplateResolver resolver =
            new ServletContextTemplateResolver();
        resolver.setPrefix("/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(2);
        return resolver;
    }

    // 模板引擎
    @Bean
    public SpringTemplateEngine springTemplateEngine(Set<ITemplateResolver> resolvers) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolvers(resolvers);
        return engine;
    }

    // Thymeleaf视图解析器
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }

    // rmi 这里的10.0.0.112会变化
    // @Bean
    // public RmiProxyFactoryBean bookService() {
    //     RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
    //     rmiProxy.setServiceUrl("rmi://10.0.0.112/bookService");
    //     rmiProxy.setServiceInterface(BookService.class);
    //     return rmiProxy;
    // }

    // 使用HTTP header将请求与session关联
    @Bean
    public HeaderHttpSessionStrategy headerHttpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

}
