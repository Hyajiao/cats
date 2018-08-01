$(document).ready(function () {
    if($(window).width()>=750){
        $('.page_box').width($(window).height()*750/1206);
        $('.page_bg').width($(window).height()*750/1206);
        $('.page_bg').height($(window).height());
        $('.page_bg').css({
            "left":"50%",
            "margin-left":-1*$('.page_bg').width()/2
        });
        $('.pop_box').css({
            "width":"300px",
            "margin-left":"-150px",
            "left":"50%"
        });

        $('.btn_box').css({
            "width":$('.page_bg').width(),
            "margin-left":-1*$('.page_bg').width()/2,
            "left":"50%"
        });
        $('.login_prompt_box').css({
            "width":$('.page_box').width(),
            "margin-left":-1*$('.page_box').width()/2,
            "left":"50%",
            "padding":"0"
        });
        $('.slideshow_box').width($('.page_box').width());
        $('.swiper-container').width($('.page_box').width());
        $('.swiper-slide').width($('.page_box').width());


    }
});