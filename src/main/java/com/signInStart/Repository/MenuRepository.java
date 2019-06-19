package com.signInStart.Repository;

import com.signInStart.Entity.Menu;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long>{
    @Query(value = "select m from Menu m left join fetch m.roleMenus order by m.createDate desc ")
    List<Menu> findAll();

    @Query("from Menu m where m.menuURL = ?1")
    List<Menu> existURL(String url);

    @Query("select m.id as id,m.menuName as menuName from Menu m where (m.parentMenuId = 0 or m.parentMenuId is null) " +
            "and m.menuStatus = 'Normal_SYS' order by m.sequence asc")
    List<Map<String,String>> findRootMenu();

    @Query("from Menu m left join fetch m.roleMenus rm left join fetch rm.role r where (m.parentMenuId = 0 or m.parentMenuId is null) " +
            "and m.menuStatus = 'Normal_SYS' and r.id = ?1 order by m.sequence asc")
    List<Menu> getAllRootMenuByRoleID(Long roleID);

    @Query("from Menu m left join fetch m.roleMenus rm left join fetch rm.role r where r.id = ?1 order by m.sequence asc")
    List<Menu> findAllByRoleID(Long id);

    @Query("select m from Menu m  where m.parentMenuId = ?1 and m.menuStatus = 'Normal_SYS' order by m.sequence asc")
    List<Menu> findChildMenu(Long parentID);

    @Query("from Menu m where m.parentMenuId = 0 or m.parentMenuId is null order by m.sequence asc")
    List<Menu> getAllRootMenu();

    @Query("select m from Menu m where m.parentMenuId = ?1 order by m.sequence asc")
    List<Menu> findAllChildMenu(Long parentID);

    @Query("select m from Menu m  where (m.parentMenuId = 0 or m.parentMenuId is null)" +
            "and m.menuStatus = 'Normal_SYS' order by m.sequence asc")
    List<Menu> findAllRootMenuByUserType();

    List<Menu> findByMenuURL(String menuURL);

    @Query("select m from Menu m where m.menuValue = ?1")
   Menu findByMenuValue(String menuValue);

    @Query("select m.menuValue as key, m.menuName as text from Menu m")
    List<Map<String,String>> getMenu();

}