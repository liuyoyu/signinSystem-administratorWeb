/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping("/add")
    public DataResult add(Role role){
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
        return ResultUtils.error(res, "删除对象不存在");
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