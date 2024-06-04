package com.Demo.Mapper;


import com.Demo.pojo.UserExtra;
import org.apache.ibatis.annotations.Param;

public interface UserExtraMapper {

    // 根据用户电话查找用户额外信息
    UserExtra selectByUserPhone(String phone);

    void deleteByUserPhone(String phone);

    void insertPhone(String phone);

    void updateUserInfo2(@Param("phone") String phone, @Param("email") String email, @Param("headphoto") byte[] headphoto,@Param("gander") String gander);

    void AddUser2(UserExtra user);
}

