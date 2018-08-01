<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加支付宝账号</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="comm">
    <form id="addAlipay" action="user/userInfo/addBankOrAliPay?type=1" method="post">
        <div class="edit_information">
            <ul>
                <li>
                    <span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                    <div>
                        <input type="text" value=""  name="cpiRealName" id="userName">
                    </div>
                </li>
                <li>
                    <span>支&nbsp;&nbsp;付&nbsp;&nbsp;宝</span>
                    <div>
                        <input type="text" placeholder="请输入您的支付宝账号" value="" name="cpiAlipayUserName" id="alipayName">
                    </div>
                </li>
                <li>
                    <div id="msg">
                    </div>
                </li>
            </ul>
            <!--提交-->
            <a href="javascript:void(0)" style="margin-top: 0rem;" class="commit" id="h-submitBtn" onclick="addAliPayInfo()">提交</a>
        </div>
    </form>
</div>
<script src="static/js/wx/base.js"></script>

<script >
    function addAliPayInfo() {
        var cpiRealName =$("#userName").val();
        var cpiAlipayUserName =$("#alipayName").val();
        $("#msg").empty();
        if(cpiRealName==null || cpiRealName==undefined || cpiRealName=="" ){
            $("#msg").html('<span style="color: red;width: 120px">请填写真实姓名！</span>')
            return;
        }
        else if(cpiAlipayUserName==null || cpiAlipayUserName==undefined || cpiAlipayUserName==""){
            $("#msg").html('<span style="color: red;width: 120px">请填写支付宝账号！</span>')
            return;
        }
        else {
            document:addAlipay.submit();
        }

    }
</script>


</body>
</html>