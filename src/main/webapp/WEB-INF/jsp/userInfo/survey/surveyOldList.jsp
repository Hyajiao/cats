<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>问卷中心</title>
    <jsp:include page="../../include/commonInclude.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../../include/question.jsp">
    <jsp:param name="questId" value="oldList"></jsp:param>
</jsp:include>
<div class="survey_content">
    <ul>
        <c:if test="${not empty catsSurveyProjectList}">
            <c:forEach items="${catsSurveyProjectList}" var="questionList" varStatus="p">
                <li>
                    <a href="user/survey/surveyDetail?id=${questionList.cspId}">
                        <h1>${questionList.cspTitle}</h1>
                        <p>
                            <span>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</span>
                                <span>${questionList.cspType}</span>
                        </p>
                        <p>
                            <span>完成日期：</span> <span>${questionList.finishTimeStr}</span>
                        </p>
                        <div class="award clear">
                            <div class="lf">
                                <i></i>
                                <span>奖励金：</span>
                                <span>${questionList.cspUnitPrice}元</span>
                            </div>
                            <input type="hidden" value="${questionList.csfrHadIssueReward}" />
                            <c:if test="${questionList.csfrHadIssueReward eq 1}">
                                <div class="rt no-join join">【已收入】</div>
                            </c:if>
                            <c:if test="${questionList.csfrHadIssueReward eq 0}">
                                <div class="rt no-join join">【待收入】</div>
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
</body>
</html>