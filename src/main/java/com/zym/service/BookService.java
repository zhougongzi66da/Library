package com.zym.service;

import com.zym.pojo.Book;
import com.zym.pojo.PageBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookService {

    PageBean<Book> selectByPage(int currentPage, int pageSize);

    /**
     * 分页条件查询
     * @param currentPage
     * @param pageSize
     * @param book
     * @return
     */

    PageBean<Book>  selectByPageAndCondition(int currentPage,int pageSize,Book book);

    void update(Book book);


    void delete(int id);

    void add(Book book);

    void addone(int id);
}
