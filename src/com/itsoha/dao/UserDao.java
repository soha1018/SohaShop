package com.itsoha.dao;

import com.itsoha.domain.User;
import com.itsoha.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class UserDao {
    /**
     * 注册
     * @param user 信息
     * @return 是否成功
     */
    public int register(User user) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
        return queryRunner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
                user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),
                user.getSex(),user.getState(),user.getCode());
    }

    /**
     * 激活用户
     * @param activeCode 激活码
     */
    public void activeUser(String activeCode) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update user set state=? where code=?";
        queryRunner.update(sql,1,activeCode);
    }

    /**
     * 检查用户名是否存在
     * @param username 用户名
     */
    public Long checkUserName(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select count(*) from user where username=?";
        return (Long) queryRunner.query(sql, new ScalarHandler(), username);
    }

    /**
     * 登陆
     */
    public User login(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username=? and password=?";
        return queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
    }
}
