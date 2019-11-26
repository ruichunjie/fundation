package cn.own.boot.fundation.condition;

import com.github.pagehelper.util.StringUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @description: 条件判断
 * @author: xinYue
 * @time: 2019/11/20 10:49
 */
public class OnDynamicDataSourceCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Map<String,Object> map =  annotatedTypeMetadata.getAnnotationAttributes(ConditionalOnDynamicDataSource.class.getName());
        Boolean flag = Boolean.valueOf(map.get("flag").toString());
        String dynamic = conditionContext.getEnvironment().getProperty("spring.datasource.dynamic");
        if(StringUtil.isNotEmpty(dynamic)
                && (dynamic.equalsIgnoreCase("false") ||dynamic.equalsIgnoreCase("true"))){
            return flag.equals(Boolean.valueOf(dynamic));
        }
        return !flag;
    }
}
