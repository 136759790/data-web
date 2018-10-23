var t_url='';
$(function(){
	$('#buy').on('click',function(){
		var row=$('#dg').datagrid('getSelected');
		if(!row){
			$.messager.show({
				title:'Error',
				msg:'没有选中任何一列'
			});
			return;
		}
		
		$.ajax({
			url:'/order/buy?orderId='+row.id,
			type:'POST',
			dataType:'json',
			async:false,
			success:function(result){
				if(result.result_status){
					$.messager.show({
						title:'成功',
						msg:'购买成功'
					});
					$('#dg').datagrid('reload');
				}else{
					$.messager.show({
						title:'失败',
						msg:result.result_msg
					});
				}
			}
		});
	});
	
	$('#add').on('click',function(){
		$('#dlg').dialog('open').dialog('setTitle','新建教师');
		$('#fm').form('clear');
		$('#name').textbox('enable');
		t_url='/order/add';
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
		$('#dlg').dialog('open').dialog('setTitle','编辑教师');
		$('#fm').form('clear'); 
		t_url='/order/update';
		$('#fm').form('load',row);
		$('#name').textbox('disable');
	});
	$('#delete').on('click',function(){
		var row=$('#dg').datagrid('getSelected');
		if(row){
			$.messager.confirm('确认信息','确认要删除这个用户吗？',function(r){
				if(r){
					$.ajax({
						url:'/order/delete/'+row.id,
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
	$('#do_search').on('click',function(){
		$('#dg').datagrid('load',{
			id:$('#toolbar_id').val(),
			name:$('#toolbar_name').val()
		});
	});
});
function formatterGender(value,row,index){
    if(value==0){
      return '未支付';
    }else{
      return '已支付';
    }
  }



