package com.zym.service.impl;

import com.zym.mapper.BookMapper;
import com.zym.mapper.BorrowMapper;
import com.zym.pojo.Book;
import com.zym.pojo.Borrow;
import com.zym.pojo.PageBean;
import com.zym.service.BookService;
import com.zym.service.BorrowService;
import com.zym.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BorrowServiceImpl implements BorrowService {
    //1. 创建SqlSessionFactory 工厂对象
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public void add(Book book,String username) {
        SqlSession sqlSession = factory.openSession();
        SqlSession sqlSession2=factory.openSession();
        BorrowMapper mapper = sqlSession.getMapper(BorrowMapper.class);
        BookMapper mapper2 = sqlSession2.getMapper(BookMapper.class);
        Integer number = book.getNumber();
        book.setNumber(number-1);
        mapper2.update(book);

        Borrow borrow = new Borrow();
        int sum=book.getLimitDays();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String begindate=format.format(calendar.getTime());
        calendar.add(Calendar.DATE,sum);
        String enddate = format.format(calendar.getTime());

        borrow.setBorrowDate(begindate);
        borrow.setReturnDate(enddate);
        borrow.setId(book.getId());
        borrow.setBookName(book.getBookName());
        borrow.setPublisher(book.getPublisher());
        borrow.setWriter(book.getWriter());
        borrow.setStatus(0);
        borrow.setUsername(username);
        mapper.add(borrow);


        sqlSession.commit();
        sqlSession2.commit();
        sqlSession.close();
        sqlSession2.close();
    }

    @Override
    public PageBean<Borrow> selectBefore(int currentPage, int pageSize,String username) {
        SqlSession sqlSession = factory.openSession();
        BorrowMapper mapper = sqlSession.getMapper(BorrowMapper.class);
        List<Borrow> borrows = mapper.selectBefore((currentPage - 1)*pageSize, pageSize,username);
        int count = mapper.selectBeforeCount(username);
        PageBean<Borrow> borrowPageBean = new PageBean<>();
        borrowPageBean.setRows(borrows);
        borrowPageBean.setTotalCount(count);
        sqlSession.close();
        return borrowPageBean;
    }

    @Override
    public PageBean<Borrow> selectAfter(int currentPage, int pageSize,String username) {
        SqlSession sqlSession = factory.openSession();
        BorrowMapper mapper = sqlSession.getMapper(BorrowMapper.class);
        List<Borrow> borrows = mapper.selectAfter((currentPage - 1)*pageSize, pageSize,username);
        int count = mapper.selectAfterCount(username);
        PageBean<Borrow> borrowPageBean = new PageBean<>();
        borrowPageBean.setRows(borrows);
        borrowPageBean.setTotalCount(count);
        sqlSession.close();
        return borrowPageBean;
    }

    @Override
    public PageBean<Borrow> selectHistory(int currentPage, int pageSize, String username) {
        SqlSession sqlSession = factory.openSession();
        BorrowMapper mapper = sqlSession.getMapper(BorrowMapper.class);
        List<Borrow> borrows = mapper.selectHistory((currentPage - 1)*pageSize, pageSize,username);
        int count = mapper.selectHistoryCount(username);
        PageBean<Borrow> borrowPageBean = new PageBean<>();
        borrowPageBean.setRows(borrows);
        borrowPageBean.setTotalCount(count);
        sqlSession.close();
        return borrowPageBean;
    }

    @Override
    public void returnbook(String date,String actualdate) {
        SqlSession sqlSession = factory.openSession();
        BorrowMapper mapper = sqlSession.getMapper(BorrowMapper.class);
        mapper.returnbook(date,actualdate);
        sqlSession.commit();
        sqlSession.close();
    }


}
