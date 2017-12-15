package com.itsoha.web.servlet;

import com.google.gson.Gson;
import com.itsoha.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 根据订单Id查询商品详情
 */
@WebServlet(name = "AdminOrderProductServlet",urlPatterns = {"/adminOrderProduct"})
public class AdminOrderProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");
        AdminService service = new AdminService();
        List<Map<String, Object>> mapList = service.findOrderProductByOid(oid);

        Gson gson = new Gson();
        String json = gson.toJson(mapList);

        System.out.println(json);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
