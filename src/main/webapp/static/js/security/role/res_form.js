/**
 * 权限资源分模块放置在fieldset中
 * @class
 * @extends Ext.util.Observable
 */
var ResForm = Ext.extend(Ext.util.Observable,{

	data : null,
	view : null,
	
	constructor : function() {
//		Ext.tip.QuickTipManager.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		this._init();
		this.loadData();
	},
	
	_init : function() {

		this.view = Ext.create('Ext.form.FieldSet', {
			title: '角色权限',
			border: true,
			layout: 'anchor',
			autoScroll: true,
			width: 900,
			height: 550,
			items: []
		});
		
	},
	
	/**
	 * 加载模块资源列表
	 */
	loadData : function() {
	   	Ext.Ajax.request({
			url: 'admin/security/role/res/list',
			success: function(response) {
				var json = Ext.JSON.decode(response.responseText);
				if(json.success){
                    var arr = json.data.datas;
                    var datas = [];
                    for(i=0;i<arr.length;i++) {
                        var module = new Object();
                        module.id = arr[i].moduleId;
                        module.name = arr[i].moduleName;

                        var items = [];
                        for(j=0;j<arr[i].resList.length;j++) {
                            var chk = {boxLabel: arr[i].resList[j].boxLabel, name: arr[i].resList[j].name, inputValue: arr[i].resList[j].inputValue};
                            items.push(chk);
                        }
                        module.resList = items;
                        datas.push(module);
                    }
                    this.data = datas;
                    this.loadCheckboxGroup();
                }else{
                    alert(json.msg);
                }

			},
			failure: function() {
				
			},
			scope:this,
			method: 'post'
		});
	},
	
	/**
	 * 动态创建不同模块的checkboxgroup
	 */
	loadCheckboxGroup : function() {
		if(this.data) {
			for(i=0;i<this.data.length;i++) {
				var module = this.data[i];
				var group = new ResCheckboxGroup('checkboxgroup' + module.id, module.name, module.resList);
				this.view.add(group.checkboxGroup);
			}
		}
	},
	
	getView : function() {
		return this.view;
	},
	getCheck:function(_id) {
		return Ext.getCmp(_id);
	},
	
	getItems:function(_id) {
		return this.getCheck(_id).items.items;
	},
	
	getAllInputValues:function(_id) {
		var inputValArray = new Array();
		var itemsArray = this.getItems(_id);
		for(var i=0;i<itemsArray.length;i++) {
			var checkModel = itemsArray[i];
			var inputValue = checkModel.inputValue;
			inputValArray.push(inputValue);
		}
		itemsArray = null;
		return inputValArray;
	},

	/**
	 * 进入修改角色窗口给不同模块的权限资源赋值
	 * @param {} val
	 */
	setValue:function(val) {
		if(val) {
			for(i=0;i<val.length;i++) {
				var cbgId = 'checkboxgroup'+val[i].moduleId;
				var itemsArray = this.getItems(cbgId);
				var inputValArray = this.getAllInputValues(cbgId);
				var values = val[i].arrRes.split(',');
				if(values) {
					for(var j=0;j<values.length;j++) {
						var v = Ext.Array.indexOf(inputValArray,Number(values[j]),0);  //要求inputValue 为数字
						if(v != -1) {
							var checkModel = itemsArray[v];
							checkModel.setValue(true);
						}
					}
				}
			}
		}
	}

});
