package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String > {
}