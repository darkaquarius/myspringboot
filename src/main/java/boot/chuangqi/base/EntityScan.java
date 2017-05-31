package boot.chuangqi.base;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author libo.ding
 * @since 2016-08-17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EntityScannerRegistrar.class)
public @interface EntityScan {

    /**
     * Alias for the {@link #basePackages()} attribute.
     */
    String[] value() default {};

    String[] basePackages() default {};
}
