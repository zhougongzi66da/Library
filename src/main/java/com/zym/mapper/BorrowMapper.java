package com.zym.mapper;

import com.zym.pojo.Book;
import com.zym.pojo.Borrow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BorrowMapper {
    @Insert("insert into tb_borrow values(#{id},#{bookName},#{writer},#{publisher},#{username},#{borrowDate},#{returnDate},#{actualDate},#{status})")
    void add(Borrow borrow);

/**
 * 1.查找没过期的
 * */

    List<Borrow> selectBefore(@Param("begin") int begin, @Param("size") int size,@Param("name") String name);


    int selectBeforeCount(String name);

    /**
     * 2.查找过期的
     * */

    List<Borrow> selectAfter(@Param("begin") int begin, @Param("size") int size,@Param("name") String name);


    int selectAfterCount(String name);

    @Update("update tb_borrow set status=1,actual_date=#{actualdate} where borrow_date=#{borrowdate}")
    void returnbook(@Param("borrowdate")String borrowdate,@Param("actualdate")String actualdate);

    List<Borrow> selectHistory(@Param("begin") int begin, @Param("size") int size,@Param("name") String name);


    int selectHistoryCount(String name);

}
