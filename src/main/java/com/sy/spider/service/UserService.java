package com.sy.spider.service;

import com.sy.spider.model.User;
import com.sy.spider.model.UserExample;

import java.util.List;

/**
 * @Author:peiliang
 * @Description:
 * @Date:2018/11/19 11:48
 * @Modified By:
 */
public interface UserService {
    /**
     * 添加User
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 查询所有User
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<User> findAllUser(UserExample example, int pageNum, int pageSize);
}
