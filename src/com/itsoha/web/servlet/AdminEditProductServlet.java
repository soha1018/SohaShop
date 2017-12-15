package com.itsoha.web.servlet;

import com.itsoha.domain.Category;
import com.itsoha.domain.Product;
import com.itsoha.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 后台修改商品信息
 */
@WebServlet(name = "AdminEditProductServlet",urlPatterns = {"/adminEdit"})
public class AdminEditProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");

        ProductService service = new ProductService();
        Product list = service.findProductByid(pid);

        List<Category> categoryList = service.findAllCategory();

        request.setAttribute("categoryList",categoryList);
        request.setAttribute("editProduct",list);
        request.getRequestDispatcher("/admin/product/edit.jsp").forward(request,response);
    }
}
