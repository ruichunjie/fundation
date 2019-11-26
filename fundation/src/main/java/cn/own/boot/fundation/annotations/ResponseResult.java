package cn.own.boot.fundation.annotations;

import java.lang.annotation.*;

/**
 * @description: 返回值封装注解
 * @author: xinYue
 * @time: 2019/11/1 13:38
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {

}
