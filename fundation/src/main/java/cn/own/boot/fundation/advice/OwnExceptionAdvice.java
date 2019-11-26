package cn.own.boot.fundation.advice;

import cn.own.boot.fundation.enums.OwnExceptionEnum;
import cn.own.boot.fundation.exception.OwnException;
import cn.own.boot.fundation.util.Result;
import cn.own.boot.fundation.util.ResultUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @description: 全局异常处理
 * @author: xinYue
 * @time: 2019/11/1 15:12
 */
@Slf4j
@RestControllerAdvice
public class OwnExceptionAdvice {

    /**
     * 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public void errorHandler(Exception e, HttpServletRequest request, HttpServletResponse response ){

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            String exJson;
            if(e instanceof OwnException) {
                log.error("出现预期异常");
                OwnException ex = (OwnException) e;
                exJson = JSON.toJSONString(ResultUtil.localEnums(ex), SerializerFeature.WriteMapNullValue);
            }else{
                log.error("出现未预期异常:",e);
                exJson = JSON.toJSONString(ResultUtil.enums(OwnExceptionEnum.FAIL),SerializerFeature.WriteMapNullValue);
            }
            if(Objects.nonNull(request.getParameter("callback")) || Objects.nonNull(request.getParameter("jsonp"))){
                String callback = Objects.isNull(request.getParameter("callback")) ?
                        request.getParameter("jsonp") : request.getParameter("callback");
                exJson = callback +"("+ exJson+");";
            }
            writer.println(exJson);
            writer.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
