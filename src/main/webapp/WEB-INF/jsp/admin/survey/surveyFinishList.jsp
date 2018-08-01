<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.kingyee.cats.enums.SysUserTypeEnum" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>调研完成列表-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_survey" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>调研完成列表-${survey.cspTitle}</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body">
                            <div class="box-header">
                                <form class="form-inline" name="searchForm" id="searchForm"
                                      style="margin-bottom: 15px;">
                                    <input type="hidden" id="page" name="page" value="${page }"/>
                                    <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
                                    <div class="form-group">
                                        完成时间：
                                        <input type="text" class="form-control" id="startDate" name="startDate"
                                               placeholder="开始时间" value="${startDate }"/>
                                        ~
                                        <input type="text" class="form-control" id="endDate" name="endDate"
                                               placeholder="截止时间" value="${endDate }"/>
                                        奖励：<select name="csfrHadIssueReward" class="form-control" >
                                        <option value="">全部</option>
                                        <option value="1" <c:if test="${csfrHadIssueReward == 1}">selected</c:if> >已发</option>
                                        <option value="0" <c:if test="${csfrHadIssueReward == 0}">selected</c:if> >未发</option>
                                        </select>
                                    </div>
                                    <button type="button" class="btn btn-success" id="searchBtn">搜索</button>
                                    <button type="button" class="btn btn-success" id="exportBtn">导出</button>
                                    <button type="button" class="btn btn-success" id="batchRewardBtn">批量发放奖励</button>
                                    <button type="button" class="btn btn-success" id="allRewardBtn">全部发放奖励</button>
                                </form>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <c:if test="${pageInfo.items != null && pageInfo.items.size() > 0 }">
                                <table id="example2" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" id="cbAll" name="cbAll"/></th>
                                        <th>序号</th>
                                        <th>姓名</th>
                                        <th>医院</th>
                                        <th>科室</th>
                                        <th>职称</th>
                                        <th>完成时间</th>
                                        <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>发放奖励</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${pageInfo.items}" var="u" varStatus="s">
                                        <tr>
                                            <td>
                                                <c:if test="${u[0].csfrHadIssueReward == 0 }">
                                                    <input type="checkbox" id="${u[0].csfrId}" name="cb"/>
                                                </c:if>
                                            </td>
                                            <td>${(pageInfo.rowsPerPage  * (pageInfo.nowPage -1)) + (s.index +1)  }</td>
                                            <td>${u[1].cegdRealName }</td>
                                            <td>${u[1].cegdHospital }</td>
                                            <td>${u[1].cegdDept }</td>
                                            <td>${u[1].cegdProfessional }</td>
                                            <td>${u[0].csfrFinishTimeStr }</td>
                                            <td>
                                                <c:if test="${u[0].csfrHadIssueReward == 1 }">
                                                    已发送
                                                </c:if>
                                                <c:if test="${u[0].csfrHadIssueReward == 0 }">
                                                    <button class="btn btn-success rewardBtn" id="rewardBtn_${u[0].csfrId}">发放奖励</button>
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
                                <jsp:param value="admin/survey/${surveyId}/finish" name="act"/>
                                <jsp:param value="searchForm" name="formName"/>
                            </jsp:include>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<input type="hidden" id="surveyId" name="surveyId" value="${surveyId }"/>
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="viewLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="viewLabel">全部发放奖励</h4>
            </div>
            <div class="modal-body">
                请确认，讲给所有完成调研的用户，发放奖励。
            </div>
            <div class="modal-footer">
                <button type="button" id="sureBtn" class="btn btn-success">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../include/adminlteJsInclude.jsp"/>
<script type="text/javascript" src="static/lib/common.js"></script>
<script type="text/javascript" src="static/js/admin/surveyFinishList.js"></script>
</body>
</html>