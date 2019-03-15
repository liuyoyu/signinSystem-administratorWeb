package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    @Query("from UserInfo where account = ?1")
    UserInfo findUserInfoByAccount(String account);
}
