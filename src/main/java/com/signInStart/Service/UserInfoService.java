package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo findUserById(Long id) throws FriendlyException;

    UserInfo findUserByAccount(String account,String receiver) throws FriendlyException;

    UserInfo findUserByAccount(String account) throws FriendlyException;

    Integer Insert(UserInfo user) throws FriendlyException;

    Integer Delete(Long userId) throws FriendlyException;

    Integer modify(UserInfo user) throws FriendlyException;

    List<UserInfo> findUserByEmail(String email) throws FriendlyException;

    List<UserInfo> findAll() throws FriendlyException;

    void allResetPwd(Long[] list) throws FriendlyException;


}
