package cn.tourism.back.core;

import cn.tourism.back.domain.Permission;
import cn.tourism.back.domain.Person;
import cn.tourism.back.domain.Role;
import cn.tourism.back.mapper.PersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @description:
 * @author: xinYue
 * @time: 2019/11/23 8:53
 */
@Component
@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private PersonMapper personMapper;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        log.info("授权开始!");
        Person person = (Person)principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        if(Objects.isNull(person)){
            throw new AuthorizationException();
        }

        List<String> permisssionList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        try{
            Set<Role> roleSet = person.getRoleSet();
            if(!CollectionUtils.isEmpty(roleSet)){
                roleSet.forEach(obj->{
                    roleList.add(obj.getName());
                    Set<Permission> permissionSet = obj.getPermissionSet();
                    if(!CollectionUtils.isEmpty(permissionSet)){
                        permissionSet.forEach(p->permisssionList.add(p.getName()));
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new AuthorizationException();
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permisssionList);
        info.addRoles(roleList);
        log.info("角色列表{}", roleList);
        log.info("权限列表{}", permisssionList);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        String username = usernamePasswordToken.getUsername();
        Person person = personMapper.findByUsername(username);
        SecurityUtils.getSubject().getSession().setAttribute("ADMIN",person);
        SecurityUtils.getSubject().getSession().setTimeout(-5000);
        return new SimpleAuthenticationInfo(person,person.getPassword(),this.getClass().getName());
    }
}
