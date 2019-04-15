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
import java.util.Date;
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
        if (token == null || "".equals(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtils.validToken(token);
        if (!map.get(JWTUtils.params.STATUS.toString()).equals(JWTUtils.TokenStatus.Valid.toString())) {
            return null;
        }
        LoginInfor login = new LoginInfor();
        login.setStatus(map.get(JWTUtils.params.STATUS.toString()).toString());
        Map<String, Object> data = (Map<String, Object>) map.get(JWTUtils.params.DATA.toString());
        if (data != null) {
            Long userRoleIDd = (Long) data.get(JWTUtils.params.DATA_USERROLEID.toString());
            UserRole byId = userRoleService.findById(userRoleIDd);
            if (byId != null) {
                login.setUname(byId.getUserInfo().getUserName());
                login.setAccount(byId.getUserInfo().getAccount());
                login.setUserRoleId(byId.getId());
                login.setRole(byId.getRole());
                login.setLoginDate(new Date());
                login.setRoleName(byId.getRole().getRoleName());
                login.setUname(byId.getUserInfo().getUserName());
                login.setEmail(byId.getUserInfo().getEmail());
                login.setLoginDate(byId.getUserInfo().getLastLogin());
            }
            return login;
        }
        return null;
    }

    @Override
    public String getAccount() {
        LoginInfor logiInfo = getLogiInfo();
        if (logiInfo == null) {
            return null;
        }
        return logiInfo.getAccount();
    }
}