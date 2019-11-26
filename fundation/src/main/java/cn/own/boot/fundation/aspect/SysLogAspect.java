package cn.own.boot.fundation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

/**
 * @description: 系统日志切面
 * @author: xinYue
 * @time: 2019/11/1 13:39
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    /**
     * 拦截注解
     */
    @Pointcut("@annotation(cn.own.boot.fundation.annotations.SysLog)")
    public void logPointCut() {}

    /**
     * 环绕通知
     * @param point
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object round(ProceedingJoinPoint point) throws Throwable{
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        String targetName = point.getTarget().getClass().getName();
        log.info("方法{}开始执行:", targetName.substring(targetName.lastIndexOf(".")+1)+"-"+method.getName());
        Instant begin = Instant.now();
        Object obj = point.proceed();
        Instant end = Instant.now();
        log.info("方法{}执行结束!消耗{}毫秒",  targetName.substring(targetName.lastIndexOf(".")+1)+"-"+method.getName(),
                Duration.between(begin,end).toMillis());
        return obj;
    }

}
