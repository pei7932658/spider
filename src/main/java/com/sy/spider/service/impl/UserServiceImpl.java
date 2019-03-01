package com.sy.spider.service.impl;

import com.github.pagehelper.PageHelper;
import com.sy.spider.mapper.UserMapper;
import com.sy.spider.model.User;
import com.sy.spider.model.UserExample;
import com.sy.spider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:peiliang
 * @Description:
 * @Date:2018/11/19 11:49
 * @Modified By:
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

     @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    /**
     * 分页插件pagehelper将参数传递给一个插件的一个静态方法即可
     * @param pageNum 开始页数
     * @param pageSize 每页条数
     * @return
     */
    @Override
    public List<User> findAllUser(UserExample example, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        return userMapper.selectByExample(example);
    }
}
