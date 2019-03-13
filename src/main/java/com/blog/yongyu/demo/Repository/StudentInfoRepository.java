package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, String> {
    StudentInfo findBySno(String sno);
}
