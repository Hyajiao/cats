<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="personal_information_top">
    <div class="comm">
        <p>
            <img src="static/images/person_icon.png" alt="">
        </p>
        <div class="personal_information">
            <img src="${cmUser.cuMedliveHeadImg}" alt="">
            <div class="person_intro">
                <h1>${cmUser.cuRealName}</h1>
                <h2>${cmUser.cuHospital}&nbsp;&nbsp;&nbsp;${cmUser.cuDept}&nbsp;&nbsp;&nbsp;${cmUser.cuProfessional} </h2>
            </div>
        </div>
    </div>
</div>
<div class="personal_list">
    <ul>
        <li>
            <a href="javascript:void(0)" id="info" class="clear">
                <span>个人信息</span>
                <div>
                    <span>完善</span>
                    <i></i>
                    <i class="update_text"></i>
                </div>

            </a>
        </li>
        <li>
            <a href="javascript:void(0)" id="auth" class="clear">
                <span>认证信息</span>
                <div>
                    <span>立即认证</span>
                    <i></i>
                    <i class="update_text"></i>
                </div>

            </a>
        </li>
        <li>
            <a href="user/userInfo/bankInfo" class="clear">
                <span>支付信息</span>
                <div>
                    <span>完善</span>
                    <i></i>
                    <i class="update_text"></i>
                </div>

            </a>
        </li>
    </ul>
</div>
<script type="text/javascript">
    $("#info").attr('href',GLOBAL_CONFIG.userInfoUrl+"/mymedlive");
    $("#auth").attr('href',GLOBAL_CONFIG.userInfoUrl+"/certify");
</script>
</body>
</html>