// $(function(){
//
// });
window.onload=function(){
    var w=$(window).width();
    console.log(w);
    if(w<1200){
        $(".top_box").css('padding','0');
    }
    $(".relative_list li").on('click',function(e){
        console.log(1);
        // e.preventDefault();
        console.log($(this).hasClass('checked')==true);
        if($(this).hasClass('checked')==true){
            console.log($(this).siblings());
            $(this).siblings().removeClass('checked');
        }else{
            $(this).addClass('checked').siblings().removeClass('checked');
        }
    });
    $(".content_message li p a").click(function (e) {
        e.preventDefault();
        console.log($(e.target));
        $(e.target).addClass('active');
    });
    var h=$(".right_content").height();
    $(".left_tab").height(h);
    $(".left_tab").height(800);
    var menuW=$(".drop_down_menu").outerWidth();
    $(".pop_menu").width(menuW);
}