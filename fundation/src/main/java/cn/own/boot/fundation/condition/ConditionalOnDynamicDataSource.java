package cn.own.boot.fundation.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @description: 是否是动态数据库的判断
 * @author: xinYue
 * @time: 2019/11/20 10:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnDynamicDataSourceCondition.class)
public @interface ConditionalOnDynamicDataSource {

    boolean flag() default false;
}
