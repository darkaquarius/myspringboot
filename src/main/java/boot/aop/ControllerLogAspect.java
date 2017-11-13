package boot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by huishen on 17/11/10.
 * Controller层中的日志记录
 */

@Aspect
@Component
public class ControllerLogAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* boot.controller.*.*(..))")
    public void webLog() {}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        logger.info("URL : " + request.getRequestURL().toString());

        logger.info("HTTP_METHOD : " + request.getMethod());

        logger.info("IP : " + request.getRemoteAddr());

        logger.info("CLASS_METHOD : "
            .concat(joinPoint.getSignature().getDeclaringTypeName())
            .concat(".")
            .concat(joinPoint.getSignature().getName()));

        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        logger.info("getKind : " + joinPoint.getKind());
        logger.info("getSourceLocation : " + joinPoint.getSourceLocation());
        logger.info("toShortString : " + joinPoint.toShortString());
        logger.info("toString : " + joinPoint.toString());
        logger.info("toLongString : " + joinPoint.toLongString());
        logger.info("getStaticPart : " + joinPoint.getStaticPart());
        logger.info("getTarget : " + joinPoint.getTarget());
        logger.info("getThis : " + joinPoint.getThis());
        logger.info("getSignature().getModifiers() : " + joinPoint.getSignature().getModifiers());
        logger.info("" + joinPoint.getSignature().getDeclaringType());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReutrning(Object ret) {
        logger.info("RESPONSE : " + ret);

        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

}
