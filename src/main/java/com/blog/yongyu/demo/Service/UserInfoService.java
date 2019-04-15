package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.UserInfo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface UserInfoService {
    UserInfo findUserById(Long id);

    UserInfo findUserByAccount(String account);

    Integer Insert(UserInfo user);

    Integer Delete(Long userId);

    Integer modify(UserInfo user);

    List<UserInfo> findUserByEmail(String email);

    List<UserInfo> findAll();

    void allResetPwd(Long[] list);

}
