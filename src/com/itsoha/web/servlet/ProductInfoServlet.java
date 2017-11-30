package com.itsoha.web.servlet;

import com.itsoha.domain.Product;
import com.itsoha.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "ProductInfoServlet",urlPatterns = {"/productInfo"})
public class ProductInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        ProductService productService = new ProductService();
        Product productList = productService.findProductInfo(pid);
        //把商品的信息，分类的ID和当前页面存入域中
        request.setAttribute("productInfo",productList);
        request.setAttribute("cid",cid);
        request.setAttribute("currentPage",currentPage);

        //获取历史记录的cookies
        Cookie[] cookies = request.getCookies();
        String pids = "";
        if (cookies != null) {
            for (Cookie cookie :
                    cookies) {
                if ("pids".equals(cookie.getName())) {
                    pids = cookie.getValue();
                    String[] split = pids.split("-");
                    //转成List集合
                    List<String> list = Arrays.asList(split);
                    LinkedList<String> linkedList = new LinkedList<>(list);
                    //判断当前集合是否包含重复的内容，如果有就删除再添加到头部，否则也添加到头部
                    if (linkedList.contains(pid)) {
                        linkedList.remove(pid);
                    }
                    linkedList.addFirst(pid);
                    StringBuilder sb = new StringBuilder();
                    //设置最大显示历史记录的数量
                    for (int i = 0; i < linkedList.size() && i < 6; i++) {
                        sb.append(linkedList.get(i));
                        sb.append("-");
                    }

                    pids = sb.substring(0, sb.length() - 1);
                }

            }
        }
        Cookie cookie_pids = new Cookie("pids", pids);
        response.addCookie(cookie_pids);

        request.getRequestDispatcher("/product_info.jsp").forward(request,response);
    }
}
