$(function(){
	$('#add form input').width(300);
	
	document.onkeypress=function(){
		if(event.keyCode == 13){
			return false;
		}
	}
	
	$.ajax({
		url: path + '/sysconfig/getlist.html?displayName=sms_',
		type: "post",
		dataType: "json",
		success: function(data){
			$(data.rows).each(function(){
				$('#'+this.displayName).form('load', this);
			});
		},
		error: function(){
			
		}
	});
	
	$('#saveBtn').click(function() {
		var saveNum=0;
		$('#add form').form('submit', {
			url : path + '/sysconfig/modify.html',
			success : function(data) {
				var obj = $.parseJSON(data);
				if (obj.success) {
					saveNum++;
				}
				if (saveNum==6) {
					$.messager.alert('提示','保存成功！');   
				}
				if (!obj.success) {
					if (obj.msg) {
						$.messager.alert('提示', obj.msg);
					}
				}
			}
		});
	});
});