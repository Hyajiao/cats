<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.kingyee.cats.enums.SysUserTypeEnum" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>调研列表-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_survey" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>调研列表</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body">
                            <div class="box-header">
                                <div style="float:left;margin-right:10px">
                                    <a class="btn btn-success" href="admin/survey/addInit">新增</a>
                                </div>
                                <form class="form-inline" name="searchForm" id="searchForm"
                                      style="margin-bottom: 15px;">
                                    <input type="hidden" id="page" name="page" value="${page }"/>
                                    <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="surveyName" name="surveyName"
                                               placeholder="主题" value="${surveyName }"/>
                                        有效期：
                                        <input type="text" class="form-control" id="startDate" name="startDate"
                                               placeholder="开始时间" value="${startDate }"/>
                                        ~
                                        <input type="text" class="form-control" id="endDate" name="endDate"
                                               placeholder="截止时间" value="${endDate }"/>
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
                                        <th>主题</th>
                                        <th>单价</th>
                                        <th>有效期</th>
                                        <th>目标数</th>
                                        <th>已完成</th>
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
                                            <td>${u.cspTitle }</td>
                                            <td>${u.cspUnitPrice }</td>
                                            <td>${u.cspStartDateStr } ~ ${u.cspEndDateStr}</td>
                                            <td>${u.cspNum }</td>
                                            <td>${u.cspFinishedNum }</td>
                                            <td>${u.cspCreateTimeStr }</td>
                                            <td>${u.cspCreateUserName }</td>
                                            <td>
                                                <c:if test="${u.cspIsValid == 1 }">正常</c:if>
                                                <c:if test="${u.cspIsValid == 0 }">无效</c:if>
                                            </td>
                                            <td>
                                                <c:if test="${u.cspIsValid == 1 }">
                                                    <a class="btn btn-success" href="admin/survey/editInit?id=${u.cspId }">
                                                        修改
                                                    </a>
                                                    <a class="btn btn-danger" href="admin/survey/reset?id=${u.cspId }">删除</a>
                                                    <c:choose>
                                                        <c:when test="${u.cspSurveyReportPath == null || u.cspSurveyReportPath == '' }">
                                                            <button id="uploadBtn_${u.cspId }" class="uploadBtn btn btn-info">上传调研报告</button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button id="viewBtn_${u.cspId }" class="viewBtn btn btn-info">查看调研报告</button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <a class="btn btn-info" href="admin/survey/${u.cspId }/notice">推送</a>
                                                    <a class="btn btn-info" href="admin/survey/${u.cspId }/finish">完成记录</a>
                                                </c:if>
                                                <c:if test="${u.cspIsValid == 0 }">
                                                    <a class="btn btn-success" href="admin/survey/reset?id=${u.cspId }">恢复</a>
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
                                <jsp:param value="admin/survey/list" name="act"/>
                                <jsp:param value="searchForm" name="formName"/>
                            </jsp:include>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<div class="modal fade" id="myFileModal" tabindex="-1" role="dialog" aria-labelledby="myFileModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myFileModalLabel">调研报告上传</h4>
            </div>
            <div class="modal-body"><input type="file" id="multiFileUpload" name="file" cssClass="form-control"/></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="viewLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="viewLabel">调研报告</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <a data-href="admin/survey/report/download/" id="downloadBtn" target="_blank" class="btn btn-default">下载查看</a>
                <button type="button" id="reUploadBtn" class="btn btn-default">重新上传</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../include/adminlteJsInclude.jsp"/>
<script type="text/javascript" src="static/lib/common.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //检索
        $("#searchBtn").bind("click", function () {
            var form = document.forms[0];
            form.action = "admin/survey/list";
            $("#page").val(1);
            form.submit();
        });

        //回车提交表单
        $("#keyword").keydown(function (event) {
            if (event.keyCode == 13) {
                $("#searchBtn").click();
            }
        });

        $('#startDate').datepicker({
            format: 'yyyy-mm-dd',
            language: 'cn',
            autoclose: true,
            todayHighlight: true
        });

        $('#endDate').datepicker({
            format: 'yyyy-mm-dd',
            language: 'cn',
            autoclose: true,
            todayHighlight: true
        });

        /**上传调研报告*/
        var surveyId = null;
        $(".uploadBtn").bind("click", function(){
            var btnId = this.id;
            surveyId = btnId.replace("uploadBtn_", "");
            $('#myFileModal').modal('show');
            uploadReport(surveyId);
        });
        /**查看调研报告*/
        $(".viewBtn").bind("click", function(){
            var btnId = this.id;
            surveyId = btnId.replace("viewBtn_", "");
            $('#viewModal').modal('show');
        });
        /**下载调研报告*/
        $("#downloadBtn").hover(function(){
            $("#downloadBtn").attr("href", $("#downloadBtn").attr("data-href") + surveyId);
        });

        /**重新上传调研报告*/
        $("#reUploadBtn").bind("click", function(){
            $('#myFileModal').modal('show');
            $('#viewModal').modal('hide');
            uploadReport(surveyId);
        });

        function uploadReport(surveyId){
            $('#multiFileUpload').fileupload({
                type: "POST",
                cache: false,
                async: false, //同步，即此代码执行时，其他的不可执行。
                dataType: "json",
                formData: {
                    surveyId :surveyId
                },
                url: 'admin/survey/upload',
                success: function (json) {
                    if (json.success) {
                        $('#myFileModal').modal('hide');
                        showMsg("成功");
                    } else {
                        showMsg(json.msg);
                    }
                }
            });
        }

    });
</script>
</body>
</html>