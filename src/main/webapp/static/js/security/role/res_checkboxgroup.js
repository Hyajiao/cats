/**
 * 根据权限模块创建不同的checkboxgroup
 * @param {} _id
 * @param {} _labelName
 * @param {} _data
 */
var ResCheckboxGroup = function(_id,_labelName,_data) {
	var columNum=3; 
	if(columNum<3)
 		columNum=_data.length; 
	this.checkboxGroup = Ext.create('Ext.form.CheckboxGroup', { 
		id:_id,
        xtype: 'checkboxgroup', 
        fieldLabel: _labelName, 
        vertical: true,
        columns: columNum, 
        items:_data
	});
	
}