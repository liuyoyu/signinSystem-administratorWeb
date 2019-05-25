package com.blog.yongyu.signInStart.Service;

import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;

public interface ShortMessageService {
    Integer sendEmailMessage(String account ,String receiver, String purpose)throws FriendlyException;

    Integer verifyEmailMessage(String code, String account, String email)throws FriendlyException;
}