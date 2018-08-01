var clipboard = new Clipboard('#speakerBtn', {
    text: function() {
        return GLOBAL_CONFIG.basePath + "user/regInit?cuType=speaker";
    }
});

var listenerClipboard = new Clipboard('#listenerBtn', {
    text: function() {
        return GLOBAL_CONFIG.basePath + "user/regInit?cuType=listener";
    }
});

clipboard.on('success', function(e) {
    showMsg("链接已复制，请发送给讲者");
});

clipboard.on('error', function(e) {
    showMsg("即将打开邀请讲者页面，请将打开后页面的链接发送给讲者");
    location.href = GLOBAL_CONFIG.basePath + "user/regInit?cuType=speaker";
});

listenerClipboard.on('success', function(e) {
    showMsg("链接已复制，请发送给听众");
});

listenerClipboard.on('error', function(e) {
    showMsg("即将打开邀请听众页面，请将打开后页面的链接发送给听众");
    location.href = GLOBAL_CONFIG.basePath + "user/regInit?cuType=listener";
});

$(document).ready(function () {
    $('.menu').click(function () {
        event.stopPropagation();
        $('.cover').toggle();
        $('.menu_bg').toggle();
    });

    $('.menu_close').click(function () {
        event.stopPropagation();
        $('.cover').hide();
        $('.menu_bg').hide();
    });
    $(document).click(function(){
        $('.cover').hide();
        $('.menu_bg').hide();
    });

    setTimeout(function () {
        var height = $(".meeting_top").height() + $(".meeting_tit_box").height();
        var windowHeight = $(window).height();
        // alert("windowHeight=" + windowHeight + ":::height=" + height);
        $("#wrapper").height(windowHeight - height);
    }, 500)

});

function changeList(type){
    $("#listTitle").removeClass("icon_all materials audit settlement settlement2");
    if(type == 0){
        $("#listTitle").text("全部会议");
        $("#listTitle").addClass("icon_all");
    }else if(type == 1){
        $("#listTitle").text("上传资料");
        $("#listTitle").addClass("materials");
    }else if(type == 2){
        $("#listTitle").text("会议审核");
        $("#listTitle").addClass("audit");
    }else if(type == 3){
        $("#listTitle").text("会议转发");
        $("#listTitle").addClass("settlement");
    }else if(type == 4){
        $("#listTitle").text("费用结算");
        $("#listTitle").addClass("settlement2");
    }


    $("#type").val(type);
    $("#thelist").html("");
    page = 1;
    myScroll.refresh();
    loaddata(1);
}

function loaddata(page,callback) {
    $.ajax({
        type: "post",
        cache: false,
        dataType: "json",
        url: "admin/meeting/list",
        data: {
            type: $("#type").val(),
            page: page
        },
        success: function (json) {
            if(json.data.length > 0){
                $("#thelist").append(createHtml(json.data));
            }
            if (typeof callback === "function"){
                callback();
            }
        }
    });
}

function createHtml(data){
    var html = "";
    for(var i = 0; i < data.length; i++){
        var item = data[i];

        var url = "";
        if(item.dmMeetingStatus == 0){
            url = "admin/meeting/upload/init/" + item.dmId;
        }else{
            url = "admin/meeting/show/" + item.dmId;
        }

        html = html + '<a href="' + url + '">' +
            '<div class="meeting_block">' +
            '                    <div class="meeting_line">' +
            '                        <div class="meeting_id">' +
            '                            会议ID：' +
            '                        </div>' +
            '                        <div class="meeting_right">' + item.dmId +
            '                            <span class="blue">（' + item.dmMeetingStatusStr + '）</span>' +
            '                        </div>' +
            '                    </div>' +
            '                    <div class="meeting_line">' +
            '                        <div class="meeting_left">' +
            '                            会议主题：' +
            '                        </div>' +
            '                        <div class="meeting_right">' + item.dmTopic +
            '                        </div>' +
            '                    </div>' +
            '                    <div class="meeting_line">' +
            '                        <div class="meeting_left">' +
            '                            会议时间：' +
            '                        </div>' +
            '                        <div class="meeting_right">' + item.dmMeetingTimeStr +
            '                        </div>' +
            '                    </div>' +
            '                    <div class="meeting_line">' +
            '                        <div class="meeting_left">' +
            '                            会议讲者：' +
            '                        </div>' +
            '                        <div class="meeting_right">' + item.dmSpeakerHospital + ' ' + item.dmSpeakerName +
            '                        </div>' +
            '                    </div>' +
            '                </div></a>'

    }
    return html;
}