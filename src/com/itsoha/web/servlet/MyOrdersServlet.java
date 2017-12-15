package com.itsoha.web.servlet;

import com.itsoha.domain.Order;
import com.itsoha.domain.OrderItem;
import com.itsoha.domain.Product;
import com.itsoha.domain.User;
import com.itsoha.service.ProductService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 我的订单
 */
@WebServlet(name = "MyOrdersServlet",urlPatterns = {"/myOrders"})
public class MyOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //判断用户是否登陆
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        //获取用户的订单
        ProductService service = new ProductService();
        List<Order> orderList = service.findAllOrdersByUid(user.getUid());
        if (orderList != null) {
            for (Order order:orderList) {
                //查询订单的所有订单项
                List<Map<String, Object>> mapList = service.findAllOrderItemByOid(order.getOid());
                for (Map<String, Object> map : mapList) {
                    try {
                        //从map中取出count，subtotal存到orderItem中
                        OrderItem item = new OrderItem();
                        BeanUtils.populate(item,map);
                        //将商品信息存入订单项
                        Product product = new Product();
                        BeanUtils.populate(product,map);
                        item.setProduct(product);
                        //将订单项存入订单
                        order.getOrderItems().add(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println(orderList);
        //orderList封装完整
        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/order_list.jsp").forward(request,response);
    }
}
