package com.itsoha.web.servlet;

import com.google.gson.Gson;
import com.itsoha.domain.Category;
import com.itsoha.service.ProductService;
import com.itsoha.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryListServlet", urlPatterns = {"/categoryList"})
public class CategoryListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Jedis jedis = JedisPoolUtils.getJedis();
        String categoryListJSON = jedis.get("categoryList");
        if (categoryListJSON == null) {
            //去数据库中取数据
            ProductService productService = new ProductService();
            List<Category> categoryList = productService.findAllCategory();
            Gson gson = new Gson();
            categoryListJSON = gson.toJson(categoryList);
            jedis.set("categoryList", categoryListJSON);
        }
        response.getWriter().write(categoryListJSON);
    }
}
