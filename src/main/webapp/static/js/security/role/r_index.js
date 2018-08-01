/**
 * 装载角色列表grid的panel
 * @class
 * @extends Ext.util.Observable
 */
var RIndex = Ext.extend(Ext.util.Observable,{
	id : null,
	view : null,
	
	constructor : function(_id) {
		this.id = _id;
		this._init();
	},
	
	_init : function() { 
		
		var roleGrid = new RoleGrid();
		var rForm = new RoleForm();
		var roleWin = new RoleWindow(rForm,960,710,roleGrid);
		roleGrid.on({
			'add' : {
				fn : function() {
					roleWin.show();
					rForm.form.getForm().reset();
				}
			},
			'edit' : {
				fn : function() {
					var id = roleGrid.getSelectId();
					rForm.form.getForm().reset();
					roleWin.show(id);
				}
			},
			'detail' : {
				fn : function() {
					var id = roleGrid.getSelectId();
					rForm.form.getForm().reset();
					roleWin.show(id);
				}	
			},
			'del' : {
				fn : function() {
					roleGrid.del();
				}
			}
		});
		
		roleGrid.selModel.on({
			'selectionchange' : function(sm, selections) {
				roleGrid.grid.down('#removeButton').setDisabled(selections.length == 0);
			}
		});
		
		this.view = Ext.create('Ext.form.Panel',{
			id: this.id,
			frame : false,
			border : false,
			bodyBorder : false,
			items : [roleGrid.grid],
			autoScroll:true,
			renderTo:Ext.getBody()
		});
		
		this.view.on({
			'show' : {
				fn : function() {
					roleGrid.loadData();
				}
			}
		});
	}
	
});
