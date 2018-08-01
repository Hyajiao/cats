$(function(){
    $(".operation_guide li").click(function(){
        if($(this).hasClass('active')==true){
            $(this).siblings().removeClass('active');
        }else{
            $(this).addClass('active').siblings().removeClass('active');
        }
    });
});