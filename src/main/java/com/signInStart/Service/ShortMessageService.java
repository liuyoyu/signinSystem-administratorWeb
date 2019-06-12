package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;

public interface ShortMessageService {
    Integer sendEmailMessage(String account ,String receiver, String purpose)throws FriendlyException;

    Integer verifyEmailMessage(String code, String account, String email)throws FriendlyException;

    void verifyEmailMessage(String code, String email)throws FriendlyException;
}