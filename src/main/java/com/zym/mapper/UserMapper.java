package com.zym.mapper;

import com.zym.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    /*
     * 根据用户名和密码查询用户记录
     * */
    @Select("select * from tb_user where username=#{username} and password =#{password}")
    User select(@Param("username") String username, @Param("password")String password);

    /*
     * 根据用户名查询用户对象
     * */
    @Select("select * from tb_user where username=#{username}")
    User selectByUsername(String username);


    /*
     * 添加用户
     * */
    @Insert("insert into tb_user values(#{username},#{password}，100)")
    void add(User user);

    @Update("update tb_user set credit=credit-10 where username=#{username}")
    void decreaseCredit(String username);

    @Select("select credit from tb_user where username=#{username}")
    Integer selectCredit(String username);
}
