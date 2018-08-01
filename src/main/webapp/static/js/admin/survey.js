var ue;
$(document).ready(function(){
	initValidation();

    $('#cspStartDateStr').datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });

    $('#cspEndDateStr').datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });

    // 实例化编辑器
    ue = UE.getEditor('container',{
        topOffset:50,//浮动时工具栏距离浏览器顶部的高度，用于某些具有固定头部的页面
        initialFrameHeight: 400
    });
    ue.ready(function() {
        if($("#id").val()!= null && $("#id").val()!= ""){
            ue.setContent($("#cspBrief").val());
        }
    });

    $("#userInfoForm_add").bind("submit", beforeSubmit);
});
//校验
function initValidation(){
	var validations = [
		{id: 'cspTitle', valid: "validate[required]"},
		{id: 'cspType', valid: "validate[required]"},
		{id: 'cspNum', valid: "validate[required,,custom[integer]]"},
		{id: 'cspUnitPrice', valid: "validate[required,custom[integer]]"},
		{id: 'cspDuration', valid: "validate[required]"},
		{id: 'cspStartDateStr', valid: "validate[required,custom[date]] datepicker"},
		{id: 'cspEndDateStr', valid: "validate[required,custom[date],future[#cspStartDateStr]] datepicker"},
		{id: 'cspSurveyUrl', valid: "validate[required,custom[url]]"}
	];
	for(var i=0,len = validations.length; i<len;i++ ){
		$("#"+ validations[i].id).addClass(validations[i].valid);
	}
	$("#userInfoForm_add").validationEngine('attach', {promptPosition : "topLeft", scroll: true});
}

//保存
function beforeSubmit(){
    var flag = true;
    //对编辑器的操作在编辑器ready之后再做
    ue.ready(function() {
        //获取html内容，返回: <p>hello</p>
        var contentHtml = ue.getContent();
        $("#cspBrief").val(contentHtml);
    });
    return flag;
}


