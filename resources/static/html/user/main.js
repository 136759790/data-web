
t_url='';
$(function(){
	$('#gender').combobox('setValue', 0);

	 $('#deptId').combobox({    
		    url:'/department/options',    
		    valueField:'id',    
		    textField:'text', 
		    method:'GET'
	 });
	
    $('#add').on('click',function(){
      $('#dlg').dialog('open').dialog('setTitle','新建用户');
      $('#fm').form('clear');
      $('#name').textbox('enable');
      t_url = '/user/add';
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
      t_url = '/user/update';
      $('#fm').form('load',row);
      $('#name').textbox('enable');
    });
    
    $('#a_photo').on("click",function(){
      var row = $('#dg').datagrid('getSelected');
      if(!row){
        $.messager.show({ // show error message
          title: 'Error',
          msg: "您没有选择任何一列"
        });
        return;
      }
     /*alert(row.photo_path);*/
     $("#img_photo").attr("src","/user/photo/"+row.id);
     $('#dlg_photo').dialog('open').dialog('setTitle','查看头像');
    });
    
    $('#delete').on("click",function(){
      var row = $('#dg').datagrid('getSelected');
      if (row){
        $.messager.confirm('确认信息','确定要删除这个用户吗?',function(r){
          if (r){
        	$.ajax({
        		url:'/user/delete/'+row.id,
        		method:'DELETE',
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
  function formatterDept(value,row,index){
	  if(row.dept){
		  return row.dept.deptName;
	  }else{
		  return '';
	  }
  }
  function formatterGender(value,row,index){
    if(value==0){
      return '女';
    }else{
      return '男';
    }
  }
 
