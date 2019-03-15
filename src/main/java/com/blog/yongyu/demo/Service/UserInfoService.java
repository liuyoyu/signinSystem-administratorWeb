package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.StudentInfo;
import com.blog.yongyu.demo.Entity.TeacherInfo;
import com.blog.yongyu.demo.Entity.UserInfo;

import java.util.Optional;

public interface UserInfoService {
    Optional<UserInfo> findUserById(String id);

    UserInfo findUserByAccount(String account);

    UserInfo createUser(UserInfo user);

    Integer removeUser(UserInfo user);

    UserInfo modifyUser(UserInfo user);

    Integer modifyUserInfo(UserInfo userInfo);

    StudentInfo addStudentInfo(UserInfo user, StudentInfo student);

    TeacherInfo addTeacherInfo(TeacherInfo teacher);


}
