<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>产品概述</title>
    <jsp:include page="../include/headerEnt.jsp"></jsp:include>
    <script>
        $(document).ready(function () {
            var date = new Date();
            // 本周一的日期
            date.setDate(date.getDate() - date.getDay() + 1);
            var startDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            // 本周日的日期
            date.setDate(date.getDate() + 6);
            var endDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();



            $("#benzhouzixun").empty();
            $("#benzhouzixun").append("正在加载");
            $("#benzhouwenxian").empty();
            $("#benzhouwenxian").append("正在加载");


            //接口获取资讯信息
            $.ajax({
                type: "get",
                url: "admin/ent/getNewsList",
                data: {"id": $("#projectId").val(),"startDate":startDate,"endDate":endDate},
                dataType: "json",
                success: function (json) {
                    if (json.success) {
                        var list = json.data;
                        var count = list.length;
                        if (count > 0) {
                            var html = "";
                            for (var i = 0; i < count; i++) {
                                var publishDate =list[i].publish_date;
                                if(publishDate && publishDate.length > 10){
                                    publishDate = publishDate.substring(0,10);
                                }
                                html = html + "<p>" + publishDate + " - " + list[i].title + "</p>";
                            }
                            $("#benzhouzixun").empty();
                            $("#benzhouzixun").append(html);
                        }else{
                            $("#benzhouzixun").empty();
                            $("#benzhouzixun").append("暂无数据");
                        }
                    } else {
                        alert(json.msg);
                    }
                }
            });

            //接口获取文献信息
            $.ajax({
                type: "get",
                url: "admin/ent/getLiteratureList",
                data: {"id": $("#projectId").val(),"startDate":startDate,"endDate":endDate},
                dataType: "json",
                success: function (json) {
                    if (json.success) {
                        var list = json.data;
                        var count = list.length;
                        if (count > 0) {
                            var html = "";
                            for (var i = 0; i < count; i++) {
                                var title = list[i].title;
                                if(title && title.length >50){
                                    title = title.substring(0,50)+"……";
                                }
                                html = html + "<p>" + list[i].publish_date + " - " + title + "</p>";
                            }
                            $("#benzhouwenxian").empty();
                            $("#benzhouwenxian").append(html);
                        }else{
                            $("#benzhouwenxian").empty();
                            $("#benzhouwenxian").append("暂无数据");
                        }
                    } else {
                        alert(json.msg);
                    }
                }
            });
        });
    </script>
</head>
<body>
<div>
    <jsp:include page="../include/headerTopEnt.jsp"></jsp:include>
    <div class="comm clear">
        <!--左侧选项卡-->
        <jsp:include page="../include/entSidebar.jsp">
            <jsp:param value="nav_abstract" name="navId"></jsp:param>
        </jsp:include>
        <!--右侧信息-->
        <div class="right_content">
            <div class="content_title">
                <i class="title_icon"></i>
                ${project.cpProjectName}
            </div>
            <ul class="content_message">
                <li>
                    <h1>本周产品相关资讯</h1>
                    <div id="benzhouzixun"> </div>
                    <p>
                        <a href="javascript:void(0);" class="active" onclick="window.location.href='admin/ent/newsList/${project.cpId}'">进入</a>
                    </p>
                </li>
                <li>
                    <h1>本周产品相关文献</h1>
                    <div id="benzhouwenxian"> </div>
                    <p>
                        <a href="javascript:void(0);" class="active" onclick="window.location.href='admin/ent/literatureList/${project.cpId}'">进入</a>
                    </p>
                </li>
                <li>
                    <h1>调研报告</h1>
                    <p>已完成报告：${finishNum}</p>
                    <p>未完成报告：${unFinishNum}</p>
                    <p>
                        <a href="javascript:void(0);" class="active" onclick="window.location.href='admin/ent/surveyList/${project.cpId}'">进入</a>
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>