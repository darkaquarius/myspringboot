package boot.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huishen on 17/5/31.
 *
 */

@Configuration
@EnableCaching
public class CachingConfig {

    // @Bean
    // public CacheManager cacheManager() {
    //     return new ConcurrentMapCacheManager();
    // }

    // @Bean
    // public EhCacheManagerFactoryBean encache() {
    //     EhCacheManagerFactoryBean ehCacheManagerFactoryBean =
    //         new EhCacheManagerFactoryBean();
    //     ehCacheManagerFactoryBean.setConfigLocation(
    //         new ClassPathResource("cache/ehcache.xml"));
    //     return ehCacheManagerFactoryBean;
    // }

    // @Bean
    // public EhCacheCacheManager ehcache(net.sf.ehcache.CacheManager cm) {
    //     return new EhCacheCacheManager(cm);
    // }

    //使用redis作为Spring缓存抽象机制,见SpringConfig的CacheManager

}
