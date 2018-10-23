$(function(){
		$("#add").on("click",function(){
			$('#fm').form('clear');
			$("#dlg_add").dialog('open').dialog('setTitle',"新建资源");
		});
		$("#save").on("click",function(){
			$("#fm").form('submit',{
				url:"/sys/res/add",
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(data){
					$('#dlg_add').dialog('close');		// close the dialog
					$("#res_tree").tree('reload');
				}
			});
			
		});
		
		$('#res_tree').tree({
		    url: "/sys/res/tree",
		    method:"get",
		    loadFilter: function(data){
		    	console.log(data);
		        if (data.d){
		            return data.d;
		        } else {
		            return data;
		        }
		    },
		    onClick:function(node){
		    	$("#res_data_grid").datagrid({
		    		queryParams:{id:node.id},
		    		method:"get",
		            border : false,  
		            rownumbers : false,  
		            animate: true,  
		            striped : true,
		            toolbar:"#tb_res_data",
		            fitColumn:true,
		            url:'/sys/ress',
		            columns:[[
		                      {field:'id',title:'序号',width:'10%'},
		                      {field:'res_name',title:'资源名称',width:'20%'},
		                      {field:'res_code',title:'资源代码',width:'20%'},
		                      {field:'res_url',title:'资源链接',width:'30%'},
		                      {field:'res_type',title:'资源种类',width:'20%'}
		                  ]]
		    	});
		    	$("#role_data_grid").datagrid({
		    		queryParams:{res_id:node.id},
		    		method:"get",
		            border : false,  
		            rownumbers : true,  
		            animate: true,  
		            striped : true,
		            toolbar:"#tb_role_data",
		            url:'/sys/roles',
		            columns:[[
		                      {field:'id',title:'序号',width:'10%'},
		                      {field:'role_name',title:'角色名称',width:'20%'},
		                      {field:'role_code',title:'角色代码',width:'20%'},
		                      {field:'enable',title:'是否可用',width:'20%'},
		                      {field:'description',title:'描述',width:'30%'}
		                  ]]
		    	});
		    }
		});
		$("#tb_res_edit").on("click",function(){
			var nodes = $('#res_data_grid').datagrid('getRows');
			var node=nodes[0];
			if(node){
				$('#fm').form('clear');
				$("#dlg_add").dialog('open').dialog('setTitle',"修改组织机构");
				$('#fm').form('load',node);
			}else{
				$.messager.show({	// show error message
					title: '错误提示',
					msg: "没有发现选中的资源"
				});
			}
		});
		$("#tb_res_delete").on("click",function(){
			var nodes = $('#res_data_grid').datagrid('getRows');
			var node=nodes[0];
			if(node){
				$.messager.confirm('确认信息','确定要删除这个资源吗?',function(r){
					if(r){
						$.post('/sys/res/delete',{id:node.id},function(result){
								$('#res_tree').tree('reload');	
								$('#role_data_grid').datagrid('loadData',{total:0,rows:[]});
								$('#res_data_grid').datagrid('loadData',{total:0,rows:[]});
								$.messager.show({	// show error message
									title: "提示信息",
									msg: result.result_msg
								});
						},'json');
					}
				});
			}else{
				$.messager.show({	// show error message
					title: '错误提示',
					msg: "没有发现选中的部门组织"
				});
			}
		});
		$("#tb_role_delete").on("click",function(){
			var nodes = $('#res_data_grid').datagrid('getRows');
			var res = nodes[0];
			var role = $('#role_data_grid').datagrid('getSelected');
			if(!res){
				$.messager.show({	
					title: '错误提示',
					msg: "没有选中资源！"
				});
				return;
			}
			if(!role){
				$.messager.show({	
					title: '错误提示',
					msg: "没有选中角色！"
				});
				return;
			}
				$.messager.confirm('确认信息','确定要删除这个角色资源吗?',function(r){
					if(r){
						$.post('/sys/res/deleteRole',{res_id:res.id,role_id:role.id},function(result){
							if (result.msg){
								$('#role_data_grid').datagrid('reload');	
							} else {
								$.messager.show({	// show error message
									title: result.title,
									msg: result.msg
								});
							}
						},'json');
					}
				});
		});
});