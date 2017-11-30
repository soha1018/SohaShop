package com.itsoha.dao;

import com.itsoha.domain.Category;
import com.itsoha.domain.Product;
import com.itsoha.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDao {

    /**
     * 获取最热商品的信息
     */
    public List<Product> findHotProductList() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where is_hot=? limit ?,?";
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class),1,0,9);
    }

    public List<Product> findNewProductList() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product order by pdate desc limit ?,?";
        return queryRunner.query(sql, new BeanListHandler<>(Product.class), 0, 9);
    }

    /**
     * 获得所有分类的信息
     */
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        return queryRunner.query(sql, new BeanListHandler<>(Category.class) );
    }

    /**
     * 查询分类中所有的商品的总数
     */
    public Long findCategoryProductCount(String cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where cid=?";
        return (Long) queryRunner.query(sql, new ScalarHandler(),cid);
    }

    /**
     * 查询分类中所有的商品
     */
    public List<Product> findPagerProductList(String cid, int index, int currentCount) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where cid=? limit ?,?";
        return queryRunner.query(sql,new BeanListHandler<>(Product.class),cid,index,currentCount);
    }

    /**
     * 根据Id查询商品的信息
     */
    public Product findProductInfo(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid=?";
        return queryRunner.query(sql, new BeanHandler<>(Product.class), pid);
    }
}
