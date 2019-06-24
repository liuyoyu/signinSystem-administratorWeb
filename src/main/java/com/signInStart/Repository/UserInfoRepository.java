package com.signInStart.Repository;

import com.signInStart.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

    UserInfo findUserInfoByAccount(String account);

    List<UserInfo> findUserByEmail(String email);

    @Query("select u from UserInfo u where u.account = ?1 or u.email = ?1")
    UserInfo findUserByAccountOrEmail(String inpput);

    @Query("select u from UserInfo u  order by u.createDate desc ")
    List<UserInfo> findAll();

    @Modifying
    @Query(value = "update UserInfo u set u.pwd = ?1, u.modifyDate = ?2, u.modifyBy=?3  where u.id in (?4)")
    void allResetPwd(String newPwd, Date date, String account, Long[] listSet);

    @Query(value = "select u from UserInfo u where u.id = ?1")
    Map<String, String> getUserInfoMap(String userId);

//    @Modifying
//    @Query(value = "update UserInfo u set u.pwd = ?2 where u.account = ?1")
//    void resetPassword(String account, String password);

}
