package cn.own.boot.fundation.annotations;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *  配置初始化
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(OwnConfigImportSelector.class)
public @interface EnableOwnConfig {
}
