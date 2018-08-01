<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>银行卡信息</title>
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
            <jsp:param name="payId" value="yhk"></jsp:param>
        </jsp:include>
    </div>
    <!--选择银行卡-->
    <div class="pay_list manage_list">
        <input type="hidden" value="0" id="type">
        <ul>
            <c:if test="${not empty bankInfoList}">
                <c:forEach items="${bankInfoList}" var="bankInfo" varStatus="p">
                    <li>
                        <div class="pay_list_top">
                            <input class="bankId" type="hidden" value="${bankInfo.cpiId}"/>
                            <h1>${bankInfo.cpiRealName}</h1>
                            <div class="pay_message">
                                <h1 style="width: 50%">${bankInfo.cpiBankName}</h1>
                                <div style="width: 50%">
                                    <span>${bankInfo.cpiTelNo}</span>
                                    <span >${bankInfo.cpiBankNo}</span>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${empty bankInfoList}">
                <li style="height: 1rem;">
                    <span>您还没有添加银行卡信息！</span>
                </li>
            </c:if>
        </ul>
    </div>
    <!--管理银行卡-->
    <c:if test="${empty bankInfoList}">
        <button class="add_card" onclick="addBank()">新增银行卡</button>
    </c:if>
    <c:if test="${ not empty bankInfoList}">
        <button class="add_card" onclick="manageBankCard()" >管理</button>
    </c:if>

</div>
<script src="static/js/wx/chose_pay_method.js"></script>
<script src="static/js/wx/managerPayList.js"></script>
<script type="text/javascript">
    //管理
    function manageBankCard(){
        window.location.href=GLOBAL_CONFIG.basePath+"user/userInfo/bankInfo";
    }
    function addBank(){
        window.location.href=GLOBAL_CONFIG.basePath+"user/userInfo/addBankInfo";
    }
</script>
</body>
</html>