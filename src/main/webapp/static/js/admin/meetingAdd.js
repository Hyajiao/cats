$(document).ready(function () {
    $('.page_bg').height($(window).height());
    //
    $('.infor_box').height($(window).height()-130);
})

$(function() {

    // 会议主题
    $("#dmTopic").bind("focus", function () {
        $("#addDiv").hide();
        $("#topicDiv").show();

        $("#sureBtn").bind("click", function () {
            $("#dmTopic").val($("input[name='topic']:checked").next("label").text());
            $("#dmTopicId").val($("input[name='topic']:checked").val());
            $("#addDiv").show();
            $("#topicDiv").hide();
        });

        $("#newTopicBtn").bind("click", function () {
            location.href = GLOBAL_CONFIG.basePath + "admin/meeting/topic/add/init";
        });

    });

    // 会议时间
    $('#dmMeetingTime').mobiscroll().datetime({
        theme: 'android-ics light',
        lang: 'zh',
        display: 'bottom',
        dateFormat: 'yy-mm-dd',
        timeFormat: 'HH:ii'
    });

    $("#provinceSelect").val($("#province").val());
    initProvince();
    initCity();
    initHospital();
    initDoctor();

    loadCityData($("#city").val());
    loadHospital($("#hospital").val());
    loadDocotr($("#dmSpeakerId").val());

    /** 省改变时**/
    $("#provinceSelect").change(function() {
        $("#provinceId").val($("#provinceSelect").val());
        initCity();
        initHospital();
        initDoctor();
        loadCityData();
    });

    /** 市改变时 */
    $("#citySelect").change(function() {
        $("#city").val($("#citySelect").val());
        initHospital();
        initDoctor();
        loadHospital();
    });
    /** 医院改变时 */
    $("#hospitalSelect").change(function() {
        $("#hospital").val($("#hospitalSelect").val());
        initDoctor()
        loadDocotr();
    });
    /** 讲者改变时 */
    $("#speakerSelect").change(function() {
        $("#dmSpeakerId").val($("#speakerSelect").val());
    });
    
    $("#addForm").submit(function () {
        if($("#dmTopic").val() == ""){
            showMsg("请选择会议讲题");
            return false;
        }
        if($("#dmMeetingTime").val() == ""){
            showMsg("请选择会议时间");
            return false;
        }
        if($("#speakerSelect").val() == ""){
            showMsg("请选择讲者姓名");
            return false;
        }
    })
});

/** 加载城市的数据 */
function loadCityData(cityName) {
    if($("#provinceSelect").val() != null && $("#provinceSelect").val() != ""){
        $.ajax({
            "url":"admin/user/reg/city",
            "type":"get",
            "data":{province : $("#provinceSelect").val()},
            "dataType":"json",
            "success":function(data){
                if(data.success){
                    var cityData = data.data;
                    for (var i = 0; i < cityData.length; i++) {
                        if (cityData[i] == cityName) {
                            $("#citySelect").append(
                                '<option value="' + cityData[i] + '" selected="selected">'
                                + cityData[i] + '</option>', null);
                        } else {
                            $("#citySelect").append(
                                '<option value="' + cityData[i] + '">' + cityData[i]
                                + '</option>', null);
                        }
                    }
                }else{
                    showMsg(data.msg);
                }
                initSelect("#citySelect");
            }
        });
    }
}

/** 加载医院名称 */
function loadHospital(hospitalName) {
    var data = {
        province : $("#provinceSelect").val(),
        city : $("#city").val()
    }
    //当省市不为空的时候，才去加载医院数据
    if(data.province != null && data.province != "" && data.city != null && data.city != ""){
        $.ajax({
            "url":"admin/user/reg/hospital",
            "type":"get",
            "data": data,
            "dataType":"json",
            "success":function(data){
                if(data.success){
                    var hospitalData = data.data;
                    for (var i = 0; i < hospitalData.length; i++) {
                        if (hospitalData[i] == hospitalName) {
                            $("#hospitalSelect").append(
                                '<option value="' + hospitalData[i] + '" selected="selected">'
                                + hospitalData[i] + '</option>', null);
                        } else {
                            $("#hospitalSelect").append(
                                '<option value="' + hospitalData[i] + '">' + hospitalData[i]
                                + '</option>', null);
                        }
                    }
                }else{
                    showMsg(data.msg);
                }
                initSelect("#hospitalSelect");
            }
        });
    }
}

/** 加载医生名称 */
function loadDocotr(doctorName) {
    var data = {
        province : $("#provinceSelect").val(),
        city : $("#city").val(),
        hospital : $("#hospital").val()
    }
    //当省市医院不为空的时候，才去加载医生数据
    if(data.province != null && data.province != "" && data.city != null && data.city != "" && data.hospital != null && data.hospital != ""){
        $.ajax({
            "url":"admin/user/reg/user",
            "type": "get",
            "data": data,
            "dataType":"json",
            "success":function(data){
                if(data.success){
                    var doctorData = data.data;
                    for (var i = 0; i < doctorData.length; i++) {
                        if (doctorData[i].cuId == doctorName) {
                            $("#speakerSelect").append(
                                '<option value="' + doctorData[i].cuId + '" selected="selected">'
                                + doctorData[i].cuRealName + '</option>', null);
                        } else {
                            $("#speakerSelect").append(
                                '<option value="' + doctorData[i].cuId + '">' + doctorData[i].cuRealName
                                + '</option>', null);
                        }
                    }
                }else{
                    showMsg(data.msg);
                }
                initSelect("#speakerSelect");
            }
        });
    }
}

/**
 * 初始化省
 */
function initProvince(){
    // $("#provinceSelect").empty();
    // $("#provinceSelect").append("<option value=''>请选择省</option>");
    initSelect("#provinceSelect");
}
/**
 * 初始化市
 */
function initCity(){
    $("#citySelect").empty();
    $("#citySelect").append("<option value=''>请选择市</option>");
    initSelect("#citySelect");
}

/**
 * 初始化医院
 */
function initHospital(){
    $("#hospitalSelect").empty();
    $("#hospitalSelect").append("<option value=''>请选择医院</option>");
    initSelect("#hospitalSelect");
}

/**
 * 初始化医生
 */
function initDoctor(){
    $("#speakerSelect").empty();
    $("#speakerSelect").append("<option value=''>请选择医生</option>");
    initSelect("#speakerSelect");
}


function initSelect(element){
    //选择省市
    $(element).mobiscroll().select({
        theme: 'android-ics light',
        mode: 'scroller',
        lang: 'zh',
        display: 'bottom',
        headerText: '请选择',
        minWidth: 200
    });
}