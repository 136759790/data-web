var common =new Common();
function Common(){
	
}
Common.prototype.isAdmin=function(role){
	var flag=false;
	$.ajax({
		url:'/sys/role/'+role,
		type:'get',
		async:false,
		success:function(result){
			flag = result;
		}
	});
	return flag;
};
