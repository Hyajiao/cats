/**
 * 资源新增修改弹出的主窗口
 * @param {} _config
 * @param {} width
 * @param {} height
 * @param {} resGrid
 */
var ResWindow = function(_config,width,height,resGrid) {

	this.form = _config || '';

	this.resGrid = resGrid;

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
					this.resGrid.refresh();
				}
			},
			scope : this
		}
	});
}

Ext.extend(ResWindow, Ext.util.Observable, {
	getWin:function() {
		return this.win;
	},

	hide:function() {
		this.getWin().hide();
	},

	show:function(resId) {
		this.getWin().show();
		if(resId) {
			this.form.load(resId);
		}
	}
})