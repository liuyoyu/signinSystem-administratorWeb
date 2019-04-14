package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.BaseClass.HttpContent;
import com.blog.yongyu.demo.Entity.BaseClass.LoginInfor;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Entity.UserRole;
import com.blog.yongyu.demo.Service.*;
import com.blog.yongyu.demo.Utils.EmailUtils;
import com.blog.yongyu.demo.Utils.JWTUtils;
import com.blog.yongyu.demo.Utils.RedisUtils;
import com.blog.yongyu.demo.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    ShortMessageService shortMessageService;
    @Autowired
    LoginInfoService loginInfoService;

    /**
     * 用户登陆
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loginCheck")
    public DataResult loginCheck(@RequestParam("account") String account,
                                 @RequestParam("password") String password) {
        UserInfo userInfo = loginService.checkLogin(account, password);
        if (userInfo != null) {
            UserRole defaultRoleByUserId = userRoleService.findDefaultRoleByUserId(userInfo.getId());
            String token = JWTUtils.generateToken(userInfo.getId(),defaultRoleByUserId.getId());//token携带用户id和用户角色id

            RedisUtils.setex(HttpContent.Token, token, RedisUtils.ValidTime);//缓存中加入token，有效时长为7天
            return ResultUtils.success(token);//返回token
        }

        return ResultUtils.error(1, "用户不存在或密码错误");
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login_signup", method = RequestMethod.POST)
    public DataResult create(UserInfo user) {
        String[] createMsg = {"创建成功", "添加账户不能为空", "账户不能为空", "该账户已被注册", "该邮箱已被注册", "密码不能为空", "邮箱不能为空"};
        Integer res = userInfoService.Insert(user);
        if (res != 0) {
            return ResultUtils.error(res, createMsg[res]);
        }
        return ResultUtils.success();
    }

    /**
     * 发送邮箱验证码
     *
     * @param receiver
     * @param
     * @return
     */
    @RequestMapping(value = "/login_sendEmail",method = RequestMethod.POST)
    public DataResult sendEmail(@RequestParam("receiver") String receiver) {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        if (logiInfo == null) {
            return ResultUtils.error(1, "请先登陆");
        }
        UserInfo userByAccount = userInfoService.findUserByAccount(logiInfo.getAccount());
        if (userByAccount == null) {
            return ResultUtils.error(2, "账号不存在");
        }
        if (userByAccount.getEmail() == null || !userByAccount.getEmail().equals(receiver)) {
            return ResultUtils.error(3, "该邮箱不是注册邮箱");
        }

        Integer res = shortMessageService.sendEmailMessage(logiInfo.getAccount(), receiver, "重置密码");
        String[] msg = {"成功", "接收人不能为空", "短信功能异常"};
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, msg[res]);
    }

    /**
     * 初始化密码：8888
     * @param code
     * @return
     */
    @RequestMapping(value = "/login_resetPwd", method = RequestMethod.POST)
    public DataResult VerifyMsgCode(@RequestParam("code") String code) {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        if (logiInfo == null) {
            return ResultUtils.error(2, "请先登陆");
        }
        String account = logiInfo.getAccount();
        String email = logiInfo.getEmail();
        Integer res = shortMessageService.verifyEmailMessage(code, account, email);
        if (res == 0) {
            UserInfo userByAccount = userInfoService.findUserByAccount(account);
            userByAccount.setInitPassword();
            userInfoService.modify(userByAccount);
            return ResultUtils.success();
        }
        return ResultUtils.error(1, "验证码错误");
    }

//
//    @RequestMapping(value = "login_remove",method = RequestMethod.POST)
//    public DataResult removeUser(@RequestParam("id") Long id) {
//        Optional<UserInfo> userById = userInfoService.findUserById(id);
//        if (!userById.isPresent()) {
//            ResultUtils.error(1, "用户不能存在");
//        }
//        Integer res = userInfoService.Delete(userById.get().getId());
//        if (res == 0) {
//            return ResultUtils.success();
//        }
//        return ResultUtils.error(2, "删除失败");
//    }

    /**
     * 注销登陆
     *
     * @return
     */
    @RequestMapping("/logOut")
    public DataResult loginOut(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HttpContent.Token);
        if (token != null || "".equals(token)) {
            RedisUtils.del("token");
        }
//        String path = request.getContextPath();
//        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//        try {
//            response.sendRedirect(basePath + "#/login");
//        } catch (IOException e) {
//            return ResultUtils.error(1, "注销失败");
//        }
        return ResultUtils.success();//return "/login.html";
    }

    @RequestMapping("/getLoginRole")
    public DataResult getLoginRole(HttpServletRequest request) {
        String token = request.getHeader(HttpContent.Token);
        if (token == null || token.equals("")) {
            return ResultUtils.error(1, "未登录!");
        }
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        if (logiInfo == null) {
            return ResultUtils.error(1, "请重新登陆");
        }
//        Map<String, Object> tokenMsg = JWTUtils.validToken(token);
//        if (tokenMsg.get(JWTUtils.params.STATUS.toString()).equals(JWTUtils.TokenStatus.Valid.toString())) {
//            return ResultUtils.success(tokenMsg.get(JWTUtils.params.DATA_USERROLEID));
//        }
        return ResultUtils.success(logiInfo);
    }
}
