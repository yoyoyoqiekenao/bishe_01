package com.example.myapplication;

public class CheckLoginEvent {
    private boolean isLogin;

    public CheckLoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
