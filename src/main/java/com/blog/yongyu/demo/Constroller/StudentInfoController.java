package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Service.StudentInfoService;
import com.blog.yongyu.demo.Entity.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentInfoController {
    @Autowired
    StudentInfoService studentInfoService;

    @RequestMapping(value = "/student", method = RequestMethod.OPTIONS)
    @ResponseBody
    public StudentInfo showStudentInfo(@RequestParam(value="sno", required = true)String sno){
        StudentInfo student = studentInfoService.findStudentInfoBySno(sno);
        return student;
    }
}
