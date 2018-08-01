<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>问卷中心</title>
    <jsp:include page="../../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../../include/question.jsp">
    <jsp:param name="questId" value="newList"></jsp:param>
</jsp:include>
<div class="survey_content">
    <ul>
     <c:if test="${not empty catsSurveyProjectList}">
        <c:forEach items="${catsSurveyProjectList}" var="projectList" varStatus="q">
            <li>
                <a href="user/survey/surveyDetail?id=${projectList.cspId}">
                    <h1>${projectList.cspTitle}</h1>
                    <p>
                        <span>类&nbsp;&nbsp;&nbsp;&nbsp;型：</span>
                            <span>${projectList.cspType}</span>
                    </p>
                    <p>
                        <span>时&nbsp;&nbsp;&nbsp;&nbsp;长：</span> <span>${projectList.cspDuration}</span>
                    </p>
                    <p>
                        <span>有效期：</span> <span>${projectList.cspStartDateStr}至${projectList.cspEndDateStr}</span>
                    </p>
                    <div class="award clear">
                        <div class="lf">
                            <i></i>
                            <span>奖励金：</span>
                            <span>${projectList.cspUnitPrice}元</span>
                        </div>
                        <c:if test="${projectList.finished==0}">
                        <div class="rt no-join">【待参与】</div>
                        </c:if>
                        <c:if test="${projectList.finished==1}">
                        <div class="rt no-join">【已参与】</div>
                        </c:if>
                    </div>
                </a>
            </li>
        </c:forEach>
        </c:if>
        <c:if test="${empty catsSurveyProjectList}">
            <li>
                <span>暂无数据！</span>
            </li>
        </c:if>
    </ul>
</div>


<script>
    $(document).ready(function () {
        $('.survey_chose a').each(function () {
            if ($($(this))[0].href == String(window.location))
                $(this).addClass('surveyActive').attr('href', 'javascript:void(0);');
        });
    })
</script>
</body>
</html>
