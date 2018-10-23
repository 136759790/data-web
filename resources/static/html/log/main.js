$(function(){
	 var timer1={}; 
	$('#start').on('click',function(){
		var time=5000;
		if($("#refresh_time").val()){
			time=parseInt($("#refresh_time").val());
		}
		timer1=window.setInterval(getLog,time*1000); 
	});
	$('#stop').on('click',function(){
		window.clearInterval(timer1); 
	});
	$('#clear').on('click',function(){
		$("#logs").html("");
	});
	
});
function getLog(){
	$.ajax({
		url:'/log/log',
		data:{
			server:$("#server").val(),
			page_no:$("#page_no").val()
		},
		method:'GET',
		async:false,
		dataType:'text',
		success:function(result){
			$("#logs").append(result);
			$('#logs').scrollTop( $('#logs')[0].scrollHeight )
		}
	});
}



