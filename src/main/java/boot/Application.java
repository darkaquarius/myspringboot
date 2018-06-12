package boot;

import boot.config.MongoConfig;
import boot.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by huishen on 16/9/24.
 *
 */

@EnableAutoConfiguration     //启动内置自动配置
@Configuration     //表示Application作为spring的配置文件在
@SpringBootApplication
@Import({SpringConfig.class, MongoConfig.class})
public class Application {

    public static void main(String args[]){
        SpringApplication.run(Application.class);
        // SpringApplication application = new SpringApplication(Application.class);
        // application.setBannerMode(Banner.Mode.OFF);
        // application.run(args);
    }

    // //@Profile在不同环境下使用不同配置
    // public static void main(String[] args) {
    //     AnnotationConfigApplicationContext context =
    //         new AnnotationConfigApplicationContext(SpringConfig.class);
    //     User user = (User) context.getBean(User.class);
    //     System.out.println(user);
    // }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            //设置跨域通过
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "OPTIONS", "PATCH", "DELETE");

                // 详细
                // registry.addMapping("/api/*")
                //     .allowedOrigins("*")
                //     // 是否允许发送cookie
                //     .allowCredentials(false)
                //     .allowedMethods("GET", "POST", "PUT", "OPTIONS", "PATCH", "DELETE")
                //     .allowedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Headers","Access-Control-Allow-Methods"
                //         ,"Access-Control-Max-Age")
                //     .exposedHeaders("Access-Control-Allow-Origin")
                //     .maxAge(3600);
            }

            // @Override
            // public void addInterceptors(InterceptorRegistry registry) {
            //     registry.addInterceptor(getTokenHeader())
            //         .addPathPatterns("/api/*")
            //         .excludePathPatterns("/robots.txt");
            // }
            //
            // @Bean
            // public HandlerInterceptor getTokenHeader() {
            //     return new HeaderTokenInterceptor();
            // }

        };
    }

}
