package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
}
