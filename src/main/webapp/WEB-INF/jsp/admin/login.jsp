<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>登录-临床学术追踪系统</title>
    <jsp:include page="include/headerEnt.jsp"></jsp:include>
</head>
<body>
<div class="comm" style="margin-top:-8%;">
    <div class="clear login overflow">
        <div class="lf">
            <img src="static/ent/images/login.png" alt="">
        </div>
        <div class="lf login_box">
            <h1>请登录-临床学术追踪系统</h1>
            <form action="admin/login" method="post" id="loginForm">
                <div class="inner_box clear">
                    <div id="errMsg">
                        <lable style="color:red;">${msg}</lable>
                    </div>
                    <div>
                        <span>用户名</span>
                        <input type="text" value="" name="auUserName" id="userName">
                    </div>
                    <div>
                        <span>密&nbsp;&nbsp;&nbsp;&nbsp;码</span>
                        <input type="password" value="" name="auPassword"/>
                    </div>
                    <button class="login_btn" type="submit">登录</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>