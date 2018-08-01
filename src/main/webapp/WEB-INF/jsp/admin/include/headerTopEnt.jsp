<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kingyee.cats.common.security.AdminUserUtil" %>
<%@ page import="com.kingyee.cats.common.security.AdminUserModel" %>
<%
    AdminUserModel user = AdminUserUtil.getLoginUser();
%>
<div class="top_bg">
    <div class="top_box clear">
        <div class="lf">
            <img src="static/ent/images/top_img.png"/>
        </div>
        <div class="rt clear">
            <div class="drop_down_menu" id="projectChange">
                <a href="javascript:void(0);">
                    项目切换
                    <i class="menu menu_rt_icon"></i>
                </a>
                <ul class="pop_menu" id="projectList">
                </ul>
            </div>
            <ul class="lf login_message clear">
                <li class="person_name">
                    <a href="admin/ent/userManage/${project.cpId}">
                        <i class="person_icon"></i><%= user.getShowName() %>
                    </a>
                </li>
                <li class="center_border">
                    <i></i>
                </li>
                <li>
                    <a href="admin/logout">
                        <span class="hidden-xs">退出</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>