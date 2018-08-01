
//    获取验证码倒计时
// $("li.code button").click(function(){
//     var num=59;
//     var html;
//     html=`已发送(${num}s)`;
//     $(this).html(html);
//     $(this).css({
//         background:'#353535',
//         color:'#fff'
//     });
//     $(this).prop('disabled',true);
//     var timer=setInterval(function(){
//         num--;
// //                console.log(num);
//         if(num>0){
//             html=`已发送(${num}s)`;
//             //console.log(html);
//             $("li.code button").html(html);
//             // console.log($("li.code button").html());
//         }else{
//             html="获取验证码";
//             $("li.code button").css({
//                 background:'#c2c2c2',
//                 color:'#000'
//             });
//             $("li.code button").html(html);
//             $("li.code button").prop('disabled',false);
//             clearInterval(timer);
//         }
//     },1000);
//
// });

$(document).ready(function(){
    //判断提现金额
    $("#money").blur("click",function () {
        var money = $("#money").val();
        var balance = $("#balance").val();
        console.log(parseFloat(money)>parseFloat(balance))
        $("#msg").empty();
        if(parseFloat(money)>parseFloat(balance)){
            $("#msg").html("提现金额不能超过总金额");
        }
    });
})
// 取得手机验证码
$("#getSmsCodeBtn").bind("click", function () {
    var tel = $("#cuTelNo").val();
    if(tel == ""){
        showMsg("请输入手机号");
    }else if(!Validator.isMobile(tel)){
        showMsg("手机号格式错误");
    }else{
        $("#getSmsCodeBtn").attr("disabled", "disabled");
        $.ajax({
            type: "GET",
            url: "user/regCode",
            data: "tel=" + tel,
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
        $("#getSmsCodeBtn").val("重新获取" + sec + "s");
        if (sec == 0) {
            $("#getSmsCodeBtn").val("重新获取");
            $("#getSmsCodeBtn").removeAttr("disabled");
            sec = 60;
            clearInterval(inter);
        }
        sec--;
    }, 1000);
}


