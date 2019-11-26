package cn.tourism.back.service;

import cn.own.boot.fundation.annotations.SysLog;
import cn.tourism.back.entity.Route;
import cn.tourism.back.entity.RouteUser;
import cn.tourism.back.enums.RouteStatusEnum;
import cn.tourism.back.mapper.RouteMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 旅程服务
 * @author: xinYue
 * @time: 2019/11/22 13:50
 */
@Service
public class RouteService {

    @Autowired
    private RouteMapper routeMapper;

    /**
     * 查询所有旅程
     * @param pageNum
     * @param pageSize
     * @return
     */
    @SysLog
    public PageInfo<Route> findAll(Date start, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Route> list = routeMapper.findAll(start);
        list.stream().filter(e-> Objects.nonNull(e.getImg())).forEach(obj->{
            String [] arr = obj.getImg().split("###");
            List<String> imgList = Arrays.asList(arr).stream().filter(e-> !StringUtils.isEmpty(e)).collect(Collectors.toList());
            obj.setImgUrlList(imgList);
        });
        PageInfo<Route> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 更新旅程
     * @param rid
     * @param status
     * @return
     */
    @SysLog
    public Integer updateStatus(Integer rid, RouteStatusEnum status){
        return routeMapper.updateRouteStatus(rid,status);
    }

    /**
     * 获取旅团人
     * @param rid
     * @return
     */
    public List<RouteUser> findRouteUser(Integer rid){
        return routeMapper.findAllUser(rid);
    }
}
