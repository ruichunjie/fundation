package cn.tourism.back.advice;

import cn.own.boot.fundation.util.Result;
import cn.own.boot.fundation.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.tourism.back.enums.ResultEnums.AUTHERROR;

/**
 * @description: 统一异常处理
 * @author: xinYue
 * @time: 2019/11/23 11:47
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = {AuthenticationException.class, AuthorizationException.class})
    public Result authExceptionHandler(AuthenticationException e){
        return ResultUtil.enums(AUTHERROR);
    }
}
