/**
 * created by liuyoyu
 * Date:2019/4/7
 **/
package com.signInStart.Service.Impl;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.BaseClass.HttpContent;
import com.signInStart.Entity.BaseClass.LoginInfor;
import com.signInStart.Entity.DTO.MenuTreeDTO;
import com.signInStart.Entity.Menu;
import com.signInStart.Entity.MenuUserType;
import com.signInStart.Repository.MenuRepository;
import com.signInStart.Repository.MenuUserTypeRepository;
import com.signInStart.Service.LoginInfoService;
import com.signInStart.Service.MenuService;
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
    MenuUserTypeRepository menuUserTypeRepository;

    @Autowired
    LoginInfoService loginInfoService;

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
        if (menu == null) {
            return 1;//不能修改空对象
        }
        List<Menu> exist = menuRepository.existURL(menu.getMenuURL());
        if (menu.getMenuURL() != null && (exist != null || exist.size() < 1)) {
            return 2; //URL已存在
        }
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        menu.setModifyBy(logiInfo.getUserId().toString());
        menu.setModifyDate(new Date());
        menuRepository.save(menu);
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

    /**
     * 根据角色获取侧边栏
     * 二级侧边栏
     *
     * @return
     */
    @Override
    public List<Menu> getSidebar() throws FriendlyException {
        String currUserType = loginInfoService.getCurrUserType();
        if (currUserType == null) {
            throw new FriendlyException("请先登陆", 1);
        }
        List<Menu> allRootMenu = menuRepository.findAllRootMenuByUserType();

        List<Menu> sidebar = new ArrayList<>();
        for (Menu m : allRootMenu) {
            List<Menu> all = menuRepository.findAllRootMenuByUserType();
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
    public void addMenu(Menu menu, String[] userType) throws FriendlyException {
        if (loginInfoService.checkUser()) {
            throw new FriendlyException("没有权限，请联系管理员", 1);
        }
        if (userType != null && userType.length > 1 && !DataUtils.containsUserType(userType)) {
            throw new FriendlyException("角色类型不在指定类型中，请重新选择");
        }
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
        if (!menuRepository.findByMenuValue(menu.getMenuValue()).isEmpty()) {
            throw new FriendlyException("菜单代码已被使用，请换其他菜单代码", 1);
        }
        if (!menuRepository.findByMenuURL(menu.getMenuURL()).isEmpty()) {
            throw new FriendlyException("URL已被使用，请更换其他URL", 1);
        }
        menu.setCreateBy(loginInfoService.getAccount());
        menu.setCreateDate(new Date());
        menuRepository.save(menu);
        if (userType != null && userType.length > 0) {
            List<MenuUserType> mList = new ArrayList<>();
            for (String u : userType) {
                MenuUserType menuUserType = new MenuUserType();
                menuUserType.setMenu(menu);
                menuUserType.setUserType(u);
                menuUserType.setCreateBy(loginInfoService.getAccount());
                menuUserType.setCreateDate(new Date());
                mList.add(menuUserType);
            }
            menuUserTypeRepository.saveAll(mList);
        }
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
     * @Author liuyoyu
     * @Description //TODO  根据菜单代码获取角色类型
     * @Date 21:20 2019/6/4
     * @Param [menuValue]
     * @return java.util.List<java.lang.String>
     **/
    @Override
    public List<String> getUserTypeByMenuValue(String menuValue) throws FriendlyException {
        List<String> userTypeByMenuValue = menuUserTypeRepository.getUserTypeByMenuValue(menuValue);
        if (userTypeByMenuValue.isEmpty()) {
            throw new FriendlyException("没有分配角色");
        }
        return userTypeByMenuValue;
    }
}