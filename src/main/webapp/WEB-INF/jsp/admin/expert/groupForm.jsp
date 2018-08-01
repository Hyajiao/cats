<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<input type="hidden" name="cegId" id="cegId" value="${group.cegId }"/>
<div class="box-body">
    <div class="form-group">
        <label for="cegExpertGroupName" class="col-sm-2 control-label">专家组名称</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cegExpertGroupName" name="cegExpertGroupName" value="${group.cegExpertGroupName }"
                   placeholder="专家组名称">
        </div>
    </div>
    <div class="form-group">
        <label for="cegExpertNum" class="col-sm-2 control-label">医生数量</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cegExpertNum" name="cegExpertNum" value="${group.cegExpertNum }"
                   placeholder="医生数量">
        </div>
    </div>
    <div class="form-group">
        <label for="cegMemo" class="col-sm-2 control-label">备注</label>
        <div class="col-sm-5">
            <textarea class="form-control" id="cegMemo" name="cegMemo" placeholder="项目简介" rows="4">${group.cegMemo }</textarea>
        </div>
    </div>
</div>
<!-- /.box-body -->

<div class="box-footer">
    <div class="col-xs-push-2 col-xs-2">
        <a class="btn btn-default" href="admin/expert/group/list" role="button">取消</a>
    </div>
    <div class="col-xs-push-4 col-xs-2">
        <button type="submit" class="btn btn-info pull-right">保存</button>
    </div>
</div>


<script>
    $(document).ready(function(){
        initValidation();
    });
    //校验
    function initValidation(){
        var validations = [
            {id: 'cegExpertGroupName', valid: "validate[required]"},
            {id: 'cegExpertNum', valid: "validate[required,custom[integer]]"}
        ];
        for(var i=0,len = validations.length; i<len;i++ ){
            $("#"+ validations[i].id).addClass(validations[i].valid);
        }
        $("#groupForm").validationEngine('attach', {promptPosition : "topLeft", scroll: true});
    }
</script>
       				
       		
       		

