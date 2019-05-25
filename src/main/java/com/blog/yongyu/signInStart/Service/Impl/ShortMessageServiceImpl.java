/**
 * created by
 * Date:2019/4/4
 **/
package com.blog.yongyu.signInStart.Service.Impl;

import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.ShortMessage;
import com.blog.yongyu.signInStart.Repository.ShortMessageRepository;
import com.blog.yongyu.signInStart.Service.ShortMessageService;
import com.blog.yongyu.signInStart.Utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("shortMessageService")
public class ShortMessageServiceImpl implements ShortMessageService {
    @Autowired
    ShortMessageRepository shortMessageRepository;

    /**
     * 发送邮箱验证码
     * @param account
     * @param receiver
     * @param purpose
     * @return
     */
    @Override
    public Integer sendEmailMessage(String account, String receiver, String purpose)throws FriendlyException {
        if ("".equals(receiver)) {
            throw new FriendlyException("接收人不能为空", 1);
        }
        String code = EmailUtils.editEmail(receiver);
        if ("".equals(code)) {
            throw new FriendlyException("短信功能异常", 1);
        }
        shortMessageRepository.save(new ShortMessage(account, receiver, code, new Date(), purpose));
        return 0;
    }

    /**
     * 验证邮箱验证码
     * @param code
     * @param account
     * @param email
     * @return
     */
    @Override
    public Integer verifyEmailMessage(String code, String account, String email)throws FriendlyException {
        List<ShortMessage> msgList = shortMessageRepository.findShortMessagesByAccountEmail(account, email);//最新的短信
        if (msgList.get(0).getCode().equals(code)) {
            return 0;
        }
        throw new FriendlyException("验证错误");
    }
}