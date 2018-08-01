<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>提现确认</title>
   <jsp:include page="../../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="comm overflow">
        <jsp:include page="../../include/payeeInfo.jsp">
            <jsp:param name="payId" value="yhk"></jsp:param>
        </jsp:include>
    <form id="withdraw" action="user/walletDraw/toBackCard?type=0&defaultId=${catsPayeeInfo.cpiId}" method="post">
        <div class="edit_information">
            <input type="hidden" value="${balance}" id="balance">
            <input  type="hidden" value="${catsPayeeInfo.cpiCuId}" name="cpiCuId">
            <ul>
                <li>
                    <span>提现金额</span>
                    <div>
                        <input class="money" type="text"  name ="cwrWithdrawMoney" id="money">
                    </div>
                </li>
                <li>
                    <span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                    <div>
                        <input type="text" placeholder="请输入您的姓名" name="cwrRealName" id="name" value="${catsPayeeInfo.cpiRealName}">
                    </div>
                </li>

                <li id="bankname">
                    <span>开&nbsp;&nbsp;户&nbsp;&nbsp;行</span>
                    <div>
                        <input type="text" placeholder="请输入您的开户行" name="cwrBankName" id="bank" value="${catsPayeeInfo.cpiBankName}">
                    </div>
                </li>
                <li id="bankcard">
                    <span>银&nbsp;&nbsp;行&nbsp;&nbsp;卡</span>
                    <div>
                        <input type="text" placeholder="请输入您的银行卡账号" name="cwrBankNo" id="card" value="${catsPayeeInfo.cpiBankNo}">
                    </div>
                </li>
                <li>
                    <div>
                        <a href="user/userInfo/bankList">
                            <i></i>
                            使用其他银行卡
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
        var cwrBankName = $("#bank").val();
        var cwrBankNo = $("#card").val();
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
        else if(cwrBankName == null||cwrBankName==""||cwrBankName==undefined){
            $("#msg").html("请填开户行名称")
            return;
        }
        else if(cwrBankNo == null||cwrBankNo==""||cwrBankNo==undefined) {
            $("#msg").html("请填开户行账号")
            return;
        }
        else if(validateCard()==false){
            console.log(validateCard())
            $("#msg").html("请填写正确的开户行账号")
            return;
        }
        else if(parseFloat(money)>parseFloat(balance)){
            $("#msg").html("提现金额不能超过总金额");
            return;
        }
        else{
            document:withdraw.submit();
        }
    }
    function validateCard(){
        var cpiBankNo = $("#card").val();
        var pattern = /^([1-9]{1})(\d{15,19})$/;
        if (!pattern.test(cpiBankNo)) {
            $("#msg").empty();
            return false;
        }
    }
</script>
</body>
</html>