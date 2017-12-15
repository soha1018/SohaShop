package com.itsoha.service;

import com.itsoha.dao.AdminDao;
import com.itsoha.domain.Category;
import com.itsoha.domain.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 后台
 */
public class AdminService {
    /**
     * 查询所有分类
     */
    public List<Category> findAllCategory() {
        AdminDao dao = new AdminDao();
        try {
            return dao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品信息存入数据库
     */
    public void saveProduct(Product product) {
        AdminDao dao = new AdminDao();
        try {
           dao.saveProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据订单Id查询商品详情
     */
    public List<Map<String, Object>> findOrderProductByOid(String oid) {
        AdminDao dao = new AdminDao();
        try {
            return dao.findOrderProductByOid(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
