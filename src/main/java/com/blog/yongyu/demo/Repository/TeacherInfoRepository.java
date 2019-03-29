package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, String> {
}