<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>编辑地址</title>
   <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="comm">
    <form id="editBank" action="user/userInfo/saveBankInfo?type=0 &bId=${catsPayeeInfo.cpiId}" method="post">
    <div class="edit_information">
        <ul>
            <li>
                <input type="hidden" value="${catsPayeeInfo.cpiId}" id="bId"/>
                <span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                <div>
                    <input type="text" value="${catsPayeeInfo.cpiRealName}"  name="cpiRealName" id="userName">
                </div>
            </li>
            <li>
                <span>开&nbsp;&nbsp;户&nbsp;&nbsp;行</span>
                <div>
                    <input type="text" placeholder="请输入您的开户行" value="${catsPayeeInfo.cpiBankName}" id="bankName" name="cpiBankName">
                </div>
            </li>
            <li>
                <span>银行卡号</span>
                <div>
                    <input type="text" placeholder="请输入您的银行卡号" value="${catsPayeeInfo.cpiBankNo}" id="bankNo" name="cpiBankNo">
                </div>
            </li>

            <li>
                <div id="msg">
                </div>
            </li>
        </ul>
        <!--提交-->
        <a href="javascript:void(0)" style="margin-top: 0rem;" class="commit" onclick="changeBankInfo()">提交</a>
    </div>
    </form>
</div>
<script src="static/js/wx/base.js"></script>

<script >

    function changeBankInfo() {
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
            $("#msg").html('<span style="color: red;width: 120px">请填写银行卡号！</span>')
            return;
        }
        else{
            document:editBank.submit();
        }
    }

    function validateCard(){
        var cpiBankNo = $("#bankNo").val();
        var pattern = /^([1-9]{1})(\d{15,19})$/;
        if (!pattern.test(cpiBankNo)) {
            return false;
        }
    }
</script>


</body>
</html>