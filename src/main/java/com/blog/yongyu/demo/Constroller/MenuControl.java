/**
 * created by
 * Date:2019/4/7
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.BaseSetting;
import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.BaseClass.LoginInfor;
import com.blog.yongyu.demo.Entity.Menu;
import com.blog.yongyu.demo.Entity.RoleMenu;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Service.MenuService;
import com.blog.yongyu.demo.Service.RoleMenuService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuControl {

    @Autowired
    MenuService menuService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    LoginInfoService loginInfoService;

    public Boolean checkAuth() {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        if (logiInfo == null) {
            return false;
        }
        if (logiInfo.getRoleName().equals(BaseSetting.ROLE.Admin_SYS.toString())) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public DataResult findAll() {
        List<Menu> all = menuService.findAll();
        return ResultUtils.success(all, all.size());
    }

    /**
     * 添加菜单，设置菜单权限
     *
     * @param menu
     * @param userTypes
     * @return
     */
    @RequestMapping(value = "/roleMenu", method = RequestMethod.POST)
    public DataResult add(Menu menu, @RequestParam("userType") String[] userTypes) {
        if (!checkAuth()) {
            return ResultUtils.error(2, "没有权限");
        }
        if (userTypes == null || userTypes.length < 1) {
            return ResultUtils.error(3, "必须设置菜单权限的角色");
        }
        if (menu == null) {
            return ResultUtils.error(4, "不能添加空值");
        }
        Integer res = menuService.add(menu);
        if (res == 0) {
            roleMenuService.add(menu, userTypes);
            return ResultUtils.success();
        }
        String[] msg = {"必填项不能为空", "该URL已被占用"};
        return ResultUtils.error(res, msg[res - 1]);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return
     */
    @RequestMapping(value = "/roleMenu", method = RequestMethod.DELETE)
    public DataResult remove(@RequestParam("id") Long id) {
        if (!checkAuth()) {
            return ResultUtils.error(2, "没有权限");
        }
        Integer res = menuService.remove(id);
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(1, "删除对象不存在");
    }

    /**
     * 删除某个角色的菜单权限
     *
     * @param roleMenuId
     * @return
     */
    @RequestMapping(value = "/role", method = RequestMethod.DELETE)
    public DataResult delRoleMenu(@RequestParam("roleMenuId") Long roleMenuId) {
        if (!checkAuth()) {
            return ResultUtils.error(2, "没有权限");
        }
        Integer res = roleMenuService.remove(roleMenuId);
        if (res == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(res, "删除对象不存在");
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/roleMenu", method = RequestMethod.PUT)
    public DataResult modify(Menu menu, @RequestParam("userTypes") String[] userTypes) {
        if (!checkAuth()) {
            return ResultUtils.error(2, "没有权限");
        }
        Integer res = menuService.modify(menu);
        if (res == 0) {
            roleMenuService.modify(menu, userTypes);
            return ResultUtils.success();
        }
        String[] msg = {"对象不能为空", "该URL已经被占用"};
        return ResultUtils.error(res, msg[res - 1]);
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public DataResult getMenuRole(@RequestParam("id") Long id) {
        List<RoleMenu> roleMenuByRoleId = roleMenuService.findRoleMenuByMenuId(id);
        if (roleMenuByRoleId == null) {
            return ResultUtils.error(1, "没有分配角色");
        }
        List<JSONObject> list = new ArrayList<>();
        for (RoleMenu rm : roleMenuByRoleId) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roleId", rm.getRole().getRoleId());
            jsonObject.put("roleName", rm.getRole().getRoleName());
            list.add(jsonObject);
        }
        return ResultUtils.success(list, list.size());
    }
}