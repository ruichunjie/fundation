package cn.own.boot.fundation.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 是否通过拦截器
 * @author: xinYue
 * @time: 2019/11/1 11:15
 */
public interface IAuthService {

    /**
     * 是否通过拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    boolean getAuth(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

}
