package com.itsoha.web.servlet;

import com.google.gson.Gson;
import com.itsoha.domain.Category;
import com.itsoha.service.AdminService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet",urlPatterns = {"/admin"})
public class AdminServlet extends BaseServlet {
    /**
     * 查询所有分类
     */
    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AdminService service = new AdminService();
        List<Category> category = service.findAllCategory();

        Gson gson = new Gson();
        String json = gson.toJson(category);

        response.getWriter().write(json);
    }
}
