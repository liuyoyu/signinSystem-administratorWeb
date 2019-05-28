package com.signInStart.Constroller;

import com.signInStart.Entity.BaseClass.*;
import com.signInStart.Entity.UserInfo;
import com.signInStart.Entity.UserRole;
import com.signInStart.Repository.UserInfoRepository;
import com.signInStart.Service.*;
import com.signInStart.Utils.JWTUtils;
import com.signInStart.Utils.RedisUtils;
import com.signInStart.Utils.ResultUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/register")
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
    @Autowired
    UserInfoRepository userInfoRepository;

    /**
     * 用户登陆
     */
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public DataResult loginCheck(@RequestParam("account") String account,
                                 @RequestParam("password") String password) throws FriendlyException {
        UserInfo userInfo = loginService.checkLogin(account, password);
        if (userInfo != null) {
            if (userInfo.getStatus().equals(BaseSetting.STATUS.Disabled_SYS.toString())) {
                throw new FriendlyException("该用户被禁用", 1);
            }
            UserRole defaultRoleByUserId = userRoleService.findDefaultRoleByUserId(userInfo.getId());
            String token = JWTUtils.generateToken(userInfo.getId(),defaultRoleByUserId.getId());//token携带用户id和用户角色id

            RedisUtils.setex(token, token, RedisUtils.ValidTime);//缓存中加入token，有效时长为7天 ；以token为键来存
            JSONObject js = new JSONObject();
            js.put("token", token);
            js.put("account", account);
            js.put("userName", defaultRoleByUserId.getUserName());
            js.put("userId", defaultRoleByUserId.getUserId());
            return ResultUtils.success(js);
        }
        throw new FriendlyException("用户名错误或密码错误", 1);
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DataResult create(UserInfo user) throws FriendlyException {
        userInfoService.Insert(user);
        return ResultUtils.success();
    }

    /**
     * 发送邮箱验证码
     * @param receiver
     * @param
     * @return
     */
    @RequestMapping(value = "/email",method = RequestMethod.GET)
    public DataResult sendEmail(@RequestParam("receiver") String receiver,
                                @RequestParam("account") String account) throws FriendlyException {
        userInfoService.findUserByAccount(account,receiver);

        shortMessageService.sendEmailMessage(account, receiver, "重置密码");
        return ResultUtils.success();
    }

    /**
     * 重置密码
     * @param code
     * @return
     */
    @RequestMapping(value = "/pwd", method = RequestMethod.PUT)
    public DataResult VerifyMsgCode(@RequestParam("code") String code,
                                    @RequestParam("pwd") String pwd,
                                    @RequestParam("account")String account,
                                    @RequestParam("email")String email) throws FriendlyException {

        Integer res = shortMessageService.verifyEmailMessage(code, account, email);
        if (res == 0) {
            UserInfo userByAccount = userInfoService.findUserByAccount(account);
            userByAccount.setNewPassword(pwd);
            userInfoRepository.save(userByAccount);
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
    @RequestMapping(value = "/logOut",method = RequestMethod.GET)
    public DataResult loginOut(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HttpContent.Token);
        if (token != null || !"".equals(token)) {
            RedisUtils.del(token);
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

    @RequestMapping(value = "/loginRole",method = RequestMethod.GET)
    public DataResult getLoginRole() throws FriendlyException  {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        return ResultUtils.success(logiInfo);
    }
}
