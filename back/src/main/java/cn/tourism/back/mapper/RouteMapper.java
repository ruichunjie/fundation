package cn.tourism.back.mapper;

import cn.tourism.back.entity.Route;
import cn.tourism.back.entity.RouteUser;
import cn.tourism.back.enums.RouteStatusEnum;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RouteMapper {

    /**查询所有*/
    List<Route> findAll(@Param("start")Date date);

    /**更新旅程状态*/
    Integer updateRouteStatus(@Param("rid")Integer rid, @Param("status")RouteStatusEnum status);

    /**查询旅程中的人员情况*/
    List<RouteUser> findAllUser(@Param("rid")Integer rid);
}
