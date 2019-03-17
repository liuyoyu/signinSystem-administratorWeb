package com.blog.yongyu.demo.Configurer.Interceptors;

import com.blog.yongyu.demo.Entity.HttpContent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取项目根目录
        String basePath = request.getContextPath();
        //获取请求路径：根目录到结尾
        String path = request.getRequestURI();
        HttpSession session = request.getSession();
        String Users = (String) session.getAttribute(HttpContent.userId);
        if (Users == null) {
            response.sendRedirect(basePath + "/login.html");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
