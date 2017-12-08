package com.itsoha.web.servlet;

import com.itsoha.domain.*;
import com.itsoha.service.ProductService;
import com.itsoha.utils.MyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 提交订单
 */
@WebServlet(name = "SubmitOrderServlet", urlPatterns = {"/submitOrder"})
public class SubmitOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //判断用户是否登陆
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        //创建一个订单
        Order order = new Order();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            order.setOid(MyUtils.getUUID());

            order.setOrdertime(MyUtils.getTime());
            //总金额
            order.setTotal(cart.getTotalMoney());
            //支付状态，未支付
            order.setState(0);
            order.setAddress(null);
            order.setName(null);
            order.setTelephone(null);
            order.setUser(user);
            //封装订单项
            Map<String, CartItem> map = cart.getMap();
            for (Map.Entry<String, CartItem> item : map.entrySet()) {
                CartItem value = item.getValue();
                OrderItem orderItem = new OrderItem();
                orderItem.setItemid(MyUtils.getUUID());
                orderItem.setCount(value.getBuyNum());
                orderItem.setSubtotal(value.getSubtotal());
                orderItem.setProduct(value.getProduct());
                orderItem.setOrder(order);
                //把订单项封装进订单
                order.getOrderItems().add(orderItem);
            }

            //提交订单到数据库
            ProductService service = new ProductService();
            service.submitOrder(order);

        }
        session.setAttribute("order", order);
        response.sendRedirect(request.getContextPath()+"/order_info.jsp");

    }
}
