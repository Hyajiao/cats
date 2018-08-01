<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>支付信息</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<div class="comm overflow">
    <div class="chose_pay">
        <h1>
            <i class="chose_icon"></i>
            支付信息
        </h1>
        <jsp:include page="../include/payeeInfoList.jsp">
            <jsp:param name="payId" value="yhk"></jsp:param>
        </jsp:include>
    </div>
    <!--选择银行卡-->
    <div class="pay_list">
        <ul>
            <c:if test="${not empty bankInfoList}">
                <c:forEach items="${bankInfoList}" var="bankInfo" varStatus="p">
                    <li>
                        <div class="pay_list_top">
                            <input class="bankId" type="hidden" value="${bankInfo.cpiId}"/>
                            <h1>${bankInfo.cpiRealName}</h1>
                            <div class="pay_message">
                                <h1>${bankInfo.cpiBankName}</h1>
                                <div>
                                    <span>${bankInfo.cpiTelNo}</span>
                                    <span>${bankInfo.cpiBankNo}</span>
                                </div>
                            </div>
                        </div>
                        <div class="pay_list_bottom">
                            <a href="#" class="chose_active">
                                <input type="radio" id="chose" name="back_card" <c:if test="${bankInfo.cpiIsDefault==0}">checked</c:if>>
                                <label for="chose" onclick="manageDefault(${bankInfo.cpiId},0)">默认银行卡</label>
                            </a>
                            <a href="user/userInfo/editBank?bId=${bankInfo.cpiId}" class="edit">
                                编辑
                            </a>
                            <i></i>
                            <a href="javascript:void(0)" class="del">
                                删除
                            </a>
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
    <!--删除信息的弹窗-->
    <div class="del_pop">
        <div class="comm">
            <div class="pop_del_box">
                <div class="pop_txt">确定删除该信息吗？</div>
                <div class="pop_btn">
                    <a href="javascript:void(0)" class="pop_cancel">取消</a>
                    <a href="javascript:void(0)" class="pop_del" onclick="deleteAlipayOrBank(0)">删除</a>
                </div>
            </div>
        </div>
    </div>
    <!--新增银行卡-->
    <button class="add_card" onclick="addBank()">新增银行卡</button>
</div>
<script src="static/js/wx/chose_pay_method.js"></script>
<script src="static/js/wx/bankOrAliPayInfo.js"></script>
<script type="text/javascript">
    //新增
    function addBank(){
        window.location.href=GLOBAL_CONFIG.basePath+"user/userInfo/addBankInfo";
    }
</script>
</body>
</html>