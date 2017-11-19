package com.itsoha.service;

import com.itsoha.dao.UserDao;
import com.itsoha.domain.User;

import java.sql.SQLException;

public class UserService {
    /**
     * 注册
     * @param user 注册的信息
     * @return 是否注册成功
     */
    public boolean register(User user)  {
        UserDao userDao = new UserDao();
        int register = 0;
        try {
            register = userDao.register(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return register > 0;
    }

    /**
     * 激活账号
     * @param activeCode 激活码
     */
    public void activeCode(String activeCode) {
        UserDao userDao = new UserDao();
        try {
            userDao.activeUser(activeCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 结果
     */
    public boolean checkUserName(String username) {
        UserDao userDao = new UserDao();
        Long aLong = 0L;
        try {
            aLong = userDao.checkUserName(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aLong>0;
    }
}
