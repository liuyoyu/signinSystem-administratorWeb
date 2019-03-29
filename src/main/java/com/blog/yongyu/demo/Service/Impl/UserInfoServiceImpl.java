package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.StudentInfo;
import com.blog.yongyu.demo.Entity.TeacherInfo;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Repository.RoleRepository;
import com.blog.yongyu.demo.Repository.StudentInfoRepository;
import com.blog.yongyu.demo.Repository.TeacherInfoRepository;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    StudentInfoRepository studentInfoRepository;

    @Autowired
    TeacherInfoRepository teacherInfoRepository;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public UserInfo findUserByAccount(String account) {
        return userInfoRepository.findUserInfoByAccount(account);
    }

    @Override
    public Optional<UserInfo> findUserById(Long id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public UserInfo createUser(UserInfo user,String roleId) {
        if (user == null) {
            return null;
        }
        Optional<Role> role = roleRepository.findById(roleId);
        user.setRole(role.get());
        if (user.getRole().getRoleId().equals("001")) {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setSno(user.getAccount());
            studentInfo.setSname(user.getUserName());
            studentInfo.setSex(user.getSex());
            studentInfoRepository.save(studentInfo);
        }
        if (user.getRole().getRoleId().equals("002")) {
            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setId(user.getAccount());
            teacherInfo.setTname(user.getUserName());
            teacherInfo.setSex(user.getSex());
            teacherInfoRepository.save(teacherInfo);
        }
        Long time = System.currentTimeMillis();
        user.setCreateTime(new Date(time));
        user.setModifyTime(new Date(time));
        UserInfo newUser = userInfoRepository.save(user);
        return newUser;
    }

    @Override
    public Integer removeUser(UserInfo user) {
        if (user == null) {
            return 1;//删除对象不存在
        }
        userInfoRepository.delete(user);
        return 0;//删除成功
    }

    @Override
    public Integer modifyUserInfo(UserInfo userInfo) {
        UserInfo save = userInfoRepository.save(userInfo);
        if (save == null) {
            return 1;//修改失败
        }
        return 0;//修改成功
    }

    /**
     * 绑定学生信息
     * @param user
     * @return
     */
    @Override
    public StudentInfo addStudentInfo(UserInfo user, StudentInfo student) {
        //方案一：填写学生信息，然后绑定用户
        //方案二：填写用户信息，然后绑定学生
        return null;
    }

    /**
     * 绑定教师信息
     * @param teacher
     * @return
     */
    @Override
    public TeacherInfo addTeacherInfo(TeacherInfo teacher) {
        return null;
    }

    /**
     * 修改教师信息
     * @param user
     * @return
     */
    @Override
    public UserInfo modifyUser(UserInfo user) {
        if (user == null) {
            return null;
        }
        UserInfo userNew = userInfoRepository.save(user);
        return userNew;
    }
}
