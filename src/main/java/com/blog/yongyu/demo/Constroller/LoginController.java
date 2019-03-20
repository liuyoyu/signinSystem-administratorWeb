package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.DataResult;
import com.blog.yongyu.demo.Entity.HttpContent;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Service.LoginService;
import com.blog.yongyu.demo.Service.UserInfoService;
import com.blog.yongyu.demo.Utils.EmailUtils;
import com.blog.yongyu.demo.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController{
    @Autowired
    LoginService loginService;
    @Autowired
    UserInfoService userInfoService;

    private static String[] message = {"SUCCESS", "账户不存在 | 密码错误"};
    @RequestMapping(method = RequestMethod.POST, value = "/loginCheck")
    public DataResult loginCheck(@RequestParam("account") String uname,
                                 @RequestParam("password") String password,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        Cookie cookie = new Cookie("account", uname);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*7);
        response.addCookie(cookie);
        UserInfo res = loginService.checkLogin(uname, password);
        if (res != null) {
            request.getSession().setAttribute(HttpContent.userId, res.getId());
            return ResultUtils.success();
        }

        return ResultUtils.error(1,message[1]);
    }

    private static String[] createMsg = {"创建成功", "用户已存在", "不能为空"};
    @RequestMapping(value = "/login/signup",method = RequestMethod.POST)
    public DataResult create(UserInfo user){
        if (user == null) {
            return ResultUtils.error(2,createMsg[2]);
        }
        UserInfo userInfo = userInfoService.createUser(user);
        if (userInfo == null) {
            return ResultUtils.error(1,createMsg[1]);
        }
        return ResultUtils.success();
    }

    /**
     * 找回密码
     * @param account
     * @return
     */
    @RequestMapping(value = "/login/forgetPwd",method = RequestMethod.GET)
    public DataResult forgetPwd(@RequestParam("account") String account) {
        UserInfo user = userInfoService.findUserByAccount(account);
        if (user == null) {
            return  ResultUtils.error(1,"用户不存在");
        }
        Map<String, String> data = new HashMap<>();
        data.put("account", user.getAccount());
        data.put("pwd", user.getPwd());
        return ResultUtils.success(data);
    }

    @RequestMapping("/login/sendEmail")
    public DataResult sendEmail(@RequestParam("receiver") String receiver,
                                @RequestParam("account") String account,
                                HttpServletRequest request){
        if (account == null || "".equals(account)) {
            return ResultUtils.error(1, "账号不能为空");
        }
        //从数据库中查看相应的邮箱账号
        UserInfo user = userInfoService.findUserByAccount(account);
        if (user == null) {
            return ResultUtils.error(2, "账号为空");
        }
        //
        HttpSession session = request.getSession();
        try {
            session.setAttribute(HttpContent.emailCode, EmailUtils.editEmail(receiver));
        } catch (Exception e) {
            e.printStackTrace();
//            return ResultUtils.error(1, "邮件可能被当垃圾邮件处理，请更换邮箱");
            return ResultUtils.error(3, e.getMessage());
        }
        return ResultUtils.success();
    }

    /**
     * 注销登陆
     * @return
     */
    @RequestMapping("/logOut")
    public DataResult loginOut(HttpServletRequest request){
        request.getSession().invalidate();
        return ResultUtils.success();//return "/login.html";
    }
}
