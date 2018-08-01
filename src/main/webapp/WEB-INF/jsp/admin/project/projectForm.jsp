<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<input type="hidden" name="cpId" id="id" value="${project.cpId }"/>
<div class="box-body">
    <div class="form-group">
        <label for="cpProjectName" class="col-sm-2 control-label">项目名称</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cpProjectName" name="cpProjectName" value="${project.cpProjectName }"
                   placeholder="项目名称">
        </div>
    </div>
    <div class="form-group">
        <label for="cpCompanyName" class="col-sm-2 control-label">药企名称</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cpCompanyName" name="cpCompanyName" value="${project.cpCompanyName }"
                   placeholder="药企名称">
        </div>
    </div>
    <div class="form-group">
        <label for="cpProjectMemo" class="col-sm-2 control-label">项目简介</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cpProjectMemo" name="cpProjectMemo" value="${project.cpProjectMemo }"
                   placeholder="项目简介">
        </div>
    </div>
    <div class="form-group">
        <label for="cpStartTimeStr" class="col-sm-2 control-label">开始时间</label>
        <div class="col-sm-5">
            <input type="text" class="form-control " id="cpStartTimeStr" name="cpStartTimeStr" value="${project.cpStartTimeStr }"
                   placeholder="开始时间">
        </div>
    </div>
    <div class="form-group">
        <label for="cpEndTimeStr" class="col-sm-2 control-label">结束时间</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cpEndTimeStr" name="cpEndTimeStr" value="${project.cpEndTimeStr }"
                   placeholder="结束时间">
        </div>
    </div>

</div>
<!-- /.box-body -->

<div class="box-footer">
    <div class="col-xs-push-2 col-xs-2">
        <a class="btn btn-default" href="admin/project/list" role="button">取消</a>
    </div>
    <div class="col-xs-push-4 col-xs-2">
        <button type="submit" class="btn btn-info pull-right">保存</button>
    </div>
</div>
		           
       				
       		
       		

