<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>重定向</title>
    <script type="text/javascript">
        function init() {
            location.href="${url}"
        }
    </script>
</head>
<body onload="init();">
</body>
</html>
