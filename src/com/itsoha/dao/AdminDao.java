package com.itsoha.dao;

import com.itsoha.domain.Category;
import com.itsoha.domain.Product;
import com.itsoha.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminDao {
    /**
     * 查询所有分类
     */
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        return runner.query(sql, new BeanListHandler<Category>(Category.class));

    }

    /**
     * 商品信息存入数据库
     */
    public int saveProduct(Product product) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        return runner.update(sql,product.getPid(), product.getPname(),product.getMarket_price(),
                product.getShop_price(),product.getPimage(),product.getPdate(),
                product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCategory().getCid() );
    }

    /**
     * 根据订单Id查询商品详情
     */
    public List<Map<String, Object>> findOrderProductByOid(String oid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select p.pimage,p.pname,p.shop_price,i.count,i.subtotal from orderitem i,product p where i.pid=p.pid and i.oid=?";
        return runner.query(sql, new MapListHandler(),oid);
    }
}
