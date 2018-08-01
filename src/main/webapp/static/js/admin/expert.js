$(document).ready(function(){
	initValidation();
});
//校验
function initValidation(){
	var validations = [
		{id: 'cegdMedliveId', valid: "validate[required,custom[integer]]"},
		{id: 'cegdRealName', valid: "validate[required]"},
		{id: 'cegdSex', valid: "validate[required]"},
		{id: 'cegdHospital', valid: "validate[required]"},
		{id: 'cegdDept', valid: "validate[required]"},
		{id: 'cegdProfessional', valid: "validate[required]"},
		{id: 'cegdTelNo', valid: "validate[required,custom[mobilePhone]]"},
		{id: 'cegdEmail', valid: "validate[required,custom[email]]"},
		{id: 'cegdBankNo', valid: "validate[custom[integer]]"},
	];
	for(var i=0,len = validations.length; i<len;i++ ){
		$("#"+ validations[i].id).addClass(validations[i].valid);
	}
	$("#expertForm").validationEngine('attach', {promptPosition : "topLeft", scroll: true});
}


