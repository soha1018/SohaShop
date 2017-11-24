package com.itsoha.web.filter;

import com.itsoha.domain.User;
import com.itsoha.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@WebFilter(filterName = "ShopFilter", urlPatterns = {"/*"})
public class ShopFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //改进getParameter方法的HttpServletRequest
//        request = encoding(request);

        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        //自动登陆
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;
        if (cookies != null) {
            for (Cookie cookie :
                    cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }

        if (username!=null && password!=null) {
            UserService userService = new UserService();
            User user = userService.login(username, password);
            session.setAttribute("user", user);
        }
        chain.doFilter(request, response);
    }

    /**
     * 动态代理解决全局乱码
     */
    private HttpServletRequest encoding(HttpServletRequest request) {
        HttpServletRequest res = (HttpServletRequest) Proxy.newProxyInstance(request.getClass().getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //获得目标对象的方法名称
                String name = method.getName();
                if ("getParameter".equals(name)) {
                    //调用的类和传入的参数
                    String invoke = (String) method.invoke(request, args);
                    invoke = new String(invoke.getBytes("iso8859-1"), "UTF-8");
                    return invoke;
                }
                return method.invoke(request, args);
            }
        });

        return res;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
