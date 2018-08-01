<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>问卷介绍</title>
    <jsp:include page="../../include/commonInclude.jsp"></jsp:include>
    <script src="static/js/wx/operation_guide.js"></script>
    <style type="text/css">
        a.no_join_survey {
            display: block;
            border: none;
            width: 100%;
            height: 0.82rem;
            background: #c9c9c9;
            -webkit-border-radius: 0.05rem;
            -moz-border-radius: 0.05rem;
            border-radius: 0.05rem;
            line-height: 0.82rem;
            color: #fff;
            font-size: 0.36rem;
            text-align: center;
            margin-top: 1.1rem;
            margin-bottom: 0.4rem;
        }
    </style>
</head>
<body>
<!--问卷介绍详情-->
<div class="operation_guide_detail">
    <div class="comm">
        <p>
            <!--详情图标-->
            <img src="static/images/detail_icon.png" alt="">
        </p>
        <div class="operation_guide_detail_content">
            <!--标题-->
            <h1 class="operation_guide_detail_title">
                ${catsSurveyProject.cspTitle}
            </h1>
            <div>
                <span>时长：</span> <span>${catsSurveyProject.cspDuration}</span>
            </div>
            <div style="overflow:hidden">
                <span style="float:left">有效期：</span> <span>${catsSurveyProject.cspStartDateStr}至${catsSurveyProject.cspEndDateStr}</span>
            </div>
            <div>
                <span>奖励金：</span> <span>${catsSurveyProject.cspUnitPrice} &nbsp;元</span>
            </div>
            <p class="operation_guide_detail_txt">
                ${catsSurveyProject.cspBrief}
            </p>
        </div>
        <!--参加调研-->
        <c:if test="${detail eq 1}">
            <a class="join_survey" href="javascript:void(0)" id="attend">参与调研</a>
        </c:if>
        <c:if test="${detail eq 0}">
            <a class="no_join_survey" href="javascript:void(0)">参与调研</a>
        </c:if>
    </div>
</div>
<script>
    $(function(){
        //js模板语法
        var url =`${catsSurveyProject.cspSurveyUrl}&user_id=${userId}&isSkip=Y&project_id=0&domain=cats&redirect_uri=${redirectUrl}`;
        $("#attend").attr("href",url);
    })
</script>
</body>
</html>