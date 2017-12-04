package com.itsoha.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登陆
 */
@WebServlet(name = "QuitLoginServlet", urlPatterns = {"/quitLogin"})
public class QuitLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie :
                    cookies) {
                //删除用户名或者密码
                if ("username".equals(cookie.getName()) || "password".equals(cookie.getName())) {
                    cookie.setPath(request.getContextPath());
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp?" + Math.random());
    }
}
