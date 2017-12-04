package com.itsoha.web.servlet;

import com.itsoha.domain.PagerBean;
import com.itsoha.domain.Product;
import com.itsoha.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProductListServlet", urlPatterns = {"/productByCid"})
public class ProductListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String page = request.getParameter("page");
        if (page.equals("")) page = "1";
        int currentPager = Integer.parseInt(page);
        int currentCount = 12;

        ProductService productService = new ProductService();
        PagerBean<Product> pagerBean = productService.findPagerProductList(cid, currentPager, currentCount);
        //获取历史记录的cookies中的内容
        Cookie[] cookies = request.getCookies();
        String pids = "";
        ArrayList<Product> list = new ArrayList<>();
        if (cookies != null) {
            for (Cookie cookie :
                    cookies) {
                if ("pids".equals(cookie.getName())) {
                    pids = cookie.getValue();
                    String[] split = pids.split("-");
                    for (String s :
                            split) {
                        Product info = productService.findProductInfo(s);
                        if (info != null) {
                            list.add(info);
                        }
                    }
                }
            }
        }

        request.setAttribute("historyProduct",list);
        request.setAttribute("pagerBean", pagerBean);
        request.setAttribute("cid", cid);

        request.getRequestDispatcher("/product_list.jsp").forward(request, response);

    }
}
