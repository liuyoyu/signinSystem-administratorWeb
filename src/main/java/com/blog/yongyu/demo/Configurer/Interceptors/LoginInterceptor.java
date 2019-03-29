package com.blog.yongyu.demo.Configurer.Interceptors;

import com.blog.yongyu.demo.Entity.HttpContent;
import com.blog.yongyu.demo.Utils.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";

        //不是映射到方法上，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //token验证
        String token = request.getHeader(HttpContent.header_AUTHORIZATION);
        if ("".equals(token) || token == null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charaset=utf-8");
            response.sendRedirect(basePath+"/login.html");
            return false;
        }
        Map<String, Object> dataMap = JWTUtils.validToken(token);
        if (!dataMap.get(JWTUtils.params.STATUS.toString()).equals(JWTUtils.TokenStatus.Valid)) {  //token验证失败
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charaset=utf-8");
            response.sendRedirect(basePath+"/login.html");
            return false;
        }
        HttpSession session = request.getSession();
        session.setAttribute(HttpContent.userId, dataMap.get(JWTUtils.params.DATA_USERID.toString()));
        session.setAttribute(HttpContent.userRole, dataMap.get(JWTUtils.params.DATA_USERROLEID.toString()));
//        request.setAttribute(HttpContent.header_AUTHORIZATION, );
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
