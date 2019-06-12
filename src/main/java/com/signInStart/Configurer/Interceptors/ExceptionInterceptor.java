/**
 * created by
 * Date:2019/5/11
 **/
package com.signInStart.Configurer.Interceptors;

import com.signInStart.Entity.BaseClass.DataResult;
import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Utils.ResultUtils;
import com.sun.mail.smtp.SMTPSendFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionInterceptor {

    org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(FriendlyException.class);

    @ExceptionHandler(FriendlyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public DataResult FriendlyExceptionHandler(FriendlyException e) {
        log.warn("----------:"+e.getMethodName()+"方法出现友好异常，异常信息："+e.getMessage());
        return ResultUtils.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public DataResult OtherExceptionHandler(Exception e) {
        log.warn("----------:统一异常拦截器，拦截到异常："+e.getMessage());
        return ResultUtils.error("ε=ε=ε=(~￣▽￣)~ 出现未知异常，苦逼的yoyu正在抓紧时间处理....");
    }
}