<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <jsp:include page="include/commonInclude.jsp"></jsp:include>
    <title>成功</title>
    <link rel="stylesheet" href="static/lib/jqweui-1.0.1/css/weui.min.css">
    <link rel="stylesheet" href="static/lib/jqweui-1.0.1/css/jquery-weui.min.css">
    <link rel="stylesheet" href="static/lib/jqweui-1.0.1/css/jquery-weui-custom.css">
</head>
<body >
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">成功</h2>
        <p class="weui-msg__title">${msg}</p>
        <c:if test="${url != null}">
            <p class="weui-msg__title"><a href="${url}">返回</a></p>
        </c:if>
    </div>
</div>
</body>
<script>
    function countdown(secs, surl){
        var time = document.getElementById("time");
        time.innerText = secs;//<span>中显示的内容值
        if(--secs>0){
            setTimeout("countdown("+secs+",'"+surl+"')",1000);//设定超时时间
        }else{
            location.href=surl;//跳转页面
        }
    }
    //	countdown(3,'index');
</script>
</html>