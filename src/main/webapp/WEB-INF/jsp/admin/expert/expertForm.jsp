<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="box box-info">
    <div class="box-header with-border">
        <h3 class="box-title">基本信息</h3>
    </div>
    <input type="hidden" name="cegdId" id="cegdId" value="${expert.cegdId }"/>
    <input type="hidden" name="cegdCegId" id="cegdCegId" value="${cegId }"/>
    <div class="box-body">
        <div class="form-group">
            <label for="cegdMedliveId" class="col-sm-2 control-label">医脉通ID</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdMedliveId" name="cegdMedliveId" value="${expert.cegdMedliveId }"
                       placeholder="医脉通ID">
            </div>
        </div>
        <div class="form-group">
            <label for="cegdRealName" class="col-sm-2 control-label">姓名</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdRealName" name="cegdRealName" value="${expert.cegdRealName }"
                       placeholder="姓名">
            </div>
        </div>
        <div class="form-group">
            <label for="cegdSex" class="col-sm-2 control-label">性别</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdSex" name="cegdSex" value="${expert.cegdSex }"
                       placeholder="性别">
            </div>
        </div>
        <div class="form-group">
            <label for="cegdHospital" class="col-sm-2 control-label">医院</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdHospital" name="cegdHospital" value="${expert.cegdHospital }"
                       placeholder="医院">
            </div>
        </div>
        <div class="form-group">
            <label for="cegdDept" class="col-sm-2 control-label">科室</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdDept" name="cegdDept" value="${expert.cegdDept }"
                       placeholder="科室">
            </div>
        </div>
        <div class="form-group">
            <label for="cegdProfessional" class="col-sm-2 control-label">职称</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdProfessional" name="cegdProfessional" value="${expert.cegdProfessional }"
                       placeholder="职称">
            </div>
        </div>
    </div>
    <!-- /.box-body -->
</div>

<div class="box box-info">
    <div class="box-header with-border">
        <h3 class="box-title">联系方式</h3>
    </div>
    <div class="box-body">
        <div class="form-group">
            <label for="cegdTelNo" class="col-sm-2 control-label">手机</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdTelNo" name="cegdTelNo" value="${expert.cegdTelNo }"
                       placeholder="手机">
            </div>
        </div>
        <div class="form-group">
            <label for="cegdEmail" class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdEmail" name="cegdEmail" value="${expert.cegdEmail }"
                       placeholder="邮箱">
            </div>
        </div>
    </div>
    <!-- /.box-body -->
</div>


<div class="box box-info">
    <div class="box-header with-border">
        <h3 class="box-title">银行卡信息</h3>
    </div>
    <div class="box-body">
        <div class="form-group">
            <label for="cegdBankRealName" class="col-sm-2 control-label">姓名</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdBankRealName" name="cegdBankRealName" value="${expert.cegdBankRealName }"
                       placeholder="姓名">
            </div>
        </div>
        <div class="form-group">
            <label for="cegdBankName" class="col-sm-2 control-label">开户行</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdBankName" name="cegdBankName" value="${expert.cegdBankName }"
                       placeholder="开户行">
            </div>
        </div>
        <div class="form-group">
            <label for="cegdBankNo" class="col-sm-2 control-label">账号</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="cegdBankNo" name="cegdBankNo" value="${expert.cegdBankNo }"
                       placeholder="账号">
            </div>
        </div>
    </div>
    <!-- /.box-body -->

    <div class="box-footer">
        <div class="col-xs-push-2 col-xs-2">
            <a class="btn btn-default" href="admin/expert/${cegId}/list" role="button">取消</a>
        </div>
        <div class="col-xs-push-4 col-xs-2">
            <button type="submit" class="btn btn-info pull-right">保存</button>
        </div>
    </div>
</div>
       				
       		
       		

