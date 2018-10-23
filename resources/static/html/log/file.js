$(function(){
	$("#get").on("click",function(){
		getLogTree();
	});
	$("#download").on("click",function(){
		var node= $("#log_tree").tree('getSelected');
		if(!node || !node.id){
			$.messager.show({
				title:'错误',
				msg:'请选择日志文件'
			});
			return;
		}
		download(node.id);
	});
	$("#view").on("click",function(){
		var node= $("#log_tree").tree('getSelected');
		if(!node || !node.id){
			$.messager.show({
				title:'错误',
				msg:'请选择日志文件'
			});
			return;
		}
		loadLog(node.id);
	});
});
/**
 * ajax获取路径下的日志文件
 */
function getLogTree(){
	var path=$("#path").val();
	var suffix=$("#suffix").val();
	if(!path || !suffix){
		$.messager.show({
			title:'错误',
			msg:'路径或者后缀名不能为空'
		});
		return;
	}
	$.ajax({
		url:'/log/logTree',
		data:{path:path,suffix:suffix},
		method:'GET',
		async:false,
		dataType:'json',
		success:function(result){
			$('#log_tree').tree('loadData',result);
			$('#log_tree').tree({
//				onClick: function(node){
//					if(node.text.indexOf(".log")> -1){
//						loadLog(node.id);
//					}
//				}
			});
		}
	});
}
function download(id){
	$("#fileName").val(id);
	$("#fm").submit();
}
function loadLog(id){
	$('#dlg').dialog('open').dialog('setTitle','日志内容');
	loadLogDada(id);
}
function loadLogDada(path){
	$.ajax({
		url:'/log/loadLog',
		data:{path:path},
		method:'GET',
		async:false,
		dataType:'json',
		success:function(result){
			$("#logs").html("");
			$("#logs").append(result.result_msg);
		}
	});
}


