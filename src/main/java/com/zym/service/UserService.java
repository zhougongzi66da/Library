package com.zym.service;

import com.zym.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserService {

    /*
    * 用户登录
    * */
    User login(String username, String password);


    /*
    * 用户注册
    * */
    boolean register(User user);

/*
* 超期扣信誉积分
* */
    void decreaseCredit(String username);


    /*
    * 查询信誉积分*/
    Integer selectCredit(String username);
}
