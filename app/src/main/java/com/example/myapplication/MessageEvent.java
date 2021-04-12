package com.example.myapplication;

public class MessageEvent {
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public MessageEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
