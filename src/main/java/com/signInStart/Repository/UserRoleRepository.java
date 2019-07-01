package com.signInStart.Repository;

import com.signInStart.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = "select u from UserRole u where u.userInfo.id = ?1 and u.role.id = ?2")
    UserRole findByUserIdRoleId(Long userId, Long roleId);

    @Query(value = "select u from UserRole u where u.userInfo.id = ?1")
    List<UserRole> findByUserId(Long id);

    @Transactional
    @Modifying
    @Query(value = "update user_role set role_id = ?3 where user_id = ?1 and role_id = ?2", nativeQuery = true)
    void modifyRole(Long userID, Long roleID, Long newRoleID);
}