package cn.tourism.back.controller;

import cn.own.boot.fundation.util.Result;
import cn.own.boot.fundation.util.ResultUtil;
import cn.tourism.back.domain.Person;
import cn.tourism.back.entity.Menu;
import cn.tourism.back.enums.PermissionTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @description: 登录/退出 权限相关
 * @author: xinYue
 * @time: 2019/11/23 12:28
 */
@RestController
@RequestMapping("/sys")
@Slf4j
@Api(value = "登录权限服务")
public class SysController {

    /**
     * 获取用户
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户", notes = "用户")
    public Result login(@RequestParam("username")String username,
                        @RequestParam("password")String password){
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject currentPerson = SecurityUtils.getSubject();
        currentPerson.login(token);
        Map map = new HashMap();
        map.put("auth",currentPerson.getSession().getId());
        map.put("name",username);
        return ResultUtil.success(map);
    }

    /**
     * 获取菜单
     * @return
     */
    @PostMapping("/menu")
    @ApiOperation(value = "获取菜单", notes ="获取菜单")
    public Result getMenu(){
        Subject currentPerson = SecurityUtils.getSubject();
        Person p = (Person)currentPerson.getPrincipal();
        if(Objects.isNull(p) || CollectionUtils.isEmpty(p.getRoleSet())){
            log.error("用户没有角色");
            return ResultUtil.success("用户没有角色");
        }

        List<Menu> list = new ArrayList<>();
        p.getRoleSet().forEach(obj->
            obj.getPermissionSet()
                    .stream()
                    .filter(e->e.getType() == PermissionTypeEnum.MENU)
                    .forEach(e->{
                Menu m = Menu.builder().icon(e.getUrl()).title(e.getName()).seq(e.getSeq()).build();
                list.add(m); }));
        Collections.sort(list,Comparator.comparing(Menu::getSeq));
        return ResultUtil.success(list);

    }
}
