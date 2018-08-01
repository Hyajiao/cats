<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.kingyee.cats.enums.SendTypeEnum" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>调研管理-推送-临床学术追踪系统</title>
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
                <li class="active">调研管理-推送</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">调研信息</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-sm-12">主题：${survey.cspTitle }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">问卷类型：${survey.cspType }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">问卷数量：${survey.cspNum }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">关联医生：${expertGroup.cegExpertGroupName }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">关联项目：${project.cpProjectName }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">关联问卷：${survey.cspSurveyUrl }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">单价：${survey.cspUnitPriceInt }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">预计时长：${survey.cspDuration }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">开始时间：${survey.cspStartDateStr }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">截止时间：${survey.cspEndDateStr }</div>
                            </div>
                        </div>
                        <div class="box-footer">
                            <div class="col-xs-push-2 col-xs-2">
                                <button id="wechatBtn" type="button" class="btn btn-info pull-right">微信发送问卷</button>
                            </div>
                            <div class="col-xs-push-4 col-xs-2">
                                <button id="smsBtn" type="button" class="btn btn-info pull-right">短信发送问卷</button>
                            </div>
                        </div>
                    </div>

                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">发送信息</h3>
                        </div>
                        <div class="box-body">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>发送时间</th>
                                    <th>发送方式</th>
                                    <th>发送数量</th>
                                    <th>成功发送数量</th>
                                    <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
                                </tr>
                                </thead>
                                <tbody id="keywordTable">
                                    <c:if test="${surveyNoticeList != null && surveyNoticeList.size() > 0 }">
                                        <c:forEach items="${surveyNoticeList}" var="u" varStatus="s">
                                            <tr>
                                                <td>${s.index + 1 }</td>
                                                <td>${u.csnCreateTimeStr }</td>
                                                <td>${SendTypeEnum.getTextByOrdinal(u.csnSendType) }</td>
                                                <td>${u.csnSendNum }</td>
                                                <td>${u.csnSuccessNum }</td>
                                                <td>
                                                    <a href="admin/survey/${survey.cspId}/notice/${u.csnId}/list" class="btn btn-info delBtn">查看</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${surveyNoticeList == null || surveyNoticeList.size() <= 0 }">
                                        <tr id="blankRow"><td colspan="8">暂无数据！</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="sureModal" tabindex="-1" role="dialog" aria-labelledby="myFileModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" style="">请选择发送范围</h4>
            </div>
            <div class="modal-body" id="modalContent">
                <button id="allBtn" type="button" class="btn btn-info">全部</button>
                <button id="onlyBtn" type="button" class="btn btn-info" data-dismiss="modal">仅未发送</button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../include/adminlteJsInclude.jsp"/>
<script>
    $(document).ready(function () {


        var type = "";
        $("#wechatBtn").bind("click", function () {
            type = 0;
            $('#sureModal').modal('show');
        });

        $("#smsBtn").bind("click", function () {
            type = 1;
            $('#sureModal').modal('show');
        });

        $("#allBtn").bind("click", function () {
            sendNotice(type, 0);
        });

        $("#onlyBtn").bind("click", function () {
            sendNotice(type, 1);
        });

        function sendNotice(type, scope) {
            var url = "admin/survey/" + ${survey.cspId} + "/notice/send";
            $.ajax({
                type: "post",
                cache: false,
                dataType: "json",
                url: url,
                data: {
                    type: type,
                    scope: scope
                },
                success: function (json) {
                    $('#sureModal').modal('hide');
                    if(json.success){
                        showMsg("发送成功");
                        location.reload();
                    }else{
                        showMsg(json.msg);
                    }

                }
            });
        }

    });
</script>
</body>
</html>

