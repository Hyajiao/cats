<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String payId = request.getParameter("payId");
%>


    <div class="chose_pay_method">
        <a href="user/userInfo/bankInfo" id="yhk">
            <i></i>
            银行卡
        </a>
        <%--<a href="user/userInfo/aliPayInfo" id="zfb">--%>
            <%--<i></i>--%>
            <%--支付宝--%>
        <%--</a>--%>
    </div>
<script>
    var payId = "<%=payId%>";
    //选中状态
    $("#"+payId).addClass("chose");
    $("#"+payId).parent().siblings().children().removeClass("chose");
</script>