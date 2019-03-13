package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findUserInfoByAccount(String account);
}
