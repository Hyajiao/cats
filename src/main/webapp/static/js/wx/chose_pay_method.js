$(function(){
    $(".chose_pay_method a").click(function(e){
        //e.preventDefault();
        console.log($(e.target));
        if($(e.target).hasClass('chose')==true){
            $(e.target).siblings().removeClass('chose');
        }else{
            $(e.target).addClass('chose').siblings().removeClass('chose');
        }
    });

    $(".pay_list_bottom a.chose_active").click(function(e){
        e.preventDefault();
        if($(e.target).hasClass('labelCheck')==true){
            $(e.target).parent().parent().parent().siblings().find('label').removeClass('labelCheck');
        }else{
            $(e.target).addClass('labelCheck').parent().parent().parent().siblings().find('label').removeClass('labelCheck')
        }

    });

});