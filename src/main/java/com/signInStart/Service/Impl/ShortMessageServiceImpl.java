/**
 * created by
 * Date:2019/4/4
 **/
package com.signInStart.Service.Impl;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.ShortMessage;
import com.signInStart.Repository.ShortMessageRepository;
import com.signInStart.Service.ShortMessageService;
import com.signInStart.Utils.DataUtils;
import com.signInStart.Utils.EmailUtils;
import com.signInStart.Utils.RedisUtils;
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
    /**
     * @Author liuyoyu
     * @Description //TODO  从redis中拿取验证码，只做验证，不保存在数据库中
     * @Date 21:05 2019/6/12
     * @Params [code, email]
     * @return java.lang.Integer
     **/
    @Override
    public void verifyEmailMessage(String code, String email) throws FriendlyException {
        if (DataUtils.isEmptyString(email)) {
            throw new FriendlyException("邮箱为空，请输入邮箱", DataUtils.CurrentMethodName());
        }
        if (DataUtils.isEmptyString(code)) {
            throw new FriendlyException("验证码为空，请输入验证码", DataUtils.CurrentMethodName());
        }
        String s = RedisUtils.get(email);
        if (!code.equals(s)) {
            throw new FriendlyException("验证码错误，请重新输入", DataUtils.CurrentMethodName());
        }
        RedisUtils.del(email);
    }
}