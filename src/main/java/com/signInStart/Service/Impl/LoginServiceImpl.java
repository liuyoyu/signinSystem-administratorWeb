package com.signInStart.Service.Impl;

import com.signInStart.Entity.BaseClass.BaseSetting;
import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.UserInfo;
import com.signInStart.Repository.UserInfoRepository;
import com.signInStart.Service.LoginService;
import com.signInStart.Service.UserInfoService;
import com.signInStart.Utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;


@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserInfoService userInfoService;

    /**
     * 登陆检查
     * @param uname
     * @param pwd
     * @return
     */
    @Override
    public UserInfo checkLogin(String uname, String pwd) throws FriendlyException {
        UserInfo user = userInfoRepository.findUserByAccountOrEmail(uname);
        if (user == null || BaseSetting.STATUS.Disabled_SYS.equals(user.getStatus())) {
            throw new FriendlyException("用户不存在", DataUtils.CurrentMethodName());
        }
        if (DigestUtils.md5DigestAsHex(pwd.getBytes()).equals(user.getPwd())) {
            user.setLastLogin(new Date());
            userInfoRepository.save(user);
            return user;//账户密码正确
        }
        throw new FriendlyException("密码错误",DataUtils.CurrentMethodName());
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public Integer createUser(UserInfo user) throws FriendlyException {
//        if (user == null) {
//            return null;
//        }
//
//
//
//        UserInfo checkUser = userInfoRepositoryImpl.findUserInfoByAccount(user.getAccount());
//        if (checkUser != null) {
//            return null;//账号已存在
//        }
//
//        UserInfo newUser = userInfoRepositoryImpl.save(user);
//        return newUser;//增加成功
        Integer res = userInfoService.Insert(user);
        return res;
    }
    /**
     * @Author liuyoyu
     * @Description //TODO  忘记密码
     * @Date 21:10 2019/6/12
     * @Params [account, password]
     * @return void
     **/
    @Override
    public void resetPassword(String account,String email, String password) throws FriendlyException {
        UserInfo userInfoByAccount = userInfoRepository.findUserInfoByAccount(account);
        if (userInfoByAccount == null) {
            throw new FriendlyException("没有找到您的账号", DataUtils.CurrentMethodName());
        }
        if (DataUtils.isEmptyString(email)) {
            throw new FriendlyException("邮箱为空，请输入邮箱", DataUtils.CurrentMethodName());
        }
        if (!email.equals(userInfoByAccount.getEmail())) {
            throw new FriendlyException("该邮箱非账户绑定邮箱", DataUtils.CurrentMethodName());
        }
        userInfoByAccount.setNewPassword(password);
        userInfoRepository.save(userInfoByAccount);
    }
}
