package com.signInStart.Configurer.Interceptors;

import com.signInStart.Entity.BaseClass.HttpContent;
import com.signInStart.Utils.DataUtils;
import com.signInStart.Utils.RedisUtils;
import com.signInStart.Utils.ResultUtils;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Controller
@Component
public class LoginInterceptor implements HandlerInterceptor {

    Log log = LogFactory.getLog(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

        //不是映射到方法上，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader(HttpContent.Token);
        String value = null;
        if (token != null && !"undefined".equalsIgnoreCase(token)) {
             value = RedisUtils.get(token);//获取redis中的token
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        ServletOutputStream writer = response.getOutputStream();

        JSONObject map = new JSONObject();
        if (value == null) { //token为空或不匹配
            writer.print(ResultUtils.error(99,"SIGN IN, PLEASE!").toString());
            writer.flush();
            writer.close();
            return false;
        }
        if (!token.equals(value)) {
//            response.reset();
//            map.put("status", "99");
//            map.put("msg", "Time out!");
            writer.print(ResultUtils.error(99,"TIME OUT").toString());
            writer.flush();
            writer.close();
            return false;
        }
        String s = DataUtils.GetClientIP(request);
        log.info("用户:"+s+" 发送了一个请求："+request.getRequestURL().substring(request.getContextPath().length()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
