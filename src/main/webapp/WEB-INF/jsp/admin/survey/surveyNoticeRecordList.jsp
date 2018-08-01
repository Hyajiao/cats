<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.kingyee.cats.enums.SendNoticeStatusEnum" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>调研管理-推送明细-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_survey" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>调研管理-${survey.cspTitle}</h1>
            <ol class="breadcrumb">
                <li><a href="admin/survey/list"><i class="fa fa-dashboard"></i>调研列表</a></li>
                <li class="active">调研管理-推送明细</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">发送明细</h3>
                        </div>
                        <div class="box-body">
                            <form class="form-inline" name="searchForm" id="searchForm"
                                  style="margin-bottom: 15px;">
                                <input type="hidden" id="page" name="page" value="${page }"/>
                                <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
                            </form>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>姓名</th>
                                    <th>医院</th>
                                    <th>科室</th>
                                    <th>职称</th>
                                    <th>是否发送成功</th>
                                    <th>发送时间</th>
                                </tr>
                                </thead>
                                <tbody id="keywordTable">
                                    <c:if test="${pageInfo.items != null && pageInfo.items.size() > 0 }">
                                        <c:forEach items="${pageInfo.items}" var="u" varStatus="s">
                                            <tr>
                                                <td>${(pageInfo.rowsPerPage  * (pageInfo.nowPage -1)) + (s.index +1)  }</td>
                                                <td>${u[1].cegdRealName }</td>
                                                <td>${u[1].cegdHospital }</td>
                                                <td>${u[1].cegdDept }</td>
                                                <td>${u[1].cegdProfessional }</td>
                                                <td>
                                                    <c:if test="${u[0].csnrSendStatus == SendNoticeStatusEnum.SEND_FINISH.ordinal()}">是</c:if>
                                                    <c:if test="${u[0].csnrSendStatus == SendNoticeStatusEnum.SEND_FAIL.ordinal()}">否</c:if>
                                                </td>
                                                <td>${u[0].csnrSendTimeStr }</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${pageInfo.items == null || pageInfo.items.size() <= 0 }">
                                        <tr id="blankRow"><td colspan="8">暂无数据！</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                            <jsp:include page="../include/pojoPageInfo.jsp">
                                <jsp:param value="admin/survey/${survey.cspId}/notice/${u.csnId}/list" name="act"/>
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
<script>
    $(document).ready(function () {

    });
</script>
</body>
</html>

