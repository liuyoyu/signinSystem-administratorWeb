/**
 * created by liuyoyu
 * Date:2019/4/7
 **/
package com.signInStart.Service.Impl;

import com.signInStart.Entity.BaseClass.DataResult;
import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.BaseClass.HttpContent;
import com.signInStart.Entity.BaseClass.LoginInfor;
import com.signInStart.Entity.DTO.MenuTreeDTO;
import com.signInStart.Entity.Menu;
import com.signInStart.Entity.MenuUserType;
import com.signInStart.Entity.Role;
import com.signInStart.Entity.RoleMenu;
import com.signInStart.Repository.MenuRepository;
import com.signInStart.Repository.MenuUserTypeRepository;
import com.signInStart.Repository.RoleMenuRepository;
import com.signInStart.Service.LoginInfoService;
import com.signInStart.Service.MenuService;
import com.signInStart.Service.RoleService;
import com.signInStart.Utils.DataUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    RoleMenuRepository roleMenuRepository;

    @Autowired
    MenuUserTypeRepository menuUserTypeRepository;

    @Autowired
    LoginInfoService loginInfoService;

    @Autowired
    RoleService roleService;


    @Override
    public Menu findById(Long id) throws FriendlyException {
        Optional<Menu> byId = menuRepository.findById(id);
        if (!byId.isPresent()) {
            throw new FriendlyException("菜单不存在", 1);
        }
        return byId.get();
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> all = menuRepository.findAll();
        if (all == null || all.size() < 1) {
            return null;
        }
        return all;
    }

    @Override
    public Integer add(Menu menu) throws FriendlyException {
        if (loginInfoService.checkUser()) {
            throw new FriendlyException("没有权限", 1);
        }
        if (menu == null) {
            return 1;//不能添加空对象
        }
        if (menu.getMenuURL() != null && menuRepository.existURL(menu.getMenuURL()) != null) {
            return 2; //URL已存在
        }
        menu.setCreateDate(new Date());
        menu.setModifyDate(new Date());
        menu.setCreateBy(loginInfoService.getAccount());
        menu.setModifyBy(loginInfoService.getAccount());
        menuRepository.save(menu);
        return 0;
    }

    /**
     * @return java.lang.Integer
     * @Author liuyoyu
     * @Description //TODO  删除菜单：有子菜单的根菜单不能删除
     * @Date 20:44 2019/6/2
     * @Param [id]
     **/
    @Override
    public Integer remove(Long id) throws FriendlyException {
        Menu menu = findById(id);
        if (menu == null) {
            return 1;//不能删除空对象
        }
        if (menu.getParentMenuId() == 0L && !menuRepository.findAllChildMenu(menu.getId()).isEmpty()) {
            throw new FriendlyException("有子菜单，不能删除");
        }
        menuRepository.delete(menu);
        return 0;
    }

    @Override
    public Integer modify(Menu menu) throws FriendlyException {
        Menu byId = findById(menu.getId());
        if (menu == null || byId == null) {
            throw new FriendlyException("不能传入空值");
        }
        if (!byId.getMenuValue().equals(menu.getMenuValue())) {
            throw new FriendlyException("菜单代码不能进行修改");
        }
        List<Menu> exist = menuRepository.existURL(menu.getMenuURL());
        if (exist != null && exist.size() >= 1) {
            throw new FriendlyException("URL已被占用");
        }
        if (menu.getParentMenuId() == null) {
            throw new FriendlyException("父级菜单不能为空");
        }
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        menu.setModifyBy(logiInfo.getUserId().toString());
        menu.setModifyDate(new Date());

        DataUtils.copyProperty(menu, byId);
        menuRepository.save(byId);
        return 0;
    }

    /**
     * 查找所有根菜单
     *
     * @return
     */
    @Override
    public List<Map<String, String>> findAllRootMenu() throws FriendlyException {
        return menuRepository.findRootMenu();
    }

    @Override
    public List<Menu> findRootMenuByRole(Long roleID) {
        return roleMenuRepository.findRootMenuByRoleID(roleID);
    }

    /**
     * 根据角色获取侧边栏
     * 二级侧边栏
     *
     * @return
     */
    @Override
    public List<Menu> getSidebar() throws FriendlyException {
//        String currUserType = loginInfoService.getCurrUserType();
//        if (currUserType == null) {
//            throw new FriendlyException("请先登陆", 1);
//        }
        Long roleId = loginInfoService.getCurrRoleID();
        if ( roleId== null) {
            throw new FriendlyException("当前用户状态未知，请重新登陆", 1);
        }
//        List<Menu> allRootMenu = menuRepository.findAllRootMenuByUserType();
        List<Menu> rootMenu = findRootMenuByRole(roleId);

        List<Menu> sidebar = new ArrayList<>();
        for (Menu m : rootMenu) {
//            List<Menu> all = menuRepository.findAllRootMenuByUserType();
            List<Menu> all = roleMenuRepository.findByRoleIDParentID(roleId, m.getId());
            m.setChildrenMenu(all);
            sidebar.add(m);
        }
        return sidebar;
    }

    /**
     * @return java.util.List<com.signInStart.Entity.Menu>
     * @Author liuyoyu
     * @Description //TODO  获取取菜单树
     * @Date 15:33 2019/5/26
     * @Param []
     **/
    @Override
    public List<MenuTreeDTO> getMenuTree() throws FriendlyException {
        List<Menu> all = menuRepository.getAllRootMenu();
        if (all == null) {
            throw new FriendlyException("列表为空，请先创建根菜单");
        }
        List<MenuTreeDTO> tree = new ArrayList<>();
        for (Menu menu : all) {
            MenuTreeDTO menuTreeDTO = new MenuTreeDTO(menu);
            List<Menu> tmp = menuRepository.findAllChildMenu(menu.getId());
            menuTreeDTO.setChildrenMenu(tmp);
            tree.add(menuTreeDTO);
        }
        return tree;
    }

    /**
     * @return void
     * @Author liuyoyu
     * @Description //TODO  添加菜单
     * @Date 19:20 2019/6/2
     * @Param [menu]
     **/
    @Override
    public void addMenu(Menu menu) throws FriendlyException {
        if (DataUtils.isEmptyString(menu.getMenuName())) {
            throw new FriendlyException("菜单名称为空，请填入菜单名称", 1);
        }
        if (DataUtils.isEmptyString(menu.getMenuValue())) {
            throw new FriendlyException("菜单代码为空，请填入菜单代码", 1);
        }

        if (DataUtils.isEmptyString(menu.getMenuStatus())) {
            throw new FriendlyException("菜单状态为必填，请填入菜单状态", 1);
        }
        if (menu.getParentMenuId() == null) {
            throw new FriendlyException("父级菜单为空，请填入父级菜单", 1);
        }
        if (DataUtils.isEmptyString(menu.getMenuURL())) {
            throw new FriendlyException("URL为空，请填入URL", 1);
        }
        if (menuRepository.findByMenuValue(menu.getMenuValue()) != null) {
            throw new FriendlyException("菜单代码已被使用，请换其他菜单代码", 1);
        }
        if (!menuRepository.findByMenuURL(menu.getMenuURL()).isEmpty()) {
            throw new FriendlyException("URL已被使用，请更换其他URL", 1);
        }
        menu.setCreateBy(loginInfoService.getAccount());
        menu.setCreateDate(new Date());
        menuRepository.save(menu);
    }

    /**
     * @return java.util.List<java.lang.String>
     * @Author liuyoyu
     * @Description //TODO  根据权限类型（userType）来返回相应的菜单
     * @Date 20:18 2019/6/4
     * @Param [userType]
     **/
    @Override
    public List<String> getMenuByUserType(String userType) throws FriendlyException {
        List<String> menuByUserType = menuUserTypeRepository.getMenuByUserType(userType);
        if (menuByUserType.isEmpty()) {
            throw new FriendlyException("没有找到相应的菜单");
        }
        return menuByUserType;
    }

    /**
     * @return java.util.List<java.lang.String>
     * @Author liuyoyu
     * @Description //TODO  根据菜单代码获取角色列表
     * @Date 21:20 2019/6/4
     * @Param [menuValue]
     **/
    @Override
    public List<Role> getUserTypeByMenuValue(String menuValue) throws FriendlyException {
        Menu byMenuValue = menuRepository.findByMenuValue(menuValue);
        if (byMenuValue == null) {
            throw new FriendlyException("找不到相应的菜单", DataUtils.CurrentMethodName());
        }
        return byMenuValue.getRole();
    }

    /**
     * @return com.signInStart.Entity.Menu
     * @Author liuyoyu
     * @Description //TODO  根据菜单代码获取菜单信息
     * @Date 16:12 2019/6/7
     * @Param [menuValue]
     **/
    @Override
    public Menu getMenuInfoByMenuValue(String menuValue) throws FriendlyException {
        Menu byMenuValue = menuRepository.findByMenuValue(menuValue);
        if (byMenuValue == null) {
            throw new FriendlyException("没有找到相应菜单信息", DataUtils.CurrentMethodName());
        }
        if (!DataUtils.isEmptyString(byMenuValue.getParentMenuId().toString())) {
            Optional<Menu> byId = menuRepository.findById(byMenuValue.getParentMenuId());
            if (byId.isPresent()) {
                Menu menu = byId.get();
                byMenuValue.setParentName(menu.getMenuName());
            }
        }
        return byMenuValue;
    }

    /**
     * @return void
     * @Author liuyoyu
     * @Description //TODO  添加菜单角色
     * @Date 22:39 2019/6/11
     * @Params [menuID, roleID]
     **/
    @Override
    public void addMenuRole(String[] menuValue, Long roleID) throws FriendlyException {
//        Menu byMenuValue = menuRepository.findByMenuValue(menuValue);
        List<Menu> byMenuValue = menuRepository.findByMenuValue(menuValue);
        Role roleById = roleService.findRoleById(roleID);
        if (byMenuValue == null || byMenuValue.size() < 1) {
            throw new FriendlyException("菜单不存在，请重新选择", DataUtils.CurrentMethodName());
        }
        if (roleById == null) {
            throw new FriendlyException("角色不存在，请重新选择", DataUtils.CurrentMethodName());
        }

        List<RoleMenu> all = new ArrayList<>();
        for (Menu m : byMenuValue) {
            if (roleMenuRepository.findByMenuIdRoleId(m.getId(), roleID) > 0) {
                continue;
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRole(roleById);
            roleMenu.setMenu(m);
            roleMenu.setCreateBy(loginInfoService.getAccount());
            roleMenu.setCreateDate(new Date());
            all.add(roleMenu);
        }
        roleMenuRepository.saveAll(all);
    }

    /**
     * @return java.util.List<com.signInStart.Entity.Menu>
     * @Author liuyoyu
     * @Description //TODO  获取所有菜单
     * @Date 13:33 2019/6/19
     * @Params []
     **/
    @Override
    public List<Map<String, String>> findAllMenuList() {
        return menuRepository.getMenu();
    }
    /**
     * @Author liuyoyu
     * @Description //TODO  根据角色id获取菜单列表
     * @Date 20:43 2019/6/22
     * @Params [id]
     * @return java.util.List<com.signInStart.Entity.Menu>
     **/
    @Override
    public List<String> getMenuByRoleID(Long id) {
        return  roleMenuRepository.findMenuByRoleID(id);
    }
}