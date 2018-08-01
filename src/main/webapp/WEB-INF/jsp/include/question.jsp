<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String questId = request.getParameter("questId");
%>
<div class="survey_chose">
      <a href="user/survey/surveyNew" id="newList">可参与调研</a>
      <a href="user/survey/surveyOld" id="oldList">历史调研</a>
</div>
<script>
    var questId = "<%=questId%>";
    //选中状态
    $("#"+questId).addClass("surveyActive");
    $("#"+questId).parent().siblings().children().removeClass("surveyActive");
</script>