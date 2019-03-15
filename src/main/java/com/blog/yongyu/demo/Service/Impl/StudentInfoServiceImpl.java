package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Repository.StudentInfoRepository;
import com.blog.yongyu.demo.Service.StudentInfoService;
import com.blog.yongyu.demo.Entity.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService{
    @Autowired
    StudentInfoRepository studentInfoRepository;

    @Override
    public StudentInfo findStudentInfoBySno(String sno) {
        StudentInfo student = studentInfoRepository.findBySno(sno);
        return student;
    }

    @Override
    public StudentInfo createStudent(StudentInfo studentInfo) {
        if (studentInfo == null) {
            return null;
        }
        StudentInfo student = studentInfoRepository.save(studentInfo);
        return student;
    }

    @Override
    public StudentInfo deleteStudent(StudentInfo studentInfo) {
        if (studentInfo == null) {
            return null;
        }
        studentInfoRepository.delete(studentInfo);
        return studentInfo;
    }

    @Override
    public StudentInfo modifyStudent(StudentInfo studentInfo) {
        if (studentInfo == null) {
            return null;
        }
        StudentInfo student = studentInfoRepository.save(studentInfo);
        return student;
    }
}
