package cn.own.boot.fundation.exception;

import cn.own.boot.fundation.interfaces.IOwnExceptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: xinYue
 * @time: 2019/11/1 11:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnException extends RuntimeException implements IOwnExceptionEnum{

    /**编码*/
    private Integer code;

    /**描述*/
    private String description;

    public OwnException(Integer code, String description){
        super(description);
        this.description = description;
        this.code = code;
    }

    public OwnException(String description){
        super(description);
        this.description = description;
    }

    public OwnException(IOwnExceptionEnum ownExceptionEnum){
        super(ownExceptionEnum.getDescription());
        this.description = ownExceptionEnum.getDescription();
        this.code = ownExceptionEnum.getCode();
    }

}
