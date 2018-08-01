<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加银行卡</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="comm">
    <form id="addBank" action="user/userInfo/addBankOrAliPay?type=0" method="post">
        <div class="edit_information">
            <ul>
                <li>
                    <span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                    <div>
                        <input type="text" value=""  name="cpiRealName" id="userName">
                    </div>
                </li>
                <li>
                    <span>开&nbsp;&nbsp;户&nbsp;&nbsp;行</span>
                    <div>
                        <input type="text" placeholder="请输入您的开户行" value="" id="bankName" name="cpiBankName">
                    </div>
                </li>
                <li>
                    <span>银行卡号</span>
                    <div>
                        <input type="text" placeholder="请输入您的银行卡号" value="" id="bankNo" name="cpiBankNo">
                    </div>
                </li>

                <li>
                    <div id="msg">
                    </div>
                </li>
            </ul>
            <!--提交-->
            <a href="javascript:void(0)" style="margin-top: 0rem;" class="commit" id="h-submitBtn"  onclick ="addBankInfo()">提交</a>
        </div>
    </form>
</div>
<script src="static/js/wx/base.js"></script>

<script >
    function addBankInfo() {
        var cpiRealName =$("#userName").val();
        var cpiBankName = $("#bankName").val();
        var cpiBankNo = $("#bankNo").val();
        $("#msg").empty();
        if(cpiRealName==null || cpiRealName==undefined || cpiRealName=="" ){
            $("#msg").html('<span style="color: red;width: 120px">请填写真实姓名！</span>')
            return;
        }
        else if(cpiBankName==null || cpiBankName==undefined || cpiBankName==""){
            $("#msg").html('<span style="color: red;width: 120px">请填写开户行！</span>')
            return;
        }
        else if(cpiBankNo==null || cpiBankNo==undefined || cpiBankNo==""){
            $("#msg").html('<span style="color: red;width: 120px">请填写银行卡号！</span>')
            return;
        }else if(validateCard()==false){
            return;
        }
        else{
            document:addBank.submit();
        }

    }
    function validateCard(){
        var cpiBankNo = $("#bankNo").val();
        var pattern = /^([1-9]{1})(\d{15,19})$/;
        if (!pattern.test(cpiBankNo)) {
            $("#msg").empty();
            $("#msg").html('<span style="color: red;width: 120px">请填写正确的卡号！</span>');
            return false;
        }
    }
</script>


</body>
</html>