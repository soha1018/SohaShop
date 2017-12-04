package com.itsoha.web.servlet;

import com.itsoha.domain.Cart;
import com.itsoha.domain.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 从购物车删除单一的商品
 */
@WebServlet(name = "DelCartItemServlet",urlPatterns = {"/delCartItem"})
public class DelCartItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        HttpSession session = request.getSession();
        Cart cart = (Cart)  session.getAttribute("cart");
        if (cart != null) {
            //删除商品后的总价(总价-单价)
            Map<String, CartItem> map = cart.getMap();
            cart.setTotalMoney(cart.getTotalMoney()-map.get(pid).getSubtotal());
            map.remove(pid);
            //删除当前商品再添加进去
            cart.setMap(map);
        }
        session.setAttribute("cart",cart);
        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }
}
