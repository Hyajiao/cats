<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kingyee.common.util.PropertyConst"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName();
    int port = request.getServerPort();
    if(port == 80){
        basePath = basePath + path + "/";
    }else{
        basePath = basePath + ":" + request.getServerPort() + path + "/";
    }

    String userInfoUrl =PropertyConst.MYMEDLIVE_URL;
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link rel="stylesheet" href="static/css/base.css">
<link rel="stylesheet" href="static/css/index.css">
<link rel="stylesheet" type="text/css" href="static/css/data.css" />
<script src="static/lib/jquery/jquery-3.0.0.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="static/js/setSize.js" ></script>
<script src="static/js/wx/base.js"></script>

<!-- form表单验证 -->
<link href="static/lib/jQuery-Validation-Engine-2.6.4/css/validationEngine.jquery.css" rel="stylesheet">
<script type="text/javascript" src="static/lib/jQuery-Validation-Engine-2.6.4/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="static/lib/jQuery-Validation-Engine-2.6.4/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="static/lib/jQuery-Validation-Engine-2.6.4/js/contrib/other-validations.js"></script>


<!--详细内容图片放大-->
<script>
    var GLOBAL_CONFIG = {};
    GLOBAL_CONFIG.basePath = '<%=basePath%>';
    GLOBAL_CONFIG.userInfoUrl = '<%=userInfoUrl%>'
    //弹出框 jquery.weui
    function showMsg(msg){
        alert(msg)
    }

    $(document).ready(function () {
        // 获取js-sdk需要的信息
//         var url = location.href;
//         $.ajax({
//             type: "GET",
//             cache: false,
//             dataType: "json",
//             url: "getJsSdk",
//             data: {url:url},
//             success: function (json) {
//                 if(json.success){
//                     wx.config({
//                         debug: false,
//                         appId: json.data.appId,
//                         timestamp: json.data.timestamp,
//                         nonceStr: json.data.nonceStr,
//                         signature: json.data.signature,
//                         jsApiList: [
//                             //所有要调用的 API 都要加到这个列表中
//                             "onMenuShareTimeline",//分享到朋友圈接口
//                             "onMenuShareAppMessage",//分享给朋友接口
//                             "onMenuShareQQ",//分享到qq接口
//                             //"onMenuShareWeibo",//分享到腾讯微博
//                             "onMenuShareQZone"//分享到QQ空间
//                         ]
//                     });
//                 }else{
//                     console.log("调用获取js-sdk的失败");
//                 }
//             }
//         });
//
//         wx.ready(function(){
//             var shareconfig = {
//                 title: $("title").text(),
//                 link: window.location.href,
//                 imgUrl: GLOBAL_CONFIG.basePath+'static/test_images/share.jpg'
// //                desc:$("#shareContent").text()
//             };
//             //分享到朋友圈
//             wx.onMenuShareTimeline(shareconfig);
//             //分享给朋友
//             wx.onMenuShareAppMessage(shareconfig);
//             //分享到QQ
//             wx.onMenuShareQQ(shareconfig);
//             //分享到QQ空间
//             wx.onMenuShareQZone(shareconfig);
//         });
    })
</script>