package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findUserInfoByAccount(String account);

    List<UserInfo> findUserByEmail(String email);

    @Query("select u from UserInfo u where u.account = ?1 or u.email = ?1")
    UserInfo findUserByAccountOrEmail(String inpput);

    @Query("select u from UserInfo u order by u.createDate desc ")
    List<UserInfo> findAll();

    @Modifying
    @Query(value = "update UserInfo u set u.pwd = ?1, u.modifyDate = ?2  where u.id in ?3")
    void allResetPwd(String newPwd, Date date, Long[] listSet);
}
