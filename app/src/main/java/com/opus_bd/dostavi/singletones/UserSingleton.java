package com.opus_bd.dostavi.singletones;

public class UserSingleton {
    private String userName;
    private String empCode;
    private static UserSingleton user;

    private UserSingleton() {
    }

    public static synchronized UserSingleton getInstance() {
        if (user == null) {
            user = new UserSingleton();
        }
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public static UserSingleton getUser() {
        return user;
    }

    public static void setUser(UserSingleton user) {
        UserSingleton.user = user;
    }
}
