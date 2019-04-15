/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.BaseRole;
import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping("/add")
    public DataResult add(Role role){
        if (role.getParentRole() == null ||
                !Objects.equals(role.getRoleId(), BaseRole.AdminId) ||
                !Objects.equals(role.getRoleId(), BaseRole.UserId)) {
            return ResultUtils.error(2, "必须继承一个角色");
        }
        Integer res = roleService.Insert(role);
        if (res == 1) {
            return ResultUtils.error(1, "添加角色不能为空");
        }
        return ResultUtils.success();
    }

    @RequestMapping("/delete")
    public DataResult delete(@RequestParam("id") Long id){
        Integer res = roleService.Delete(id);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"删除对象不存在", "不能删除基本角色"};
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping("/modify")
    public DataResult modify(Role role){
        Integer res = roleService.modify(role);
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, "修改对象不存在");
    }

    @RequestMapping("/findAll")
    public DataResult findAll(){
        List<Role> all = roleService.findAll();
        return ResultUtils.success(all);
    }
}