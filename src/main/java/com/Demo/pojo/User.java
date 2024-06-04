package com.Demo.pojo;

public class User {
    private String phone;
    private String username;
    private String password;

    public User(){}


    public User(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "phone=" + phone +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
