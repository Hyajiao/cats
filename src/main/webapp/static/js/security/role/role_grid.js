/**
 * 角色列表
 */
var RoleGrid = function() {

	Ext.define('AuthRole', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'id'},
            {name: 'aroName'}
         ],
         idProperty: 'id'
    });
    
    var itemsPerPage = 20;   // set the number of items you want per page 
    this.remoteStore = Ext.create('Ext.data.Store', {
    	model: 'AuthRole',
    	pageSize: itemsPerPage, // items per page 
    	remoteSort: true,
    	autoLoad: false,
    	proxy: {
    		type: 'ajax',
    		url: 'admin/security/role/list',
    		reader: {
    			type: 'json',
    			root: 'data.datas',
    			totalProperty: 'data.totalCount' 
    		}
    	},
    	sorters: {property: 'id', direction: 'ASC'}
    });
    
   
   this.selModel = Ext.create('Ext.selection.CheckboxModel', {
   		mode:'single'
    });
    
    this.selModel.addEvents('selectionchange');
    
    this.grid = Ext.create('Ext.grid.Panel', {
        store: this.remoteStore,
        columns: [
            {text: "id", flex: 1, sortable: false, dataIndex: 'id',hidden:true,header:'主键'},
            {text: "aroName", width: 420 ,sortable: false, dataIndex: 'aroName', align:'center',header:'角色名称'}
        ],
        columnLines: true,
        selModel: this.selModel,
		
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text:'新增',
                tooltip:'新增角色',
                iconCls:'add',
                handler: function() {
                	this.fireEvent('add');
                },
                scope: this
            }, '-', {
                text:'修改',
                tooltip:'修改角色',
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
                tooltip:'删除选中角色',
                iconCls:'remove',
                disabled: true,
                handler: function() {
                	this.fireEvent('del');
                },
                scope: this
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
        title: '角色管理',
        iconCls: 'icon-grid'
    });
    
    this.grid.on('itemdblclick',function(_grid, record, item, index, e) {
    	_grid.getSelectionModel().select(index);
    	this.fireEvent('detail');
    },this);
    
    this.addEvents('add','edit','detail','del');
}

Ext.extend(RoleGrid, Ext.util.Observable, {
	
	/**
	 * 加载角色列表数据
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
		return this.getSelectionModel().getSelection()[0].get("id");
	},
	
	/**
	 * 删除指定角色
	 */
	del : function() {
		if (!this.hasSelect()) {
			Ext.Msg.show({
				title : '删除角色',
				msg : '请选择要删除的角色',
				width : 200,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return;
		}
		var aroId = this.getSelectId();
		Ext.Msg.show({
			title: '删除确认',
			msg: '角色删除后不可恢复，确认要删除？',
			width: 300,
			buttons: Ext.MessageBox.OKCANCEL,
			icon: Ext.MessageBox.QUESTION,
			fn:function(_btn) {
				if(_btn == 'ok') {
					Ext.Ajax.request({
						url: 'admin/security/role/delete',
						success: function(_res,_ops) {
							var json = Ext.JSON.decode(_res.responseText);
							if(json.success) {
								this.refresh();
							} else {
								Ext.Msg.show({
									title:'删除角色',
									msg: json.msg ||　'未知错误',
									buttons: Ext.Msg.OK,
									icon: Ext.MessageBox.ERROR
								});
							}
						},
						failure: function(){
							Ext.Msg.show({
								title:'删除角色',
								msg: '服务器错误',
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.ERROR
							});
						},
						params: {
							aroId:aroId
						},
						scope:this
					});	
				}
			},
			scope:this
		});
	}
});