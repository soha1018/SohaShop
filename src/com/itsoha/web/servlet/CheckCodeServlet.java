package com.itsoha.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检测验证码
 */
@WebServlet(name = "CheckCodeServlet",urlPatterns = {"/checkCode"})
public class CheckCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String attribute = (String) request.getSession().getAttribute("checkcode_session");
        String checkCode = request.getParameter("checkCode");

        if (checkCode.equals(attribute)) {
            response.getWriter().write("{\"isCode\":" + true + "}");
        }
    }
}
