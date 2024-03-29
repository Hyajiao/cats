<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<input type="hidden" name="auId" id="id" value="${user.auId }"/>
<div class="box-body">
    <div class="form-group">
        <label for="auUserName" class="col-sm-2 control-label">用户名</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="auUserName" name="auUserName" value="${user.auUserName }"
                   placeholder="用户名"
            <c:if test='${user.auId != null}'>
                   readonly
            </c:if>
            >
        </div>
    </div>
    <div class="form-group">
        <label for="auPassword" class="col-sm-2 control-label">密码</label>
        <div class="col-sm-5">
            <input type="password" class="form-control" id="auPassword" name="auPassword" value=""${user.auPassword }
                   placeholder="企业账号的密码必须包含字母和数字，并且不低于8位" autocomplete="off">
        </div>
    </div>
    <div class="form-group">
        <label for="surePass" class="col-sm-2 control-label">确认密码</label>
        <div class="col-sm-5">
            <input type="password" class="form-control" id="surePass" placeholder="确认密码" autocomplete="off">
        </div>
    </div>
    <div class="form-group">
        <label for="auShowName" class="col-sm-2 control-label">姓名</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="auShowName" name="auShowName" value="${user.auShowName }"
                   placeholder="姓名">
        </div>
    </div>
    <div class="form-group">
        <label for="auTel" class="col-sm-2 control-label">手机号</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="auTel" name="auTel" value="${user.auTel }"
                   placeholder="手机号">
        </div>
    </div>
    <div class="form-group">
        <label for="auEmail" class="col-sm-2 control-label">邮箱</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="auEmail" name="auEmail" value="${user.auEmail }"
                   placeholder="邮箱">
        </div>
    </div>
    <div class="form-group">
        <label for="auRole" class="col-sm-2 control-label">角色</label>
        <div class="col-sm-5">
            <form:select id="auRole" path="user.auRole" items="${roleList}" itemLabel="aroName" itemValue="aroId" class="form-control" >
            </form:select>
        </div>
    </div>
</div>
<!-- /.box-body -->

<div class="box-footer">
    <div class="col-xs-push-2 col-xs-2">
        <a class="btn btn-default" href="admin/sysuser/list" role="button">取消</a>
    </div>
    <div class="col-xs-push-4 col-xs-2">
        <button type="submit" class="btn btn-info pull-right">保存</button>
    </div>
</div>
		           
       				
       		
       		

