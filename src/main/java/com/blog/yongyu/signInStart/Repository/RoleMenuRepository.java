package com.blog.yongyu.signInStart.Repository;

import com.blog.yongyu.signInStart.Entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long> {

    @Query("select rm from RoleMenu rm order by rm.createDate desc")
    List<RoleMenu> findAll();

    @Query("select rm from RoleMenu rm where rm.menu.id = ?1 order by rm.createDate desc ")
    List<RoleMenu> findRoleMenuByRoleId(Long id);

    @Modifying
    @Query("delete from RoleMenu rm where rm.menu.id = ?1")
    Integer deleteByMenuID(Long menuID);
}