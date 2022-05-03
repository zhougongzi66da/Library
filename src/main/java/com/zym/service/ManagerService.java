package com.zym.service;

import com.zym.pojo.Manager;
import org.apache.ibatis.annotations.Param;

public interface ManagerService {
    Manager select(String username,String password);
}
