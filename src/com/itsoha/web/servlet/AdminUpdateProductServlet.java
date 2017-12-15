package com.itsoha.web.servlet;

import com.itsoha.domain.Product;
import com.itsoha.service.ProductService;
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

/**
 * 修改后台商品的信息
 */
@WebServlet(name = "AdminUpdateProductServlet",urlPatterns = {"/adminUpdateProduct"})
public class AdminUpdateProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService service = new ProductService();
        Product product = new Product();

        HashMap<String, Object> map = new HashMap<>();
        try {
            //获取磁盘文件项工厂类
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //文件上传类
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //解析request
            List<FileItem> list = fileUpload.parseRequest(request);
            if (list != null) {
                for (FileItem item : list) {
                    boolean formField = item.isFormField();
                    if (formField) {
                        //普通参数
                        String fieldName = item.getFieldName();
                        String values = item.getString("UTF-8");

                        map.put(fieldName, values);
                    } else {
                        String name = item.getName();
                        if (name != null && !name.equals("")) {
                            String path = getServletContext().getRealPath("/upload");
                            FileOutputStream outputStream = new FileOutputStream(path+"/"+name);
                            InputStream inputStream = item.getInputStream();
                            IOUtils.copy(inputStream, outputStream);
                            inputStream.close();
                            outputStream.close();
                            item.delete();

                            map.put("pimage", "/upload/"+name);
                        }

                    }
                }

            }
            //根据Id修改商品信息
            map.put("pdate", MyUtils.getTime());
            BeanUtils.populate(product, map);
            service.updateProductByPid(product);

            request.getRequestDispatcher("/productList").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
