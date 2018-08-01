<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.kingyee.cats.common.security.AdminUserUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>导入专家信息-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_expert_group" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>${group.cegExpertGroupName } > 导入</h1>
            <ol class="breadcrumb">
                <li><a href="admin/expert/${cegId}/list"><i class="fa fa-dashboard"></i>专家组列表</a></li>
                <li class="active">导入专家信息</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">专家组信息</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form class="form-horizontal" role="form" id="groupForm" name="groupForm"
                              action="admin/expert/group/edit" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">专家组名称</label>
                                    <div class="col-sm-5">${group.cegExpertGroupName }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">医生数量</label>
                                    <div class="col-sm-5">${group.cegExpertNum }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">备注</label>
                                    <div class="col-sm-5">${group.cegMemo }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">创建时间</label>
                                    <div class="col-sm-5">${group.cegCreateTimeStr }</div>
                                </div>
                            </div>
                            <!-- /.box-body -->

                            <div class="box-footer">
                                <div class="col-xs-push-2 col-xs-2">
                                    <a class="btn btn-default" href="admin/expert/group/list" role="button">取消</a>
                                </div>
                                <div class="col-xs-push-4 col-xs-2">
                                    <button type="button" id="importBtn" class="btn btn-info pull-right">导入</button>
                                </div>
                            </div>
                        </form>
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
                <h4 class="modal-title" id="myFileModalLabel">专家导入</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12"><input type="file" id="multiFileUpload" name="file" cssClass="form-control"/></div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <div class="col-md-12">仅支持导入<a href="static/res/expert_import_template.xlsx">模板格式</a>的excel文件导入</div>
                </div>
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

        $("#importBtn").bind("click", function () {
            $('#myFileModal').modal('show');
            $('#multiFileUpload').fileupload({
                type: "POST",
                cache: false,
                async: false, //同步，即此代码执行时，其他的不可执行。
                dataType: "json",
                url: 'admin/expert/${group.cegId }/import',
                success: function (json) {
                    if (json.success) {
                        $('#myFileModal').modal('hide');
                        showMsg(json.msg);
                        location.href = GLOBAL_CONFIG.basePath + "admin/expert/${group.cegId }/list"
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

