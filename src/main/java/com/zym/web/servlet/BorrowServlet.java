package com.zym.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zym.pojo.Book;
import com.zym.pojo.Borrow;
import com.zym.pojo.PageBean;
import com.zym.pojo.User;
import com.zym.service.BookService;
import com.zym.service.BorrowService;
import com.zym.service.UserService;
import com.zym.service.impl.BookServiceImpl;
import com.zym.service.impl.BorrowServiceImpl;
import com.zym.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

@WebServlet("/borrow/*")
public class BorrowServlet extends BaseServlet{
    private BorrowService borrowService=new BorrowServiceImpl();
    private BookService bookService=new BookServiceImpl();
    private UserService userservice= new UserServiceImpl();

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

/*        接收用户名*/
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");

        String username = user.getUsername();

        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //转为book对象
        Book book = JSON.parseObject(params, Book.class);

        Integer credit = userservice.selectCredit(username);

        if (credit<=0){
            response.getWriter().write("error");
            return;
        }
        borrowService.add(book,username);

        //3. 响应成功的标识
        response.getWriter().write("success");
    }

    public void selectBefore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        PageBean<Borrow> borrowPageBean = borrowService.selectBefore(currentPage, pageSize, user.getUsername());

        //2. 转为JSON
        String jsonString = JSON.toJSONString(borrowPageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);


    }

    public void selectAfter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        PageBean<Borrow> borrowPageBean = borrowService.selectAfter(currentPage, pageSize, user.getUsername());

        //2. 转为JSON
        String jsonString = JSON.toJSONString(borrowPageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);

    }

    public void selectHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        PageBean<Borrow> borrowPageBean = borrowService.selectHistory(currentPage, pageSize, user.getUsername());

        //2. 转为JSON
        String jsonString = JSON.toJSONString(borrowPageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);

    }

    public void returnbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收数据
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //转为对象
        Borrow borrow = JSON.parseObject(params, Borrow.class);
        String borrowDate = borrow.getBorrowDate();
        String returnDate = borrow.getReturnDate();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String actualdate=format.format(calendar.getTime());

        borrowService.returnbook(borrowDate,actualdate);
        bookService.addone(borrow.getId());
        response.setContentType("text/html;charset=UTF-8");
        if(actualdate.compareTo(returnDate)>0){
            userservice.decreaseCredit(borrow.getUsername());
            response.getWriter().write("超时");
        }else {
            response.getWriter().write("未超时");
        }

    }


}
