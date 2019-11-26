package cn.tourism.back.core;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @description:
 * @author: xinYue
 * @time: 2019/11/23 10:31
 */
@Component
public class CredentialMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        if(Objects.isNull(usernamePasswordToken)){
            throw new AuthenticationException();
        }
        String password = new String(usernamePasswordToken.getPassword());
        String dbPassword = (String)info.getCredentials();
        if(!this.equals(password,dbPassword)){
            throw new AuthenticationException();
        }
        return this.equals(password,dbPassword);
    }

}
