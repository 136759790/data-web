function addTab(title, url){
      if ($('#tabsInfo').tabs('exists', title)){
          $('#tabsInfo').tabs('select', title);
      } else {
          var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
          $('#tabsInfo').tabs('add',{
              title:title,
              content:content,
              closable:true
          });
      }
  }
  $(function(){
	  $.ajax({
		  url:"/sys/res/mytree",
		  type:"get",
		  dataType:"json",
		  success:function(data){
			  $.each(data,function(i,e){
				  $('#parent_menu').accordion('add',
					{    
					  title : e.text,  
                      iconCls : e.icon,  
                      selected : true,
                      content : '<div id=\"menu'+i+'\" iconCls=\"icon-menu1\"  name="'+e.text+'"></div>',    
					}
				  );  
				  $("#menu"+i).tree({data:e.children});
				  $("#menu"+i).tree({
					  onClick: function(node){
						  if(node.attributes.res_type == 'link'){
							  addTab(node.text,node.attributes.res_url);  
						  }
					  }
				  });
			  });
		  }
	  });
	  //处理登录用户信息
	  $.get("/sys/user/login",function(data){
		  if(data.result_status == 1){
			  $("#current_user").text(data.nickname);
			  $('#current_user').menubutton({    
				    iconCls: 'icon-man',    
				    menu: '#mm'   ,
				    hasDownArrow:'true',
				    menuAlign:'left'
			  }); 
		  }
	  });
	  //用户登出
	  $("#logout").on("click",function(){
		 $.post('/sys/user/logout',function(data){
			 window.location.reload();
		 }); 
	  });
  });