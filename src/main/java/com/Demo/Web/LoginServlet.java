package com.Demo.Web;

import com.Demo.Mapper.UserMapper;
import com.Demo.pojo.User;
import com.google.gson.Gson;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取phone和密码
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        //2.调用MyBatis完成查询
        //2.1获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.2获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //2.3获取Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //2.4调用方法
        User user = userMapper.CheckLogin(phone, password);
        //2.5释放资源
        sqlSession.close();
        //获取字符输出流，并设置content type
        resp.setContentType("text/html;charset=utf-8");

        //判断user是否为空
        if (user !=null){
            Cookie cookie = new Cookie("userPhone", user.getPhone());
            cookie.setMaxAge(3600); // 设置 Cookie 有效期，单位为秒
            resp.addCookie(cookie);

            // 返回用户信息 JSON
            String json = new Gson().toJson(user);
            resp.setContentType("application/json");
            resp.getWriter().write(json);
        }
        else {
            resp.getWriter().write("登录失败！");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

