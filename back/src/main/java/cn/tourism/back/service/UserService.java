package cn.tourism.back.service;

import cn.own.boot.fundation.annotations.SysLog;
import cn.tourism.back.enums.SexEnum;
import cn.tourism.back.mapper.UserMapper;
import cn.tourism.back.vo.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用户服务
 * @author: xinYue
 * @time: 2019/11/22 9:27
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    @SysLog
    public PageInfo<UserVo> findAll(SexEnum sex,String name, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<UserVo> list = userMapper.findAll(sex,name);
        PageInfo<UserVo> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 更新等级
     * @param uid
     * @param level
     * @param nickName
     * @return
     */
    @SysLog
    public Integer updateLevel(Integer uid, Integer level, String nickName){
        return  userMapper.updateUser(uid,nickName,level);
    }

    /**
     * 获取子集
     * @param id
     * @return
     */
    @SysLog
    public UserVo getChildren(Integer id){
        UserVo userVo = userMapper.findById(id);
        userVo = findChildren(userVo);
        userVo.setClevel(1);
        List<UserVo> list= findChildrenSize(userVo,new ArrayList<>());
        log.info("子集数目:{}",list.size());
        return  userVo;
    }

    /**
     * 获取子集
     * @param userVo
     * @return
     */
    @SysLog
    private UserVo  findChildren(UserVo userVo){
        List<UserVo> sonList = userMapper.findChildrenById(userVo.getUid());
        if(CollectionUtils.isEmpty(sonList)){
            userVo.setChildren(new ArrayList<>());
            return userVo;
        }
        for(UserVo obj: sonList){
            if(CollectionUtils.isEmpty(obj.getChildren())){
                obj.setChildren(new ArrayList<>());
            }
            obj.getChildren().add( this.findChildren(obj));
        }
        userVo.setChildren(sonList);
        return userVo;
    }

    /**
     * 获取所有子集
     * @param userVo
     * @return
     */
    @SysLog
    private List<UserVo>  findChildrenSize(UserVo userVo,List<UserVo> list){
        List<UserVo> sonList = userMapper.findChildrenById(userVo.getUid());
        if(CollectionUtils.isEmpty(sonList)){
            userVo.setChildren(new ArrayList<>());
            return new ArrayList<>();
        }
        for(int i=0; i<sonList.size();i++){
            if(CollectionUtils.isEmpty(sonList.get(i).getChildren())){
                sonList.get(i).setChildren(new ArrayList<>());
            }
            sonList.get(i).setClevel(userVo.getClevel()+1);
            list.add(sonList.get(i));
            list.addAll(findChildrenSize(sonList.get(i),new ArrayList<>()));
        }
        userVo.setChildren(sonList);
        return list;
    }

}
