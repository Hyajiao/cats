<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>产品文献</title>
    <jsp:include page="../include/headerEnt.jsp"></jsp:include>
    <script src="static/ent/lib/echarts.common.min.js"></script>
    <script src="static/js/admin/timeUtil.js"></script>
</head>
<body>
<div>
    <jsp:include page="../include/headerTopEnt.jsp"></jsp:include>
    <div class="comm clear">
        <jsp:include page="../include/entSidebar.jsp">
            <jsp:param value="nav_literature" name="navId"></jsp:param>
        </jsp:include>
        <div class="right_content">
            <div class="content_title">
                <i class="title_icon"></i>
                ${project.cpProjectName}
            </div>
            <div class="product_document_message">
                <h1>
                    <i class="document_icon"></i>
                    文献指数
                </h1>
                <div>
                    <div class="chart_top">
                        <ul class="clear" style="width:800px;margin-left:-12%;">
                            <li class="select" id="hm">
                                <a href="javascript:void(0);" onclick="halfMonth()">
                                    半月
                                </a>
                            </li>
                            <li id="hy">
                                <a href="javascript:void(0);" onclick="halfYear()">
                                    半年
                                </a>
                            </li>
                            <li id="oy">
                                <a href="javascript:void(0);" onclick="oneYear()">
                                    一年
                                </a>
                            </li>
                            <li id="zdy">
                                <a href="javascript:void(0);" onclick="zdycx()">
                                    自定义
                                </a>
                            </li>
                            <li style="width:300px;">
                                <input type="text" class="form-control first" id="startDate" name="startDate"
                                       placeholder="开始时间" value="${startDate }"/>
                                <span style="margin-left:-8%;">~</span>
                                <input type="text" class="form-control" id="endDate" name="endDate"
                                       placeholder="截止时间" value="${endDate }"/>
                            </li>
                        </ul>

                    </div>
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div class="chart" id="main" style="width:110%;margin-top:-2%;">

                    </div>
                    <div id="nodata" style="height:100px;">
                        <br/>
                        <span style='font-size:1.5em;' id="isload">正在加载</span>
                    </div>
                </div>
                <br/> <br/>
                <h1>
                    <i class="document_icon"></i>
                    文献数据库
                </h1><br/>
                <div class="information_database">
                    <ul id="allDataList">

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var now = new Date(); //当前日期
    var startDate;
    var endDate = now.Format("yyyy-MM-dd");
    //本月
    function halfMonth(){
        var startDateD = new Date();
        startDateD.setDate(now.getDate()-15);
        startDate = startDateD.Format("yyyy-MM-dd");
        getLiteratureAnalyze(startDate,endDate);
        //选中状态
        $("#hm").addClass("select");
        $("#hm").siblings().removeClass("select");
    }
    //半年
    function halfYear(){
        var startDateD = new Date();
        startDateD.setDate(now.getDate()-180);
        startDate = startDateD.Format("yyyy-MM-dd");
        getLiteratureAnalyze(startDate,endDate);
        //选中状态
        $("#hy").addClass("select");
        $("#hy").siblings().removeClass("select");
    }
    //一年
    function oneYear(){
        var startDateD = new Date();
        startDateD.setDate(now.getDate()-365);
        startDate = startDateD.Format("yyyy-MM-dd");
        getLiteratureAnalyze(startDate,endDate);
        //选中状态
        $("#oy").addClass("select");
        $("#oy").siblings().removeClass("select");
    }
    //自定义
    function zdycx(){
        // $('#startDate')[0].focus();
        //选中状态
        $("#zdy").addClass("select");
        $("#zdy").siblings().removeClass("select");
    }
    $(document).ready(function () {
        $('#startDate').datepicker({
            format: 'yyyy-mm-dd',
            language: 'cn',
            autoclose: true,
            todayHighlight: true
        }).on('changeDate',function(ev){
            startDate = $("#startDate").val();
            getLiteratureAnalyze(startDate,endDate);
            //选中状态
            $("#zdy").addClass("select");
            $("#zdy").siblings().removeClass("select");
        });

        $('#endDate').datepicker({
            format: 'yyyy-mm-dd',
            language: 'cn',
            autoclose: true,
            todayHighlight: true
        }).on('changeDate',function(ev){
            endDate = $("#endDate").val();
            getLiteratureAnalyze(startDate,endDate);
            //选中状态
            $("#zdy").addClass("select");
            $("#zdy").siblings().removeClass("select");
        });
        //文献检索接口
        getLiteratureList();
        //文献统计接口
        halfMonth();
    });
    //文献检索接口
    function getLiteratureList(startDate,endDate){
        $("#allDataList").empty();
        $("#allDataList").append("</br><span style='font-size:1.5em;'>正在加载</span>");
        $.ajax({
            type: "get",
            url: "admin/ent/getLiteratureList",
            data: {"id":$("#projectId").val(),"startDate":startDate,"endDate":endDate},
            dataType: "json",
            success: function (json) {
                if (json.success) {
                    var list = json.data;
                    var count = list.length;
                    if(count >0){
                        var html = "";
                        for (var i = 0; i < count; i++) {
                            var timeClass="time1";
                            if((i+1)%2==0){
                                timeClass="time2";
                            }
                            var time = list[i].publish_date;
                            if(time){
                                time = time.substring(0,10);
                            }
                            var digest = list[i].digest;
                            if(digest && digest.length >90){
                                digest = digest.substring(0,90)+"……";
                            }else if(!digest || digest == undefined){
                                digest = "暂无";
                            }
                            html = html+ '<li>'+
                                '<div class="lf">'+
                                '<img src="static/ent/images/wenxian.png" style="width:128px;height:54px;"/>'+
                                '<b class="'+timeClass+'">'+time+'</b>'+
                                '</div>'+
                                '<div class="rt right_message">'+
                                '<h1><a href="'+list[i].link+'" target="_blank" style="color:#373737;">'+list[i].title+'</a></h1>'+
                                '<p>'+digest+'</p>'+
                                '</div>'+
                                '</li>';
                        }
                        $("#allDataList").empty();
                        $("#allDataList").append(html);
                    }else{
                        $("#allDataList").empty();
                        $("#allDataList").append("</br><span style='font-size:1.5em;'>暂无数据</span>");
                    }
                } else {
                    alert(json.msg);
                }
            }
        });
    }

    //文献统计接口
    function getLiteratureAnalyze(startDate,endDate){
        $("#nodata").show();
        $("#main").hide();
        $("#startDate").val(startDate);
        $("#endDate").val(endDate);
        $.ajax({
            type: "get",
            url: "admin/ent/getLiteratureAnalyze",
            data: {"id":$("#projectId").val(),"startDate":startDate,"endDate":endDate},
            dataType: "json",
            success: function (json) {
                if (json.success) {
                    var list = json.data;
                    var count = list.length;
                    if(count >0){
                        $("#nodata").hide();
                        $("#main").show();
                        // 基于准备好的dom，初始化echarts实例
                        var myChart = echarts.init(document.getElementById('main'));
                        var arr = [];
                        var values = [];
                        for (var i = 0; i < count; i++) {
                            arr.push(list[i].name);
                            values.push(list[i].value);
                        }
                        // 指定图表的配置项和数据
                        var option = {
                            tooltip: {
                                trigger: 'axis'
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            toolbox: {
                                feature: {
                                    saveAsImage: {}
                                }
                            },
                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                data: arr
                            },
                            yAxis: {
                                type: 'value',
                                max : function(value) {
                                    return value.max +2;
                                }
                            },
                            series: [{
                                data: values,
                                type: 'line'
                            }]
                        };
                        if (option && typeof option === "object") {
                            myChart.setOption(option, true);
                        }
                    }else{
                        $("#main").hide();
                        $("#nodata").show();
                        $("#isload").html("暂无数据");
                    }
                } else {
                    // alert(json.msg);
                }
            }
        });
    }
</script>
</body>
</html>