package com.signInStart.Constroller;

import com.signInStart.Entity.BaseClass.*;
import com.signInStart.Entity.UserInfo;
import com.signInStart.Entity.UserRole;
import com.signInStart.Repository.UserInfoRepository;
import com.signInStart.Service.*;
import com.signInStart.Utils.*;
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
    public DataResult create(UserInfo user,@RequestParam("code")String code) throws FriendlyException {
        if (DataUtils.isEmptyString(code)) {
            throw new FriendlyException("验证码为空，请填入验证码", DataUtils.CurrentMethodName());
        }
//        if (user.getEmail() != null) {
//            String key = user.getEmail()+"_"+MsgContent.TYPE.LOGIN.toString();
//            String s = RedisUtils.get(key); //从redis缓存中那取验证吗
//            if (!code.equals(s)) {
//                throw new FriendlyException("验证码错误", DataUtils.CurrentMethodName());
//            }
//            RedisUtils.del(key);//删除相关验证码
//        }
        userInfoService.Insert(user);
        return ResultUtils.success("注册成功");
    }
    /**
     * @Author liuyoyu
     * @Description //TODO  发送验证邮件
     * @Date 23:19 2019/6/11
     * @Params [receiver]
     * @return com.signInStart.Entity.BaseClass.DataResult
     **/
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public DataResult sendMsg(@RequestParam("receiver")String receiver, @RequestParam("type")String type) throws FriendlyException {
        if (DataUtils.isEmptyString(receiver)) {
            throw new FriendlyException("短信接收人不能为空", DataUtils.CurrentMethodName());
        }
        String s = EmailUtils.editEmail(receiver);
//        RedisUtils.set(receiver, s); //将用户的验证码存在缓存中
        MsgContent msgContent = new MsgContent(receiver,type);
        RedisUtils.setex(msgContent.toString(), s, 10*60); //将用户的验证码存在缓存中
        return ResultUtils.success("验证码发送成功，请及时查看");
    }

//    /**
//     *
//     * @param receiver
//     * @param
//     * @return
//     */
//    @RequestMapping(value = "/email",method = RequestMethod.GET)
//    public DataResult sendEmail(@RequestParam("receiver") String receiver,
//                                @RequestParam("account") String account) throws FriendlyException {
//        userInfoService.findUserByAccount(account,receiver);
//
//        shortMessageService.sendEmailMessage(account, receiver, "重置密码");
//        return ResultUtils.success();
//    }

    /**
     * 重置密码
     * @param code
     * @return
     */
//    @RequestMapping(value = "/pwd", method = RequestMethod.PUT)
//    public DataResult VerifyMsgCode(@RequestParam("code") String code,
//                                    @RequestParam("pwd") String pwd,
//                                    @RequestParam("account")String account,
//                                    @RequestParam("email")String email) throws FriendlyException {
//
//        Integer res = shortMessageService.verifyEmailMessage(code, account, email);
//        if (res == 0) {
//            UserInfo userByAccount = userInfoService.findUserByAccount(account);
//            userByAccount.setNewPassword(pwd);
//            userInfoRepository.save(userByAccount);
//            return ResultUtils.success();
//        }
//        return ResultUtils.error(1, "验证码错误");
//    }

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
    /**
     * @Author liuyoyu
     * @Description //TODO  获取用户信息
     * @Date 20:57 2019/6/12
     * @Params []
     * @return com.signInStart.Entity.BaseClass.DataResult
     **/
    @RequestMapping(value = "/loginRole",method = RequestMethod.GET)
    public DataResult getLoginRole() throws FriendlyException  {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        return ResultUtils.success(logiInfo);
    }
    /**
     * @Author liuyoyu
     * @Description //TODO  忘记密码
     * @Date 21:00 2019/6/12
     * @Params [account, password, code验证码]
     * @return com.signInStart.Entity.BaseClass.DataResult
     **/
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public DataResult resetPassword(@RequestParam("account") String account,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password,
                                    @RequestParam("code") String code) throws FriendlyException{
        shortMessageService.verifyEmailMessage(code, email+"_"+MsgContent.TYPE.FORGETPWD.toString());

        loginService.resetPassword(account,email, password);

        return ResultUtils.success("修改密码成功");
    }
}
