package cn.own.boot.fundation.annotations;

import java.lang.annotation.*;

/**
 * @description: 日志注解
 * @author: xinYue
 * @time: 2019/11/1 13:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}

