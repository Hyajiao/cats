<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>提现确认</title>
   <jsp:include page="../../include/commonInclude.jsp"></jsp:include>
    <script type="text/javascript" src="static/lib/validator.js" ></script>
</head>
<body>
<div class="comm overflow">
    <jsp:include page="../../include/payeeInfo.jsp">
        <jsp:param name="payId" value="zfb"></jsp:param>
    </jsp:include>
    <input type="hidden" value="${balance}" id="balance">
    <form id="aliPay" action="user/walletDraw/toBackCard?type=1&defaultId=${catsPayeeInfo.cpiId}" method="post">
    <div class="edit_information">
        <ul>
            <input type="hidden" value="${catsPayeeInfo.cpiCuId}" name="cpiCuId">
            <li>
                <span>提现金额</span>
                <div>
                    <input type="text"  name="cwrWithdrawMoney" id="money">
                </div>
            </li>
            <li>
                <span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                <div>
                    <input type="text" placeholder="请输入您的姓名" name="cwrRealName" id="name" value="${catsPayeeInfo.cpiRealName}">
                </div>
            </li>
            <li>
                <span>支&nbsp;&nbsp;付&nbsp;&nbsp;宝</span>
                <div>
                    <input type="text" placeholder="请输入您的支付宝账号" name ="cwrAlipayUserName" id="aliPayNum" value="${catsPayeeInfo.cpiAlipayUserName}">
                </div>
            </li>
            <li>
                <div>
                    <a href="user/userInfo/aliPayList">
                        <i></i>
                        使用其他支付宝
                    </a>
                    <span class="tip" id="msg"></span>
                </div>
            </li>
        </ul>
        <!--提交-->
        <a href="javascript:void(0)" class="commit commit_with_draw" onclick="withdrawMoney()">确认提现</a>
    </div>
    </form>
</div>
<script src="static/js/wx/chose_pay_method.js"></script>
<script src="static/js/wx/code.js"></script>
<script type="text/javascript">
    function withdrawMoney() {
        var money =$("#money").val();
        var cwrRealName = $("#name").val();
        var cwrAlipayUserName = $("#aliPayNum").val();
        var balance = $("#balance").val();
        $("#msg").empty();
        if(money ==null || money =="" ||money ==undefined){
            $("#msg").html("请填写提现金额");
            return;
        }
        if(cwrRealName ==null ||cwrRealName == "" ||cwrRealName==undefined){
            $("#msg").html("请填写真实姓名")
            return;
        }
        if(cwrAlipayUserName == null||cwrAlipayUserName==""||cwrAlipayUserName==undefined){
            $("#msg").html("请支付宝账号")
            return;
        }
        if(parseFloat(money)>parseFloat(balance)){
            $("#msg").html("提现金额不能超过总金额");
            return;
        }
        document:aliPay.submit();
    }

</script>

</body>
</html>