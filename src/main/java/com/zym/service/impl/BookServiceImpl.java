package com.zym.service.impl;

import com.zym.mapper.BookMapper;
import com.zym.pojo.Book;
import com.zym.pojo.PageBean;
import com.zym.service.BookService;
import com.zym.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BookServiceImpl implements BookService {
    //1. 创建SqlSessionFactory 工厂对象
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();


    @Override
    public PageBean<Book> selectByPage(int currentPage, int pageSize) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BookMapper mapper = sqlSession.getMapper(BookMapper.class);

        //4. 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;

        //5. 查询当前页数据
        List<Book> rows = mapper.selectByPage(begin, size);

        //6. 查询总记录数
        int totalCount = mapper.selectTotalCount();

        //7. 封装PageBean对象
        PageBean<Book> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        //8. 释放资源
        sqlSession.close();
        return pageBean;
    }

    @Override
    public PageBean<Book> selectByPageAndCondition(int currentPage, int pageSize, Book book) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BookMapper mapper = sqlSession.getMapper(BookMapper.class);


        //4. 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;

        // 处理book条件，模糊表达式
        String bookName = book.getBookName();
        String writer = book.getWriter();
        String publisher = book.getPublisher();

        if (bookName != null && bookName.length() > 0) {
            book.setBookName("%" + bookName + "%");
        }

        if (writer != null && writer.length() > 0) {
            book.setWriter("%" + writer + "%");
        }
        if (publisher != null && publisher.length() > 0) {
            book.setPublisher("%" + publisher + "%");
        }


        //5. 查询当前页数据
        List<Book> rows = mapper.selectByPageAndCondition(begin, size, book);

        //6. 查询总记录数
        int totalCount = mapper.selectTotalCountByCondition(book);

        //7. 封装PageBean对象
        PageBean<Book> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        //8. 释放资源
        sqlSession.close();

        return pageBean;
    }

    @Override
    public void update(Book book) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BookMapper mapper = sqlSession.getMapper(BookMapper.class);

        mapper.update(book);

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(int id) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BookMapper mapper = sqlSession.getMapper(BookMapper.class);

        mapper.delete(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void add(Book book) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BookMapper mapper = sqlSession.getMapper(BookMapper.class);
        mapper.add(book);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void addone(int id) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BookMapper mapper = sqlSession.getMapper(BookMapper.class);
        mapper.addone(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
