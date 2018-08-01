<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   	<title>项目列表</title>
	<jsp:include page="../include/headerEnt.jsp"></jsp:include>
</head>
<body>

<div>
    <div class="comm clear">
        <!--右侧信息-->
        <div class="right_content product_list_content">
            <div class="content_title product_list_title">
                <i class="product_list"></i>
                产品列表
                <i class="border_bottom"></i>
            </div>
            <ul class="content_message product_list_message">
                <c:if test="${pageInfo.items != null && pageInfo.items.size() > 0 }">
                    <c:forEach items="${pageInfo.items}" var="p" varStatus="s">
                        <li>
                            <h1>${p[1].cpProjectName}</h1>
                            <p>${p[1].cpProjectMemo}</p>
                            <p>开始时间：<span>${p[1].cpStartTimeStr}</span></p>
                            <p>
                                结束时间：<span>${p[1].cpEndTimeStr}</span>
                                <a href="javascript:void(0);" class="active" onclick="window.location.href='admin/ent/abstractInfo/${p[1].cpId}'">进入</a>
                            </p>
                        </li>
                    </c:forEach>
                </c:if>
                <c:if test="${pageInfo.items == null || pageInfo.items.size() <= 0 }">
                    暂无数据！
                </c:if>
            </ul>
        </div>
    </div>
</div>












<%--

<h1>产品列表</h1>
<form class="form-inline" name="searchForm" id="searchForm" style="margin-bottom: 15px;">
    <input type="hidden" id="page" name="page"/>
    <input type="hidden" id="rowsPerPage" name="rowsPerPage" value="${rowsPerPage }"/>
</form>
<c:if test="${pageInfo.items != null && pageInfo.items.size() > 0 }">
    <c:forEach items="${pageInfo.items}" var="p" varStatus="s">
        项目名称：${p[1].cpProjectName}
        简介：${p[1].cpProjectMemo}
        开始时间：${p[1].cpStartTimeStr}
        结束时间：${p[1].cpEndTimeStr}
        <a class="btn btn-success" href="admin/ent/abstractInfo/${p[1].cpId}" role="button">进入</a>
        <br/>
    </c:forEach>
</c:if>
<c:if test="${pageInfo.items == null || pageInfo.items.size() <= 0 }">
    暂无数据！
</c:if>--%>
<%--<jsp:include page="../include/pojoPageInfo.jsp" >
    <jsp:param value="admin/user/reg/list" name="act"/>
    <jsp:param value="searchForm" name="formName"/>
</jsp:include>--%>
</body>
</html>