package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findUserInfoByAccount(String account);
}
