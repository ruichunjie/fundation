package cn.own.boot.fundation.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 返回账号
 * @author: xinYue
 * @time: 2019/11/1 14:02
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 3068837394742385883L;

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;

}
