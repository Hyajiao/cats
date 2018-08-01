<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   	<title>字典管理列表-项目发布系统</title>
	<jsp:include page="../../include/commonInclude.jsp"></jsp:include>
</head>
<body class="hold-transition skin-red sidebar-mini">
	<div class="wrapper">
		<jsp:include page="../../include/header.jsp">
			<jsp:param value="nav_dic" name="navId"></jsp:param>
		</jsp:include>
		
		<div class="content-wrapper">
			<section class="content-header">
		      <h1>字典列表</h1>
		    </section>
		    <section class="content">
		      <div class="row">
		        <div class="col-xs-12">
		          <div class="box">
           			<div class="box-body">
		            <div class="box-header">
		              <div style="float:left;margin-right:10px"><a class="btn btn-success" href="cm/dic/addInit">新增</a></div>
		              <form class="form-inline" name="searchForm" id="searchForm" style="margin-bottom: 15px;" action="cm/dic/list">
                          <input type="hidden" id="page" name="page" value="${page }"/>
						  <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
                          <div class="form-group">
                              <%--类型：<form:select id="status" path="statusHolder.value" items="${allStatus}" labelCssClass="inline"/>--%>
                              <select class="form-control" name="type">
                              	<option value="">请选择类型：</option>	
						      	<c:forEach items="${typeList}" var="s" varStatus="ss">
						      		<option value ="${s.value }" <c:if test="${s.key == type}">selected="selected"</c:if>>${s.key }</option>
								</c:forEach>	
				              </select>	
                          </div>
                          <div class="form-group">
                                <input type="text" class="form-control" id="key" name="key" placeholder="key" value="${key }"/>
                            </div>
                          <div class="form-group">
                              <input type="text" class="form-control" id="value" name="value" placeholder="value" value="${value }"/>
                          </div>
                          <div class="form-group">
                              <input type="radio" name="state" value="1" <c:if test="${state == 1 }">checked</c:if> />正常
                              <input type="radio" name="state" value="0" <c:if test="${state == 0 }">checked</c:if> />失效
                              <button type="button" class="btn btn-success" id="searchBtn">搜索</button>
                          </div>
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
							<th>字典类型</th>
							<th>说明</th>
							<th>key</th>
                            <th>value</th>
                            <th>备注</th>
                            <th>状态</th>
                            <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
		                </tr>
		                </thead>
		                <tbody>
		                <c:forEach items="${pageInfo.items}" var="u" varStatus="s">
     					<tr>
     						<td>${(pageInfo.rowsPerPage  * (pageInfo.nowPage -1)) + (s.index +1)  }</td>
     								<td>${u.cdType }</td>
     								<td>${u.cdDesc }</td>
									<td>${u.cdKey }</td>
									<td>${u.cdValue }</td>
									<td>${u.cdMemo }</td>
									<td>
										<c:if test="${u.cdIsValid == 1 }">
											正常
										</c:if>	
										<c:if test="${u.cdIsValid == 0 }">
											失效
										</c:if>
									</td>
									<td>
										<c:if test="${u.cdIsValid == 1 }">
											<a href="cm/dic/editInit?id=${u.cdId }">
												<span class="glyphicon glyphicon-pencil"></span>
											</a>|
											<a href="cm/dic/reset?id=${u.cdId }" >注销</a>																				
										</c:if>
										
										<c:if test="${u.cdIsValid == 0 }">
											<a href="cm/dic/reset?id=${u.cdId }" >恢复</a>
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
					  <jsp:include page="../../include/pojoPageInfo.jsp" >
							<jsp:param value="cm/dic/list" name="act"/>
							<jsp:param value="searchForm" name="formName"/>
					  </jsp:include> 
            	   </div>
            	   <!-- /.box-body -->
          	    </div>
          	    <!-- /.box -->
          	 </div>
    		 <!-- /.col -->
      	  </div>
          <!-- /.row -->
	    </section>
	    <!-- /.content -->		             		                		                		                
	</div>
	<!-- /.content-wrapper -->
	</div>
	<jsp:include page="../../include/adminlteJsInclude.jsp"/>
	<script type="text/javascript">
		$(document).ready(function(){	
			//检索
			$("#searchBtn").bind("click", function(){				
				$("#page").val(1);
				$("#searchForm").submit();
			});
			
			//回车提交表单
			$("#keyword").keydown(function(event){
				if(	event.keyCode == 13){
					$("#searchBtn").click();
				}
			});
		});
	</script>  
</body>
</html>