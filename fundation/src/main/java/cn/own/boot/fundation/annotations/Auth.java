package cn.own.boot.fundation.annotations;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @description: 登录拦截器 有则代表拦截器通过  没有进行校验
 * @author: xinYue
 * @time: 2019/10/31 16:36
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Auth {
}
