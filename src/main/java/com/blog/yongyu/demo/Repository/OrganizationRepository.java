package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}