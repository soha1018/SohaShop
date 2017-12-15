package com.itsoha.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet",urlPatterns = {"/BaseServlet"})
public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {

            //1、获得请求的method的名称
            String methodName = request.getParameter("method");
            //2、获得当前被访问的对象的字节码对象
            Class clazz = this.getClass();//ProductServlet.class ---- UserServlet.class
            //3、获得当前字节码对象的中的指定方法
            Method method = null;
            method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4、执行相应功能方法
            method.invoke(this, request, response);
        }  catch (Exception e) {
            e.printStackTrace();
        }

    }
}
