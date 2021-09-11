package com.zking.ssm.shiro;

import com.zking.ssm.model.User;
import com.zking.ssm.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取参数中提交过来的账号密码
        String username = authenticationToken.getPrincipal().toString();
        String password = authenticationToken.getCredentials().toString();
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        //通过用户账号，找到该用户在数据库账号所有数据
        User u = userService.login(user);
        if(null==u){
            throw new RuntimeException("账号不存在");
        }
        //将找到的数据封装到身份验证信息类中
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(
                u.getUsername(),u.getPassword(), ByteSource.Util.bytes(u.getSalt()),this.getName());
        return authenticationInfo;
    }
}
