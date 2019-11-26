package cn.own.boot.fundation.intercept;

import cn.own.boot.fundation.exception.OwnException;
import cn.own.boot.fundation.interfaces.IAuthService;
import cn.own.boot.fundation.annotations.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @description: 登录拦截器
 * @author: xinYue
 * @time: 2019/11/1 15:48
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private IAuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果是资源文件的话 放行
        if(handler instanceof ResourceHttpRequestHandler){
            return Boolean.TRUE;
        }

        boolean flag = accessRequired((HandlerMethod)handler);

        if(!flag && Objects.isNull(authService)){
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            try{
                authService = wac.getBean(IAuthService.class);
            }catch (NoSuchBeanDefinitionException ex){
                log.error("异常:",ex);
                throw new OwnException("登录拦截器权限服务没有具体实现类--请实现IAuthService接口并实现getAuth()方法");
            }
        }
        return flag || authService.getAuth(request,response,handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    private Boolean accessRequired(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getMethod().getDeclaringClass();
        return !(Objects.nonNull(method.getAnnotation(Auth.class)) || Objects.nonNull(clazz.getAnnotation(Auth.class)));
    }

}
