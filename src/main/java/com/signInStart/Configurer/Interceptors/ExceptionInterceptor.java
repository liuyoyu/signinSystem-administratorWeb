/**
 * created by
 * Date:2019/5/11
 **/
package com.signInStart.Configurer.Interceptors;

import com.signInStart.Entity.BaseClass.DataResult;
import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Utils.ResultUtils;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionInterceptor {

    org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(FriendlyException.class);

    @ExceptionHandler(FriendlyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public DataResult FriendlyExceptionHandler(FriendlyException e) {
        log.warn(e.getMessage());
        return ResultUtils.error(e.getMessage());
    }
}