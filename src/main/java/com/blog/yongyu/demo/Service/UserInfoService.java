package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.UserInfo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface UserInfoService {
    Optional<UserInfo> findUserById(Long id);

    UserInfo findUserByAccount(String account);

    Integer createUser(UserInfo user);

    Integer removeUser(Long userId);

    Integer modifyUser(UserInfo user);

    Integer modifyUserInfo(UserInfo userInfo);

}
