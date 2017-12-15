package com.itsoha.dao;

import com.itsoha.domain.Category;
import com.itsoha.domain.Order;
import com.itsoha.domain.OrderItem;
import com.itsoha.domain.Product;
import com.itsoha.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    /**
     * 提交订单
     * @param order 订单
     */
    public void addOrder(Order order) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = DataSourceUtils.getConnection();
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        runner.update(conn, sql, order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
                order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
    }

    /**
     * 插入订单项
     * @param order 订单
     */
    public void addOrderItem(Order order) throws SQLException {
        QueryRunner runner = new QueryRunner();
        List<OrderItem> list = order.getOrderItems();
        Connection conn = DataSourceUtils.getConnection();
        String sql = "insert into orderitem values(?,?,?,?,?)";
        for (OrderItem item :list) {
            runner.update(conn, sql, item.getItemid(),item.getCount(),item.getSubtotal(),item.getProduct().getPid(),item.getOrder().getOid());
        }
    }

    /**
     * 更新用户订单信息
     */
    public void updateOrder(Order order) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update orders set name=?,address=?,telephone=? where oid=?";
        runner.update(sql, order.getName(),order.getAddress(),order.getTelephone(),order.getOid());
    }

    /**
     * 根据用户的Id获取订单
     * @param uid 用户的Id
     */
    public List<Order> findAllOrdersByUid(String uid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders where uid=?";
        return runner.query(sql, new BeanListHandler<Order>(Order.class),uid);
    }

    /**
     * 根据订单Id获取订单详情
     */
    public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select i.count,i.subtotal,p.pimage,p.pname,p.shop_price from orderItem i,product p where i.pid=p.pid and i.oid=?";
        return runner.query(sql, new MapListHandler(), oid);

    }

    /**
     * 更改支付状态
     * @param oid
     */
    public void updateStatus(String oid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update orders set state=1 where oid=?";
        runner.update(sql,oid);
    }

    /**
     * 查询所有商品信息
     */
    public List<Product> findAllProduct() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product";
        return runner.query(sql, new BeanListHandler<Product>(Product.class));
    }

    /**
     * 根据Id查询商品信息
     */
    public Product findProductById(String pid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid=?";
        return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
    }

    /**
     * 根据Id修改商品信息
     */
    public void updateProductById(Product product) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=? ";
        runner.update(sql, product.getPname(),product.getMarket_price(),
                product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
                product.getPdesc(),product.getPflag(),product.getCid(),product.getPid());
    }

    /**
     * 根据Id删除商品
     */
    public void delProductById(String pid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from product where pid=?";
        runner.update(sql, pid);
    }

    /**
     * 查询所有订单
     */
    public List<Order> findAllOrder() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders";
        return runner.query(sql, new BeanListHandler<Order>(Order.class));
    }
}
