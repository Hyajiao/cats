<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.kingyee.cats.common.security.AdminUserUtil" %>
<%@ page import="com.kingyee.cats.common.security.AdminUserModel" %>
<%@ page import="com.kingyee.fuxi.security.ShiroConst" %>
<%@ page import="com.kingyee.fuxi.security.AuthUtil" %>

<%
    String navId = request.getParameter("navId");
    String subNavId = request.getParameter("subNavId");
    AdminUserModel user = AdminUserUtil.getLoginUser();
%>
<!-- Main Header -->
<header class="main-header"> <!-- Logo -->
    <a href="javascript:void(0);" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini" style="margin-top:75%;"><i class="fa fa-cog"></i></span>
        <span class="logo-lg"><b>临床学术追踪系统</b></span>
    </a>
    <nav class="navbar navbar-static-top" role="navigation">
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>

        <div class="navbar-custom-menu" style="border:0px solid red;">
            <ul class="nav navbar-nav">
                <li class="dropdown user user-menu">
                    <a href="admin/sysuser/editInit?id=<%= user.getId() %>" style="color:CFCFCF;">
                        <span class="glyphicon glyphicon-user"></span>
                        <%= user.getShowName() %>，你好
                    </a>
                </li>
                <li class="dropdown user user-menu">
                    <a href="admin/logout">
                        <span class="hidden-xs">退出</span>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu tree" data-widget="tree">
            <c:if test="${AuthUtil.hasAuth(ShiroConst.ADMIN_USER_WECHAT_LIST) || AuthUtil.hasAuth(ShiroConst.ADMIN_USER_REG_LIST)}">
                <li id="nav_user" class="treeview">
                    <a href="#">
                        <i class="fa fa-dashboard"></i> <span>前台用户管理</span>
                        <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                </span>
                    </a>
                    <ul class="treeview-menu">
                        <shiro:hasPermission name="${ShiroConst.ADMIN_USER_WECHAT_LIST}">
                            <li id="nav_wechatUser"><a href="admin/user/wechat/list"><i class="fa fa-circle-o"></i>关注微信用户</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="${ShiroConst.ADMIN_USER_REG_LIST}">
                            <li id="nav_regUser"><a href="admin/user/reg/list"><i class="fa fa-circle-o"></i> 注册绑定用户</a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </c:if>
            <shiro:hasPermission name="${ShiroConst.ADMIN_SYS_USER_LIST}">
                <li id="nav_system_user"><a href="admin/sysuser/list"><i class="fa fa-user"></i> <span>系统用户管理</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="${ShiroConst.ADMIN_PROJECT_LIST}">
                <li id="nav_project"><a href="admin/project/list"><i class="fa fa-meetup"></i> <span>项目管理</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="${ShiroConst.ADMIN_EXPERT_GROUP_LIST}">
                <li id="nav_expert_group"><a href="admin/expert/group/list"><i class="fa fa-meetup"></i> <span>专家管理</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="${ShiroConst.ADMIN_SURVEY_LIST}">
                <li id="nav_survey"><a href="admin/survey/list"><i class="fa fa-meetup"></i> <span>调研管理</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="${ShiroConst.ADMIN_FINANCE_LIST}">
                <li id="nav_finance"><a href="admin/finance/list"><i class="fa fa-meetup"></i> <span>财务管理</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="${ShiroConst.ADMIN_SECURITY_INDEX}">
                <li id="nav_meeting"><a href="admin/security/index" target="_blank"><i class="fa fa-meetup"></i> <span>权限管理</span></a></li>
            </shiro:hasPermission>
            <%--<li id="nav_permission" class="treeview">--%>
                <%--<a href="#">--%>
                    <%--<i class="fa fa-dashboard"></i> <span>权限管理</span>--%>
                    <%--<span class="pull-right-container">--%>
                        <%--<i class="fa fa-angle-left pull-right"></i>--%>
                    <%--</span>--%>
                <%--</a>--%>
                <%--<ul class="treeview-menu">--%>
                    <%--<li id="nav_role"><a href="admin/security/role/list"><i class="fa fa-circle-o"></i>角色管理</a></li>--%>
                    <%--<li id="nav_resource"><a href="admin/security/resource/list"><i class="fa fa-circle-o"></i> 资源管理</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>
        </ul>
    </section>
</aside>

<script>
    //设置选中状态
    var navId = "<%=navId%>";
    var subNavId = "<%=subNavId%>";
    //选中状态
    $("#" + navId).addClass("active");
    $("#" + navId).parent().siblings().children().removeClass("active");

    if(subNavId){
        $("#" + subNavId).addClass("active");
    }
</script>
