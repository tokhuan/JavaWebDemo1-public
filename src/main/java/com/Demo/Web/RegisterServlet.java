package com.Demo.Web;

import com.Demo.Mapper.UserMapper;
import com.Demo.pojo.User;
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

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收参数
        String phone = req.getParameter("phone");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("phone:"+phone);
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        //封装对象
        User user=new User();
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(password);
        //2.调用MyBatis完成查询
        //2.1获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.2获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //2.3获取Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //调用方法
        User u = userMapper.selectByUserName(username);
        //设置utf-8编码格式以及识别html标签
        resp.setContentType("text/html;charset=utf-8");
        //判断u是否为空
        if (u ==null){
            //为空，添加用户信息
            userMapper.AddUser(user);
            //提交事务
            sqlSession.commit();
            //释放资源
            sqlSession.close();
            //注册成功，后续可以跳转到首页
            resp.getWriter().write("注册成功111！");
        }
        else {
            resp.getWriter().write("用户已经存在，点击登录吧^_^");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

