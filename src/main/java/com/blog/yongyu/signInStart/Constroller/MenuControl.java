/**
 * created by
 * Date:2019/4/7
 **/
package com.blog.yongyu.signInStart.Constroller;

import com.blog.yongyu.signInStart.Entity.BaseClass.BaseSetting;
import com.blog.yongyu.signInStart.Entity.BaseClass.DataResult;
import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.BaseClass.LoginInfor;
import com.blog.yongyu.signInStart.Entity.Menu;
import com.blog.yongyu.signInStart.Entity.RoleMenu;
import com.blog.yongyu.signInStart.Service.LoginInfoService;
import com.blog.yongyu.signInStart.Service.MenuService;
import com.blog.yongyu.signInStart.Service.RoleMenuService;
import com.blog.yongyu.signInStart.Utils.ResultUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuControl {

    @Autowired
    MenuService menuService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    LoginInfoService loginInfoService;

    private Boolean checkAuth()throws FriendlyException  {
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
    public DataResult findAll() throws FriendlyException {
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
    public DataResult add(Menu menu, @RequestParam("userType") String[] userTypes)throws FriendlyException  {
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
    public DataResult remove(@RequestParam("id") Long id)throws FriendlyException  {
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
    public DataResult delRoleMenu(@RequestParam("roleMenuId") Long roleMenuId)throws FriendlyException  {
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
    public DataResult modify(Menu menu, @RequestParam("userTypes") String[] userTypes)throws FriendlyException  {
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
    public DataResult getMenuRole(@RequestParam("id") Long id)throws FriendlyException  {
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

    /**
     * 获取所有根菜单
     *
     * @return
     */
    @RequestMapping(value = "/allRootMenu", method = RequestMethod.GET)
    public DataResult findAllRootMenu()throws FriendlyException  {
        List<Map<String, String>> allRootMenu = menuService.findAllRootMenu();
        return ResultUtils.success(allRootMenu, allRootMenu.size());
    }

    /**
     * 获取侧边栏
     * 二级侧边栏
     * @return
     */
    @RequestMapping(value = "/sidebar", method = RequestMethod.GET)
    public DataResult getSideBar() throws FriendlyException {
        try {
            List<Menu> sidebar = menuService.getSidebar();
            return ResultUtils.success(sidebar);
        } catch (FriendlyException e) {
            return ResultUtils.error(e.getErrorCode(), e.getMessage());
        }
    }

}