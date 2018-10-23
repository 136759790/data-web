$(function(){
		$("#add").on("click",function(){
			$('#dlg').dialog('open').dialog('setTitle','新建角色');
			$('#fm').form('clear');
		});
		$("#edit").on("click",function(){
			var row = $('#dg').datagrid('getSelected');
			if(!row){
				$.messager.show({	// show error message
					title: 'Error',
					msg: "您没有选择任何一列"
				});
				return;
			}
			$('#dlg').dialog('open').dialog('setTitle','编辑角色');
			$('#fm').form('clear');
			url = '${pageContext.request.contextPath}/user/saveOrUpdateRole.do';
			$('#fm').form('load',row);
		});
		$("#delete").on("click",function(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.confirm('确认信息','确定要删除这个角色吗?',function(r){
					if (r){
						$.post('/sys/role/delete',{id:row.id},function(result){
							$('#dg').datagrid('reload');	// reload the user data
							$.messager.show({	// show error message
								title: '消息提醒',
								msg: result.result_msg
							});
						},'json');
					}
				});
			}
		});
		$("#save").on("click",function(){
			$('#fm').form('submit',{
				url: "/sys/role/add",
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					$.messager.show({
						title: '消息提醒',
						msg: result.result_msg
					});
					$('#dlg').dialog('close');		// close the dialog
					$('#dg').datagrid('reload');	// reload the user data
				}
			});
		});
		$("#do_search").on("click",function(){
			$("#dg").datagrid('load',{
				id:$("#id").val(),
				role_name:$("#role_name").val(),
				role_code:$("#role_code").val()
			});
		});
		$("#setOrg").on("click",function(){
			var row = $('#dg').datagrid('getSelected');
			if(!row){
        $.messager.show({ // show error message
          title: '错误',
          msg: "您没有选择任何一列"
        }); 
        return;
      }
			//已有人员datagrid
			$('#manage_org').dialog('open');
			$('#table_role_detail').datagrid('loadData', { rows: [] });  
			$('#table_role_detail').datagrid('appendRow',row);
      $("#dg_list_my_user").datagrid({
          border : false,  
          rownumbers : true,  
          animate: true,  
          striped : true,
          toolbar:"#tb_list_my_user",
          url:'/sys/users?role_id='+row.id,
          method:'get',
          pagination:"true",
          columns:[[
                    {field:'id',title:'序号',width:'10%'},
                    {field:'username',title:'账号',width:'20%'},
                    {field:'nickname',title:'姓名',width:'20%'},
                    {field:'orgs',title:'所属部门',width:'30%'}
                ]]
      });
		});
		$("#setRes").on("click",function(){
			var row = $('#dg').datagrid('getSelected');
			if(!row){
				$.messager.show({	// show error message
					title: '错误',
					msg: "您没有选择任何一列"
				}); 
				return;
			}
			$('#dlg_set_res').dialog('open');
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
				onContextMenu: function(e, node){
					e.preventDefault();
					$('#res_tree').tree('select', node.target);
					$('#menu_all_tree').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
				}
			});
			$('#dg_tree_my_res').tree({
				url: "/sys/res/mytree",
				method:"get",
				loadFilter: function(data){
					console.log(data);
					if (data.d){
						return data.d;
					} else {
						return data;
					}
				},
				onContextMenu: function(e, node){
					e.preventDefault();
					$('#dg_tree_my_res').tree('select', node.target);
					$('#menu_my_tree').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
				}
			});
			$('#table_role_detail_res').datagrid('loadData', { rows: [] });  
			$('#table_role_detail_res').datagrid('appendRow',row);
		});
		$("#btn_org_set").on("click",function(){
			$('#fm_manage_org').form('submit',{
				url: "${pageContext.request.contextPath}/user/manageUser.do",
				success: function(result){
					$('#manage_org').dialog('close');	
					$.messager.show({
						title: result.title,
						msg: result.msg
					});
					$("#dg").datagrid("reload");
				}
			});
		});
		$("#do_search_all_user").on("click",function(){
			$("#dg_list_all_user").datagrid('load',{
				id:$("#all_user_id").val(),
				username:$("#all_username").val(),
				nickname:$("#all_nickname").val()
			});
		});
		$("#do_search_my_user").on("click",function(){
			$("#dg_list_my_user").datagrid('load',{
				id:$("#my_user_id").val(),
				username:$("#my_username").val(),
				nickname:$("#my_nickname").val()
			});
		});
		$("#do_add_user").on("click",function(){
			var row = $('#dg_list_all_user').datagrid('getSelected');
			var row_role=$('#dg').datagrid('getSelected');
			if(!row){
				$.messager.show({	// show error message
					title: '错误',
					msg: "您没有选择任何一列"
				}); 
				return;
			}else{
				$.ajax({
					url:"/sys/role/user",
					data:{"user_id":row.id,"role_id":row_role.id},
					type:"post",
					dataType:"json",
					success:function(data){
						$.messager.show({
							title: "提示",
							msg: data.result_msg
						}); 
						$('#dg_list_my_user').datagrid('reload');
					},
					error:function(e){
						console.log(e);
						$.messager.show({	// show error message
							title: '错误',
							msg: "内部错误，请联系管理员！"
						});
					}
				});
			}
		});
		$("#do_remove_user").on("click",function(){
			var row = $('#dg_list_my_user').datagrid('getSelected');
			var row_role=$('#dg').datagrid('getSelected');
			if(!row){
				$.messager.show({	// show error message
					title: '错误',
					msg: "您没有选择任何一列"
				}); 
				return;
			}else{
				$.ajax({
					url:"/sys/role/user/delete",
					data:{"user_id":row.id,"role_id":row_role.id},
					type:"post",
					dataType:"json",
					success:function(data){
						$.messager.show({
							title: "提示",
							msg: data.result_msg
						});
						$('#dg_list_my_user').datagrid('reload');
					},
					error:function(e){
						console.log(e);
						$.messager.show({	// show error message
							title: '错误',
							msg: "内部错误，请联系管理员！"
						});
					}
				});
			}
		});
	});

	function formatEnable(obj){
		if(obj=='Y'){return "可用";}
		if(obj=='N'){return "禁用";}
	}
	function formatOrg(obj){
		if(obj){
			return obj.org_name;
		}else{
			return "";
		}
	}
	function append(id){
		var res= $("#"+id).tree('getSelected');
		var role=$('#dg').datagrid('getSelected');
		addResToMy(role.id,res.id);
	}
	function remove(id){
		var res= $("#"+id).tree('getSelected');
		var role=$('#dg').datagrid('getSelected');
		removeRes(role.id,res.id);
	}
	function addResToMy(role_id,res_id){
		$.ajax({
			url:"/sys/role/res",
			data:{res_id:res_id,role_id:role_id},
			type:"post",
			dataType:"json",
			success:function(data){
				$.messager.show({
					title: "提示",
					msg: data.result_msg
				});
				$('#dg_tree_my_res').tree('reload');
			}
		});
	}
	function removeRes(role_id,res_id){
		$.ajax({
			url:"/sys/role/res/delete",
			data:{res_id:res_id,role_id:role_id},
			type:"post",
			dataType:"json",
			success:function(data){
				$.messager.show({
					title: "提示",
					msg: data.result_msg
				});
				$('#dg_tree_my_res').tree('reload');
			}
		});
	}
