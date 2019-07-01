package com.signInStart.Repository;

import com.signInStart.Entity.Menu;
import com.signInStart.Entity.Role;
import com.signInStart.Entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long> {

    @Query("select rm from RoleMenu rm order by rm.createDate desc")
    List<RoleMenu> findAll();

    @Query("select rm from RoleMenu rm where rm.menu.id = ?1 order by rm.createDate desc ")
    List<RoleMenu> findRoleMenuByRoleId(Long id);

    @Modifying
    @Query("delete from RoleMenu rm where rm.menu.id = ?1")
    Integer deleteByMenuID(Long menuID);

    @Query("select rm from RoleMenu rm where rm.menu.id = ?1 and rm.role.id = ?2")
    RoleMenu findByMenuIdUsetType(Long id, String userType);

    @Modifying
    @Query("delete from RoleMenu rm where rm.menu.id = ?1 and rm.role.id = ?2")
    Integer deleteByMenuIdRoleId(Long id, Long roleId);

    @Query("select count(rm) from RoleMenu rm where rm.menu.id = ?1 and rm.role.id = ?2")
    Integer findByMenuIdRoleId(Long id, Long roleId);

//    @Modifying    //不能用
//    @Query("delete from RoleMenu rm where rm.menu.id = ?1 and rm.role.userType = ?2")
//    Integer deleteByMenuIdUserType(Long id, String userType);

    @Query("select rm from RoleMenu rm where rm.menu.id = ?1")
    List<RoleMenu> findByMenuId(Long menuId);

    @Query("select rm.menu.menuName as menuName, rm.menu.id as id from RoleMenu rm where rm.role.id = ?1")
    List<Map<String, String>> findMenuByRoleID(Long roleId);

    @Query("select rm.menu from RoleMenu rm where rm.role.id = ?1 and rm.menu.menuStatus = 'Normal_SYS' and rm.menu.parentMenuId = 0")
    List<Menu> findRootMenuByRoleID(Long roleId);

    @Query("select rm.menu from RoleMenu rm where rm.role.id = ?1 and rm.menu.menuStatus = 'Normal_SYS' and rm.menu.parentMenuId = ?2")
    List<Menu> findByRoleIDParentID(Long roleId, Long parentID);

    @Modifying
    @Transactional
    @Query("delete from RoleMenu where role.id = ?1")
    void deleteRoleByMenuValue(Long roleID);

    @Query("select rm.menu.id as id, rm.menu.menuURL as url from RoleMenu rm where rm.role.roleId = ?1")
    List<Map<String, String>> findMenuIdAndURLByRoleId(Long roleId);
}