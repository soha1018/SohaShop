package com.itsoha.web.servlet;

import com.itsoha.domain.Category;
import com.itsoha.domain.Product;
import com.itsoha.service.AdminService;
import com.itsoha.utils.MyUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加商品信息
 */
@WebServlet(name = "AdminAddProductServlet", urlPatterns = {"/adminAddProduct"})
public class AdminAddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Product product = new Product();
        Map<String, Object> map = new HashMap<>();
        try {
            //获取多表单数据
            //1.创建磁盘文件项工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2.创建文件上传对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //解析数据
            List<FileItem> list = fileUpload.parseRequest(request);
            if (list != null) {
                for (FileItem item : list) {
                    //检查是否是文件的表单项
                    boolean formField = item.isFormField();
                    if (formField) {
                        //普通表单项
                        String fieldName = item.getFieldName();
                        //设置编码
                        String values = item.getString("UTF-8");

                        System.out.println(fieldName+":::"+values);
                        map.put(fieldName, values);
                    } else {
                        String name = item.getName();
                        String path = getServletContext().getRealPath("/upload");
                        FileOutputStream stream = new FileOutputStream(path+"/"+name);
                        InputStream inputStream = item.getInputStream();
                        IOUtils.copy(inputStream,stream);
                        stream.close();
                        inputStream.close();
                        item.delete();
                        map.put("pimage", "/upload/"+name);
                    }
                }
                BeanUtils.populate(product, map);

                //封装商品
                product.setPid(MyUtils.getUUID());
                product.setPdate(MyUtils.getTime());
                product.setPflag(0);
                Category category = new Category();
                category.setCid(map.get("cid").toString());
                product.setCategory(category);

                //把数据更新到数据库
                AdminService service = new AdminService();
                service.saveProduct(product);

                response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
