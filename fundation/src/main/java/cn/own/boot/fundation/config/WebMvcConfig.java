package cn.own.boot.fundation.config;

import cn.own.boot.fundation.handler.AutoResultReturnHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 添加自定义returnHandler
 * @author: xinYue
 * @time: 2019/11/8 17:06
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements InitializingBean {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /**
     * 添加自定义returnHandler
     */
    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter
                .getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> list = new ArrayList<>();
        list.add(new AutoResultReturnHandler());
        list.addAll(returnValueHandlers);
        requestMappingHandlerAdapter.setReturnValueHandlers(list);
    }
}
