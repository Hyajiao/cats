<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String payId = request.getParameter("payId");
%>
<div class="chose_pay">
    <h1>
        <i class="chose_icon width_draw"></i>
        提现方式
    </h1>
    <div class="chose_pay_method">
        <a href="user/walletDraw/backCard?type=0" id="yhk">
            <i></i>
            银行卡
        </a>
        <a href="user/walletDraw/backCard?type=1" id="zfb">
            <i></i>
            支付宝
        </a>
    </div>
</div>
<script>
    var payId = "<%=payId%>";
    //选中状态
    $("#"+payId).addClass("chose");
    $("#"+payId).parent().siblings().children().removeClass("chose");
</script>