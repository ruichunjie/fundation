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
public class Role {

    /**ID*/
    private Integer rid;

    /**名称*/
    private String name;

    /**状态*/
    private SysStatusEnum status;

    /**更新时间*/
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    /**权限列表*/
    private Set<Permission> permissionSet = new HashSet<>();

    /**人员*/
    private Set<Person> personSet = new HashSet<>();
}
