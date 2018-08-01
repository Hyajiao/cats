	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
    <base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="static/lib//ext-4.0.2/resources/css/ext-all.css">
<%--		<link rel="stylesheet" type="text/css" href="include/firefox.css">--%>
	
	<script type="text/javascript" src="static/lib//ext-4.0.2/bootstrap.js"></script>
	 
	<!-- <script type="text/javascript" src="static/lib//ext-4.0.2/ext-all.js"></script>-->
	<script type="text/javascript" src="static/lib//ext-4.0.2/locale/ext-lang-zh_CN.js"></script>
  	<%-- charset --%>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<!-- no-cache -->
  	<meta http-equiv="pragma" content="no-cache">
  	<meta http-equiv="cache-control" content="no-cache">
  	<meta http-equiv="expires" content="0">
  	<script type="text/javascript">
  		Ext.BLANK_IMAGE_URL = Ext.isIE6 || Ext.isIE7 || Ext.isAir ?
			"<%=basePath%>static/lib//ext-4.0.2/resources/themes/images/default/s.gif" :
            'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
		var COMMON_BASE_PATH = '<%=basePath%>';	
	</script>
 