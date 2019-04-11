package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.BaseClass.HttpContent;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Entity.UserRole;
import com.blog.yongyu.demo.Service.LoginService;
import com.blog.yongyu.demo.Service.ShortMessageService;
import com.blog.yongyu.demo.Service.UserInfoService;
import com.blog.yongyu.demo.Service.UserRoleService;
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
public class LoginController{
    @Autowired
    LoginService loginService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    ShortMessageService shortMessageService;
    /**
     * 用户登陆
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loginCheck")
    public DataResult loginCheck(@RequestParam("account") String uname,
                                 @RequestParam("password") String password){
        UserInfo userInfo = loginService.checkLogin(uname, password);
        if (userInfo != null) {
            UserRole defaultRoleByUserId = userRoleService.findDefaultRoleByUserId(userInfo.getId());
            String token = JWTUtils.generateToken(defaultRoleByUserId.getId(),userInfo.getId());//token携带用户id和用户角色id
            RedisUtils.setex("token",token,RedisUtils.ValidTime);//缓存中加入token，有效时长为7天
            return ResultUtils.success(token);//返回token
        }

        return ResultUtils.error(1,"用户不存在或密码错误");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/login_signup",method = RequestMethod.POST)
    public DataResult create(UserInfo user){
        String[] createMsg = {"创建成功", "添加账户不能为空", "账户不能为空","该账户已被注册","该邮箱已被注册"};
        Integer res = userInfoService.Insert(user);
        if (res != 0) {
            return ResultUtils.error(res,createMsg[res]);
        }
        return ResultUtils.success();
    }

    /**
     * 发送邮箱验证码
     * @param receiver
     * @param account
     * @return
     */
    @RequestMapping("/login_sendEmail")
    public DataResult sendEmail(@RequestParam("receiver") String receiver,
                                @RequestParam("account") String account){
        if (account == null || "".equals(account)) {
            return ResultUtils.error(1, "账号不能为空");
        }
        UserInfo userByAccount = userInfoService.findUserByAccount(account);
        if (userByAccount == null) {
            return ResultUtils.error(2, "账号不存在");
        }
        if (userByAccount.getEmail()==null || !userByAccount.getEmail().equals(receiver)) {
            return ResultUtils.error(3, "该邮箱不是注册邮箱");
        }

        Integer res = shortMessageService.sendEmailMessage(account, receiver, "重置密码");
        String[] msg = {"成功", "接收人不能为空", "短信功能异常"};
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, msg[res]);
    }

    /**
     * 初始化密码：8888
     * @param code
     * @param account
     * @param email
     * @return
     */
    @RequestMapping("/login_verifyMsgCode")
    public DataResult VerifyMsgCode(@RequestParam("code")String code,
                                    @RequestParam("account")String account,
                                    @RequestParam("email")String email) {
        Integer res = shortMessageService.verifyEmailMessage(code, account, email);
        if (res == 0) {
            UserInfo userByAccount = userInfoService.findUserByAccount(account);
            userByAccount.setInitPassword();
            userInfoService.modify(userByAccount);
            return ResultUtils.success();
        }
        return ResultUtils.error(1, "验证码错误");
    }


    @RequestMapping(value = "login_remove",method = RequestMethod.POST)
    public DataResult removeUser(@RequestParam("id") Long id) {
        Optional<UserInfo> userById = userInfoService.findUserById(id);
        if (!userById.isPresent()) {
            ResultUtils.error(1, "用户不能存在");
        }
        Integer res = userInfoService.Delete(userById.get().getId());
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(2, "删除失败");
    }

    /**
     * 注销登陆
     * @return
     */
    @RequestMapping("/logOut")
    public DataResult loginOut(HttpServletRequest request, HttpServletResponse response) {
        RedisUtils.del("token");
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        try {
            response.sendRedirect(basePath + "#/login");
        } catch (IOException e) {
            return ResultUtils.error(1, "注销失败");
        }
        return ResultUtils.success();//return "/login.html";
    }

    @RequestMapping("/getLoginRole")
    public DataResult getLoginRole(HttpServletRequest request){
        String token = request.getHeader(HttpContent.Token);
        Map<String, Object> tokenMsg = JWTUtils.validToken(token);
        if (tokenMsg.get(JWTUtils.params.STATUS.toString()).equals(JWTUtils.TokenStatus.Valid.toString())) {
            return ResultUtils.success(tokenMsg.get(JWTUtils.params.DATA_USERROLEID));
        }
        return ResultUtils.success(null);
    }
}
