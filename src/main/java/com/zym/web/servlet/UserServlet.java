package com.zym.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zym.pojo.User;
import com.zym.service.UserService;
import com.zym.service.impl.UserServiceImpl;
import com.zym.util.CheckCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService=new UserServiceImpl();

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.login(username, password);
        if(user!=null){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            // 写数据
            response.getWriter().write("success");
        }
    }
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");

        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");

        response.setContentType("text/json;charset=utf-8");
        if(!checkCodeGen.equalsIgnoreCase(checkCode)){
            //不允许注册;
            response.getWriter().write("验证码错误");
            return;
        }
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);

        boolean flag= userService.register(user);

        if(!flag){
            //注册失败
            response.getWriter().write("用户名已存在");
        }else {
            response.getWriter().write("success");
        }

    }

    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletOutputStream os = response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);
        session.setAttribute("checkCodeGen",checkCode);
    }

    public void getUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        response.getWriter().write(user.getUsername());
    }
}
