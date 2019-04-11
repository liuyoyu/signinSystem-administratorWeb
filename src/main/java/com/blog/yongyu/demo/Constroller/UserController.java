/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Service.UserInfoService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("/insertUserInfo")
    public DataResult insertUserInfo(UserInfo userInfo) {
        Integer res = userInfoService.Insert(userInfo);
        String[] msg = {"成功", "不能创建空值", "账户不能为空", "该账户已被注册", "该邮箱已被注册"};
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping("/deleteUserInfo")
    public DataResult deleteUserInfo(@RequestParam("uid") Long uid) {
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

    @RequestMapping("/findAll")
    public DataResult findAll() {
        List<UserInfo> all = userInfoService.findAll();
        return ResultUtils.success(all);
    }
    @RequestMapping("/resetPwd")
    public DataResult resetPwd(@RequestParam("id")Long id){
        Optional<UserInfo> userById = userInfoService.findUserById(id);
        if (!userById.isPresent()) {
            return ResultUtils.error(1, "用户不存在");
        }
        userById.get().setInitPassword();
        userInfoService.modify(userById.get());
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
}