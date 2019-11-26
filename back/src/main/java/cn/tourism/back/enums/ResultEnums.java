package cn.tourism.back.enums;

import cn.own.boot.fundation.interfaces.IOwnExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: xinYue
 * @time: 2019/11/23 11:50
 */
@AllArgsConstructor
@Getter
public enum ResultEnums implements IOwnExceptionEnum {

    AUTHERROR(1,"用户名/密码错误!");

    private Integer code;

    private String description;

}
