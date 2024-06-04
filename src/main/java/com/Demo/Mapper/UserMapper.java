package com.Demo.Mapper;

import com.Demo.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    //根据手机和密码查询用户对象
    User CheckLogin(@Param("phone") String phone, @Param("password") String password);

    //根据用户名查找对象
    User selectByUserName(String username);

    User selectByPhone(String phone);

    //更改用户信息
    void UpdateUserInfo(@Param("phone") String phone, @Param("username") String username, @Param("password") String password);

    //添加用户
    void AddUser(User user);

    //销户
    void deleteUserByUserPhone(String phone);
}

