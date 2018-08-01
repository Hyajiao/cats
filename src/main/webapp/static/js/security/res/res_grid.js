/**
 * 资源列表
 */
var ResGrid = function() {
	
	this.params = {};
	
	Ext.define('AuthResource', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'arId'},
            {name: 'amName'},
            {name: 'arName'},
            {name: 'arPermission'},
            {name: 'arUrl'},
            {name: 'arDescription'}
         ],
         idProperty: 'arId'
    });
    
    var itemsPerPage = 20;   // set the number of items you want per page 
    this.remoteStore = Ext.create('Ext.data.Store', {
    	model: 'AuthResource',
    	pageSize: itemsPerPage, // items per page 
    	remoteSort: true,
    	autoLoad: false,
    	proxy: {
    		type: 'ajax',
    		url: 'admin/security/resource/list',
    		actionMethods:{read: 'POST'},  //提交方式为POST时解决传递参数中文乱码问题
    		reader: {
    			type: 'json',
    			root: 'data.datas',
    			totalProperty: 'data.totalCount' 
    		}
    	},
    	sorters: {property: 'arId', direction: 'ASC'}
    });
    
   this.selModel = Ext.create('Ext.selection.CheckboxModel', {
   		mode:'single'
   });
    
    this.selModel.addEvents('selectionchange');
    
    /**
     * 加载查询条件模块下拉列表
     */
	this.myStore = new Ext.data.ArrayStore({
		fields : ['moduleId','moduleName'],
		data : []
	});

	var moduleCombo = Ext.create('Ext.form.ComboBox', {
		name:'resourceModule',
		hiddenName: 'resourceModule',
		id:'moduleComboId',
		queryMode: 'local',
		editable:false,
		triggerAction: 'all',
		store:this.myStore,
		emptyText:'请选择权限模块',
		displayField: 'moduleName',
		valueField: 'moduleId'
	});
	
    this.remoteStore.on('beforeload',function(_store, options) {
    	this.params = {
    		resourceModule : Ext.getCmp('moduleComboId').getValue(),
    		resourceName : Ext.getCmp('resourceName').getValue(),
    		resourceUniqueName : Ext.getCmp('resourceUniqueName').getValue(),
    		resourceUrl : Ext.getCmp('resourceUrl').getValue()
    	};
    	Ext.apply(_store.proxy.extraParams, this.params);
    },this);
    
    this.grid = Ext.create('Ext.grid.Panel', {
        store: this.remoteStore,
        columns: [
            {text: "arId", flex: 1, sortable: false, dataIndex: 'arId',hidden:true,header:'主键'},
            {text: "amName", width: 120 ,sortable: false, dataIndex: 'amName', align:'center',header:'权限模块'},
            {text: "arName", width: 150 ,sortable: false, dataIndex: 'arName', align:'center',header:'权限名称'},
            {text: "arPermission", width: 200 ,sortable: false, dataIndex: 'arPermission', align:'left',header:'权限标示'},
            {text: "arUrl", width: 320 ,sortable: false, dataIndex: 'arUrl', align:'left',header:'权限URL'},
            {text: "arDescription", width: 320 ,sortable: false, dataIndex: 'arDescription', align:'left',header:'权限描述'}
        ],
        columnLines: true,
        selModel: this.selModel,
		
        // inline buttons
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text:'新增',
                tooltip:'新增资源',
                iconCls:'add',
                handler: function() {
                	this.fireEvent('add');
                },
                scope: this
            }, '-', {
                text:'修改',
                tooltip:'修改资源',
                iconCls:'option',
                handler: function() {
                	if(this.hasSelect()) {
                		this.fireEvent('edit');
                	}
                },
                scope: this
            },'-',{
                itemId: 'removeButton',
                text:'删除',
                tooltip:'删除选中资源',
                iconCls:'remove',
                disabled: true,
                handler: function() {
                    this.fireEvent('del');
                },
                scope: this
            },'-',{
                itemId: 'flushButton',
                text:'刷入数据',
                tooltip:'刷入数据',
                iconCls:'x-tree-icon-leaf',
                handler: function() {
                	this.fireEvent('flush');
                },
                scope: this
            },'简单查询:',moduleCombo,'-',{
            	xtype:'textfield',
            	name:'resourceName',
            	id:'resourceName',
            	emptyText:'请输入权限名称',
            	width:100,
            	enableKeyEvents:true,
            	listeners:{
            		'specialkey' : {
	            		fn: function(_field, _e) {
	            			if(_e.getKey() == Ext.EventObject.ENTER) {
	            				this.refresh();
	            			}
	            		},
	            		scope:this	
            		}
            	}
            },'-',{
            	xtype:'textfield',
            	name:'resourceUniqueName',
            	id:'resourceUniqueName',
            	emptyText:'请输入权限标识',
            	width:100,
            	enableKeyEvents:true,
            	listeners:{
            		'specialkey' : {
	            		fn: function(_field, _e) {
	            			if(_e.getKey() == Ext.EventObject.ENTER) {
	            				this.refresh();
	            			}
	            		},
	            		scope:this	
            		}
            	}
            },'-',{
            	xtype:'textfield',
            	name:'resourceUrl',
            	id:'resourceUrl',
            	emptyText:'请输入权限URL',
            	width:120,
            	enableKeyEvents:true,
            	listeners:{
            		'specialkey' : {
	            		fn: function(_field, _e) {
	            			if(_e.getKey() == Ext.EventObject.ENTER) {
	            				this.refresh();
	            			}
	            		},
	            		scope:this	
            		}
            	}
            },{
            	text: '搜索',
				icon:'static/js/security/img/search.gif',
				handler: function() {
					this.refresh();
				},
				scope:this
            }]
        },{
            xtype: 'pagingtoolbar', 
            store: this.remoteStore,   // same store GridPanel is using 
            dock: 'bottom', 
            displayInfo: true 
        }],
		bodyStyle:'width:100%',
		height: 650,
		autoScroll:true,
        title: '资源管理',
        iconCls: 'icon-grid'
    });
    
    this.grid.on('itemdblclick',function(_grid, record, item, index, e) {
    	_grid.getSelectionModel().select(index);
    	this.fireEvent('detail');
    },this);
    
    this.addEvents('add','edit','detail','del','flush');
}

