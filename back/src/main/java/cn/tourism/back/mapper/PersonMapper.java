package cn.tourism.back.mapper;

import cn.tourism.back.domain.Permission;
import cn.tourism.back.domain.Person;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 管理员
 * @author: xinYue
 * @time: 2019/11/23 9:20
 */
public interface PersonMapper {

    /**查询用户*/
    Person findByUsername(@Param("username")String username);

    /**获取所有有效的目录*/
    List<Permission> findAllPermissionList();

}
