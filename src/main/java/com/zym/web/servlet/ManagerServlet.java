package com.zym.web.servlet;

import com.zym.pojo.Manager;
import com.zym.pojo.User;
import com.zym.service.ManagerService;
import com.zym.service.impl.ManagerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/manager/*")
public class ManagerServlet extends BaseServlet{
   public ManagerService managerService=new ManagerServiceImpl();
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Manager manager = managerService.select(username, password);
        if(manager!=null){
            HttpSession session = request.getSession();
            session.setAttribute("manager",manager);
            // 写数据
            response.getWriter().write("success");
        }
    }
    public void getUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        Manager manager = (Manager)session.getAttribute("manager");
        response.getWriter().write(manager.getUsername());
    }
}
