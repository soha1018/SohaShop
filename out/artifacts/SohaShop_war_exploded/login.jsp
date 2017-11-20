<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员登录</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/jquery.validate.min.js"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }

        .container .row div {
            /* position:relative;
                         float:left; */

        }

        font {
            color: #666;
            font-size: 22px;
            font-weight: normal;
            padding-right: 17px;
        }

        .error {
            color: #ff0000;
        }

        #J_Message {
            padding: 6px;
            background-color: #fef2f2;
            color: #6C6C6C;
        }

    </style>

    <script type="text/javascript">
        //自定义验证码验证
        $.validator.addMethod(
            "checkCode", function
                (values, element, params) {
                var code = false;
                $.ajax({
                    async: false,
                    type: "POST",
                    url: "${pageContext.request.contextPath}/checkCode",
                    data: "checkCode=" + values,
                    dataType: "json",
                    success: function (data) {
                        code = data.isCode;
                    }
                });
                return code;
            }
        )
        ;
        $(function () {
            //点击图片更换验证码
            $("#loginCode").click(function () {
                $(this).attr("src", "${pageContext.request.contextPath}/checkImage?" + Math.random());
            });
            //登陆表单验证
            $("#loginForm").validate({
                rules: {
                    "username": {
                        required: true
                    },
                    "password": {
                        required: true,
                        rangelength: [6, 12]
                    },
                    "checkCode": {
                        required: true,
                        checkCode: true
                    }

                },
                messages: {
                    "username": {
                        required: "请输入用户名"
                    },
                    "password": {
                        required: "请输入密码",
                        rangelength: "密码长度6-12"
                    },
                    "checkCode": {
                        required: "请输入验证码",
                        checkCode: "验证码不正确，请重新输入"
                    }
                }
            });
        });
    </script>

</head>
<body>

<!-- 引入header.jsp -->
<jsp:include page="/header.jsp"></jsp:include>


<div class="container"
     style="width: 100%; height: 460px; background: #FF2C4C url('images/loginbg.jpg') no-repeat;">
    <div class="row">
        <div class="col-md-7">
            <!--<img src="./image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
        </div>

        <div class="col-md-5">
            <div style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
                <font>会员登录</font>USER LOGIN
                <c:if test="${requestScope.loginInfo.equals('error')}">
                    <div id="J_Message" style="border: 1px solid #ffb4a8;">
                        <img width="22px" height="16px" src="image/loginError.ico">
                        <p class="loginError">你输入的密码和账户名不匹配，是否<a href="javascript:void(0)" style="color: #f40">忘记密码</a>或重新登陆
                        </p>
                    </div>
                </c:if>
                <c:if test="${!requestScope.loginInfo.equals('')}">
                    <div id="J_Message" style="border: 1px solid #ffb4a8;display: none">
                        <img width="22px" height="16px" src="image/loginError.ico">
                        <p class="loginError">你输入的密码和账户名不匹配，是否<a href="javascript:void(0)" style="color: #f40">忘记密码</a>或重新登陆
                        </p>
                    </div>
                </c:if>

                <div>&nbsp;</div>
                <form class="form-horizontal" id="loginForm" method="post"
                      action="${pageContext.request.contextPath}/login">
                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-6">
                            <c:if test="${cookie.saveName!=null}">
                                <input type="text" class="form-control" id="username"
                                       value="${cookie.saveName.value}" name="username">
                            </c:if>
                            <c:if test="${cookie.saveName==null}">
                                <input type="text" class="form-control" id="username"
                                       placeholder="请输入用户名" name="username">
                            </c:if>

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="password"
                                   placeholder="请输入密码" name="password">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="checkCode" class="col-sm-2 control-label">验证码</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="checkCode"
                                   placeholder="验证码" name="checkCode">
                        </div>
                        <div class="col-sm-3">
                            <img id="loginCode" src="${pageContext.request.contextPath}/checkImage"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label> <input type="checkbox" name="autoLogin" value="autoLogin"> 自动登录</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <c:if test="${cookie.saveName!=null}">
                                    <label> <input type="checkbox" name="saveName" value="saveName" checked="checked">
                                        记住用户名</label>
                                </c:if>
                                <c:if test="${cookie.saveName==null}">
                                    <label> <input type="checkbox" name="saveName" value="saveName"> 记住用户名</label>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" width="100" value="登录" name="submit"
                                   style="background: url('./images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>