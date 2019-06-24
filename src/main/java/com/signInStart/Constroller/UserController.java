/**
 * created by liuyoyu
 * Date:2019/4/11
 **/
package com.signInStart.Constroller;

import com.signInStart.Entity.BaseClass.Auth;
import com.signInStart.Entity.BaseClass.BaseSetting;
import com.signInStart.Entity.BaseClass.DataResult;
import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.DTO.SearchUserDTO;
import com.signInStart.Entity.Role;
import com.signInStart.Entity.UserInfo;
import com.signInStart.Entity.UserRole;
import com.signInStart.Service.LoginInfoService;
import com.signInStart.Service.RoleService;
import com.signInStart.Service.UserInfoService;
import com.signInStart.Service.UserRoleService;
import com.signInStart.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    LoginInfoService loginInfoService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;

    /**
     * @Author liuyoyu
     * @Description //TODO  新增用户
     * @Date 9:56 2019/5/26
     * @Param [userInfo]
     * @return DataResult
     **/
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    @Auth(BaseSetting.NOUSER)
    public DataResult insertUserInfo(UserInfo userInfo) throws FriendlyException {
        userInfoService.Insert(userInfo);
        return ResultUtils.success();
    }

    /**
     * @Author liuyoyu
     * @Description //TODO  删除用户，由管理员操作
     * @Date 10:24 2019/5/26
     * @Param [uid]
     * @return DataResult
     **/
    @RequestMapping(value = "/userInfo", method = RequestMethod.DELETE)
    @Auth(BaseSetting.NOUSER)
    public DataResult deleteUserInfo(@RequestParam("uid") Long uid) throws FriendlyException {
        userInfoService.Delete(uid);
        return ResultUtils.success();
    }
    /**
     * @Author liuyoyu
     * @Description //TODO  修改用户信息，由管理员操作
     * @Date 10:26 2019/5/26
     * @Param [userInfo]
     * @return DataResult
     **/
    @RequestMapping(value = "/userInfo", method = RequestMethod.PUT)
    @Auth(BaseSetting.NOUSER)
    public DataResult modifyUserInfo(UserInfo userInfo) throws FriendlyException {
        userInfoService.modify(userInfo);
        return ResultUtils.success();
    }

    /**
     * @Author liuyoyu
     * @Description //TODO  获取所有用户
     * @Date 10:28 2019/5/26
     * @Param []
     * @return DataResult
     **/
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public DataResult findAll() throws FriendlyException {
        List<UserInfo> allUser = userInfoService.findAll();

        return ResultUtils.success(allUser, allUser.size());
    }

    /**
     * @Author liuyoyu
     * @Description //TODO  重置密码，由管理员操作
     * @Date 10:29 2019/5/26
     * @Param [id]
     * @return DataResult
     **/
    @RequestMapping(value = "/pwd", method = RequestMethod.PUT)
    @Auth(BaseSetting.NOUSER)
    public DataResult resetPwd(@RequestParam("id") Long id) throws FriendlyException {
        UserInfo userById = userInfoService.findUserById(id); //获取用户
        userById.setInitPassword();     //初始化
        userInfoService.modify(userById);   //修改密码
        return ResultUtils.success("重置密码成功");
    }

    /**
     * @Author liuyoyu
     * @Description //TODO  批量重置密码，由管理员操作
     * @Date 10:29 2019/5/26
     * @Param [idList]
     * @return DataResult
     **/
    @RequestMapping(value = "/allPwd", method = RequestMethod.PUT)
    @Auth(BaseSetting.NOUSER)
    public DataResult allResetPwd(@RequestParam("idList") Long[] idList) throws FriendlyException {
        userInfoService.allResetPwd(idList);    //批量重置密码
        return ResultUtils.success("批量重置密码成功");
    }

    /**
     * @Author liuyoyu
     * @Description //TODO  增加用户新的角色，由管理员操作
     * @Date 23:22 2019/5/25
     * @Param [userId, roleId]
     * @return DataResult
     **/
    @RequestMapping(value = "/userRole", method = RequestMethod.POST)
    @Auth(BaseSetting.NOUSER)
    public DataResult addUserRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) throws FriendlyException {
        UserInfo user = userInfoService.findUserById(userId);   //查找用户
        Role role = roleService.findRoleById(roleId);           //查找角色
        UserRole userRole = new UserRole(user, role);           //创建新的用户角色
        userRoleService.addUserRole(userRole);                  //插入新的用户角色
        return ResultUtils.success("成功添加用户角色");
    }
    /**
     * @Author liuyoyu
     * @Description //TODO  通过用户id获取用户信息
     * @Date 23:23 2019/5/25
     * @Param [userId]
     * @return DataResult
     **/
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public DataResult findById(@RequestParam("userId") Long userId) throws FriendlyException {
        UserInfo user = userInfoService.findUserById(userId);
        return ResultUtils.success(user);
    }

    /**
     * @Author liuyoyu
     * @Description //TODO  通过token获取用户信息，前台验证使用
     * @Date 23:23 2019/5/25
     * @Param []
     * @return DataResult
     **/
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public DataResult getTokenInfo() throws FriendlyException {
        return ResultUtils.success(loginInfoService.getUserInfo());
    }
}