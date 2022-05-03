package com.zym.mapper;

import com.zym.pojo.Manager;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ManagerMapper {
    /*
     * 根据用户名和密码查询用户记录
     * */
    @Select("select * from tb_manager where username=#{username} and password =#{password}")
    Manager select(@Param("username") String username, @Param("password")String password);

}
