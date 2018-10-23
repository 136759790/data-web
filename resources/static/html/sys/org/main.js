$(function(){
		$("#add").on("click",function(){
			$('#fm').form('clear');
			$("#dlg_add").dialog('open').dialog('setTitle',"新建组织机构");
		});
		$("#save").on("click",function(){
			$("#fm").form('submit',{
				url:"/sys/org/add",
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(data){
					$('#dlg_add').dialog('close');		// close the dialog
					$("#tt").tree('reload');
				}
			});
		});
		$('#tt').tree({
		    url: "/sys/org/tree",
		    method: "GET",
		    loadFilter: function(data){
		    	console.log(data);
		        if (data){
		            return data;
		        } else {
		            return data;
		        }
		    },
		    onClick:function(node){
		    	$("#org_data_grid").datagrid({
		    		    queryParams:{id:node.id},
		            border : false,  
		            rownumbers : false,  
		            animate: true,  
		            striped : true,
		            toolbar:"#tb_org_data",
		            fitColumn:true,
		            url:'/sys/orgs',
		            method:"GET",
		            columns:[[
		                      {field:'id',title:'序号',width:'20%'},
		                      {field:'org_name',title:'组织名称',width:'40%'},
		                      {field:'type_code',title:'种类代码',width:'15%'},
		                      {field:'type_value',title:'种类名称',width:'15%'}
		                  ]]
		    	});
		    	$("#user_data_grid").datagrid({
		    		queryParams:{org_id:node.id},
		            border : false,  
		            rownumbers : true,  
		            animate: true,  
		            striped : true,
		            pagination:true,
		            toolbar:"#tb_user_data",
		            url:'/sys/org/users',
		            method:"get",
		            columns:[[
		                      {field:'id',title:'序号',width:'10%'},
		                      {field:'username',title:'帐号',width:'25%'},
		                      {field:'nickname',title:'昵称',width:'25%'},
		                      {field:'create_time',title:'创建日期',width:'35%'}
		                  ]]
		    	});
		    }
		});
		$("#tb_org_edit").on("click",function(){
			var node = $('#tt').tree('getSelected');
			if(node){
				url="/sys/org/add";
				var detail={parent_id:node.parent_id,org_name:node.text,id:node.id};
				$('#fm').form('clear');
				$("#dlg_add").dialog('open').dialog('setTitle',"修改组织机构");
				$('#fm').form('load',detail);
			}else{
				$.messager.show({	// show error message
					title: '错误提示',
					msg: "没有发现选中的部门组织"
				});
			}
		});
		$("#tb_org_delete").on("click",function(){
			var node = $('#tt').tree('getSelected');
			if(node){
				$.messager.confirm('确认信息','确定要删除这个组织吗?',function(r){
					if(r){
						$.post('/sys/org/delete',{id:node.id},function(result){
							if (result.result_msg){
								$('#tt').tree('reload');	
								$('#org_data_grid').datagrid('loadData',{total:0,rows:[]});
								$('#user_data_grid').datagrid('loadData',{total:0,rows:[]});
							}
							$.messager.show({	// show error message
								title: '消息提醒',
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
		$("#tb_user_delete").on("click",function(){
			var org = $('#tt').tree('getSelected');
			var user = $('#user_data_grid').datagrid('getSelected');
			if(!user){
				$.messager.show({	
					title: '错误提示',
					msg: "没有发现选中的用户！"
				});
				return;
			}
			if(!org){
				$.messager.show({	
					title: '错误提示',
					msg: "没有发现选中的部门！"
				});
				return;
			}
				$.messager.confirm('确认信息','确定要删除这个用户吗?',function(r){
					if(r){
						$.post('/sys/org/delete/user',{user_id:user.id,org_id:org.id},function(result){
							$('#user_data_grid').datagrid('reload');	
							$.messager.show({	// show error message
								title: "提示信息",
								msg: result.result_msg
							});
						},'json');
					}
				});
		});
	});