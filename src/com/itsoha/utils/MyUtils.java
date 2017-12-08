package com.itsoha.utils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }


        /**
         * 检测是否是移动设备访问
         *
         *
         * @param request
         * @return true:移动设备接入，false:pc端接入
         */
        public static boolean isMobileDevice(HttpServletRequest request) {
            String userAgent = request.getHeader("user-agent");
            // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
            // 字符串在编译时会被转码一次,所以是 "\\b"
            // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
            String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
                    + "|windows (phone|ce)|blackberry"
                    + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
                    + "|laystation portable)|nokia|fennec|htc[-_]"
                    + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
            String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
                    + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

            // 移动设备正则匹配：手机端、平板
            Pattern phonePat = Pattern.compile(phoneReg,
                    Pattern.CASE_INSENSITIVE);
            Pattern tablePat = Pattern.compile(tableReg,
                    Pattern.CASE_INSENSITIVE);
            if (null == userAgent) {
                userAgent = "";
            }
// 匹配
            Matcher matcherPhone = phonePat.matcher(userAgent);
            Matcher matcherTable = tablePat.matcher(userAgent);
            return matcherPhone.find() || matcherTable.find();
        }
    public static String getTime() {
        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}
