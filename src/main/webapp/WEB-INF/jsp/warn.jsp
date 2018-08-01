<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<jsp:include page="include/commonInclude.jsp"></jsp:include>
	<title>请注意</title>
    <link rel="stylesheet" href="static/lib/jqweui-1.0.1/css/weui.min.css">
    <link rel="stylesheet" href="static/lib/jqweui-1.0.1/css/jquery-weui.min.css">
    <link rel="stylesheet" href="static/lib/jqweui-1.0.1/css/jquery-weui-custom.css">
</head>
<body >
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-info weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">注意</h2>
            <p class="weui-msg__desc">
            <h2>
                <c:if test="${msg != null && msg != ''}">${msg}</c:if>
            </h2>
            </p>
        </div>
    </div>
</body>
</html>