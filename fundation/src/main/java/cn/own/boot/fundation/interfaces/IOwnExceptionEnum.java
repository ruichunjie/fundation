package cn.own.boot.fundation.interfaces;

/**
 * @description: 异常枚举父级
 * @author: xinYue
 * @time: 2019/11/1 11:15
 */
public interface IOwnExceptionEnum {

    /**
     * 获取错误编码
     * @return
     */
    Integer getCode();

    /**
     * 错误描述
     * @return
     */
    String getDescription();
}
