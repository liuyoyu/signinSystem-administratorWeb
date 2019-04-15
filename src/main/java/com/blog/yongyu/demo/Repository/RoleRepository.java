package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.Role;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    @Query("select r from Role r order by r.createDate")
    List<Role> findAll();

    Role findByRoleId(Long roleId);
}