$(document).ready(function(){
	initValidation();

    $('#cpStartTimeStr').datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });

    $('#cpEndTimeStr').datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });
});
//校验
function initValidation(){
	var validations = [
		{id: 'cpProjectName', valid: "validate[required]"},
		{id: 'cpCompanyName', valid: "validate[required]"},
		{id: 'cpProjectMemo', valid: "validate[required]"},
		{id: 'cpStartTimeStr', valid: "validate[required,custom[date]] datepicker"},
		{id: 'cpEndTimeStr', valid: "validate[required,custom[date]] datepicker"}
	];
	for(var i=0,len = validations.length; i<len;i++ ){
		$("#"+ validations[i].id).addClass(validations[i].valid);
	}
	$("#userInfoForm_add").validationEngine('attach', {promptPosition : "topLeft", scroll: true});
}


