package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.DTO.MenuTreeDTO;
import com.signInStart.Entity.Menu;
import com.signInStart.Entity.MenuUserType;
import com.signInStart.Entity.Role;

import java.util.List;
import java.util.Map;

public interface MenuService {

    Menu findById(Long id)throws FriendlyException ;

    List<Menu> findAll()throws FriendlyException ;

    Integer add(Menu menu)throws FriendlyException ;

    Integer remove(Long id)throws FriendlyException ;

    Integer modify(Menu menu)throws FriendlyException ;

    List<Map<String,String>> findAllRootMenu()throws FriendlyException ;

    List<Menu> getSidebar() throws FriendlyException;

    List<MenuTreeDTO> getMenuTree() throws FriendlyException;

    void addMenu(Menu menu)throws FriendlyException;

    List<String> getMenuByUserType(String userType) throws FriendlyException;

    List<Role> getUserTypeByMenuValue(String menuValue) throws FriendlyException;

    Menu getMenuInfoByMenuValue(String menuValue) throws FriendlyException;

    void addMenuRole(String[] menuValue, Long roleID) throws FriendlyException;

    List<Map<String, String>> findAllMenuList();

    List<Map<String,String>> getMenuByRoleID(Long id);

    List<Menu> findRootMenuByRole(Long roleID);

    List<Map<String, String>> getMenuIdAndURLByRoleId(Long roleID);

}