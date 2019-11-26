package cn.own.boot.fundation.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description: 获取上下文
 * @author: xinYue
 * @time: 2019/11/7 11:09
 */
@Component
public class ContextHandler implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextHandler.context = applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
