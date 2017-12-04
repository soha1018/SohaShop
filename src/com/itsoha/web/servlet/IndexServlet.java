package com.itsoha.web.servlet;

import com.itsoha.domain.Product;
import com.itsoha.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet",urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        //获取最热商品的信息
        List<Product> hotProductList = productService.findHotProductList();
        //获取最新商品的信息
        List<Product> newProductList = productService.findNewProductList();

        //存到域中，转发给页面
        request.setAttribute("hotProductList",hotProductList);
        request.setAttribute("newProductList",newProductList);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
}
