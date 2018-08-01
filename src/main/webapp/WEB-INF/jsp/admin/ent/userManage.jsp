<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>账号管理</title>
    <jsp:include page="../include/headerEnt.jsp"></jsp:include>
    <script type="text/javascript">
        $(document).ready(function () {
            var validations = [
                //旧密码
                {name: "oldPwd", valid: "validate[required] text-input"},
                //新密码
                {name: "newPwd", valid: "validate[required ,custom[pwdchange]] text-input"},
                //确认密码
                {name: "confirmedPwd", valid: "validate[required, equals[newPwd]] text-input"},
            ];
            for (var i = 0, len = validations.length; i < len; i++) {
                $("#" + validations[i].name).addClass(validations[i].valid);
            }
            //绑定表单验证
            $("#changePwdForm").validationEngine("attach", {
                promptPosition: "topRight",
                scroll: false,
                autoHidePrompt: true
            });

            $("#changePwd").click(function () {
                if ($("#changePwdForm").validationEngine('validate')) {
                    var data = {
                        "newPwd": $("#newPwd").val(),
                        "oldPwd": $("#oldPwd").val()
                    };
                    $.ajax({
                        type: "post",
                        url: "admin/ent/changePwd",
                        data: data,
                        dataType: "json",
                        success: function (json) {
                            if (json.success) {
                                alert("修改成功！");
                            } else {
                                alert("旧密码填写错误！");
                            }
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>
<div>
    <jsp:include page="../include/headerTopEnt.jsp"></jsp:include>
    <div class="comm clear">
        <jsp:include page="../include/entSidebar.jsp">
            <jsp:param value="nav_user" name="navId"></jsp:param>
        </jsp:include>
        <div class="right_content">
            <div class="product_document_message">
                <h1>
                    <i class="document_icon"></i>
                    修改密码
                </h1>
                <div class="update_pwd">
                    <form action="admin/ent/changePwd" method="post" id="changePwdForm">
                        <ul>
                            <li>
                                <span>账号名称：</span>
                                <span>${user.showName}</span>
                            </li>
                            <li>
                                <span>原始密码：</span>
                                <input type="password" value="" id="oldPwd"/>
                            </li>
                            <li>
                                <span>新的密码：</span>
                                <input type="password" value="" id="newPwd"/>
                                <h1>包含字母和数字,不低于8位</h1>
                            </li>
                            <li>
                                <span>密码确认：</span>
                                <input type="password" value="" id="confirmedPwd"/>
                            </li>
                        </ul>
                        <button type="button" class="commit_btn" id="changePwd">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

