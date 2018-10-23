
t_url='';
$(function(){
	//上来默认加载一次数据
	var isOrderAdmin=common.isAdmin("admin");
	var columns =[];
	if(isOrderAdmin){
		columns=[[    
			        {field:'id',title:'ID',width:10,align:'center'},    
			        {field:'nickname',title:'预约人',width:50,align:'center'},   
			        {field:'room_name',title:'教室名称',width:50,align:'center'},    
			        {field:'seat_no',title:'座位号',width:50,align:'center'},   
			        {field:'order_state',title:'预约状态',width:50,align:'center'},   
			        {field:'submit_time',title:'提交时间',width:50,align:'center'},   
			        {field:'order_time',title:'预约时间',width:50,align:'center'},   
			        {field:'operate_time',title:'操作时间',width:50,align:'center'},   
			        {field:'operate',title:'操作',width:50,align:'center',formatter: function(value,row,index){
			        	return '<button onclick="confirm('+row.id+')">确认座位</button>';
					}} 
			    ]];
	}else{
		columns=[[    
		          {field:'id',title:'ID',width:10,align:'center'},    
		          {field:'nickname',title:'预约人',width:50,align:'center'},   
		          {field:'room_name',title:'教室名称',width:50,align:'center'},    
		          {field:'seat_no',title:'座位号',width:50,align:'center'},  
		          {field:'order_state',title:'预约状态',width:50,align:'center',formatter: function(value,row,index){
		        	  //0:预约,1:撤销,2:就位,3预约未就位
		        	  if(value==0){
		        		  return "预约";
		        	  }else if(value == 1){
		        		  return "撤销";
		        	  }else if(value == 2){
		        		  return "就位";
		        	  }else{
		        		  return "预约未就位";
		        	  }
					}
		          },  
		          {field:'submit_time',title:'提交时间',width:50,align:'center'},   
		          {field:'order_time',title:'预约时间',width:50,align:'center'},   
		          {field:'operate_time',title:'操作时间',width:50,align:'center'}  
		          ]];
		
	}
	$('#dg').datagrid({    
	    url:'/reser/reseres',  
	    method:'get',
	    columns:columns,
	    toolbar:"#toolbar",
	    rownumbers:true,
	    fitColumns:true,
	    pagination:true,
	    striped:true
	}); 
    $('#add').on('click',function(){
      if(!isOrderAdmin){
    	  alert("您不是管理员，只能搜索");
    	  return;
      }
      $('#dlg').dialog('open').dialog('setTitle','新建用户');
      $('#fm').form('clear');
      $('#name').textbox('enable');
      t_url = '/reser/add';
    });
    $('#edit').on("click",function(){
    	if(!isOrderAdmin){
      	  alert("您不是管理员，只能搜索");
      	  return;
        }
      var row = $('#dg').datagrid('getSelected');
      if(!row){
        $.messager.show({ // show error message
          title: 'Error',
          msg: "您没有选择任何一列"
        });
        return;
      }
      $('#dlg').dialog('open').dialog('setTitle','编辑用户');
      $('#fm').form('clear');
      t_url = '/reser/update';
      $('#fm').form('load',row);
      $('#name').textbox('enable');
    });
    
    
    $('#delete').on("click",function(){
    	if(!isOrderAdmin){
      	  alert("您不是管理员，只能搜索");
      	  return;
        }
      var row = $('#dg').datagrid('getSelected');
      if (row){
        $.messager.confirm('确认信息','确定要删除这个记录吗?',function(r){
          if (r){
        	$.ajax({
        		url:'/reser/delete/'+row.id,
        		method:'POST',
        		async:false,
        		dataType:"json",
        		success:function(result){
        			if (result.result_status){
                        $('#dg').datagrid('reload');  // reload the user data
                      } else {
                        $.messager.show({ // show error message
                          title: 'Error',
                          msg: result.result_msg
                        });
                      }
        		}
        	});
          }
        });
      }
    });
    $('#save').on("click",function(){
      $('#fm').form('submit',{
        url: t_url,
        onSubmit: function(){
          return $(this).form('validate');
        },
        success: function(result){
          var result = eval('('+result+')');
          if (result.errorMsg){
            $.messager.show({
              title: 'Error',
              msg: result.errorMsg
            });
          } else {
            $('#dlg').dialog('close');    // close the dialog
            $('#dg').datagrid('reload');  // reload the user data
          }
        }
      });
    });
    $('#do_search').on('click',function(){
      $("#dg").datagrid('load',{
        id:$('#toolbar_id').val(),
        name:$('#toolbar_name').val(),
      });
    });
  });


function operate(value,row,index){
	return '<button onclick="confirm('+row.id+')">确认座位</button>'
}

function confirm(value){
		$.ajax({
			url:'/reser/confirm',
			type:'POST',
			data:{
				id:value
			},
			dataType:'json',
			async:false,
			success:function(result){
				/*alert(result.result_status);*/
    			if (result.result_status){
    				
    				$.messager.show({
    					title: '成功',
    					msg: '操作成功'
    				});
    			} else {
                    $.messager.show({
                      title: '失败',
                      msg: '操作失败'
                    });
                  }
			}
		});
		$('#dg').datagrid('load',{});
}
function getState(value,row,index){
	console.log(value);
	return value;
	
}
