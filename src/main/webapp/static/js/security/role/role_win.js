/**
 * 角色表单主窗口
 * @param {} _config
 * @param {} width
 * @param {} height
 * @param {} roleGrid
 */
var RoleWindow = function(_config,width,height,roleGrid) {

	this.form = _config || '';

	this.roleGrid = roleGrid;
	
	this.roleId = '';

	this.win = Ext.create('Ext.window.Window', {
		layout:'fit',
		width:width,
		height:height,
		modal:true,
		bodyBorder:false,
		closeAction:'hide',
		items:this.form.form,
		listeners: {
			'beforehide' : {
				fn : function () {
					this.roleGrid.refresh();
				}
			},
			'show':{
				fn:this._loadData
			},
			scope : this
		}
	});
}

Ext.extend(RoleWindow, Ext.util.Observable, {
	getWin:function() {
		return this.win;
	},

	hide:function() {
		this.getWin().hide();
	},

	show:function(roleId) {
		this.roleId = roleId;
		this.getWin().show();
	},
	
	_loadData:function() {
		if(this.roleId) {
			this.form.load(this.roleId);
		}
	}
})