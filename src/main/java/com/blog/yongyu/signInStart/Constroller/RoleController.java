/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.signInStart.Constroller;

import com.blog.yongyu.signInStart.Entity.BaseClass.DataResult;
import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.Role;
import com.blog.yongyu.signInStart.Service.LoginInfoService;
import com.blog.yongyu.signInStart.Service.RoleService;
import com.blog.yongyu.signInStart.Utils.ResultUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    LoginInfoService loginInfoService;

    @RequestMapping(value = "/roleInfo", method = RequestMethod.POST)
    public DataResult add(Role role) throws FriendlyException{
//        try {
            Integer res = roleService.Insert(role);
//            if (res != 0) {
//                String[] msg = {"成功", "添加角色不能为空", "角色名称重复"};
//                return ResultUtils.error(res, msg[res]);
//            }
            return ResultUtils.success();
//        } catch (FriendLyException e) {
//            return ResultUtils.error(e.getErrorCode(), e.getMessage());
//        }

    }

    @RequestMapping(value = "/roleInfo", method = RequestMethod.DELETE)
    public DataResult delete(@RequestParam("id") Long id) throws FriendlyException{
//        if (!loginInfoService.checkAdmin()) {
//            return ResultUtils.error(1, "没有权限，只有管理员才能删除角色");
//        }
        Integer res = roleService.Delete(id);
//        if (res == 0) {
            return ResultUtils.success();
//        }
//        String[] msg = {"成功","删除对象不存在", "不能删除基本角色"};
//        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping(value = "/roleInfo", method = RequestMethod.PUT)
    public DataResult modify(Role role) throws FriendlyException{
//        if (!loginInfoService.checkAdmin()) {
//            return ResultUtils.error(1, "没有权限，只有管理员才能修改角色");
//        }
        Integer res = roleService.modify(role);
//        if (res == 0) {
            return ResultUtils.success();
//        }
//        String[] msg = {"成功","修改对象不存在","修改对象不存在"};
//        return ResultUtils.error(res,msg[res]);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public DataResult findAll() throws FriendlyException {
        List<JSONObject> all = roleService.findAll();
        return ResultUtils.success(all, all.size());
    }
}