package com.itsoha.web.servlet;

import com.itsoha.domain.Order;
import com.itsoha.service.ProductService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 提交订单
 */
@WebServlet(name = "ConfirmOrderServlet", urlPatterns = {"/confirmOrder"})
public class ConfirmOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //更新收货人信息
        Map<String, String[]> map = request.getParameterMap();
        ProductService service = new ProductService();
        Order order = new Order();
        try {
            BeanUtils.populate(order, map);
            service.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //支付状态修改
        service.updateStatus(order.getOid());
        //在线支付
        request.getRequestDispatcher("/success.jsp").forward(request, response);
    }
}
