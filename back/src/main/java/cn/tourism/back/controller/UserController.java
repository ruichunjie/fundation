package cn.tourism.back.controller;

import cn.own.boot.fundation.annotations.ResponseResult;
import cn.tourism.back.enums.SexEnum;
import cn.tourism.back.service.UserService;
import cn.tourism.back.vo.UserVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用户服务
 * @author: xinYue
 * @time: 2019/11/22 9:33
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户服务")
@ResponseResult
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 所有用户
     * @param num
     * @param size
     * @return
     */
    @GetMapping("/all")
    @ApiOperation(value = "用户", notes = "用户")
    public PageInfo<UserVo> getAll(@ApiParam(value = "性别")@RequestParam("sex") SexEnum sex,
                                   @ApiParam(value = "关键字")@RequestParam("name")String name,
                                   @ApiParam(value = "页数")@RequestParam("num")Integer num,
                                   @ApiParam(value = "每页个数")@RequestParam("size")Integer size){
        return userService.findAll(sex,name,num,size);
    }

    /**
     * 更新用户信息
     * @param uid
     * @param level
     * @param nickname
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新用户", notes = "更新用户")
    public Integer update(@ApiParam(value = "用户ID")@RequestParam("uid")Integer uid,
                          @ApiParam(value = "等级")@RequestParam("level")Integer level,
                          @ApiParam(value = "昵称")@RequestParam("nickname")String nickname){
        return userService.updateLevel(uid,level,nickname);
    }

    /**
     * 获取子集数据
     * @param id
     * @return
     */
    @GetMapping("/son")
    @ApiOperation(value = "获取子集数据", notes = "获取获取子集数据")
    public List<UserVo> getSonUser(@ApiParam(value = "用户ID") @RequestParam(value = "id",required = false)Integer  id){
        UserVo userVo= userService.getChildren(id);
        List<UserVo> list = new ArrayList<>();
        list.add(userVo);
        return  list;
    }
}
