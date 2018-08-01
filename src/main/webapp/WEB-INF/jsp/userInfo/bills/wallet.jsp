<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我的钱包</title>
    <jsp:include page="../../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="comm">
    <div class="balance">
        <h1 id="count">
            余额 <span>${balance}</span>元
        </h1>
        <a class="withdraw" href="user/walletDraw/backCard?type=0" id="walId">提现</a>
    </div>
    <!--交易记录-->
    <div class="trading_record">
        <h1>
            <i></i>交易记录
        </h1>
        <table>
            <tr>
                <th width="30%">类目</th>
                <th width="20%">交易金额</th>
                <th width="30%">交易时间</th>
                <th width="20%">状态</th>
            </tr>
            <c:forEach items="${recordList}" var="recordList" varStatus="b" >
                <tr>
                    <td>${recordList.title}</td>
                    <c:if test="${recordList.title == '提现'}">
                        <td>-${recordList.money}</td>
                    </c:if>
                    <c:if test="${recordList.title != '提现'}">
                        <td>${recordList.money}</td>
                    </c:if>
                    <td style="text-align: center;">${recordList.timeStr}</td>
                    <c:if test="${recordList.status eq 0}"><td style="text-align: center;">待入账</td></c:if>
                    <c:if test="${recordList.status eq 1}"><td style="text-align: center;">已入账</td></c:if>
                    <c:if test="${recordList.status eq 2}"><td style="text-align: center;">已退回</td></c:if>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        var money = parseInt(${balance})
        if(money == 0){
           $("#walId").attr("background","#c9c9c9");
           $("#walId").attr("href","javascript:void(0)");
        }
    })
</script>
</body>
</html>