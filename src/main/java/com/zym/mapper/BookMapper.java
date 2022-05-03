package com.zym.mapper;

import com.zym.pojo.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {


    /**
     * 分页查询
     * @param begin
     * @param size
     * @return
     */
    @Select("select * from tb_book limit #{begin} , #{size}")
    List<Book> selectByPage(@Param("begin") int begin, @Param("size") int size);

    /**
     * 查询总记录数
     * @return
     */
    @Select("select count(*) from tb_book ")
    int selectTotalCount();


    /**
     * 分页条件查询
     * @param begin
     * @param size
     * @return
     */
    List<Book> selectByPageAndCondition(@Param("begin") int begin,@Param("size") int size,@Param("book") Book book);

    /**
     * 根据条件查询总记录数
     * @return
     */
    int selectTotalCountByCondition(Book book);


    @Update("update tb_book set book_name=#{bookName},writer=#{writer},publisher=#{publisher},limit_days=#{limitDays},number=#{number} where id=#{id}")
    void update(Book book);

    @Delete("delete from tb_book where id =#{id}")
    void delete(int id);

    @Insert("insert into tb_book values(null,#{bookName},#{writer},#{publisher},#{limitDays},#{number})")
    void add(Book book);

    @Update("update tb_book set number=number+1 where id=#{id}")
    void addone(int id);
}
