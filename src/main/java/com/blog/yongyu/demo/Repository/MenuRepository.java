package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.Menu;
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

    @Query("from Menu m left join fetch m.roleMenus rm where (m.parentMenuId = 0 or m.parentMenuId is null) " +
            "and m.menuStatus = 'Normal_SYS' and rm.role.id = ?1 order by m.sequence asc")
    List<Menu> getAllRootMenuByRoleID(Long roleID);

    @Query("from Menu m left join fetch m.roleMenus rm where rm.role.id = ?1 order by m.sequence asc")
    List<Menu> findAllByRoleID(Long id);

    @Query("from Menu where parentMenuId = ?1 order by sequence asc")
    List<Menu> findChildMenu(Long parentID);
}