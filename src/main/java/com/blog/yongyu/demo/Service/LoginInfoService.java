/**
 * created by
 * Date:2019/4/13
 **/
package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.BaseClass.LoginInfor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginInfoService {

    HttpServletRequest getRequest();
    HttpServletResponse getResponse();
    String getRequestPath();
    LoginInfor getLogiInfo();
    String getAccount();
}