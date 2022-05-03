package com.zym.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zym.pojo.Book;
import com.zym.pojo.PageBean;
import com.zym.service.BookService;
import com.zym.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/book/*")
public class BookServlet extends BaseServlet{
    private BookService bookService=new BookServiceImpl();


    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收数据
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //转为对象
        Book book = JSON.parseObject(params, Book.class);

        Integer id = book.getId();

        //2. 调用service添加
        bookService.delete(id);

        //3. 响应成功的标识
        response.getWriter().write("success");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 接收数据
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //转为对象
        Book book = JSON.parseObject(params, Book.class);

        //2. 调用service添加
        bookService.update(book);

        //3. 响应成功的标识
        response.getWriter().write("success");
    }


    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 接收数据
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //转为对象
        Book book = JSON.parseObject(params, Book.class);

        //2. 调用service添加
        bookService.add(book);

        //3. 响应成功的标识
        response.getWriter().write("success");
    }

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        //2. 调用service查询
        PageBean<Book> pageBean = bookService.selectByPage(currentPage, pageSize);

        //2. 转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }




    /**
     * 分页条件查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        // 获取查询条件对象
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //转为 Book
        Book book = JSON.parseObject(params, Book.class);


        //2. 调用service查询
        PageBean<Book> pageBean = bookService.selectByPageAndCondition(currentPage,pageSize,book);

        //2. 转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

}
