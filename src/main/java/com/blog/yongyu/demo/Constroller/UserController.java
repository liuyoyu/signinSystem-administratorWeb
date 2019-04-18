/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.BaseRole;
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
import javafx.geometry.Pos;
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
    @RequestMapping(value = "/insertUserInfo", method = RequestMethod.POST)
    public DataResult insertUserInfo(UserInfo userInfo) {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        if (logiInfo == null || !logiInfo.getRoleId().equals(BaseRole.AdminId)) {
            return ResultUtils.error(9, "没有权限");
        }
        Integer res = userInfoService.Insert(userInfo);
        String[] msg = {"创建成功", "添加账户不能为空", "账户不能为空", "该账户已被注册", "该邮箱已被注册", "密码不能为空", "邮箱不能为空","账号长度要大于4小于10"};
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping(value = "/deleteUserInfo", method = RequestMethod.POST)
    public DataResult deleteUserInfo(@RequestParam("uid") Long uid) {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        if (logiInfo == null || !Objects.equals(logiInfo.getRoleId(), BaseRole.AdminId)) {
            return ResultUtils.error(2, "没有权限");
        }
        Integer res = userInfoService.Delete(uid);
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, "删除对象不存在");
    }

    @RequestMapping("/modifyUserInfo")
    public DataResult modifyUserInfo(UserInfo userInfo) {
        Integer res = userInfoService.modify(userInfo);
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, "修改对象不存在");
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public DataResult findAll() {

        List<UserRole> allUserRole = userRoleService.findAll();
        Map<String, Integer> map = new HashMap<>();
        List<JSONObject> list = new ArrayList<>();
        for (int i=0; i<allUserRole.size(); i++) {
            UserRole ur = allUserRole.get(i);
            JSONObject jsonObject = new JSONObject();
            if (map.containsKey(ur.getAccount())) {
                int loc = map.get(ur.getAccount());
                JSONObject tmp = list.get(loc);
                List<String> rn = (List<String>) tmp.get("roleName");
                rn.add(ur.getRoleName());
                tmp.put("roleName", rn);
            } else {
                //未出现重复账户时
                List<String> roleName = new ArrayList<>();
                roleName.add(ur.getRoleName());
                jsonObject.put("roleName", roleName);
                jsonObject.put("userId", ur.getUserId());
                jsonObject.put("account", ur.getAccount());
                jsonObject.put("userName", ur.getUserName());
                jsonObject.put("sex", ur.getSex());
                jsonObject.put("email", ur.getEmail());
                jsonObject.put("phone", ur.getPhone());
                jsonObject.put("status", ur.getUserStatus());
                jsonObject.put("lastLogin", ur.getLastLogin());
                map.put(ur.getAccount(),i);
                list.add(jsonObject);
            }
        }
//        List<UserInfo> all = userInfoService.findAll();
        return ResultUtils.success(list, list.size());
    }

    @RequestMapping("/resetPwd")
    public DataResult resetPwd(@RequestParam("id") Long id) {
        UserInfo userById = userInfoService.findUserById(id);
        if (userById == null) {
            return ResultUtils.error(1, "用户不存在");
        }
        userById.setInitPassword();
        userInfoService.modify(userById);
        return ResultUtils.success();
    }

    @RequestMapping("/allResetPwd")
    public DataResult allResetPwd(@RequestParam("idList") Long[] idList) {
        if (idList == null || idList.length < 1) {
            return ResultUtils.error(1, "重置密码名单为空");
        }
        userInfoService.allResetPwd(idList);
        return ResultUtils.success();
    }

    @RequestMapping("/addUserRole")
    public DataResult addUserRole(@RequestParam("userId")Long userId,@RequestParam("roleId")Long roleId){
        UserInfo user = userInfoService.findUserById(userId);
        if (user == null) {
            return ResultUtils.error(2, "必须选择一个用户");
        }
        Role role = roleService.findRoleById(roleId);
        if (role == null) {
            return ResultUtils.error(2, "必须选择一个角色");
        }
        UserRole userRole = new UserRole(user,role);
        Integer res = userRoleService.addUserRole(userRole);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"成功","添加对象不存在","没有权限","用户已存在该角色"};
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping("/findById")
    public DataResult findById(@RequestParam("userId")Long userId){
        UserInfo user = userInfoService.findUserById(userId);
        if (user == null) {
            return ResultUtils.error(1, "用户不存在");
        }
        return ResultUtils.success(user);
    }
}