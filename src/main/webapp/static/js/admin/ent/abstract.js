$(document).ready(function () {
    //接口获取本周产品相关资讯
    $.ajax({
        "url":"admin/ent/getNewsList",
        "type":"get",
        "data": {
            id : $("#projectId").val()
        },
        "dataType":"json",
        success: function (json) {
            if(json.success){
                alert();
            }
        }
    });

    //接口获取本周产品相关文献
    $.ajax({
        "url":"admin/ent/getLiteratureList",
        "type":"get",
        "data": {
            id : $("#projectId").val()
        },
        "dataType":"json",
        success: function (json) {
            if(json.success){
                alert();
            }
        }
    });
});