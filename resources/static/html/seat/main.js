
t_url='';
$(function(){
	 $('#order_time').datebox('setValue', getToday(new Date()));
	 $('#q_room_id').combobox({    
		    url:'/seat/options',    
		    valueField:'id',    
		    textField:'text', 
		    method:'GET'
	 });
	 
	 $('#room_id').combobox({    
		 url:'/seat/options',    
		 valueField:'id',    
		 textField:'text', 
		 method:'GET'
	 });
	
    $('#add').on('click',function(){
      $('#dlg').dialog('open').dialog('setTitle','新建用户');
      $('#fm').form('clear');
      $('#name').textbox('enable');
      t_url = '/seat/add';
    });
    $('#edit').on("click",function(){
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
      t_url = '/seat/update';
      $('#fm').form('load',row);
      $('#name').textbox('enable');
    });
    
    
    $('#delete').on("click",function(){
      var row = $('#dg').datagrid('getSelected');
      if (row){
        $.messager.confirm('确认信息','确定要删除这个用户吗?',function(r){
          if (r){
        	$.ajax({
        		url:'/seat/delete/'+row.id,
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
        q_room_id:$('#room_id').val(),
        order_date:$('#order_time').val()
      });
    });
  });
  function fName(value,row,index){
	  if(row.room){
		  return row.room.name;
	  }else{
		  return '';
	  }
  }
  function formatterGender(value,row,index){
    if(value==0){
      return '未预约';
    }else{
      return '预约成功';
    }
  }
  function getToday(mydate){
	   var str = "" + mydate.getFullYear() + "-";
	   str += (mydate.getMonth()+1) + "-";
	   str += mydate.getDate();
	   return str;
  }
