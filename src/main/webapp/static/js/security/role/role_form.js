/**
 * 角色新增、修改表单
 * @class
 * @extends Ext.util.Observable
 */
var RoleForm = Ext.extend(Ext.util.Observable,{

	data : null,
	roleId : null,
	form : null,
	resForm : null,

	constructor : function(_config) {
		this.resForm = new ResForm();
		this._init();
	},

	_init : function() {
		this.form = Ext.create('Ext.form.Panel', {
			title:'角色',
			bodyPadding: 5,
	    	buttonAlign: 'center',
	    	items:[{
	    		layout:'table',
	    		style:'margin-left:10px;margin-top:10px',
				border:false,
				items:[{
					xtype:'textfield',
					hidden: true,
					name : 'id',
					id:'id'
				},{
					xtype : 'label',
					text: '角色名称：',
					style: 'padding-left:12px; padding-right:10px;font-family:tahoma,arial,helvetica,sans-serif;font-size:12px;'
				},{
					xtype:'textfield',
					allowBlank: false,
					blankText: '角色名称不能为空',
					name: 'aroName',
					id: 'aroName'
				}]
	    	},{
				layout:'table',
				style:'margin-left:10px;margin-top:30px',
				border:false,
		        items:[this.resForm.getView()]
			}],
		    buttons: [{ 
		        text: '重置', 
		        handler: function() { 
		            this.up('form').getForm().reset(); 
		        } 
		    }, { 
		        text: '保存', 
		        formBind: true, //only enabled once the form is valid 
		        disabled: true, 
		        handler: function() { 
		            this.save(); 
		        },
		        scope: this
		    }]
		});
	},
	
	/**
	 * 角色保存事件
	 */
	save : function() {
		var form = this.form.getForm(); 
		var obj = form.getValues();
        
        if (form.isValid()) { 
        	Ext.Ajax.request({
        		url:'admin/security/role/save',
        		success: function(response) {
        			var json = Ext.JSON.decode(response.responseText);
        			if(json.success) {
        				Ext.getCmp("id").setValue(json.data.id);
        				Ext.Msg.alert('提示信息','角色保存成功！');
        			} else {
        				Ext.Msg.show({ 
						     title:'提示信息', 
						     msg: json.msg || '服务器出错！', 
						     buttons: Ext.Msg.OK, 
						     icon: Ext.window.MessageBox.ERROR 
						}); 
        			}
        		},
        		failure: function() {
        			Ext.Msg.show({ 
					     title:'提示信息', 
					     msg: '服务器出错！', 
					     buttons: Ext.Msg.OK, 
					     icon: Ext.window.MessageBox.ERROR 
					}); 
        		},
        		scope: this,
        		method: 'post',
                jsonData : obj
        		// params: {
        		// 	data : Ext.JSON.encode(obj)
        		// }
        	});
        }
	},
	
	/**
	 * 角色加载
	 * @param {} _id
	 */
	load: function(_id) {
		Ext.Ajax.request({
			url: 'admin/security/role/update',
			success: function(response) {
				var json = Ext.JSON.decode(response.responseText);
				if(json.success) {
					this.form.getForm().setValues(json.data);
					this.resForm.setValue(json.data.datas);
				}
			},
			scope:this,
			method: 'post',
			params: {
				aroId:_id
			}
		});
	}
});