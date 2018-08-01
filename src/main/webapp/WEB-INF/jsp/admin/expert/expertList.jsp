<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>专家列表-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_expert_group" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>专家列表</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body">
                            <div class="box-header">
                                <div style="float:left;margin-right:10px">
                                    <a class="btn btn-success" href="admin/expert/addInit/${cegId}">新增</a>
                                </div>
                                <form class="form-inline" name="searchForm" id="searchForm"
                                      style="margin-bottom: 15px;">
                                    <input type="hidden" id="page" name="page" value="${page }"/>
                                    <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="expertName" name="expertName"
                                               placeholder="专家名" value="${expertName }"/>
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
                                        <th>id</th>
                                        <th>姓名</th>
                                        <th>医院</th>
                                        <th>科室</th>
                                        <th>职称</th>
                                        <th>手机</th>
                                        <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${pageInfo.items}" var="u" varStatus="s">
                                        <tr>
                                            <td>${(pageInfo.rowsPerPage  * (pageInfo.nowPage -1)) + (s.index +1)  }</td>
                                            <td>${u.cegdId }</td>
                                            <td>${u.cegdRealName }</td>
                                            <td>${u.cegdHospital }</td>
                                            <td>${u.cegdDept }</td>
                                            <td>${u.cegdProfessional }</td>
                                            <td>${u.cegdTelNo }</td>
                                            <td>
                                                <a class="btn btn-success" href="admin/expert/editInit/${cegId}?id=${u.cegdId }">
                                                    修改
                                                </a>
                                                <button id="delBtn_${u.cegdId }" class="btn btn-danger delBtn">删除</button>
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
                                <jsp:param value="admin/expert/${cegId}/list" name="act"/>
                                <jsp:param value="searchForm" name="formName"/>
                            </jsp:include>
                            <input type="hidden" id="cegId" value="${cegId}"/>
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
<script type="text/javascript">
    $(document).ready(function () {
        //检索
        $("#searchBtn").bind("click", function () {
            var form = document.forms[0];
            form.action = "admin/expert/"+$("#cegId").val()+"list";
            $("#page").val(1);
            form.submit();
        });

        //回车提交表单
        $("#keyword").keydown(function (event) {
            if (event.keyCode == 13) {
                $("#searchBtn").click();
            }
        });

        // 删除按钮被点击
        var cplcId = null;
        $(".delBtn").bind("click", function(){
            var btnId = this.id;
            cplcId = btnId.replace("delBtn_", "");
            var content = "请注意，将删除此专家，id=<span style='color: red'>" + cplcId + "，不可恢复！</span>";
            $("#modalContent").html(content);
            $('#sureModal').modal('show');
        });

        $("#sureBtn").bind("click", function () {
            $('#sureModal').modal('hide');
            location.href = GLOBAL_CONFIG.basePath + "admin/expert/" + cplcId + "/del";
        });

    });
</script>
</body>
</html>