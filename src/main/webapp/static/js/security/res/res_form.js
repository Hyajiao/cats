/**
 * 资源新增、修改表单
 * @class
 * @extends Ext.util.Observable
 */
var ResourceForm = Ext.extend(Ext.util.Observable,{

	resId : null,
	form : null,
	moduleData : null,

	constructor : function(_config) {
		Ext.form.Field.prototype.msgTarget = 'side';
		this.resId = _config || 0;
		this._init();
		this.loadModule();
	},

	_init : function() {
		
		var isExist = false;
		var nameIsExist = false;
		isLoad = false;
		this.myStore = new Ext.data.ArrayStore({
			fields : ['moduleId','moduleName'],
			data : []
		});

		var moduleCombo = Ext.create('Ext.form.ComboBox', {
			name:'arModule',
			hiddenName: 'arModule',
			id:'module_combo',
			queryMode: 'local',
			triggerAction: 'all',
			editable:false,
			store:this.myStore,
			allowBlank: false,
			blankText: '权限模块不能为空！',
			displayField: 'moduleName',
			valueField: 'moduleId'
		});
		
		this.form = Ext.create('Ext.form.Panel', {
			title:'权限资源',
			bodyPadding: 5,
	    	buttonAlign: 'center',
	    	items:[{
	    		layout:'table',
	    		style:'margin-left:10px;margin-top:10px',
				border:false,
				items:[{
					xtype:'numberfield',
					hidden: true,
					name : 'arId',
					id:'arId'
				},{
					xtype:'label',
					text:'权限模块：',
					style: 'padding-left:12px; padding-right:10px;font-family:tahoma,arial,helvetica,sans-serif;font-size:12px;'
				}, moduleCombo ]
	    	},{
	    		layout:'table',
	    		style:'margin-left:10px;',
				border:false,
				items:[{
					xtype:'label',
					text:'权限名称：',
					style: 'padding-left:12px; padding-right:10px;font-family:tahoma,arial,helvetica,sans-serif;font-size:12px;'
				},{
					xtype:'textfield',
					name: 'arName',
					id:'arName'
				}]
	    	},{
	    		layout:'table',
	    		style:'margin-left:10px;',
				border:false,
				items:[{
					xtype:'label',
					text:'权限标示：',
					style: 'padding-left:12px; padding-right:10px;font-family:tahoma,arial,helvetica,sans-serif;font-size:12px;'
				},{
					xtype:'textfield',
					name: 'arPermission',
					id:'arPermission',
					allowBlank:false,
					blankText:'权限标示不能为空',
					regex:/^[A-Za-z0-9_.:]+$/,
					regexText:'非法的输入，字母数字下划线冒号',
					invalidText:'',
					validator: function(_value) {
						if(!isLoad) {
							if(_value) {
								Ext.Ajax.request({
									url:'admin/security/resource/checkUniqueName',
									success: function(response) {
					        			var json = Ext.JSON.decode(response.responseText);
					        			if(json.success) {
                                            nameIsExist = json.data;
                                        } else {
					        			    alert(json.msg);
                                        }
					        		},
					        		failure: function() {
					        			Ext.Msg.show({ 
										     title:'提示信息', 
										     msg: json.msg || '服务器出错！', 
										     buttons: Ext.Msg.OK, 
										     icon: Ext.window.MessageBox.ERROR 
										}); 
					        		},
					        		scope: this,
					        		method: 'post',
					        		params: {
					        			arPermission : _value
					        		}
								});
								if(!nameIsExist) return true;
								else return '权限标示已被使用';
							}
						}
						return true;
					}
				}]
	    	},{
	    		layout:'table',
	    		style:'margin-left:10px;',
				border:false,
				items:[{
					xtype:'label',
					text:'权限URL：',
					style: 'padding-left:14px; padding-right:10px;font-family:tahoma,arial,helvetica,sans-serif;font-size:12px;'
				},{
					xtype:'textfield',
					name: 'arUrl',
					id:'arUrl',
					width:400
				}]
	    	},{
	    		layout:'table',
	    		style:'margin-left:10px;',
				border:false,
				items:[{
					xtype:'label',
					text:'权限描述：',
					style: 'padding-left:12px; padding-right:10px;font-family:tahoma,arial,helvetica,sans-serif;font-size:12px;'
				},{
					xtype:'textarea',
					name: 'arDescription',
					id:'arDescription',
					width:400
				}]
	    	}],
	    	// Reset and Submit buttons 
		    buttons: [{ 
		        text: '重置', 
		        handler: function() { 
		            this.up('form').getForm().reset(); 
		        } 
		    }, { 
		        text: '保存', 
//		        formBind: true, //only enabled once the form is valid 
//		        disabled: true, 
		        handler: function() { 
		            this.save(); 
		        },
		        scope: this
		    }]
		});
	},

	/**
	 * 资源保存
	 */
	save : function() {
		var form = this.form.getForm(); 
		var obj = form.getValues();
        if (form.isValid()) { 
        	Ext.Ajax.request({
        		url:'admin/security/resource/save',
        		success: function(response) {
        			var json = Ext.JSON.decode(response.responseText);
        			if(json.success) {
        				Ext.getCmp("arId").setValue(json.data.id);
        				Ext.Msg.alert('提示信息','权限资源保存成功！');
        				Ext.getCmp('module_combo').disable();
						Ext.getCmp('arPermission').disable();
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
        			
        		},
        		scope: this,
        		method: 'post',
                jsonData : obj,
        	});
        }
	},
	
	/**
	 * 加载权限模块列表
	 */
	loadModule : function() {
		Ext.Ajax.request({
			url: 'admin/security/module/list',
			success: function(response) {
				var json = Ext.JSON.decode(response.responseText);
				if(json.success) {
					var modules = json.data.modules;
					var tmpData = "[";
					for(i=0;i<modules.length;i++) {
						tmpData += "['" + modules[i].moduleId + "','" + modules[i].moduleName + "'],"
					}
					tmpData = tmpData.substring(0,tmpData.length-1);
					tmpData += "]";
					this.moduleData = tmpData;
					this.myStore.loadData(Ext.JSON.decode(this.moduleData));
				}
			},
			failure: function() {
				
			},
			scope:this,
			method: 'post'
		});
	},

	/**
	 * 根据id加载资源
	 * @param {} _id
	 */
	load: function(_id) {
		Ext.Ajax.request({
			url: 'admin/security/resource/load',
			success: function(response) {
				var json = Ext.JSON.decode(response.responseText);
				if(json.success) {
					isLoad = true;
					this.form.getForm().setValues(json.data);
					Ext.getCmp('module_combo').disable();
					Ext.getCmp('arPermission').disable();
				}
			},
			failure: function() {
				
			},
			scope:this,
			method: 'post',
			params: {
				resId:_id
			}
		});
	}

});