
t_url='';
$(function(){
	
    $('#add').on('click',function(){
      $('#dlg').dialog('open').dialog('setTitle','新建进出记录表');
      $('#fm').form('clear');
      $('#name').textbox('enable');
      t_url = '/record/add';
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
      $('#dlg').dialog('open').dialog('setTitle','新建进出记录表');
      $('#fm').form('clear');
      t_url = '/record/update';
      $('#fm').form('load',row);
      $('#name').textbox('enable');
    });
    
    
    $('#delete').on("click",function(){
      var row = $('#dg').datagrid('getSelected');
      if (row){
        $.messager.confirm('确认信息','确定要删除这个记录吗?',function(r){
          if (r){
        	$.ajax({
        		url:'/record/delete/'+row.id,
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
function status(value,row,index){
	if(value==1){
		return '在座';
	}else{
		return '离开'
	}
}

