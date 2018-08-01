<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.kingyee.cats.enums.SysUserTypeEnum" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户列表-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_system_user" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>用户列表</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body">
                            <div class="box-header">
                                <div style="float:left;margin-right:10px">
                                    <a class="btn btn-success" href="admin/sysuser/addInit">新增</a>
                                    <button id="importBtn" class="btn btn-success">导入</button>
                                </div>
                                <form class="form-inline" name="searchForm" id="searchForm"
                                      style="margin-bottom: 15px;">
                                    <input type="hidden" id="page" name="page" value="${page }"/>
                                    <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="keyword" name="keyword"
                                               placeholder="用户名、真实姓名" value="${keyword }"/>
                                    </div>
                                    <div class="form-group">
                                        角色：
                                        <form:select id="roleId" path="user.auRole" class="form-control">
                                            <option value="">请选择</option>
                                            <form:options items="${roleList}" itemLabel="aroName" itemValue="aroId"></form:options>
                                        </form:select>
                                    </div>
                                    <input type="radio" name="state" value="1"
                                           <c:if test="${state == 1 }">checked</c:if> />正常
                                    <input type="radio" name="state" value="0"
                                           <c:if test="${state == 0 }">checked</c:if> />注销
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
                                        <th>用户名</th>
                                        <th>真实姓名</th>
                                        <th>角色</th>
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
                                            <td>${u.auUserName }</td>
                                            <td>${u.auShowName }</td>
                                            <td>${u.roleName }</td>
                                            <td>${u.auCreateDateStr }</td>
                                            <td>${u.auCreateUserName }</td>
                                            <td>
                                                <c:if test="${u.auIsValid == 1 }">
                                                    正常
                                                </c:if>
                                                <c:if test="${u.auIsValid == 0 }">
                                                    注销
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${u.auIsValid == 1 }">
                                                    <a class="btn btn-success"
                                                       href="admin/sysuser/editInit?id=${u.auId }">
                                                        修改
                                                    </a>
                                                    <a class="btn btn-danger" href="admin/sysuser/reset?id=${u.auId }">注销</a>
                                                </c:if>
                                                <c:if test="${u.auIsValid == 0 }">
                                                    <a class="btn btn-success" href="admin/sysuser/reset?id=${u.auId }">恢复</a>
                                                </c:if>
                                                <c:if test="${u.auRole == SysUserTypeEnum.ENTERPRISE_USER.ordinal()}">
                                                    <a class="btn btn-info"
                                                       href="admin/sysuser/${u.auId }/project">关联项目</a>
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
                                <jsp:param value="admin/sysuser/list" name="act"/>
                                <jsp:param value="searchForm" name="formName"/>
                            </jsp:include>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myFileModal" tabindex="-1" role="dialog" aria-labelledby="myFileModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myFileModalLabel">文件上传</h4>
            </div>
            <div class="modal-body">
                <input type="file" id="multiFileUpload" name="file" cssClass="form-control"/>
                <br/>
                <div class="col-md-12">仅支持导入<a href="static/res/expert_upload_template.xlsx">模板格式</a>的excel文件导入</div><br/>
            </div>
            <div class="modal-footer">
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
            form.action = "admin/sysuser/list";
            $("#page").val(1);
            form.submit();
        });

        //回车提交表单
        $("#keyword").keydown(function (event) {
            if (event.keyCode == 13) {
                $("#searchBtn").click();
            }
        });

        $("#importBtn").bind("click", function () {
            $('#myFileModal').modal('show');
            $('#multiFileUpload').fileupload({
                type: "POST",
                cache: false,
                async: false, //同步，即此代码执行时，其他的不可执行。
                dataType: "json",
                url: 'admin/sysuser/import',
                success: function (json) {
                    if (json.success) {
                        $('#myFileModal').modal('hide');
                        // window.location.href="admin/sysuser/list";
                        showMsg("导入成功,请刷新页面。");
                    } else {
                        showMsg(json.msg);
                    }
                }
            });
        });
    });
</script>
</body>
</html>