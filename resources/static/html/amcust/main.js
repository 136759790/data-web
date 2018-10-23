function change (obj){
	var $obj=$(obj);
	if($obj.attr("o_type")=='reduce'){
		if($obj.next().val()==0){
			$.messager.show({
				title:'提示：',
				msg:'不能再减了！'
			});
			return;
		}
		$obj.next().val(parseInt($obj.next().val())-1);
	}else{
		$obj.prev().val(parseInt($obj.prev().val())+1);
	}
}

function fop(value,row,index){
	return '<button o_type="reduce" onclick="change(this)">-</button>'
			+'<input id_1="'+row.id+'" value="1" style="width:50px;" />' 
			+'<button o_type="add" onclick="change(this)">+</button>';
}

var t_url='';
$(function(){
	
	$('#makeOrder').on('click',function(){
		var rows=$('#dg').datagrid('getSelections');
		if(rows.length==0){
			$.messager.show({
				title:'Error',
				msg:'没有选中任何一列'
			});
			return;
		}
		var ids='';
		var counts='';
		$.each(rows,function(index,ele){
			if(index==0){
				ids+='bookIds='+ele.id;
			}else{
				ids+='&bookIds='+ele.id;
			}
			counts+='&counts='+$('[id_1='+ele.id+']').val();
		});
		$.ajax({
			url:'/order/neworder?'+ids+counts,
			type:'POST',
			async:false,
			dataType:'json',
			success:function(result){
				if(result.result_status){
					$.messager.show({
						title:'成功',
						msg:'下单成功'
					});
				}else{
					$.messager.show({
						title:'Error',
						msg:result.result_msg
					});
				}
			}
			
		});
	});
	$('#add').on('click',function(){
		$('#dlg').dialog('open').dialog('setTitle','新建书本');
		$('#fm').form('clear');
		$('#name').textbox('enable');
		t_url='/book/add';
	});
	$('#add_json').on('click',function(){
		$('#dlg_json').dialog('open').dialog('setTitle','上传json');
		$('#fm_json').form('clear');
		$('#name_json').textbox('enable');
		t_url='/weather/add';
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
		$('#dlg').dialog('open').dialog('setTitle','编辑书本');
		$('#fm').form('clear'); 
		t_url='/book/update';
		$('#fm').form('load',row);
	});
	$('#delete').on('click',function(){
		var row=$('#dg').datagrid('getSelected');
		if(row){
			$.messager.confirm('确认信息','确认要删除这个用户吗？',function(r){
				if(r){
					$.ajax({
						url:'/book/delete'+row.id,
						method:'DELETE',
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
			url:"/amcust/uploadXml",
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
	$('#save_json').on('click',function(){
		$('#fm_json').form('submit',{
			url:"/weather/upLoadWeather",
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
					$('#dlg_json').dialog('close');
					$('#dg').datagrid('reload');
				}
			}
		});
	});
	$('#do_search').on('click',function(){
		$('#dg').datagrid('load',{
			id:$('#toolbar_id').val(),
			bookName:$('#toolbar_name').val()
		});
	});
});