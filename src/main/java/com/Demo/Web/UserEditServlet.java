package com.Demo.Web;

import com.Demo.Mapper.UserExtraMapper;
import com.Demo.Mapper.UserMapper;
import com.Demo.pojo.User;
import com.Demo.pojo.UserExtra;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/EditPart")
public class UserEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("phone");

        //2.调用MyBatis完成查询
        //2.1获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.2获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //2.3获取Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserExtraMapper userExtraMapper = sqlSession.getMapper(UserExtraMapper.class);

        //2.4调用方法
        User userInfo = userMapper.selectByPhone(phone);
        UserExtra userExtraInfo = userExtraMapper.selectByUserPhone(phone);

        //2.5释放资源
        sqlSession.close();

        //获取字符输出流，并设置content type
        response.setContentType("text/html;charset=utf-8");

        Map<String,Object> combinedUserInfo = new HashMap<>();
        combinedUserInfo.put("phone", userInfo.getPhone());
        combinedUserInfo.put("username", userInfo.getUsername());
        combinedUserInfo.put("password", userInfo.getPassword());
        combinedUserInfo.put("email",userExtraInfo.getEmail());
        combinedUserInfo.put("headphoto",userExtraInfo.getHeadphoto());
        combinedUserInfo.put("gander",userExtraInfo.getGander());

        // 将用户信息转换为 JSON 字符串
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(combinedUserInfo);

        // 返回 JSON 响应
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"success\": true, \"userInfo\": " + userInfoJson + "}");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 使用 commons-fileupload 解析表单数据，包括头像图片
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        Map<String, String> params = new HashMap<>();
        String headphotoBase64 = null; // 用于存储头像的 Base64 编码

        try {
            for (FileItem item : upload.parseRequest(request)) {
                if (item.isFormField()) {
                    params.put(item.getFieldName(), item.getString("UTF-8")); // 文本字段
                } else {
                    // 处理头像文件
                    byte[] imageBytes = item.get();
                    headphotoBase64 = Base64.getEncoder().encodeToString(imageBytes);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"文件上传失败\"}");
            return;
        }

        // 2. 从表单数据中获取用户信息
        String phone = params.get("phone");
        String username = params.get("username");
        String email = params.get("email");
        String password = params.get("password");
        String gender = params.get("gender");

        // 3. 调用 MyBatis 完成更新
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserExtraMapper userExtraMapper = sqlSession.getMapper(UserExtraMapper.class);

        // 3.1 更新 User 表
        User user = userMapper.selectByPhone(phone);
        if (user != null) {
            user.setUsername(username);
            if (!password.isEmpty()) { // 如果密码不为空，则更新密码
                user.setPassword(password);
            }
            userMapper.UpdateUserInfo(user.getPhone(),user.getUsername(),user.getPassword());
        }

        // 3.2 更新 UserExtra 表
        UserExtra userExtra = userExtraMapper.selectByUserPhone(phone);
        if (userExtra != null) {
            userExtra.setEmail(email);
            userExtra.setGander(gender);
            if (headphotoBase64 != null) {
                userExtra.setHeadphoto(headphotoBase64.getBytes());
            }
            userExtraMapper.updateUserInfo2(userExtra.getPhone1(), userExtra.getEmail(), userExtra.getHeadphoto(), userExtra.getGander());
        }

        sqlSession.commit();
        sqlSession.close();

        // 4. 返回 JSON 响应
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"success\": true}");
        out.flush();
    }
}
