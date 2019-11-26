package cn.tourism.back.entity;

import cn.tourism.back.enums.IdentityEnum;
import cn.tourism.back.enums.RouteUserStatusEnum;
import lombok.Data;

@Data
public class RouteUser {

    /**ID*/
    private Integer id;

    /**旅程ID*/
    private Integer rid;

    /**用户ID*/
    private Integer uid;

    /**在旅程中的身份*/
    private IdentityEnum identity;

    /**状态*/
    private RouteUserStatusEnum status;
}
