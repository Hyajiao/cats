<%@ page import="com.kingyee.common.util.PropertyConst" %>
<%@ page import="com.kingyee.cats.enums.UploadFileTypeEnum" %>
<%@ page import="com.kingyee.common.spring.mvc.WebUtil" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    String folderPath = PropertyConst.UPLOAD_PATH + UploadFileTypeEnum.SURVEY_REPORT.text();
    String path = WebUtil.getRealPath(folderPath);
    System.out.println(path);
%>
<head>
    <title>调研报告</title>
    <jsp:include page="../include/headerEnt.jsp"></jsp:include>
    <script>
        //下载报告
        function downloadBtn(spId) {
            window.location.href="admin/ent/downloadReport/"+spId;
        }
    </script>
</head>
<body>
<div>
    <jsp:include page="../include/headerTopEnt.jsp"></jsp:include>
    <div class="comm clear">
        <jsp:include page="../include/entSidebar.jsp">
            <jsp:param value="nav_survey" name="navId"></jsp:param>
        </jsp:include>
        <div class="right_content">
            <div class="content_title">
                <i class="title_icon"></i>
                ${project.cpProjectName}
            </div>
            <ul class="content_message">
                <c:if test="${surveyProjectList != null && surveyProjectList.size() > 0 }">
                    <c:forEach items="${surveyProjectList}" var="p" varStatus="s">
                        <li class="clear">
                            <h1>主题：<span>${p.cspTitle}</span></h1>
                            <p>类型：<span>${p.cspType}</span></p>
                            <p>时间：<span>${p.cspStartDateStr}  - ${p.cspEndDateStr}</span></p>
                            <p class="no-complete">
                                <c:choose>
                                    <c:when test="${p.cspSurveyReportPath == null || p.cspSurveyReportPath == '' }">
                                        【未完成】
                                    </c:when>
                                    <c:otherwise>
                                        <%--<a class="active" href="<%=path%>${fn:substring(p.cspSurveyReportPath,17,fn:length(p.cspSurveyReportPath))}">下载报告</a>--%>
                                        <a class="active" onclick="downloadBtn('${p.cspId}')"
                                           href="javascript:void(0)" id="isDownload"
                                        >下载报告</a>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </li>
                    </c:forEach>
                </c:if>
                <c:if test="${surveyProjectList == null || surveyProjectList.size() <= 0 }">
                    暂无数据！
                </c:if>
            </ul>
        </div>
    </div>
</div>
</body>
</html>