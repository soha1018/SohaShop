package com.itsoha.web.servlet;

import com.google.gson.Gson;
import com.itsoha.domain.User;
import com.itsoha.service.UserService;
import com.itsoha.utils.MD5Utils;
import com.itsoha.utils.MyUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.itsoha.utils.MailUtils.sendMail;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        User user = new User();
        try {
            //接收移动端发送的数据
            boolean mobileDevice = MyUtils.isMobileDevice(request);
            if (mobileDevice) {
                BufferedReader reader = request.getReader();
                Gson gson = new Gson();
                user = gson.fromJson(reader.readLine(), User.class);
            }
            //类型转换（String ---> Date）
            ConvertUtils.register(new Converter() {

                public Object convert(Class aClass, Object o) {
                    SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                    format.applyPattern("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = format.parse(o.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date;
                }
            }, Date.class);
            //BeanUtils解析参数封装到user中
            Map<String, String[]> map = request.getParameterMap();
            BeanUtils.populate(user, map);

            user.setUid(MyUtils.getUUID());
            String password = user.getPassword();
            user.setPassword(MD5Utils.md5(password));
            user.setState(0);
            String activeCode = MyUtils.getUUID();
            user.setCode(activeCode);

            //将user传递给service
            UserService userService = new UserService();
            boolean register = userService.register(user);
            if (register) {
                //发送邮箱激活验证
                String msg = "请点击以下内容激活您的账号:<a href=http://localhost:8080/Shop/active?activeCode=" + activeCode + " target='_BLANK' >http://localhost:8080/Shop/active?activeCode=" + activeCode + "<a/>";
                sendMail(user.getEmail(), msg);
                //给移动端返回数据
                if (mobileDevice) {
                    response.getOutputStream().write("{\"success\":\"ok\"}".getBytes());
                } else {
                    response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
