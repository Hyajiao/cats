<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<input type="hidden" name="cspId" id="id" value="${survey.cspId }"/>
<div class="box-body">
    <div class="form-group">
        <label for="cspTitle" class="col-sm-2 control-label">主题</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cspTitle" name="cspTitle" value="${survey.cspTitle }"
                   placeholder="主题">
        </div>
    </div>
    <div class="form-group">
        <label for="cspType" class="col-sm-2 control-label">问卷类型</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cspType" name="cspType" value="${survey.cspType }"
                   placeholder="问卷类型">
        </div>
    </div>
    <div class="form-group">
        <label for="cspNum" class="col-sm-2 control-label">问卷数量</label>
        <div class="col-sm-5">
            <input type="number" class="form-control" id="cspNum" name="cspNum" value="${survey.cspNum }"
                   placeholder="问卷数量">
        </div>
    </div>
    <div class="form-group">
        <label for="cspUnitPrice" class="col-sm-2 control-label">奖励金</label>
        <div class="col-sm-5">
            <input type="number" class="form-control" id="cspUnitPrice" name="cspUnitPrice" value="${survey.cspUnitPriceInt }"
                   placeholder="奖励金">
        </div>
        <label for="cspUnitPrice" class="control-label">元</label>
    </div>
    <div class="form-group">
        <label for="cspDuration" class="col-sm-2 control-label">时长</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cspDuration" name="cspDuration" value="${survey.cspDuration }"
                   placeholder="时长">
        </div>
    </div>
    <div class="form-group">
        <label for="cspStartDateStr" class="col-sm-2 control-label">开始时间</label>
        <div class="col-sm-5">
            <input type="text" class="form-control " id="cspStartDateStr" name="cspStartDateStr" value="${survey.cspStartDateStr }"
                   placeholder="开始时间">
        </div>
    </div>
    <div class="form-group">
        <label for="cspEndDateStr" class="col-sm-2 control-label">截止时间</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cspEndDateStr" name="cspEndDateStr" value="${survey.cspEndDateStr }"
                   placeholder="截止时间">
        </div>
    </div>
    <div class="form-group">
        <label for="cspCegId" class="col-sm-2 control-label">关联医生</label>
        <div class="col-sm-5">
            <form:select id="cspCegId" path="survey.cspCegId" items="${expertGroupList}" itemLabel="cegExpertGroupName" itemValue="cegId" class="form-control" >
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label for="cspCpId" class="col-sm-2 control-label">关联项目</label>
        <div class="col-sm-5">
            <form:select id="cspCpId" path="survey.cspCpId" items="${projectList}" itemLabel="cpProjectName" itemValue="cpId" class="form-control" >
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label for="cspSurveyUrl" class="col-sm-2 control-label">关联问卷</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="cspSurveyUrl" name="cspSurveyUrl" value="${survey.cspSurveyUrl }"
                   placeholder="关联问卷">
        </div>
    </div>
    <div class="form-group">
        <label for="cspMemo" class="col-sm-2 control-label">备注</label>
        <div class="col-sm-5">
            <textarea class="form-control" id="cspMemo" name="cspMemo" placeholder="备注">${survey.cspMemo }</textarea>
        </div>
    </div>

    <div class="form-group">
        <label for="cspBrief" class="col-sm-2 control-label">简介</label>
        <div class="col-sm-7">
            <input type="hidden" id="cspBrief" name="cspBrief" value="${survey.cspBrief}"/>
            <!-- 加载编辑器的容器 -->
            <script id="container" name="content" type="text/plain" ></script>
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