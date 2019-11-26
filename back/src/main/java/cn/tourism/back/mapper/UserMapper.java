package cn.tourism.back.mapper;

import cn.tourism.back.enums.SexEnum;
import cn.tourism.back.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 用户
 * @author: xinYue
 * @time: 2019/11/22 9:21
 */
public interface UserMapper {

    /**根据uid查询数据*/
    UserVo findById(@Param("uid")Integer uid);

    /**查询所有用户*/
    List<UserVo> findAll(@Param("sex") SexEnum sex, @Param("name") String name);

    /**更新用户信息*/
    Integer updateUser(@Param("uid")Integer uid, @Param("nickname")String nickname, @Param("level")Integer level);

    /**获取子集*/
    List<UserVo> findChildrenById(Integer uid);
}
