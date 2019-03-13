package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public Optional<UserInfo> findUserById(String id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public Integer createUser(UserInfo user) {
        UserInfo newUser = userInfoRepository.save(user);
        if (newUser == null) {
            return 1;//创建失败
        }
        return 0;//创建成功
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
}
