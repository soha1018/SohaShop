package com.itsoha.web.servlet;

import com.google.gson.Gson;
import com.itsoha.domain.User;
import com.itsoha.service.UserService;
import com.itsoha.utils.MD5Utils;
import com.itsoha.utils.MyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //判断移动端
        boolean device = MyUtils.isMobileDevice(request);
        if (device) {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            User user = gson.fromJson(reader.readLine(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        }
        //进行MD5加密
        password = MD5Utils.md5(password);

        UserService userService = new UserService();
        User user = userService.login(username, password);

        if (user != null) {
            //自动登录
            autoLogin(request, response, username, password);
            //记住账号
            saveLogin(request,response,username);
            //登陆成功
            if (device) {
                response.getWriter().write("{\"success\":\"ok\"}");
            } else {
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        } else {
            request.setAttribute("loginInfo", "error");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }


    }

    /**
     * 记住账号
     */
    private void saveLogin(HttpServletRequest request, HttpServletResponse response, String username) {
        String saveName = request.getParameter("saveName");
        if (saveName != null) {
            Cookie cookie = new Cookie("saveName", username);
            cookie.setPath(request.getContextPath() + "/login.jsp");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("saveName", username);
            cookie.setPath(request.getContextPath() + "/login.jsp");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 自动登录
     */
    private void autoLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        //获取自动登陆的参数
        String autoLogin = request.getParameter("autoLogin");

        if (autoLogin != null) {
            request.getSession().setAttribute("autoLogin", true);
            Cookie usernameCookie = new Cookie("username", username);
            Cookie passwordCookie = new Cookie("password", password);
            usernameCookie.setMaxAge(60 * 60);
            passwordCookie.setMaxAge(60 * 60);
            usernameCookie.setPath(request.getContextPath());
            passwordCookie.setPath(request.getContextPath());
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
        } else {
            request.getSession().setAttribute("autoLogin", false);
        }
    }
}
