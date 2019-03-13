package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(method = RequestMethod.POST, value = "/loginCheck")
    public ModelAndView loginCheck(@RequestParam("account") String uname,
                                   @RequestParam("password") String password){
        Integer res = loginService.checkLogin(uname, password);
        String[] message = {"SUCCESS", "账户不存在", "密码错误"};
        String[] view = {"index", "login", "login"};
        return new ModelAndView(view[res],"message",message[res]);
    }
}
