package cn.own.boot.fundation.handler;

import cn.own.boot.fundation.annotations.ResponseResult;
import cn.own.boot.fundation.util.ResultUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @description:  返回结果封装
 * @author: xinYue
 * @time: 2019/11/8 17:02
 */
public class AutoResultReturnHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.getMethodAnnotation(ResponseResult.class) != null
                || methodParameter.getDeclaringClass().getAnnotation(ResponseResult.class) != null;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) {
        modelAndViewContainer.setRequestHandled(true);
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            if(Objects.isNull(request.getParameter("callback")) && Objects.isNull(request.getParameter("jsonp"))){
                writer.print(JSON.toJSONString(ResultUtil.success(o),SerializerFeature.WriteMapNullValue));
            }else{
                String callback = Objects.isNull(request.getParameter("callback")) ?
                        request.getParameter("jsonp") : request.getParameter("callback");
                writer.print(new String ((callback + "("+ JSON.toJSONString(ResultUtil.success(o),SerializerFeature.WriteMapNullValue)+");").getBytes("UTF-8")));
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
