package com.itsoha.web.servlet;

import com.itsoha.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 根据Id删除商品
 */
@WebServlet(name = "AdminDelProductServlet",urlPatterns = {"/adminDel"})
public class AdminDelProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        ProductService service = new ProductService();
        service.delProductById(pid);

        request.getRequestDispatcher("/productList").forward(request,response);

    }
}
