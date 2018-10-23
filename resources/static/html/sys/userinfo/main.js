$(function(){
    $("#add").on("click",function(){
      $('#dlg').dialog('open').dialog('setTitle','新建用户');
      $('#fm').form('clear');
      $("#username").textbox('enable');
      url = '/sys/add/user';
    });
    $("#edit").on("click",function(){
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
      url = '/sys/update/user';
      $('#fm').form('load',row);
      $("#username").textbox('disable');
    });
    $("#delete").on("click",function(){
      var row = $('#dg').datagrid('getSelected');
      if (row){
        $.messager.confirm('确认信息','确定要删除这个用户吗?',function(r){
          if (r){
        	$.ajax({
        		url:'/sys/user/'+row.id,
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
    $("#save").on("click",function(){
      $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
          return $(this).form('validate');
        },
        success: function(result){
          console.log(result);
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
    $("#do_search").on("click",function(){
      $("#dg").datagrid('load',{
        id:$("#id").val(),
        username:$("#username").val(),
        nickname:$("#nickname").val()
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
      $('#manage_org').dialog('open');
      $("#fm_manage_org").form('clear');
      var org_ids=[];
      if(row && row.orgs.length>0){
      	$.each(row.orgs,function(i,e){
          org_ids.push(e.id);
      	});
      }
      $("#fm_manage_org").form('load',{user_id:row.id,org_id:org_ids});
    });
    $("#btn_org_set").on("click",function(){
      $('#fm_manage_org').form('submit',{
        url: "/sys/org/user",
        success: function(result){
          $('#manage_org').dialog('close'); 
          $.messager.show({
            title: "提示信息",
            msg: "设置成功"
          });
          console.log(result.result_msg);
          $("#dg").datagrid("reload");
        }
      });
    });
  });
  