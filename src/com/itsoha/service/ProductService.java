package com.itsoha.service;

import com.itsoha.dao.ProductDao;
import com.itsoha.domain.Category;
import com.itsoha.domain.Order;
import com.itsoha.domain.PagerBean;
import com.itsoha.domain.Product;
import com.itsoha.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductService {
    /**
     * 获取最热商品的信息
     */
    public List<Product> findHotProductList() {
        ProductDao productDao = new ProductDao();
        List<Product> hotProductList = null;
        try {
            hotProductList = productDao.findHotProductList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotProductList;
    }

    /**
     * 获取最新商品的信息
     */
    public List<Product> findNewProductList() {
        ProductDao productDao = new ProductDao();
        try {
            return productDao.findNewProductList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获得所有分类的信息
     */
    public List<Category> findAllCategory() {
        ProductDao dao = new ProductDao();
        try {
            return dao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询分类中所有的商品
     */
    public PagerBean<Product> findPagerProductList(String cid, int currentPager, int currentCount) {
        PagerBean<Product> pagerBean = new PagerBean<>();
        pagerBean.setCurrentPager(currentPager);
        pagerBean.setCurrentCount(currentCount);

        ProductDao dao = new ProductDao();
        //当前分类显示的总条数
        int productCount = 0;
        try {
            productCount = Math.toIntExact(dao.findCategoryProductCount(cid));
            pagerBean.setTotalCount(productCount);
            //总页数
            int pagerCount = (int) Math.ceil(1.0 * productCount / currentCount);
            pagerBean.setTotalPager(pagerCount);
            //分页从哪里开始
            int index = (currentPager - 1) * currentCount;
            List<Product> list = dao.findPagerProductList(cid, index, currentCount);
            pagerBean.setList(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagerBean;
    }

    /**
     * 根据Id查询商品的信息
     */
    public Product findProductInfo(String pid) {
        ProductDao dao = new ProductDao();
        try {
            return dao.findProductInfo(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 提交订单
     *
     * @param order 订单
     */
    public void submitOrder(Order order) {
        ProductDao dao = new ProductDao();
        //开启事物
        try {
            DataSourceUtils.startTransaction();
            dao.addOrder(order);
            dao.addOrderItem(order);
        } catch (SQLException e) {
            try {
                DataSourceUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                DataSourceUtils.commitAndRelease();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 更新用户订单信息
     */
    public void updateOrder(Order order) {
        ProductDao dao = new ProductDao();
        try {
            dao.updateOrder(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户的Id获取订单
     * @param uid 用户的Id
     */
    public List<Order> findAllOrdersByUid(String uid) {
        ProductDao dao = new ProductDao();
        try {
            return dao.findAllOrdersByUid(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据订单Id获取订单详情
     */
    public List<Map<String, Object>> findAllOrderItemByOid(String oid) {
        ProductDao dao = new ProductDao();
        try {
            return dao.findAllOrderItemByOid(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更改支付状态
     */
    public void updateStatus(String oid) {
        ProductDao dao = new ProductDao();
        try {
            dao.updateStatus(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有商品信息
     */
    public List<Product> findAllProduct() {
        ProductDao dao = new ProductDao();
        try {
            return dao.findAllProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据Id查询商品信息
     */
    public Product findProductByid(String pid) {
        ProductDao dao = new ProductDao();
        try {
            return dao.findProductById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据Id修改商品信息
     * @param pid
     */
    public void updateProductByPid(Product pid) {
        ProductDao dao = new ProductDao();
        try {
            dao.updateProductById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据Id删除商品
     */
    public void delProductById(String pid) {
        ProductDao dao = new ProductDao();
        try {
            dao.delProductById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有订单
     */
    public List<Order> findAllOrders() {
        ProductDao dao = new ProductDao();
        try {
            return dao.findAllOrder();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
