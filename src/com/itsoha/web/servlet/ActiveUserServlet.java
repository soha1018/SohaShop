package com.itsoha.web.servlet;

import com.itsoha.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ActiveUserServlet",urlPatterns = {"/active"})
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activeCode = request.getParameter("activeCode");
        UserService userService = new UserService();
        //激活账号
        userService.activeCode(activeCode);

        response.sendRedirect(request.getContextPath()+"/login.jsp");
    }
}
