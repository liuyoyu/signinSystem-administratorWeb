/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.BaseSetting;
import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.BaseClass.LoginInfor;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Entity.UserRole;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Service.UserInfoService;
import com.blog.yongyu.demo.Service.UserRoleService;
import com.blog.yongyu.demo.Utils.ResultUtils;
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
    public DataResult insertUserInfo(UserInfo userInfo) {
        if (!loginInfoService.checkAdmin()) {
            return ResultUtils.error(9, "没有权限");
        }
        Integer res = userInfoService.Insert(userInfo);
        String[] msg = {"创建成功", "添加账户不能为空", "账户不能为空", "该账户已被注册", "该邮箱已被注册", "密码不能为空", "邮箱不能为空", "账号长度要大于4小于10"};
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, msg[res]);
    }

    /**
     * 删除用户
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.DELETE)
    public DataResult deleteUserInfo(@RequestParam("uid") Long uid) {
        if (!loginInfoService.checkAdmin()) {
            return ResultUtils.error(2, "没有权限");
        }
        Integer res = userInfoService.Delete(uid);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"成功", "删除对象不存在", "没有权限"};
        return ResultUtils.error(res, msg[res]);
    }

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.PUT)
    public DataResult modifyUserInfo(UserInfo userInfo) {
        Integer res = userInfoService.modify(userInfo);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"修改成功", "修改对象不存在", "邮箱已被占用", "邮箱不能为空", "没有权限", "该用户为管理员，没有权限修改"};
        return ResultUtils.error(res, msg[res]);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public DataResult findAll() {
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
    public DataResult resetPwd(@RequestParam("id") Long id) {
        UserInfo userById = userInfoService.findUserById(id);
        if (userById == null) {
            return ResultUtils.error(1, "用户不存在");
        }
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
    public DataResult allResetPwd(@RequestParam("idList") Long[] idList) {
        if (idList == null || idList.length < 1) {
            return ResultUtils.error(1, "重置密码名单为空");
        }
        userInfoService.allResetPwd(idList);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/userRole",method = RequestMethod.POST)
    public DataResult addUserRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        UserInfo user = userInfoService.findUserById(userId);
        if (user == null) {
            return ResultUtils.error(2, "必须选择一个用户");
        }
        Role role = roleService.findRoleById(roleId);
        if (role == null) {
            return ResultUtils.error(2, "必须选择一个角色");
        }
        UserRole userRole = new UserRole(user, role);
        Integer res = userRoleService.addUserRole(userRole);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"成功", "添加对象不存在", "没有权限", "用户已存在该角色"};
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public DataResult findById(@RequestParam("userId") Long userId) {
        UserInfo user = userInfoService.findUserById(userId);
        if (user == null) {
            return ResultUtils.error(1, "用户不存在");
        }
        return ResultUtils.success(user);
    }
}