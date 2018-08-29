package com.example.opus.models;

public class User {
    private String userEmail;
    private String userCode;
    private String userName;
    private String nextEmpCode;
    private static User user;

    private User() {
    }

    public static synchronized User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNextEmpCode() {
        return nextEmpCode;
    }

    public void setNextEmpCode(String nextEmpCode) {
        this.nextEmpCode = nextEmpCode;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }
}
