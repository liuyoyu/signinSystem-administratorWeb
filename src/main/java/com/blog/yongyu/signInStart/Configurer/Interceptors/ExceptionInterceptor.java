/**
 * created by
 * Date:2019/5/11
 **/
package com.blog.yongyu.signInStart.Configurer.Interceptors;

import com.blog.yongyu.signInStart.Entity.BaseClass.DataResult;
import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionInterceptor {

    @ExceptionHandler(FriendlyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public DataResult FriendlyExceptionHandler(FriendlyException e) {
        return ResultUtils.error(e.getErrorCode(), e.getMessage());
    }
}