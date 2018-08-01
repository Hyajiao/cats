<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>财务列表-临床学术追踪系统</title>
    <jsp:include page="../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../include/header.jsp">
        <jsp:param value="nav_finance" name="navId"></jsp:param>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>财务列表</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body">
                            <div class="box-header">
                                <form class="form-inline" name="searchForm" id="searchForm" style="margin-bottom: 15px;">
                                    <input type="hidden" id="page" name="page" value="${page }"/>
                                    <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="keyword" name="keyword"
                                               placeholder="收款人" value="${keyword }"/>
                                        &nbsp;&nbsp;&nbsp;
                                        打款状态：
                                        <select id="state" name="state" class="form-control">
                                            <option <c:if test="${state == null}">selected</c:if> value="">全部</option>
                                            <option <c:if test="${state == 0}">selected</c:if> value="0">未打款</option>
                                            <option <c:if test="${state == 1}">selected</c:if> value="1">已打款</option>
                                            <option <c:if test="${state == 2}">selected</c:if> value="2">已退回</option>
                                        </select>
                                        提现方式：
                                        <select id="txState" name="txState" class="form-control">
                                            <option <c:if test="${txState == null}">selected</c:if> value="">全部</option>
                                            <option <c:if test="${txState == 0}">selected</c:if> value="0">支付宝</option>
                                            <option <c:if test="${txState == 1}">selected</c:if> value="1">银行卡</option>
                                        </select>
                                        &nbsp;&nbsp;&nbsp;
                                        申请时间：
                                        <input type="text" class="form-control" id="startDate" name="startDate"
                                               placeholder="开始时间" value="${startDate }"/>
                                        ~
                                        <input type="text" class="form-control" id="endDate" name="endDate"
                                               placeholder="截止时间" value="${endDate }"/>
                                    </div>
                                    &nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-success" id="searchBtn">搜索</button>
                                    <button type="button" class="btn btn-success" id="exportBtn" style="margin-left:10%;">导出</button>
                                </form>
                            </div>
                            <div class="box-header">
                                已选金额：<span id="selectMoney"></span>
                                <button type="button" class="btn btn-success" id="updateBatch">批量打款</button>
                            </div>
                        </div>
                        <div class="box-body">
                            <c:if test="${pageInfo.items != null && pageInfo.items.size() > 0 }">
                                <table id="example2" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" id="allCk" /></th>
                                        <th>序号</th>
                                        <th>申请人</th>
                                        <th>收款人</th>
                                        <th>提现方式</th>
                                        <th>提现金额</th>
                                        <th>余额</th>
                                        <th>冻结金额</th>
                                        <th>申请时间</th>
                                        <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${pageInfo.items}" var="u" varStatus="s">
                                        <tr id="errorId_${u[0].cwrId}">
                                            <td>
                                                <c:if test="${u[0].cwrStatus == null || u[0].cwrStatus == 0}">
                                                    <input type="checkbox" name="ck" id="${u[0].cwrId}" class="${u[0].cwrWithdrawMoney}"/>
                                                </c:if>
                                            </td>
                                            <td>${(pageInfo.rowsPerPage  * (pageInfo.nowPage -1)) + (s.index +1)  }</td>
                                            <td>${u[1].cuRealName }</td>
                                            <td>${u[0].cwrRealName }</td>
                                            <td>${u[0].tixianState }</td>
                                            <td>${u[0].cwrWithdrawMoney }</td>
                                            <td>${u[1].cuBalance}</td>
                                            <td>${u[1].cuLockMoney}</td>
                                            <td>${u[0].cwrWithdrawTimeStr}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${u[0].cwrStatus == null || u[0].cwrStatus == 0}">
                                                        <a class="btn btn-success" href="javascript:void(0)" onclick="dakuan(${u[0].cwrId})" id="a_${u[0].cwrId}">打款</a>
                                                        <span id="fengexian_${u[0].cwrId}"> | </span>
                                                        <a class="btn btn-success" href="javascript:void(0)" onclick="backMoney(${u[0].cwrId})" id="back_${u[0].cwrId}">退回余额</a>
                                                    </c:when>
                                                    <c:when test="${u[0].cwrStatus != null && u[0].cwrStatus == 1}">
                                                        已打款
                                                    </c:when>
                                                    <c:otherwise>
                                                        已退回
                                                    </c:otherwise>
                                                </c:choose>
                                                |
                                                <a class="btn btn-info" href="admin/finance/detailList?userId=${u[0].cwrCuId}">查看</a>
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
                                <jsp:param value="admin/finance/list" name="act"/>
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">确认</h4>
            </div>
            <div class="modal-body">确认要打款么？</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sureBtn">确定</button>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="delType"/>


<!-- Modal -->
<div class="modal fade" id="errorIdsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm" style="width:40%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="errorIdsModalLabel">提示</h4>
            </div>
            <div class="modal-body">批量打款失败，以下记录<span style="color:red;">提现金额</span>和<span style="color:red;">冻结金额</span>比对失败</div>
            <div class="modal-body">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>申请人</th>
                        <th>收款人</th>
                        <th>提现方式</th>
                        <th>提现金额</th>
                        <th>余额</th>
                        <th>冻结金额</th>
                        <th>申请时间</th>
                    </tr>
                    </thead>
                    <tbody id="errorList">

                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="daochuModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">确认</h4>
            </div>
            <div class="modal-body">确认要导出所勾选的记录么？</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sureDaochuBtn">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="backMoneyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">确认要将这条提现退回到余额吗？</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sureBackMoneyBtn">确定</button>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="backMoneyType"/>

