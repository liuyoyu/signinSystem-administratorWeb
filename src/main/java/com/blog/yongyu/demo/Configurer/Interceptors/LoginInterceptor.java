package com.blog.yongyu.demo.Configurer.Interceptors;

import com.blog.yongyu.demo.Entity.BaseClass.HttpContent;
import com.blog.yongyu.demo.Utils.JWTUtils;
import com.blog.yongyu.demo.Utils.RedisUtils;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@Component
@Log4j2
public class LoginInterceptor implements HandlerInterceptor {
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
        if (token != null) {
             value = RedisUtils.get("token");//获取redis中的token
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();

        JSONObject map = new JSONObject();
        if (value == null ) { //token为空或不匹配
            map.put("status", "99");
            map.put("msg", "请先登陆");
            writer.write(map.toString());
//            response.sendRedirect(basePath + "#/login");
            return false;
        }
        if (!token.equals(value)) {
            map.put("status", "99");
            map.put("msg", "登陆超时，请重新登陆");
            writer.write(map.toString());
//            response.sendRedirect(basePath + "#/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
