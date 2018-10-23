var t_url='';
$(function(){
	//toolbar_time
	$('#toolbar_time').datebox('setValue', getToday(new Date()));	
	$('#add').on('click',function(){
		$('#dlg').dialog('open').dialog('setTitle','新建教室');
		$('#fm').form('clear');
		$('#name').textbox('enable');
		t_url='/room/add';
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
		$('#dlg').dialog('open').dialog('setTitle','编辑教室');
		$('#fm').form('clear'); 
		t_url='/room/update';
		$('#fm').form('load',row);
		$('#name').textbox('disable');
	});
	$('#delete').on('click',function(){
		var row=$('#dg').datagrid('getSelected');
		if(row){
			$.messager.confirm('确认信息','确认要删除这个教室吗？',function(r){
				if(r){
					$.ajax({
						url:'/room/delete/'+row.id,
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

function f_order(value,row,index){
	if(value==0){
		return '未预约';
	}else{
		return '已预约';
	}
}

function f_btn(value,row,index){
	if(row.code==0){
		return '<button onclick="makeOrder('+row.id+')" >预约</button>';
	}else{
		return '不可预约';
	}
}

//在教室表中选择座位
function order(value,row,index){
	return '<button onclick="view(\''+row.room_pic+'\','+row.id+')" >选择座位</button>';
}
var order_room_id;
function view(value,room_id){
	debugger
	order_room_id=room_id;
	$('#dlg_seats').window({    
	    title: '座位位置图',    
	    fit:true,   
	    closed: false,    
	    cache: false,    
	    href: value,    
	    modal: true   
	});
	//$('#dlg_seats').dialog('open').dialog('setTitle','座位信息');
}

//在教室表重置预约信息
function reset(value,row,index){
	return '<button class="easyui-linkbutton" onclick="operate('+row.id+')" >重置</button>';
}

function operate(value){
	
	$.ajax({
		url:'/seat/reset',
		data:{
			id:value
		},
		type:'POST',
		async:false,
		dataType:'json',
		success:function(data){
			if(data.result_status){
				$.messager.show({
					title:'提示',
					msg:'重置成功'
				});
			}
		}
	});
}
function fn_rest(value,row,index){
	var vv= row.rest_st;
	if(!vv){
		return  row.total_st;
	}
	return vv;
}
function getToday(mydate){
	   var str = "" + mydate.getFullYear() + "-";
	   str += (mydate.getMonth()+1) + "-";
	   str += mydate.getDate();
	   return str;
}


