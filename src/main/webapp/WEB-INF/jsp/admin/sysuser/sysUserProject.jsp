<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>企业账户_项目关联-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_system_user" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>企业账户_项目关联</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-6">
                    <div class="box box-success">
                        <div class="box-header with-border">
                            <h3 class="box-title">可选项目列表</h3>
                        </div>
                        <div class="box-body">
                            <c:if test="${unLinkedProjectList != null && unLinkedProjectList.size() > 0 }">
                                <table id="example2" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>项目名称</th>
                                        <th>药企</th>
                                        <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${unLinkedProjectList}" var="u" varStatus="s">
                                        <tr>
                                            <td>${s.index + 1 }</td>
                                            <td>${u.cpProjectName }</td>
                                            <td>${u.cpCompanyName }</td>
                                            <td><a class="btn btn-success" href="admin/sysuser/${uid}/project/add/${u.cpId }">添加</a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${unLinkedProjectList == null || unLinkedProjectList.size() <= 0 }">
                                暂无数据！
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="box box-danger">
                        <div class="box-header with-border">
                            <h3 class="box-title">已选项目列表</h3>
                        </div>
                        <div class="box-body">
                            <c:if test="${linkedProjectList != null && linkedProjectList.size() > 0 }">
                                <table id="example2" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>项目名称</th>
                                        <th>药企</th>
                                        <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${linkedProjectList}" var="u" varStatus="s">
                                        <tr>
                                            <td>${s.index + 1 }</td>
                                            <td>${u.cpProjectName }</td>
                                            <td>${u.cpCompanyName }</td>
                                            <td><a class="btn btn-danger" href="admin/sysuser/${uid}/project/delete/${u.cpId }">删除</a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${linkedProjectList == null || linkedProjectList.size() <= 0 }">
                                暂无数据！
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<jsp:include page="../include/adminlteJsInclude.jsp"/>
<script type="text/javascript">
    $(document).ready(function () {
    });
</script>
</body>
</html>