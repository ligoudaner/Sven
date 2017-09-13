$(function () {
    $("#jqGrid").jqGrid({
        url: '../moments/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户Id', name: 'userId', index: 'user_Id', width: 80 }, 			
			{ label: '朋友圈内容', name: 'content', index: 'content', width: 80 }, 			
			{ label: '朋友圈图片，多张用逗号分割', name: 'photos', index: 'photos', width: 80 }, 			
			{ label: '用户名:发朋友圈的人', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '类型:1：点赞、2：评论', name: 'type', index: 'type', width: 80 }, 			
			{ label: '用户名,点赞或评论人名', name: 'mUserName', index: 'm_user_name', width: 80 }, 			
			{ label: '回复评论人', name: 'replyToUserName', index: 'reply_to_user_name', width: 80 }, 			
			{ label: '回复内容，点赞时为空', name: 'replyContent', index: 'reply_content', width: 80 }, 			
			{ label: '', name: 'status', index: 'status', width: 80 }, 			
			{ label: '', name: 'createUser', index: 'create_user', width: 80 }, 			
			{ label: '', name: 'createTime', index: 'create_time', width: 80 }			
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
		moments: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.moments = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.moments.id == null ? "../moments/save" : "../moments/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.moments),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
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
				    url: "../moments/delete",
				    data: JSON.stringify(ids),
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
		getInfo: function(id){
			$.get("../moments/info/"+id, function(r){
                vm.moments = r.moments;
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