<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>支付宝信息</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="comm overflow">
    <div class="chose_pay">
        <h1>
            <i class="chose_icon"></i>
            请选择
        </h1>
        <jsp:include page="../include/managePayInfo.jsp">
            <jsp:param name="payId" value="zfb"></jsp:param>
        </jsp:include>
    </div>
    <!--选择支付宝-->
    <input type="hidden" value="1" id="type">
    <div class="pay_list manage_list">
        <ul>
            <c:if test="${not empty aliPayInfoList}">
                <c:forEach items="${aliPayInfoList}" var="aliPayInfo" varStatus="p">
                    <li>
                        <div class="pay_list_top">
                            <input class="bankId" type="hidden" value="${aliPayInfo.cpiId}"/>
                            <h1></h1>
                            <div class="pay_message">
                                <h1>${aliPayInfo.cpiRealName}</h1>
                                <div>
                                    <span>${aliPayInfo.cpiTelNo}</span>
                                    <span>${aliPayInfo.cpiAlipayUserName}</span>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${empty aliPayInfoList}">
                <li style="height: 1rem;">
                    <span>您还没有添加支付宝信息！</span>
                </li>
            </c:if>
        </ul>
    </div>
    <!--管理支付宝-->
    <c:if test="${empty aliPayInfoList}">
        <button class="add_card"  onclick="addAliPay()">新增支付宝账号</button>
    </c:if>
    <c:if test="${ not empty aliPayInfoList}">
        <button class="add_card" onclick="manageAliPay()" >管理</button>
    </c:if>
</div>
<script src="static/js/wx/chose_pay_method.js"></script>
<script src="static/js/wx/managerPayList.js"></script>
<script type="text/javascript">
    //管理
    function manageAliPay(){
        window.location.href=GLOBAL_CONFIG.basePath+"user/userInfo/aliPayInfo";
    }
    //新增
    function addAliPay(){
        window.location.href=GLOBAL_CONFIG.basePath+"user/userInfo/addApliPayInfo";
    }
</script>
</body>
</html>