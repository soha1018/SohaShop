package com.itsoha.web.servlet;

import com.itsoha.domain.Cart;
import com.itsoha.domain.CartItem;
import com.itsoha.domain.Product;
import com.itsoha.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 把商品信息添加到购物车
 */
@WebServlet(name = "AddProductToCartServlet", urlPatterns = {"/addProductToCart"})
public class AddProductToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductService productService = new ProductService();

        String pid = request.getParameter("pid");
        String buyNum = request.getParameter("buyNum");
        String currentPage = request.getParameter("currentPage");

        int count = 1;
        int parseInt = Integer.parseInt(buyNum);
        if (buyNum.equals("") || parseInt <= 0) {
            buyNum = "1";
        }
        count = Integer.parseInt(buyNum);

        Product product = productService.findProductInfo(pid);

        //这个商品的单价和个数，算出来的一个购物项目的总价
        double money = count * product.getShop_price();
        //把单个购物项的内容存储
        CartItem cartItem = new CartItem();
        cartItem.setBuyNum(count);
        cartItem.setSubtotal(money);
        cartItem.setProduct(product);

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        //判断购物车是否存在该商品
        Map<String, CartItem> map = cart.getMap();
        if (map.containsKey(pid)) {
            count += map.get(pid).getBuyNum();
            cartItem.setBuyNum(count);

            money = map.get(pid).getSubtotal() * count;
            System.out.println(money);
            cartItem.setSubtotal(money);
            //总价
            double v = cart.getTotalMoney() + money - product.getShop_price();
            cart.setTotalMoney(v);
        } else {
            cart.setTotalMoney(cart.getTotalMoney()+money);
        }
        //将购物项放到车中
        cart.getMap().put(pid, cartItem);

        request.setAttribute("currentPage",currentPage);
        session.setAttribute("cart", cart);
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}
