package com.zym.service.impl;

import com.zym.mapper.ManagerMapper;
import com.zym.pojo.Manager;
import com.zym.service.ManagerService;
import com.zym.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ManagerServiceImpl implements ManagerService {
    //1. 创建SqlSessionFactory 工厂对象
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public Manager select(String username, String password) {
        SqlSession sqlSession = factory.openSession();
        ManagerMapper mapper = sqlSession.getMapper(ManagerMapper.class);
        Manager manager = mapper.select(username, password);
        return manager;
    }
}
