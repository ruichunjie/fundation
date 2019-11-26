package cn.own.boot.fundation.enums;

import cn.own.boot.fundation.interfaces.IOwnExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: xinYue
 * @time: 2019/11/1 11:26
 */
@Getter
@AllArgsConstructor
public enum OwnExceptionEnum implements IOwnExceptionEnum {
    ApiVersion(5,"版本号错误!"),
    SUCCESS(0,"执行成功!"),
    FAIL(1,"执行失败!");

    Integer code;

    String description;
}
