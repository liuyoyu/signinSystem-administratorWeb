/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    LoginInfoService loginInfoService;

    @RequestMapping(value = "/roleInfo", method = RequestMethod.POST)
    public DataResult add(Role role){
        if (!loginInfoService.checkSupperAdimn()) {
            return ResultUtils.error(5, "没有权限，只有超级管理员才能添加角色");
        }
        if (role.getParentRole() == null ) {
            return ResultUtils.error(3, "必须继承一个角色");
        }
        if (role.getParentRole() == null || role.getRoleName() == null || role.getStatus() == null) {
            return ResultUtils.error(4, "必填项不能为空");
        }
        Integer res = roleService.Insert(role);
        if (res != 0) {
            String[] msg = {"成功", "添加角色不能为空", "角色名称重复"};
            return ResultUtils.error(res,msg[res]);
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/roleInfo", method = RequestMethod.DELETE)
    public DataResult delete(@RequestParam("id") Long id){
        if (!loginInfoService.checkSupperAdimn()) {
            return ResultUtils.error(1, "没有权限，只有超级管理员才能删除角色");
        }
        Integer res = roleService.Delete(id);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"成功","删除对象不存在", "不能删除基本角色"};
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping(value = "/roleInfo", method = RequestMethod.PUT)
    public DataResult modify(Role role){
        if (!loginInfoService.checkSupperAdimn()) {
            return ResultUtils.error(1, "没有权限，只有超级管理员才能修改角色");
        }
        Integer res = roleService.modify(role);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"成功","修改对象不存在","修改对象不存在"};
        return ResultUtils.error(res,msg[res]);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public DataResult findAll(){
        List<Role> all = roleService.findAll();
        return ResultUtils.success(all, all.size());
    }
}