<jsp:include page="../include/adminlteJsInclude.jsp"/>
<script type="text/javascript" src="static/lib/common.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //检索
        $("#searchBtn").bind("click", function () {
            var form = document.forms[0];
            form.action = "admin/finance/list";
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
        var money = 0.0;
        var ids = [];
        //全选、全不选
        $("#allCk").bind("change", function(){
            money = null;
            $("#selectMoney").empty();
            var checkbox = document.getElementById('allCk');
            if(checkbox.checked){
                $("input[name='ck']").each(function(){
                    $(this)[0].checked = true;
                    var thisMoney = $(this).attr("class");
                    thisMoney = parseFloat(thisMoney);
                    money = money +thisMoney;
                    $("#selectMoney").html(money);
                    ids.push( $(this)[0].id);
                });
            }else{
                ids = [];
                $("input[name='ck']").each(function(){
                    $(this)[0].checked = false;
                });
            }
        });

        $("input[name='ck']").click(function(){
            var thisMoney = $(this).attr("class");
            thisMoney = parseFloat(thisMoney);
            var id = $(this)[0].id;
            if($(this)[0].checked){
                money = money + thisMoney;
                ids.push(id);
            }else{
                money = money - thisMoney;
                var index = ids.indexOf(id)
                ids.splice(index,1);
            }
            $("#selectMoney").empty();
            $("#selectMoney").html(money);
        });

        $("#updateBatch").bind("click", function(){
            if(ids.length > 0){
                $("#delType").val("multi");
                $('#myModal').modal('show');
            }else{
                alert("请选择要打款的记录");
            }
            console.log(ids);
        });

        $("#sureBtn").bind("click", function(){
            if($("#delType").val() != "multi"){
                // 单删
                var id=$("#delType").val();
                var url = "admin/finance/updateWithDraw";
                $.ajax({
                    type: "get",
                    url: url,
                    data: {"wdId":id},
                    dataType: "json",
                    success: function (json) {
                        if (json.success) {
                            showMsg("打款成功！");
                            var form = document.forms[0];
                            form.action = "admin/finance/list";
                            form.submit();
                        } else {
                            $('#myModal').modal('hide');
                            updateList(json);
                        }
                    }
                });
            }else if($("#delType").val() == "multi"){
                var url = "admin/finance/updateBatch?ids="+ids;
                $.ajax({
                    type: "get",
                    url: url,
                    dataType: "json",
                    success: function (json) {
                        if (json.success) {
                            showMsg("打款成功！");
                            var form = document.forms[0];
                            form.action = "admin/finance/list";
                            form.submit();
                        } else {
                            $('#myModal').modal('hide');
                            updateList(json);
                        }
                    }
                });
            }
        });

        //导出
        $("#exportBtn").click(function () {
            if(ids && ids.length>0){
                $('#daochuModal').modal('show');
            }else{
                if(!$("#txState").val()){
                    alert("请选择提现方式。");
                }else{
                     window.location.href="admin/finance/exportFinanceList?keyword="+$("#keyword").val()+"&state=" +$("#state").val()+"&txState="+$("#txState").val()+
                         "&startDate="+($("#startDate").val() || "")+"&endDate="+($("#endDate").val() || "");
                }
            }
        });
        $("#sureDaochuBtn").bind("click", function(){
            if(ids && ids.length>0){
                window.location.href="admin/finance/exportFinanceList?keyword="+$("#keyword").val()+"&state=" +$("#state").val()+"&txState="+$("#txState").val()+
                    "&startDate="+($("#startDate").val() || "")+"&endDate="+($("#endDate").val() || "")+"&ids="+ids;
            }
        });
    });

    // 单独删除
    function dakuan(id){
        $("#delType").val(id);
        $('#myModal').modal('show');
    }

    //更新状态
    function updateList(json){
        $('#myModal').modal('hide');
        var errorIds = json.msg;
        if(errorIds){
            var html = "";
            if(errorIds.indexOf(",")>=0){
                var idArray = errorIds.split(",");
                for(var i=0; i<idArray.length; i++){
                    if(idArray[i]){
                        html = html+"<tr>"+$("#errorId_"+idArray[i]).html()+"</tr>";
                    }
                }
            }else{
                html = "<tr>"+$("#errorId_"+errorIds).html()+"</tr>";
            }
            $('#errorIdsModal').modal('show');
            $('#errorList').empty();
            $('#errorList').append(html);

            $("#errorList").find("tr").each(function(){
                var tdArr = $(this).children();
                var td9 = tdArr.eq(9);//操作栏
                $(td9).remove();
                var td0 = tdArr.eq(0);//复选框
                $(td0).remove();
            });
        }
    }

    //回退余额
    function backMoney(id){
        $("#backMoneyType").val(id);
        $('#backMoneyModal').modal('show');
    }

    $("#sureBackMoneyBtn").bind("click", function(){
        var id = $("#backMoneyType").val();
        if(id){
            var url = "admin/finance/backMoney?wdId="+id;
            $.ajax({
                type: "get",
                url: url,
                dataType: "json",
                success: function (json) {
                    if (json.success) {
                        showMsg("退回成功！");
                        var form = document.forms[0];
                        form.action = "admin/finance/list";
                        form.submit();
                    }else{
                        $('#backMoneyModal').modal('hide');
                        updateList(json);
                    }
                }
            });
        }
    });
</script>
</body>
</html>