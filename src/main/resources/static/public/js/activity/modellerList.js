$(function () {
    $("#jqGrid").jqGrid({
        url: '../models/modelList',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: 'id', width: 50, key: true },
			{ label: 'KEY', name: 'key', index: 'key', width: 80 }, 			
			{ label: 'Name', name: 'name', index: 'name', width: 80 }, 			
			{ label: 'Version', name: 'version', index: 'version', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'version', width: 80 }, 			
			{ label: '最后更新时间', name: 'lastUpdateTime', index: 'version', width: 80 }, 			
			{ label: '元数据', name: 'metaInfo', index: 'version', width: 80 } 	
			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		modelEntity:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.modelEntity = {};
		},
		save: function (event) {
			$.ajax({
			    url: "../models/newModel",
			    type : 'post',
			    dataType : 'json',
				data : JSON.stringify(vm.modelEntity),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
							window.location.href="activity/modeler.html?modelId="+r.msg;
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		update:function(event){
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			window.location.href="../activity/modeler.html?modelId="+ids;
		},
		deploy:function(event){
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要发布选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../models/"+ids+"/deployment",
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../models/deleteModel/"+ids,
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		exportModel:function(event){
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			window.location.href="../models/export/"+ids;
		},
		importModel:function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "导入流程",
				area: ['550px', '200px'],
				shadeClose: false,
				content: jQuery("#importModelLayer"),
				btn: ['上传','取消'],
				btn1: function (index) {
					$.ajaxFileUpload({
						type: "POST",
					    url: "../models/uploadFile",
						secureuri : false,
						fileElementId : "uploadfile",
						dataType : 'json',
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('上传成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});