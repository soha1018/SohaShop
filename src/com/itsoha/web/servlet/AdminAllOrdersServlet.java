package com.itsoha.web.servlet;

import com.itsoha.domain.Order;
import com.itsoha.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 后台查询所有订单
 */
@WebServlet(name = "AdminAllOrdersServlet",urlPatterns = {"/allOrders"})
public class AdminAllOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = new ProductService();
        List<Order> orderList = service.findAllOrders();

        request.setAttribute("adminOrderList",orderList);
        request.getRequestDispatcher("/admin/order/list.jsp").forward(request,response);
    }
}
