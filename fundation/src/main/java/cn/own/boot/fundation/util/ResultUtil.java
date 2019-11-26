package cn.own.boot.fundation.util;

import cn.own.boot.fundation.interfaces.IOwnExceptionEnum;
import cn.own.boot.fundation.enums.OwnExceptionEnum;

import java.util.Objects;

/**
 * @description: 返回封装结果
 * @author: xinYue
 * @time: 2019/11/1 14:03
 */
public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        if(object instanceof IOwnExceptionEnum){
            result.setCode(((IOwnExceptionEnum) object).getCode());
            String description = ((IOwnExceptionEnum) object).getDescription();
            if(Objects.nonNull(LocaleMessageSourceUtil.getMessage(description))){
                description = LocaleMessageSourceUtil.getMessage(description);
            }
            result.setMsg(description);
            return result;
        }
        result.setData(object);
        result.setCode(OwnExceptionEnum.SUCCESS.getCode());
        result.setMsg(OwnExceptionEnum.SUCCESS.getDescription());
        return result;
    }

    public static Result enums(IOwnExceptionEnum ownEnum){
        Result result = new Result();
        result.setCode(ownEnum.getCode());
        result.setMsg(ownEnum.getDescription());
        return result;
    }

    public static Result localEnums(IOwnExceptionEnum ownEnum){
        Result result = new Result();
        result.setCode(ownEnum.getCode());
        result.setMsg(LocaleMessageSourceUtil.getMessage(ownEnum.getDescription()));
        return result;
    }

}