Ext.extend(ResGrid, Ext.util.Observable, {
	
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
					tmpData += "['','全部']"
					for(i=0;i<modules.length;i++) {
						tmpData += ",['" + modules[i].moduleId + "','" + modules[i].moduleName + "']";
					}
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
	 * 资源列表显示时加载资源列表相关数据
	 */
	loadData : function() {
		this.remoteStore.load({ 
	       params:{
	           start:0,     
	           limit: 20 
	       }
   		});
	},

	getGrid:function() {
		return this.grid;
	},
	
	getSelectionModel:function() {
		return this.getGrid().getSelectionModel();
	},
	
	hasSelect:function() {
		return this.getSelectionModel().hasSelection();
	},
	
	getSelectRecord:function() {
		if(!this.hasSelect()) return null;
		return this.getSelectionModel().getSelection();
	},

	refresh:function(_curPage) {
		if(_curPage) {
			this.remoteStore.loadPage(_curPage);
		} else {
			this.remoteStore.loadPage(1);
		}
	},

	isVisible:function() {
		return this.grid.isVisible();
	},

	getSelectId : function() {
		if(!this.hasSelect())return null;
		return this.getSelectionModel().getSelection()[0].get("arId");
	},
	
	/**
	 * 删除指定的资源
	 */
	del : function() {
		if (!this.hasSelect()) {
			Ext.Msg.show({
				title : '删除资源',
				msg : '请选择要删除的资源',
				width : 200,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return;
		}
		var resId = this.getSelectId();
		Ext.Msg.show({
			title: '删除确认',
			msg: '资源删除后不可恢复，确认要删除？',
			width: 300,
			buttons: Ext.MessageBox.OKCANCEL,
			icon: Ext.MessageBox.QUESTION,
			fn:function(_btn) {
				if(_btn == 'ok') {
					Ext.Ajax.request({
						url: 'admin/security/resource/delete',
						success: function(_res,_ops) {
							var json = Ext.JSON.decode(_res.responseText);
							if(json.success) {
								this.refresh();
							} else {
								Ext.Msg.show({
									title:'删除资源',
									msg: json.msg ||　'未知错误',
									buttons: Ext.Msg.OK,
									icon: Ext.MessageBox.ERROR
								});
							}
						},
						failure: function(){
							Ext.Msg.show({
								title:'删除资源',
								msg: '服务器错误',
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.ERROR
							});
						},
						params: {
							resId:resId
						},
						scope:this
					});	
				}
			},
			scope:this
		});
	},

    /**
     * 刷数据到内存
     */
    flush : function() {
        Ext.Ajax.request({
            url: 'admin/security/resource/flush',
            success: function(_res,_ops) {
                var json = Ext.JSON.decode(_res.responseText);
                if(json.success) {
                    Ext.Msg.show({
                        title:'刷入资源',
                        msg: '成功',
                        buttons: Ext.Msg.OK,
                        icon: Ext.MessageBox.INFO
                    });
                } else {
                    Ext.Msg.show({
                        title:'刷入资源',
                        msg: json.msg ||　'未知错误',
                        buttons: Ext.Msg.OK,
                        icon: Ext.MessageBox.ERROR
                    });
                }
            },
            failure: function(){
                Ext.Msg.show({
                    title:'刷入资源',
                    msg: '服务器错误',
                    buttons: Ext.Msg.OK,
                    icon: Ext.MessageBox.ERROR
                });
            },
            scope:this
        });
    }
});