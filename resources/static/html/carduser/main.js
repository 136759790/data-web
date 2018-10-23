var t_url='';
$(function(){
	$('#add').on('click',function(){
		$('#dlg').dialog('open').dialog('setTitle','新建一卡通用户关系');
		$('#fm').form('clear');
		t_url='/carduser/add';
	});
	$('#do_search').on('click',function(){
		$("#dg").datagrid('load',{
	        order_time:$('#toolbar_time').val()
	    });
	});
	$('#edit').on('click',function(){
		var row=$('#dg').datagrid('getSelected');
		if(!row){
			$.messager.show({
				title:'Error',
				msg:'没有选中任何一列'
			});
			return;
		}
		$('#dlg').dialog('open').dialog('setTitle','编辑一卡通用户关系');
		$('#fm').form('clear'); 
		t_url='/carduser/update';
		$('#fm').form('load',row);
	});
	$('#delete').on('click',function(){
		var row=$('#dg').datagrid('getSelected');
		if(row){
			$.messager.confirm('确认信息','确认要删除这个关系吗？',function(r){
				if(r){
					$.ajax({
						url:'/carduser/delete/'+row.id,
						method:'POST',
						async:false,
						dataType:'json',
						success:function(result){
							if(result.result_status){
								$('#dg').datagrid('reload');
							}else{
								$.messager.show({
									title:'Error',
									msg:result.result_msg
								});
							}
						}
						
					});
				}
			});
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
		});
	});

});

