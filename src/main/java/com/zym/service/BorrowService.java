package com.zym.service;

import com.zym.pojo.Book;
import com.zym.pojo.Borrow;
import com.zym.pojo.PageBean;

public interface BorrowService {
    void add(Book book,String username);

    PageBean<Borrow> selectBefore(int currentPage, int pageSize,String username);

    PageBean<Borrow> selectAfter(int currentPage,int pageSize,String username);

    PageBean<Borrow> selectHistory(int currentPage,int pageSize,String username);

    void returnbook(String date,String actualdate);
}
