package com.gbq.hospital.config.shiro;

import com.alibaba.druid.util.StringUtils;
import com.gbq.hospital.dao.LoginMapper;
import com.gbq.hospital.entity.Login;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


/**
 * @author aqian666
 */
public class MyShiroRealm extends AuthorizingRealm{

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private HttpServletRequest request;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
	    //获取用户信息
	    String username = authenticationToken.getPrincipal().toString();
        String password = new String((char[]) authenticationToken.getCredentials());
        Login user = loginMapper.findByUsername(username);

        //验证登录账号
        if (user == null) {
            throw new UnknownAccountException("用户名不存在");
        }
        //验证密码
        if(!StringUtils.equals(user.getPassword(), password)){
            throw new UnknownAccountException("密码错误，请重新输入");
        }

        //把登录人存进session
        SecurityUtils.getSubject().getSession().setAttribute("currentManager", user);
        return new SimpleAuthenticationInfo(user, password, this.getName());
    }

}
