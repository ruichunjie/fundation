package cn.tourism.back.controller;

import cn.own.boot.fundation.annotations.ResponseResult;
import cn.own.boot.fundation.util.DateUtil;
import cn.tourism.back.entity.Route;
import cn.tourism.back.entity.RouteUser;
import cn.tourism.back.enums.RouteStatusEnum;
import cn.tourism.back.service.RouteService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @description: 旅程服务
 * @author: xinYue
 * @time: 2019/11/22 13:58
 */
@RestController
@RequestMapping("/route")
@Slf4j
@Api(value = "旅程服务")
@ResponseResult
public class RouteController {

    @Autowired
    private RouteService routeService;

    /**
     * 获取所有旅程
     * @param num
     * @param size
     * @return
     */
    @GetMapping("/all")
    @ApiOperation(value = "获取旅程", notes = "获取旅程")
    public PageInfo<Route> findAll(@ApiParam(value = "开始时间")@RequestParam(value = "start",required = false)String start,
                                   @ApiParam(value = "页数")@RequestParam("num")Integer num,
                                   @ApiParam(value = "每页个数")@RequestParam("size")Integer size) throws ParseException {

        Date startDate =null;
        if(!StringUtils.isEmpty(start)){
            startDate= DateUtil.stringToDate(start, "yyyy-MM-dd");
            startDate= DateUtil.moveTime(startDate,1);
        }
        return routeService.findAll(startDate,num,size);
    }

    /**
     * 更新旅程
     * @param rid
     * @param status
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新旅程", notes = "更新旅程")
    public Integer update(@ApiParam(value = "旅程ID")@RequestParam("rid")Integer rid,
                          @ApiParam(value = "状态")@RequestParam("status") RouteStatusEnum status){
        return routeService.updateStatus(rid,status);
    }

    /**
     * 获取旅程成员
     * @return
     */
    @GetMapping("/user")
    @ApiOperation(value = "获取旅程成员", notes = "获取旅程成员")
    public List<RouteUser> findRouteUser(@ApiParam(value = "旅团ID")@RequestParam("rid")Integer rid){
        return routeService.findRouteUser(rid);
    }
}
