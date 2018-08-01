<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>专家组列表-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_expert_group" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>专家组列表</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body">
                            <div class="box-header">
                                <div style="float:left;margin-right:10px">
                                    <a class="btn btn-success" href="admin/expert/group/addInit">新增</a>
                                </div>
                                <form class="form-inline" name="searchForm" id="searchForm"
                                      style="margin-bottom: 15px;">
                                    <input type="hidden" id="page" name="page" value="${page }"/>
                                    <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="groupName" name="groupName"
                                               placeholder="专家组名" value="${groupName }"/>
                                        <input type="radio" name="state" value="1"
                                               <c:if test="${state == 1 }">checked</c:if> />正常
                                        <input type="radio" name="state" value="0"
                                               <c:if test="${state == 0 }">checked</c:if> />无效
                                    </div>
                                    <button type="button" class="btn btn-success" id="searchBtn">搜索</button>
                                </form>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <c:if test="${pageInfo.items != null && pageInfo.items.size() > 0 }">
                                <table id="example2" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>专家组名称</th>
                                        <th>医生数量</th>
                                        <th>创建时间</th>
                                        <th>创建人</th>
                                        <th>状态</th>
                                        <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${pageInfo.items}" var="u" varStatus="s">
                                        <tr>
                                            <td>${(pageInfo.rowsPerPage  * (pageInfo.nowPage -1)) + (s.index +1)  }</td>
                                            <td>${u.cegExpertGroupName }</td>
                                            <td>${u.cegExpertNum }</td>
                                            <td>${u.cegCreateTimeStr }</td>
                                            <td>${u.cegCreateUserName }</td>
                                            <td>
                                                <c:if test="${u.cegIsValid == 1 }">正常</c:if>
                                                <c:if test="${u.cegIsValid == 0 }">无效</c:if>
                                            </td>
                                            <td>
                                                <c:if test="${u.cegIsValid == 1 }">
                                                    <a class="btn btn-success" href="admin/expert/group/editInit?id=${u.cegId }">
                                                        修改
                                                    </a>
                                                    <a class="btn btn-success" href="admin/expert/${u.cegId }/list">
                                                        查看医生
                                                    </a>
                                                    <a class="btn btn-success" href="admin/expert/${u.cegId }/importInit">
                                                        导入医生
                                                    </a>
                                                    <a class="btn btn-danger" href="admin/expert/group/reset?id=${u.cegId }">删除</a>
                                                </c:if>
                                                <c:if test="${u.cegIsValid == 0 }">
                                                    <a class="btn btn-success" href="admin/expert/group/reset?id=${u.cegId }">恢复</a>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${pageInfo.items == null || pageInfo.items.size() <= 0 }">
                                暂无数据！
                            </c:if>
                            <jsp:include page="../include/pojoPageInfo.jsp">
                                <jsp:param value="admin/expert/group/list" name="act"/>
                                <jsp:param value="searchForm" name="formName"/>
                            </jsp:include>
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
        //检索
        $("#searchBtn").bind("click", function () {
            var form = document.forms[0];
            form.action = "admin/expert/group/list";
            $("#page").val(1);
            form.submit();
        });

        //回车提交表单
        $("#keyword").keydown(function (event) {
            if (event.keyCode == 13) {
                $("#searchBtn").click();
            }
        });

    });
</script>
</body>
</html>