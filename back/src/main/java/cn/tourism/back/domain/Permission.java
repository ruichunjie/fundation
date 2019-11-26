package cn.tourism.back.domain;

import cn.tourism.back.enums.PermissionTypeEnum;
import cn.tourism.back.enums.SysStatusEnum;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @Author:ChunJieRen
 * @Date:2019/11/22 20:10
 * @Description:
 */
@Data
public class Permission {

    /**ID*/
    private Integer pnid;

    /**名称*/
    private String name;

    /**类别*/
    private PermissionTypeEnum type;

    /**优先级*/
    private Integer seq;

    /**路径*/
    private String url;

    /**状态*/
    private SysStatusEnum status;

    /**更新时间*/
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

}
