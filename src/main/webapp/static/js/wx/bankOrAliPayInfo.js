$(document).ready(function(){
    $(".pay_list_bottom .chose_active").find("input:checked").next().addClass("labelCheck");


    var h=$(window).height();
    var bH=$("body").height();
    if(bH<h){
        $(".del_pop").height(h);
    }else{
        $(".del_pop").height(bH);
    }
    $(".pay_list_bottom .del").click(function(){
        var bId = $(this).closest('li').find('.bankId').val();
        $(".pop_del").attr("id",bId);
        $(".del_pop").css('display','block');
    });
    //删除弹框点击取消的时候
    $(".pop_cancel").click(function(){
        $(".del_pop").css('display','none');
    });
});
//    删除的弹窗

//删除
function deleteAlipayOrBank(type) {
    var bId = $(".pop_del").attr("id");
    window.location.href = GLOBAL_CONFIG.basePath + "user/userInfo/deleteInfo?bId=" + bId + "&type=" + type;
}

//设置默认
function manageDefault(bId,type){
    window.location.href=GLOBAL_CONFIG.basePath+"user/userInfo/changeDefault?bId="+bId +"&type=" + type;
}