package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Modifying
    @Query(value = "delete from user_role where user_id = ?1", nativeQuery = true)
    Integer deleteAllByUserId(Long id);

    @Query(value = "select u from UserRole u where u.userInfo.id = ?1")
    List<UserRole> findAllByUserId(Long userId);

    @Query(value = "select u from UserRole u order by u.createDate desc ")
    List<UserRole> findAll();

}