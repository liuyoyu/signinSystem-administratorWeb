/**
 * created by
 * Date:2019/4/13
 **/
package com.signInStart.Service.Impl;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.BaseClass.HttpContent;
import com.signInStart.Entity.BaseClass.LoginInfor;
import com.signInStart.Entity.UserInfo;
import com.signInStart.Entity.UserRole;
import com.signInStart.Service.LoginInfoService;
import com.signInStart.Service.RoleService;
import com.signInStart.Service.UserInfoService;
import com.signInStart.Service.UserRoleService;
import com.signInStart.Utils.JWTUtils;
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
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RoleService roleService;


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
    public LoginInfor getLogiInfo()throws FriendlyException  {
        HttpServletRequest request = getRequest();
        String token = request.getHeader(HttpContent.Token);
        if (token == null || "".equals(token)) {
            throw new FriendlyException("请先登陆",1);
        }
        Map<String, Object> map = JWTUtils.validToken(token);
        if (!map.get(JWTUtils.params.STATUS.toString()).equals(JWTUtils.TokenStatus.Valid.toString())) {
            throw new FriendlyException("登陆过期，请重新进行登陆",1);
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
                login.setUserId(byId.getUserId());
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
    public String getAccount()throws FriendlyException  {
        LoginInfor logiInfo = getLogiInfo();
        if (logiInfo == null) {
            return null;
        }
        return logiInfo.getAccount();
    }

    @Override
    public Boolean checkAdmin() throws FriendlyException {
        String account = getAccount();
        UserInfo user = userInfoService.findUserByAccount(account);
        if (user == null) {
            return false;
        }
        return userRoleService.isAdmin(user.getId());
    }

    @Override
    public Boolean checkSupperAdimn() throws FriendlyException {
        String account = getAccount();
        UserInfo user = userInfoService.findUserByAccount(account);
        if (user == null) {
            return false;
        }
        return userRoleService.isSupperAdmin(user.getId());
    }

    @Override
    public Long getCurrRoleID()throws FriendlyException  {
        LoginInfor logiInfo = getLogiInfo();
        if (logiInfo == null) {
            return null;
        }
        return logiInfo.getRoleId();
    }

    @Override
    public Boolean checkUser() throws FriendlyException  {
        return !checkAdmin() && !checkSupperAdimn();
    }

    @Override
    public UserInfo getUserInfo() throws FriendlyException {
        UserInfo user = userInfoService.findUserByAccount(getAccount());
        if (user == null) {
            throw new FriendlyException("用户信息不存在，请重新登陆",1);
        }
        return user;
    }

    @Override
    public String getCurrRoleName() throws FriendlyException {
        return  roleService.findRoleById(getCurrRoleID()).getRoleName();
    }

    @Override
    public String getCurrUserType() throws FriendlyException {
        return roleService.findRoleById(getCurrRoleID()).getUserType();
    }
}