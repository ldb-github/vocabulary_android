package com.ldb.vocabulary.android.data;

import java.util.Date;

/**
 * Created by lsp on 2016/9/20.
 */
public class Account {

    // 用户名
    private String username;
    // 用户邮箱
    private String email;
    // 用户手机
    private String phoneNumber;
    // 状态
    private String state;
    // 注册时间
    private Date registerTime;
    // TODO 令牌 暂时用UUID
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
