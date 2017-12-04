<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员登录</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
            width: 100%;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }
    </style>
</head>

<body>


<!-- 引入header.jsp -->
<jsp:include page="/header.jsp"></jsp:include>


<div class="row" style="width: 1210px; margin: 0 auto;">
    <div class="col-md-12">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
        </ol>
    </div>

    <c:forEach items="${pagerBean.list}" var="pager">
        <div class="col-md-2" style="height: 250px">
            <a href="${pageContext.request.contextPath}/productInfo?pid=${pager.pid}&cid=${cid}&currentPage=${pagerBean.currentPager}">
                <img src="${pageContext.request.contextPath}/${pager.pimage}" width="170px" height="180px"
                     style="display: inline-block;">
            </a>
            <p>
                <a href="${pageContext.request.contextPath}/productInfo?pid=${pager.pid}&cid=${cid}&currentPage=${pagerBean.currentPager}"
                   style='color: green'>${pager.pname}</a>
            </p>
            <p>
                <font color="#FF0000">商城价：&yen;${pager.market_price}</font>
            </p>
        </div>
    </c:forEach>

</div>

<!--分页 -->
<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
    <ul class="pagination" style="text-align: center; margin-top: 10px;">
        <c:if test="${pagerBean.currentPager==1}">
            <li class="disabled">
                <a href="javascript:void(0)" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>
        <c:if test="${pagerBean.currentPager!=1}">
            <li>
                <a href="${pageContext.request.contextPath}/productByCid?cid=${cid}&page=${pagerBean.currentPager-1}"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>
        <c:forEach begin="1" end="${pagerBean.totalPager}" var="number">
            <c:if test="${number==pagerBean.currentPager}">
                <li class="active"><a href="javascripy:void(0)">${number}</a></li>
            </c:if>
            <c:if test="${number!=pagerBean.currentPager}">
                <li><a href="${pageContext.request.contextPath}/productByCid?cid=${cid}&page=${number}">${number}</a>
                </li>
            </c:if>
        </c:forEach>

        <c:if test="${pagerBean.currentPager==pagerBean.totalPager}">
            <li class="disabled">
                <a href="#" aria-label="Previous">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>
        <c:if test="${pagerBean.currentPager!=pagerBean.totalPager}">
            <li>
                <a href="${pageContext.request.contextPath}/productByCid?cid=${cid}&page=${pagerBean.currentPager+1}"
                   aria-label="Previous">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>
    </ul>
</div>
<!-- 分页结束 -->

<!--商品浏览记录-->
<div style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: auto;">

    <h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
    <div style="width: 50%; float: right; text-align: right;">
        <a href="">more</a>
    </div>
    <div style="clear: both;"></div>

    <div style="overflow: hidden;">

           <c:forEach items="${historyProduct}" var="pager">
               <div class="col-md-2">
                   <a href="${pageContext.request.contextPath}/productInfo?pid=${pager.pid}&cid=${cid}&currentPage=${pagerBean.currentPager}">
                       <img src="${pageContext.request.contextPath}/${pager.pimage}" width="170px" height="170px"
                            style="display: inline-block;">
                   </a>
                   <p>
                       <a href="${pageContext.request.contextPath}/productInfo?pid=${pager.pid}&cid=${cid}&currentPage=${pagerBean.currentPager}"
                          style='color: green'>${pager.pname}</a>
                   </p>
                   <p>
                       <font color="#FF0000">商城价：&yen;${pager.market_price}</font>
                   </p>
               </div>
           </c:forEach>

    </div>
</div>


<!-- 引入footer.jsp -->
<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>