/**
 * 装载资源列表grid的panel
 * @class
 * @extends Ext.util.Observable
 */
var ResIndex = Ext.extend(Ext.util.Observable,{
	id : null,
	view : null,
	
	constructor : function(_id) {
		this.id = _id;
		this._init();
	},
	
	_init : function() { 
		
		var resGrid = new ResGrid();
		
		resGrid.on({
			'add' : {
				fn : function() {
					var resForm = new ResourceForm();
					var resWin = new ResWindow(resForm,645,450,resGrid);
					resWin.show();
					resForm.form.getForm().reset();
				}
			},
			'edit' : {
				fn : function() {
					var resForm = new ResourceForm();
					var resWin = new ResWindow(resForm,645,450,resGrid);
					var id = resGrid.getSelectId();
					resWin.show(id);
				}
			},
			'detail' : {
				fn : function() {
					var resForm = new ResourceForm();
					var resWin = new ResWindow(resForm,645,450,resGrid);
					var id = resGrid.getSelectId();
					resWin.show(id);
				}	
			},
			'del' : {
				fn : function() {
					resGrid.del();
				}
			},
            'flush' : {
                fn : function() {
                    resGrid.flush();
                }
            }
		});
		
		resGrid.selModel.on({
			'selectionchange' : function(sm, selections) {
				resGrid.grid.down('#removeButton').setDisabled(selections.length == 0);
			}
		});
		
		this.view = Ext.create('Ext.form.Panel',{
			id: this.id,
			frame : false,
			border : false,
			bodyBorder : false,
			items : [resGrid.grid],
			autoScroll:true,
			renderTo:Ext.getBody()
		});
		
		this.view.on({
			'show' : {
				fn : function() {
					resGrid.loadModule();
					resGrid.loadData();
				}
			}
		});
	}
	
});
