package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.UserInfo;

import java.util.Optional;

public interface UserInfoService {
    Optional<UserInfo> findUserById(String id);

    Integer createUser(UserInfo user);

    Integer removeUser(UserInfo user);

    Integer modifyUserInfo(UserInfo userInfo);

}
