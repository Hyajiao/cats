<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.kingyee.cats.enums.AuthenticationStatusEnum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增用户-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_user" name="navId"></jsp:param>
        <jsp:param value="nav_regUser" name="subNavId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>用户信息</h1>
            <ol class="breadcrumb">
                <li><a href="admin/user/reg/list"><i class="fa fa-dashboard"></i>用户列表</a></li>
                <li class="active">用户信息</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">基本信息</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form class="form-horizontal" role="form" name="userInfoForm_add">
                            <div class="box-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">真实姓名</label>
                                    <div class="col-sm-5">${user.cuRealName }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">手机号</label>
                                    <div class="col-sm-5">${user.cuTelNo }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">省市</label>
                                    <div class="col-sm-5">${user.cuProvince }${user.cuCity }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">医院</label>
                                    <div class="col-sm-5">${user.cuHospital }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">科室</label>
                                    <div class="col-sm-5">${user.cuDept }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">职称</label>
                                    <div class="col-sm-5">${user.cuProfessional }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">新建时间</label>
                                    <div class="col-sm-5">${user.cuCreateTimeStr }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">更新时间</label>
                                    <div class="col-sm-5">${user.cuUpdateTimeStr }</div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                        </form>
                    </div>

                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">认证信息</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form class="form-horizontal" role="form">
                            <div class="box-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">执业证照片</label>
                                    <div class="col-sm-10"><img src="${user.cuCertificateImg }" style="max-width: 100%" /></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">本人头像照片</label>
                                    <div class="col-sm-10"><img src="${user.cuIdPhoto }" style="max-width: 100%"/></div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                        </form>
                    </div>

                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">银行信息</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form class="form-horizontal" role="form" action="admin/user/reg/${user.cuId }/auth" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">银行卡号</label>
                                    <div class="col-sm-5">${user.cuBankCardNo }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">银行名称</label>
                                    <div class="col-sm-5">${user.cuBankName }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">项目专员姓名</label>
                                    <div class="col-sm-5">${user.cuProjectExecutiveName }</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">项目专员手机号</label>
                                    <div class="col-sm-5">${user.cuProjectExecutiveTelNo }</div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                        </form>
                    </div>

                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">审核信息</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form id="authForm" class="form-horizontal" role="form" action="admin/user/reg/${user.cuId }/auth" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">审核状态</label>
                                    <div class="col-sm-5">
                                        <form:select id="cuIsAuthentication" path="user.cuIsAuthentication" items="${AuthenticationStatusEnum.textMap()}">
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->

                            <div class="box-footer">
                                <div class="col-xs-push-2 col-xs-2">
                                    <a class="btn btn-default" href="admin/user/reg/list" role="button">返回</a>
                                </div>
                                <div class="col-xs-push-4 col-xs-2">
                                    <button id="submitBtn" type="button" class="btn btn-info pull-right">提交</button>
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
    $(document).ready(function(){

        $("#submitBtn").bind("click", function(){
            var content = "请注意，用户的审核状态将修改为<span style='color: red'>" + $("#cuIsAuthentication option:selected").text() + "</span>";
            $("#modalContent").html(content);
            $('#sureModal').modal('show');
        });

        $("#sureBtn").bind("click", function () {
            $('#sureModal').modal('hide');
            $("#authForm").submit();
        });


    })
</script>
</body>
</html>

