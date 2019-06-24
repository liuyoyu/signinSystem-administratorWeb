/**
 * @Author liuyoyu
 * @Date:2019/6/24 9:38
 **/
package com.signInStart.Entity.DTO;

public class SearchUserDTO {
    private String name;
    private String account;
    private String userType;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}