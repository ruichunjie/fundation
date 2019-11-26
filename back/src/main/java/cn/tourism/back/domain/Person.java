package cn.tourism.back.domain;

import cn.tourism.back.enums.SysStatusEnum;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author:ChunJieRen
 * @Date:2019/11/22 20:10
 * @Description:
 */
@Data
public class Person {

    /**ID*/
    private Integer pid;

    /**用户名*/
    private String username;

    /**密码*/
    private String password;

    /**状态*/
    private SysStatusEnum status;

    /**更新时间*/
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    /**角色列表*/
   private Set<Role> roleSet = new HashSet<>();
}
