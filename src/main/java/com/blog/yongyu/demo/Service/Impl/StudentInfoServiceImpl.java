package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Repository.StudentInfoRepository;
import com.blog.yongyu.demo.Service.StudentInfoService;
import com.blog.yongyu.demo.Entity.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentInfoServiceImpl implements StudentInfoService{
    @Autowired
    StudentInfoRepository studentInfoRepository;
    @Override
    public StudentInfo findStudentInfoBySno(String sno) {
        StudentInfo student = studentInfoRepository.findBySno(sno);
        return student;
    }
}
