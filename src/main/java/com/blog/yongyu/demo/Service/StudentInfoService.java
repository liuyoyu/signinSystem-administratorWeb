package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.StudentInfo;

public interface StudentInfoService{
    StudentInfo findStudentInfoBySno(String sno);

    StudentInfo createStudent(StudentInfo studentInfo);

    StudentInfo deleteStudent(StudentInfo studentInfo);

    StudentInfo modifyStudent(StudentInfo studentInfo);
}
