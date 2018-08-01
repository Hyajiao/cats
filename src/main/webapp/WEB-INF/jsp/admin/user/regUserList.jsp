<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kingyee.cats.enums.UserTypeEnum" %>
<%@ page import="com.kingyee.cats.enums.YesNoEnum" %>
<%@ page import="com.kingyee.cats.enums.AuthenticationStatusEnum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   	<title>注册绑定用户列表-临床学术追踪系统系统</title>
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
		      <h1>注册绑定用户列表</h1>
		    </section>
		    <section class="content">
		      <div class="row">
		        <div class="col-xs-12">
		          <div class="box">
           			<div class="box-body">
		            <div class="box-header">
		              <form class="form-inline" name="searchForm" id="searchForm" style="margin-bottom: 15px;">
							<input type="hidden" id="page" name="page"/>
							<input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
							<div class="form-group">
                                <label for="userType" class="control-label">用户类型</label>
                                <select id="userType" name="userType" class="form-control">
                                    <option value="">全部</option>
                                    <option <c:if test="${userType == UserTypeEnum.SPEAKER.text()}" >selected</c:if> value="${UserTypeEnum.SPEAKER.text()}">讲者</option>
                                    <option <c:if test="${userType == UserTypeEnum.LISTENER.text()}" >selected</c:if> value="${UserTypeEnum.LISTENER.text()}">听者</option>
                                </select>
                                <label for="realName" class="control-label">真实姓名</label>
								<input class="form-control" id="realName" name="realName" placeholder="真实姓名" value="${realName }"/>
                                <label for="cuMedliveId" class="control-label">医脉通ID</label>
                                <input class="form-control" id="cuMedliveId" name="cuMedliveId" placeholder="医脉通ID" value="${cuMedliveId }"/>
							</div>
                            <div class="form-group">
                                注册时间：
                                <div class="form-group">
                                    <div class="input-group date" style="width:40%;">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" class="form-control pull-right searchDate" id="datepicker"
                                               name="startDate" value="${startDate}" readonly="true"/>
                                    </div>
                                    &nbsp;~&nbsp;
                                    <div class="input-group date" style="width:40%;">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" class="form-control pull-right searchDate" id="datepicker1"
                                               name="endDate" value="${endDate}" readonly="true"/>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-success" id="searchBtn">搜索</button>
                                <button type="button" class="btn btn-success" id="exportBtn">导出</button>
                            </div>
					  </form>

		            </div>
		            </div>
		            <!-- /.box-header -->
		            <div class="box-body">
		            <c:if test="${pageInfo.items != null && pageInfo.items.size() > 0 }">
		              <table class="table table-bordered table-hover">
		                <thead>
		                <tr>
		                  	<th>用户id</th>
							<th>医脉通id</th>
							<th>真实姓名</th>
							<th>省市</th>
							<th>医院</th>
							<th>科室</th>
							<th>职称</th>
                            <th>手机号</th>
		                </tr>
		                </thead>
		                <tbody>
		                <c:forEach items="${pageInfo.items}" var="u" varStatus="s">
     					<tr>
     						<td>${u.cuId }</td>
							<td>${u.cuMedliveId }</td>
							<td>${u.cuRealName }</td>
							<td>${u.cuProvince }${u.cuCity }</td>
							<td>${u.cuHospital }</td>
                            <td>${u.cuDept }</td>
                            <td>${u.cuProfessional }</td>
                            <td>${u.cuTelNo }</td>
						</tr>
						</c:forEach>
		               	</tbody>		                		                
              		  </table>
              		  </c:if>
					  <c:if test="${pageInfo.items == null || pageInfo.items.size() <= 0 }">
							暂无数据！
					  </c:if>
					  <jsp:include page="../include/pojoPageInfo.jsp" >
							<jsp:param value="admin/user/reg/list" name="act"/>
							<jsp:param value="searchForm" name="formName"/>
					  </jsp:include> 
            	   </div>
          	    </div>
          	 </div>
      	  </div>
	    </section>
	</div>
	</div>
	<jsp:include page="../include/adminlteJsInclude.jsp"/>
	<script type="text/javascript">
        //回车提交表单
        $("#cuMedliveId").keydown(function(event){
            if(	event.keyCode == 13){
                $("#searchBtn").click();
            }
        });
        $("#realName").keydown(function(event){
            if(	event.keyCode == 13){
                $("#searchBtn").click();
            }
        });
        //日期控件
        $(".searchDate").datepicker({
            format: 'yyyy-mm-dd',
            language: 'cn',
            orientation: "top left",
            autoclose: true,
            todayHighlight: true
        });
        $(".searchDateTime").datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: 'zh-cn',
            sideBySide: true,
            tooltips: {
                incrementHour: '增加小时',
                decrementHour: '减少小时',
                incrementMinute: '增加分钟',
                decrementMinute: '减少分钟',
                pickHour: '选择小时',
                pickMinute: '选择分钟'
            }
        });
		$("#searchBtn").click(function(){
		    console.log($("#searchForm"));
		    $("#searchForm")[0].action = "admin/user/reg/list";
            $("#searchForm").submit();
		});

        $("#exportBtn").click(function(){
            $("#searchForm")[0].action = "admin/user/exportRegList";
            $("#searchForm").submit();
        });
	</script>  
</body>
</html>