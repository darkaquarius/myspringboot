package boot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by huishen on 16/10/27.
 */
@Aspect
@Component
public class MyAop {
    private static Logger log = LoggerFactory.getLogger(MyAop.class);

    @Pointcut("execution(* boot.controller.*.*(..))")
    public void myPointcut(){}

//    @Around("execution(* boot.controller.*.*(..))")
//     @Around(value = "myPointcut()")
    public Object processTX(ProceedingJoinPoint jp) throws Throwable{
        log.info("aop around before method");
        try{
            Object proceed = jp.proceed();
//            jp.proceed();
            return proceed;
        }finally {
            log.info("aop around log after method");
        }
    }

    @Around(value = "@annotation(MyAnnotation)")
    public void func(ProceedingJoinPoint pjp){
        System.out.println(pjp.getTarget());
        System.out.println(pjp.getSignature());
    }
}
