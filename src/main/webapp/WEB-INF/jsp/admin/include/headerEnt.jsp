<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">--%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="stylesheet" href="static/ent/css/base.css">
<link rel="stylesheet" href="static/ent/css/index.css">
<script src="static/lib/jquery/jquery-3.0.0.min.js"></script>
<script src="static/ent/lib/product_overview.js"></script>

<!-- form表单验证 -->
<link href="static/lib/jQuery-Validation-Engine-2.6.4/css/validationEngine.jquery.css" rel="stylesheet">
<script type="text/javascript" src="static/lib/jQuery-Validation-Engine-2.6.4/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="static/lib/jQuery-Validation-Engine-2.6.4/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="static/lib/jQuery-Validation-Engine-2.6.4/js/contrib/other-validations.js"></script>

<!-- 日期控件 -->
<link rel="stylesheet" href="static/lib/adminlte-2.3.7/plugins/datepicker/datepicker3.css">
<script src="static/lib/adminlte-2.3.7/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="static/lib/datetime/js/moment.min.js"></script>
<script type="text/javascript" src="static/lib/datetime/js/zh-cn.js"></script>
<script type="text/javascript" src="static/lib/datetime/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="static/lib/datetime/css/bootstrap-datetimepicker.min.css" />


<!--IE8只能支持jQuery1.9-->
<!--[if lte IE 9]>
<script src="static/lib/jquery/jquery-1.11.3.js"></script>
<![endif]-->