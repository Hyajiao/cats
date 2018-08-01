<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>操作指南</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body>

<script type="text/javascript">
    $(".guide li").addClass("active");
    $(".guide li").parent().siblings().children().removeClass("active");
</script>
<!--操作指南-->
<div class="operation_guide">
    <ul class="guide">
        <li>
            <a href="guide/connect">如何参与调研</a>
        </li>
        <li >
            <a href="guide/guideDetail">支付的周期与提现说明</a>
        </li>
        <li>
            <a href="guide/guideDetail">调研中途停止如何操作?</a>
        </li>
        <li>
            <a href="guide/guideDetail">支付的周期与提现说明</a>
        </li>
    </ul>
</div>
<script src="static/js/wx/operation_guide.js"></script>
</body>
</html>