<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>问卷</title>
    <jsp:include page="../../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="question">
    <div class="question_message">
        <div class="comm">
            <c:if test="${record eq 0}">
                <h1>感谢您的参与！</h1>
            </c:if>
            <c:if test="${record eq 1}">
                <h1>感谢您的参与,获得${price}奖励金</h1>
                <h2>管理员审核后会打入您的钱包,请注意查收</h2>
            </c:if>
        </div>
    </div>
    <div class="comm">
        <a href="user/wallet/recordList" class="commit">确定</a>
    </div>
</div>
</body>
</html>