/**
 * created by
 * Date:2019/4/13
 **/
package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.BaseClass.HttpContent;
import com.blog.yongyu.demo.Entity.BaseClass.LoginInfor;
import com.blog.yongyu.demo.Entity.UserRole;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Service.UserRoleService;
import com.blog.yongyu.demo.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service("loginInfoService")
public class LoginInfoServiceImpl implements LoginInfoService {
    @Autowired
    UserRoleService userRoleService;


    @Override
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @Override
    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    @Override
    public String getRequestPath() {
        HttpServletRequest request = getRequest();
        String requestPath = request.getRequestURI();
        requestPath = requestPath.substring(request.getContextPath().length());
        return requestPath;
    }

    @Override
    public LoginInfor getLogiInfo() {
        HttpServletRequest request = getRequest();
        String token = request.getHeader(HttpContent.Token);
        if ("".equals(token) || token == null) {
            return null;
        }
        Map<String, Object> map = JWTUtils.validToken(token);
        if (!map.get(JWTUtils.params.STATUS.toString()).equals(JWTUtils.TokenStatus.Valid)) {
            return null;
        }
        LoginInfor login = new LoginInfor();
        login.setStatus(map.get(JWTUtils.params.STATUS.toString()).toString());
        Map<String,Object> data = (Map<String, Object>) map.get(JWTUtils.params.DATA.toString());
        if (data != null) {
            String userRoleIDd = (String) data.get(JWTUtils.params.DATA_USERROLEID.toString());
            UserRole byId = userRoleService.findById(Long.parseLong(userRoleIDd));
            if (byId != null) {
                login.setUser(byId.getUserInfo());
                login.setUserRoleId(byId.getId().toString());
                login.setRole(byId.getRole());
            }
            return login;
        }
        return null;
    }
}