t_url='';
$(function(){

	 
	$('#add').on('click',function(){
		$('#dlg').dialog('open').dialog('setTitle','编辑用户');
		$('#fm').form('clear');
		$('#deptName').textbox('enable');
		url='/department/add';
	});
	$('#edit').on('click',function(){
		var row=$('#dg').datagrid('getSelected');
		if(!row){
			$.messager.show({
				title:'Error',
				msg:'你没有选中任何一列'
			});
			return;
		}
		$('#dlg').dialog('open').dialog('setTitle','编辑用户');
		$('#fm').form('clear');
		t_url='/department/update';
		$('#fm').form('load',row);
	});
	$('#delete').on('click',function(){
		var row=$('#dg').datagrid('getSelected');
		if(row){
			$.messager.confirm('确认消息','确认要删除吗？',function(r){
				if(r){
					$.ajax({
						url:'/department/delete/'+row.id,
						method:'DELETE',
						async:false,
						dataType:'json',
						success:function(result){
							if(result.result_status){
								$('#dg').datagrid('reload');
							}else{
								$.messager.show({
									title:'Error',
									msg:result.result.msg
								});
							}
						}
					});
				}
			})
		}
	});
	$('#save').on('click',function(){
		$('#fm').form('submit',{
			url:t_url,
			onSubmit:function(){
				return $(this).form('validate');
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.errorMsg){
					$.messager.show({
						title:'Error',
						msg:result.errorMsg
					});
				}else{
					$('#dlg').dialog('close');
					$('#dg').datagrid('reload');
				}
			}
		
		})
	});
	$('#search').on('click',function(){
		$('#dg').datagrid('load',{
			id:$('#toolbar_id').val(),
			deptName:$('#toolbar_name').val()
		});
	})
});

function fop(value,row,index){
	return '<button onclick="view('+row.id+')">查看部门人员</button>';
}

function view(value){
	$('#dlg_users').dialog('open').dialog('setTitle','部门人员');
	$('#dg_users').datagrid('load',{
		deptId: value
	});
}


