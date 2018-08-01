//动态添加绑定事件
$('.pay_list_top').on('click',function(){
    var bId = $(this).find('.bankId').val();
    var type = $("#type").val();
    window.location.href = "user/walletDraw/newBankOrAlipay?bId=" + bId + "&type=" + type;

})