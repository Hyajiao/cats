<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>项目管理-文献配置-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_project" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>项目管理-${project.cpProjectName}</h1>
            <ol class="breadcrumb">
                <li><a href="admin/project/list"><i class="fa fa-dashboard"></i>项目列表</a></li>
                <li class="active">项目管理-文献配置</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">项目信息</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-sm-12">项目名称：${project.cpProjectName }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">药企名称：${project.cpCompanyName }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">开始时间：${project.cpStartTimeStr }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">结束时间：${project.cpEndTimeStr }</div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">项目简介：${project.cpProjectMemo }</div>
                            </div>
                        </div>
                    </div>
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class=""><a href="admin/project/config/news/${project.cpId }" aria-expanded="true">资讯设置</a></li>
                            <li class="active"><a href="#tab_2" data-toggle="tab" aria-expanded="false">文献设置</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active">
                                <div class="row">
                                    <!-- Left col -->
                                    <section class="col-lg-4 connectedSortable ui-sortable">
                                        <!-- Custom tabs (Charts with tabs)-->
                                        <div class="nav-tabs-custom" style="cursor: move;">
                                            <div class="tab-content no-padding">
                                                <form class="form-horizontal" id="saveForm" role="form" action="admin/project/config/literature/${project.cpId}/save" method="post">
                                                    <input type="hidden" name="cplcCpId" value="${project.cpId}">
                                                    <div id="keywordDiv" class="box-body">
                                                        <div class="form-group">
                                                            <div class="col-sm-7">
                                                                <input type="text" class="form-control" id="cplcTitle" name="cplcTitle" value="" placeholder="标题">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-sm-7">
                                                                <input type="text" class="form-control" id="cplcAuthor" name="cplcAuthor" value="" placeholder="作者">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-sm-7">
                                                                <input type="text" class="form-control" id="cplcJour" name="cplcJour" value="" placeholder="刊名">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-sm-7">
                                                                <input type="text" class="form-control" id="cplcKeyword" name="cplcKeyword" value="" placeholder="关键字">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-sm-7">
                                                                <input type="text" class="form-control" id="cplcAbstract" name="cplcAbstract" value="" placeholder="摘要">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-sm-3">
                                                                <input type="text" class="form-control" id="cplcPublishDateStart" name="cplcPublishDateStart" value="" placeholder="出版年">
                                                            </div>
                                                            <div class="col-sm-1">
                                                                ~
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <input type="text" class="form-control" id="cplcPublishDateEnd" name="cplcPublishDateEnd" value="" placeholder="出版年">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- /.box-body -->

                                                    <div class="box-footer">
                                                        <div class="col-xs-push-4 col-xs-2">
                                                            <button id="saveBtn" type="submit" class="btn btn-info pull-right">保存</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </section>
                                    <!-- /.Left col -->
                                    <!-- right col (We are only adding the ID to make the widgets sortable)-->
                                    <section class="col-lg-8 connectedSortable ui-sortable">
                                        <div class="box-body">
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                <tr>
                                                    <th>id</th>
                                                    <th>标题</th>
                                                    <th>作者</th>
                                                    <th>刊名</th>
                                                    <th>关键字</th>
                                                    <th>摘要</th>
                                                    <th>出版年</th>
                                                    <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
                                                </tr>
                                                </thead>
                                                <tbody id="keywordTable">
                                                <c:if test="${projectLiteratureConfigList != null && projectLiteratureConfigList.size() > 0 }">
                                                    <c:forEach items="${projectLiteratureConfigList}" var="u" varStatus="s">
                                                        <tr>
                                                            <td>${u.cplcId }</td>
                                                            <td>${u.cplcTitle }</td>
                                                            <td>${u.cplcAuthor }</td>
                                                            <td>${u.cplcJour }</td>
                                                            <td>${u.cplcKeyword }</td>
                                                            <td>${u.cplcAbstract }</td>
                                                            <td>${u.cplcPublishDateStart }-${u.cplcPublishDateEnd }</td>
                                                            <td>
                                                                <button id="delBtn_${u.cplcId}" class="btn btn-danger delBtn">删除</button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${projectLiteratureConfigList == null || projectLiteratureConfigList.size() <= 0 }">
                                                    <tr id="blankRow"><td colspan="8">暂无数据！</td></tr>
                                                </c:if>
                                                </tbody>
                                            </table>
                                        </div>
                                    </section>
                                    <!-- right col -->
                                </div>

                            </div>
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
                <h4 class="modal-title" style="color: red;">请确认</h4>
            </div>
            <div class="modal-body" id="modalContent"></div>
            <div class="modal-footer">
                <button id="sureBtn" type="button" class="btn btn-info">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../include/adminlteJsInclude.jsp"/>
<script>
    $(document).ready(function () {
        $("#saveForm").bind("submit", function () {
            if($("#cplcTitle").val() != "" || $("#cplcAuthor").val() != "" || $("#cplcJour").val() != ""
                || $("#cplcKeyword").val() != "" || $("#cplcAbstract").val() != "" || $("#cplcPublishDateStart").val() != ""
                || $("#cplcPublishDateEnd").val() != ""){
                $("#saveForm").submit();
            }else{
                showMsg("请填写文献的设置项。");
                return false;
            }
        });

    })

    // 删除按钮被点击
    var cplcId = null;
    $(".delBtn").bind("click", function(){
        var btnId = this.id;
        cplcId = btnId.replace("delBtn_", "");
        var content = "请注意，将删除此配置，id=<span style='color: red'>" + cplcId + "</span>";
        $("#modalContent").html(content);
        $('#sureModal').modal('show');
    });

    $("#sureBtn").bind("click", function () {
        $('#sureModal').modal('hide');
        location.href = GLOBAL_CONFIG.basePath + "admin/project/config/literature/" + cplcId + "/del";
    });


</script>
</body>
</html>

