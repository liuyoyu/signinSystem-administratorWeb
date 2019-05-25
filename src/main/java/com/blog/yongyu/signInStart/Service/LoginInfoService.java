/**
 * created by
 * Date:2019/4/13
 **/
package com.blog.yongyu.signInStart.Service;

import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.BaseClass.LoginInfor;
import com.blog.yongyu.signInStart.Entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginInfoService {

    HttpServletRequest getRequest()throws FriendlyException;

    HttpServletResponse getResponse()throws FriendlyException ;

    String getRequestPath()throws FriendlyException ;

    LoginInfor getLogiInfo()throws FriendlyException ;

    String getAccount()throws FriendlyException ;

    Boolean checkAdmin()throws FriendlyException ;

    Boolean checkSupperAdimn()throws FriendlyException ;

    Long getCurrRoleID()throws FriendlyException ;

    Boolean checkUser()throws FriendlyException ;

    UserInfo getUserInfo() throws FriendlyException;
}