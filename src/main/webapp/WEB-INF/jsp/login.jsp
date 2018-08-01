<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <jsp:include page="include/commonInclude.jsp"></jsp:include>

    <title>用户登录</title>
</head>
<body>
<div class="page_box">
    <div class="login_bg">
        <div class="pic_box">
            <img src="static/images/login_pic.jpg" />
            <div class="login_prompt_box">
                登录后才可以听课哦~
                <input class="login_prompt_close" type="button" />
            </div>
        </div>
        <form id="loginForm" action="login" method="post">
            <div class="login_box">
                <div class="login_cont">
                    <div style="color: red;">${errMsg}</div>
                    <div class="login_block">
                        <label class="login_phone"></label>
                        <input class="login_txt" id="telNo" name="telNo" type="number" placeholder="请输入手机号" />
                    </div>
                    <div class="login_block">
                        <label class="login_msg"></label>
                        <input class="login_txt" type="text" id="code" name="code" placeholder="短信验证码" />
                        <input id="getSmsCodeBtn" class="login_msg_btn" type="button" value="获取验证码" />
                    </div>
                </div>
                <div class="infor_btn_block">
                    <input id="loginBtn" class="infor_btn" type="submit" value="登 录" />
                </div>

                <div class="register_box">
                    还没注册？<span>>></span> <a href="user/regInit?cuType=listener&back=${param.back}" class="register">点击注册</a>
                </div>
                <div class="logo_box" style="padding-top: 10%;">
                    <img src="static/images/logo.png" />
                </div>
            </div>
            <input type="hidden" name="back" value="${param.back}">
        </form>
    </div>
</div>
<script type="text/javascript" src="static/lib/validator.js" ></script>
<script>
    $(document).ready(function () {

        //关闭登录提醒
        $('.login_prompt_close').click(function () {
            $('.login_prompt_box').slideUp();
        });

        // 取得手机验证码
        $("#getSmsCodeBtn").bind("click", function () {
            var telNo = $("#telNo").val();
            if(telNo == ""){
                showMsg("请输入手机号");
            }else if(!Validator.isMobile(telNo)){
                showMsg("手机号格式错误");
            }else{
                $("#getSmsCodeBtn").attr("disabled", "disabled");
                $.ajax({
                    type: "GET",
                    url: "loginCode",
                    data: "tel=" + telNo,
                    dataType: 'json',
                    success: function (response) {
                        if(response.msg){
                            showMsg(response.msg);
                            $("#getSmsCodeBtn").removeAttr("disabled");
                        }else{
                            countdown();
                        }
                    }
                });
            }
        });

        // 倒计时
        function countdown(){
            // 倒计时
            var sec = 60;
            var inter = setInterval(function () {
                $("#getSmsCodeBtn").val("重新获取(" + sec + "s)");
                if (sec == 0) {
                    $("#getSmsCodeBtn").val("重新获取");
                    $("#getSmsCodeBtn").removeAttr("disabled");
                    sec = 60;
                    clearInterval(inter);
                }
                sec--;
            }, 1000);
        }
        
        $("#loginForm").submit(function () {
            if(!Validator.isMobile($("#telNo").val())){
                showMsg("请输入正确的手机号");
                return false;
            }else if($("#code").val == ""){
                showMsg("请输入验证码");
                return false;
            }
            return true;
        })
    })
</script>
</body>
</html>