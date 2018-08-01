<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   	<title>微信用户列表-临床学术追踪系统系统</title>
	<jsp:include page="../include/commonInclude.jsp"></jsp:include>	
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<jsp:include page="../include/header.jsp">
			<jsp:param value="nav_user" name="navId"></jsp:param>
			<jsp:param value="nav_wechatUser" name="subNavId"></jsp:param>
		</jsp:include>
		
		<div class="content-wrapper">
			<section class="content-header">
		      <h1>微信用户列表</h1>
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
								<input type="text" class="form-control" id="nickName" name="nickName" placeholder="昵称" value="${nickName }"/>
							</div>
							<input type="text" style="display:none;"/>
							<input type="radio" name="subscribe" value="1" <c:if test="${subscribe == 1 }">checked</c:if> />关注
							<input type="radio" name="subscribe" value="0" <c:if test="${subscribe == 0 }">checked</c:if> />取关
							<button type="button" class="btn btn-success" id="searchBtn">搜索</button>
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
							<th style="color:red;">微信用户ID</th>
							<th>头像</th>
							<th>昵称</th>
							<th>openid</th>
							<th>性别</th>
							<th>国家</th>
							<th>省</th>
							<th>城市</th>
							<th>订阅时间</th>
							<th>订阅状态</th>
							<th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>操作</th>
		                </tr>
		                </thead>
		                <tbody>
		                <c:forEach items="${pageInfo.items}" var="u" varStatus="s">
     					<tr>
     						<td>${(pageInfo.rowsPerPage  * (pageInfo.nowPage -1)) + (s.index +1)  }</td>
							<td>${u.cwuId }</td>
							<td><img src="${u.cwuHeadimg }" style="width:40px; height: 40px;"></td>
							<td>${u.cwuNickname }</td>
							<td>${u.cwuOpenid }</td>
							<td>${u.cwuSex }</td>
                            <td>${u.cwuCountry }</td>
                            <td>${u.cwuProvince }</td>
							<td>${u.cwuCity }</td>
							<td>${u.cwuSubscribeTimeStr }</td>
							<td>
								<c:if test="${u.cwuSubscribe == 1 }">
								    关注
								</c:if>
								<c:if test="${u.cwuSubscribe == 0 }">
									<span style="color: red;">取关</span>
								</c:if>
							</td>
                            <td>
                                <c:if test="${u.cwuCuId != null}">
                                    <a class="btn btn-success" href="admin/user/reg/list?cuId=${u.cwuCuId}" role="button">查看关联注册用户</a>
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
					  <jsp:include page="../include/pojoPageInfo.jsp" >
							<jsp:param value="admin/user/wechat/list" name="act"/>
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
		$(document).ready(function(){	
			//检索
			$("#searchBtn").bind("click", function(){
				var form = document.forms[0];
				form.action = "admin/user/wechat/list";
				$("#page").val(1);
				form.submit();
			});
			
			//回车提交表单
			$("#nickName").keydown(function(event){
				if(	event.keyCode == 13){
					$("#searchBtn").click();
				}
			});
		});
	</script>  
</body>
</html>