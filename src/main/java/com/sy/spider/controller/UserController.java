package com.sy.spider.controller;

import com.sy.spider.message.Message;
import com.sy.spider.message.MessageCode;
import com.sy.spider.model.User;
import com.sy.spider.model.UserExample;
import com.sy.spider.request.UserAddReq;
import com.sy.spider.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:peiliang
 * @Description:
 * @Date:2018/11/19 11:47
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    @ApiOperation(value = "添加用户接口")
    public Message addUser(UserAddReq req) {

        User user = new User();
        BeanUtils.copyProperties(req, user);
        return messageFactory.getInstance(MessageCode.SUCCESS, userService.addUser(user));
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @ApiOperation(value = "查询所有用户接口")
    public Message findAllUser(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "phone", required = false) String phone,
                               @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike(username);
        }
        if (!StringUtils.isEmpty(phone)) {
            criteria.andPhoneLike(phone);
        }

        return messageFactory.getInstance(MessageCode.SUCCESS, userService.findAllUser(example, pageNum, pageSize));
    }
}
