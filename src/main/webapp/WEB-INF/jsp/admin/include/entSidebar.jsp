<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String navId = request.getParameter("navId");
%>
<input type="hidden" id="projectId" value="${project.cpId}"/>
<div class="left_tab">
    <div class="search">
        <a href="javascript:void(0);"><i class="search_icon"></i></a>
    </div>
    <ul class="relative_list">
        <li id="nav_abstract">
            <a href="admin/ent/abstractInfo/${project.cpId}">
                <i class="border"></i>
                <span><i class="product_summarize"></i>产品概述</span>
                <div class="icon"></div>
            </a>
        </li>
        <li id="nav_news">
            <a href="admin/ent/newsList/${project.cpId}">
                <i class="border"></i>
                <span><i class="product_info"></i>产品资讯</span>
                <div class="icon"></div>
            </a>
        </li>
        <li id="nav_literature">
            <a href="admin/ent/literatureList/${project.cpId}">
                <i class="border"></i>
                <span><i class="product_document"></i>产品文献</span>
                <div class="icon"></div>
            </a>
        </li>
        <li id="nav_survey">
            <a href="admin/ent/surveyList/${project.cpId}">
                <i class="border"></i>
                <span><i class="investigation_report"></i>调研报告</span>
                <div class="icon"></div>
            </a>
        </li>
        <li id="nav_user">
            <a href="admin/ent/userManage/${project.cpId}">
                <i class="border"></i>
                <span><i class="account_management"></i>账号管理</span>
                <div class="icon"></div>
            </a>
        </li>
    </ul>
</div>
<script>//设置选中状态
    var navId = "<%=navId%>";
    //选中状态
    $("#" + navId).addClass("checked");
    $("#" + navId).parent().siblings().children().removeClass("checked");


    /*if(navId != "nav_abstract"){
       $("#projectChange").hide();
    }else{*/
        //查询项目列表
        $.ajax({
            type: "post",
            url: "admin/ent/getProjectList",
            dataType: "json",
            success: function (json) {
                if (json.success) {
                    var list = json.data;
                    var count = list.length;
                    var html = "";
                    for (var i = 0; i < count; i++) {
                        html = html+ '<li>'+
                                        '<a href="admin/ent/abstractInfo/'+list[i][1].cpId+'">'+list[i][1].cpProjectName+'</a>'+
                                    '</li>';
                    }
                    $("#projectList").empty();
                    $("#projectList").append(html);
                } else {
                    showMsg(json.msg);
                }
            }
        });
    // }
</script>
