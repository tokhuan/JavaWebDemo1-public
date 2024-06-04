package com.Demo.pojo;

import java.util.Arrays;

public class UserExtra {
    private String phone;
    private String email;
    private byte[] headphoto;
    private String gander;

    public UserExtra(){}

    public UserExtra(String phone, String email, byte[] headphoto, String gander) {
        this.phone = phone;
        this.email = email;
        this.headphoto = headphoto;
        this.gander = gander;
    }

    public String getPhone1() {
        return phone;
    }
    public void setPhone1(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getHeadphoto() {
        return headphoto;
    }
    public void setHeadphoto(byte[] headphoto) {
        this.headphoto = headphoto;
    }

    public String getGander() {
        return gander;
    }
    public void setGander(String gander) {
        this.gander = gander;
    }

    @Override
    public String toString() {
        return "UserExtra{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", headphoto=" + Arrays.toString(headphoto) +
                ", gander='" + gander + '\'' +
                '}';
    }
}
