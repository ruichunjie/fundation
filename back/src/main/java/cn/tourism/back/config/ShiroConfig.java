package cn.tourism.back.config;

import cn.tourism.back.core.AuthRealm;
import cn.tourism.back.core.OwnSessionManage;
import cn.tourism.back.domain.Permission;
import cn.tourism.back.mapper.PersonMapper;
import org.apache.shiro.mgt.SecurityManager;
import cn.tourism.back.core.CredentialMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @description:
 * @author: xinYue
 * @time: 2019/11/23 10:34
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private PersonMapper personMapper;

    @Bean
    public AuthRealm authRealm(CredentialMatcher credentialMatcher){
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCacheManager(new MemoryConstrainedCacheManager());
        authRealm.setCredentialsMatcher(credentialMatcher);
        return authRealm;
    }

    @Bean
    public SecurityManager securityManager(AuthRealm realm, OwnSessionManage ownSessionManage){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setSessionManager(ownSessionManage);
        manager.setRealm(realm);
        return manager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager manager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(manager);
        return authorizationAttributeSourceAdvisor;

    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager manager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorized");
        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        List<Permission> permissionList =  personMapper.findAllPermissionList();
        if(!CollectionUtils.isEmpty(permissionList)){
            permissionList.forEach(obj->filterChainDefinitionMap.put(obj.getUrl(),"perms["+obj.getName()+"]"));
        }
        filterChainDefinitionMap.put("/**","anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }



}
