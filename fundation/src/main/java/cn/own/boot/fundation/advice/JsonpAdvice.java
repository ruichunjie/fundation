package cn.own.boot.fundation.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * @description: SpringBoot对Jsonp的支持
 * @author: xinYue
 * @time: 2019/11/1 15:10
 */
@ControllerAdvice(basePackages = "cn")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

    public JsonpAdvice() {
        super("callback","jsonp");
    }

}
