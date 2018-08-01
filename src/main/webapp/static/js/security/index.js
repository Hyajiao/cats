Ext.onReady(function() {
	
	Ext.tip.QuickTipManager.init();
	
	var start = {
		id:'admin_start',
		title:'后台管理',
		layout:'fit',
		bodyStyle:'padding:25px',
		html:'后台管理'
	};
	
	var rIndex = new RIndex('snf_role');
	var resIndex = new ResIndex('snf_res');

	var treestore = Ext.create('Ext.data.TreeStore', { 
	    root: { 
	        expanded: true,  
	        text:"", 
	        user:"", 
	        status:"",  
	        children: [ 
	            { text:"权限管理", expanded: true,  
	                children: [
	                    //{ text:"用户管理", leaf: true, panel: 'user', iconCls: 'x-icon-users'}, 
	                    { text:"角色管理", leaf: true, panel: 'role', iconCls: 'x-icon-role'} ,
	                    { text:"资源管理", leaf: true, panel: 'res', iconCls: 'x-icon-res'}
	                ]
	            }
	        ] 
	    } 
	});      
	
	var treepanel = Ext.create('Ext.tree.Panel' , {
	
		title: '后台管理',
		region:'west',
		width: 200, 
	    height: 150, 
	    store: treestore, 
	    rootVisible: false
	});
	
	var contentPanel = {
		id:'content-panel',
		region:'center',
		layout:'card',
		margins:'5 5 5 0',
		activeItem:0,
		border:false,
		items: [
			start,rIndex.view,
			resIndex.view
		]
	};
	
	treepanel.on('itemclick', function(record, item, index, e){
		if(item.raw.panel) {
			Ext.getCmp('content-panel').layout.setActiveItem('snf_' + item.raw.panel);
		} else {
			Ext.getCmp('content-panel').layout.setActiveItem('admin_start');			
		}
	});
	
	Ext.create('Ext.Viewport', {
		layout: 'border',
		border: false,
		title:'Ext Layout Browser',
		bodyBorder: false,
		items: [
			treepanel,
			contentPanel
		]
	})
});
