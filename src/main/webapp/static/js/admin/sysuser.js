$(document).ready(function(){
	initValidation();
});
//校验
function initValidation(){
	var validations = [
		{id: 'surePass', valid: "validate[required, equals[auPassword]] text-input"},
		{id: 'auAge', valid: "validate[custom[onlyNumberSp]] text-input"},//年龄
		{id: 'auTel', valid: "validate[required, custom[mobilePhone]] text-input"},//电话
		{id: 'auShowName', valid: "validate[required] text-input"},//电话
        {id: 'auEmail', valid: "validate[custom[email]]"},//邮箱
        {id: 'auZone', valid: "validate[required] text-input"},//大区
        {id: 'auRole', valid: "validate[required] text-input"},//角色
		{id: 'auPostCode', valid: "validate[custom[postCode]]"}//邮编
	];
	var role =  $("#auRole").val();
    if(role && role == 2){
        validations[validations.length] = {id: 'auPassword', valid: "validate[required ,custom[pwdchange]] text-input"};
    }else{
        validations[validations.length] = {id: 'auPassword', valid: "validate[required, minSize[1], maxSize[16]] text-input"};
    }

    if(!$("#id").val()){
        validations[validations.length] = {id: 'auUserName', valid: "validate[required, ajax[ajaxNameCall]] text-input"};
    }

    for(var i=0,len = validations.length; i<len;i++ ){
        $("#"+ validations[i].id).addClass(validations[i].valid);
    }
    $("#userInfoForm").validationEngine('attach', {promptPosition : "topLeft", scroll: true});

    $("#auRole").change(function () {
        if($(this).val() && $(this).val() == 2){
            $("#auPassword").removeClass("validate[required, minSize[1], maxSize[16]] text-input");
            $("#auPassword").addClass("validate[required ,custom[pwdchange]] text-input");
        }else{
            $("#auPassword").removeClass("validate[required ,custom[pwdchange]] text-input");
            $("#auPassword").addClass("validate[required, minSize[1], maxSize[16]] text-input");
        }
    });

}

