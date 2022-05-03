package com.zym.service.impl;

import com.zym.mapper.UserMapper;
import com.zym.pojo.User;
import com.zym.service.UserService;
import com.zym.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserServiceImpl implements UserService {
    //1. 创建SqlSessionFactory 工厂对象
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();


    @Override
    public User login(String username, String password) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.select(username, password);
        sqlSession.close();
        return user;
    }

    @Override
    public boolean register(User user) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User u = mapper.selectByUsername(user.getUsername());
        if (u==null){
            mapper.add(user);
        }
        sqlSession.commit();
        sqlSession.close();
        return u==null;
    }

    @Override
    public void decreaseCredit(String username) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        mapper.decreaseCredit(username);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Integer selectCredit(String username) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer credit = mapper.selectCredit(username);
        sqlSession.close();
        return credit;
    }
}
