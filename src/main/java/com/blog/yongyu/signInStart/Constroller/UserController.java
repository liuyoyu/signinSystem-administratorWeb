/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.signInStart.Constroller;

import com.blog.yongyu.signInStart.Entity.BaseClass.DataResult;
import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.Role;
import com.blog.yongyu.signInStart.Entity.UserInfo;
import com.blog.yongyu.signInStart.Entity.UserRole;
import com.blog.yongyu.signInStart.Service.LoginInfoService;
import com.blog.yongyu.signInStart.Service.RoleService;
import com.blog.yongyu.signInStart.Service.UserInfoService;
import com.blog.yongyu.signInStart.Service.UserRoleService;
import com.blog.yongyu.signInStart.Utils.ResultUtils;
import net.minidev.json.JSONObject;
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
     * 新增用户，由管理员操作
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public DataResult insertUserInfo(UserInfo userInfo) throws FriendlyException {
        if (!loginInfoService.checkAdmin()) {
            return ResultUtils.error(9, "没有权限");
        }
        userInfoService.Insert(userInfo);
        return ResultUtils.success();
    }

    /**
     * 删除用户
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.DELETE)
    public DataResult deleteUserInfo(@RequestParam("uid") Long uid) throws FriendlyException {
        userInfoService.Delete(uid);
        return ResultUtils.success();
    }

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.PUT)
    public DataResult modifyUserInfo(UserInfo userInfo) throws FriendlyException {
        userInfoService.modify(userInfo);
        return ResultUtils.success();
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public DataResult findAll() throws FriendlyException {
        List<UserRole> allUserRole = userRoleService.findAll();
        Map<String, Integer> map = new HashMap<>();
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < allUserRole.size(); i++) {
            UserRole ur = allUserRole.get(i);
            JSONObject jsonObject = new JSONObject();
            if (map.containsKey(ur.getAccount())) {
                int loc = map.get(ur.getAccount());
                JSONObject tmp = list.get(loc);
                List<String> rn = (List<String>) tmp.get("userType");
                rn.add(ur.getRoleName());
                tmp.put("userType", rn);
            } else {
                //未出现重复账户时
                List<String> roleName = new ArrayList<>();
                roleName.add(ur.getRoleName());
                jsonObject.put("userType", roleName);
                jsonObject.put("userId", ur.getUserId());
                jsonObject.put("account", ur.getAccount());
                jsonObject.put("userName", ur.getUserName());
                jsonObject.put("sex", ur.getSex());
                jsonObject.put("email", ur.getEmail());
                jsonObject.put("phone", ur.getPhone());
                jsonObject.put("status", ur.getUserStatus());
                jsonObject.put("lastLogin", ur.getLastLogin());
                map.put(ur.getAccount(), i);
                list.add(jsonObject);
            }
        }
        return ResultUtils.success(list, list.size());
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/pwd", method = RequestMethod.PUT)
    public DataResult resetPwd(@RequestParam("id") Long id) throws FriendlyException {
        UserInfo userById = userInfoService.findUserById(id);
        userById.setInitPassword();
        userInfoService.modify(userById);
        return ResultUtils.success();
    }

    /**
     * 批量重置密码
     *
     * @param idList
     * @return
     */
    @RequestMapping(value = "/allPwd", method = RequestMethod.PUT)
    public DataResult allResetPwd(@RequestParam("idList") Long[] idList) throws FriendlyException {
        userInfoService.allResetPwd(idList);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/userRole", method = RequestMethod.POST)
    public DataResult addUserRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) throws FriendlyException {
        UserInfo user = userInfoService.findUserById(userId);
        Role role = roleService.findRoleById(roleId);
        UserRole userRole = new UserRole(user, role);
        userRoleService.addUserRole(userRole);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public DataResult findById(@RequestParam("userId") Long userId) throws FriendlyException {
        UserInfo user = userInfoService.findUserById(userId);
        return ResultUtils.success(user);
    }

    /**
     * @return com.blog.yongyu.signInStart.Entity.BaseClass.DataResult
     * @Author liuyoyu
     * @Description //TODO
     * @Date 22:15 2019/5/25 通过传入的token，进行解析和验证，返回验证结果
     * @Param
     **/
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public DataResult getTokenInfo() throws FriendlyException {
        return ResultUtils.success(loginInfoService.getUserInfo());
    }
}