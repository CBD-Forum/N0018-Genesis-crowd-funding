$(function(){
	if (loanNo) {
		$.ajax({
			type : "POST",
			url : path + '/crowdfunding/getStockBackByLoanNo.html',
			dataType:'json',
			data : {
				'loanNo' : loanNo
			},
			success : function(data) {
				$('#userForm').form('load', data);
			}
		});
	}
	
	$("#userForm").validate({
        submitHandler:function(form){$('#userForm').form('submit', {
			url : path + '/crowdfunding/updateEquityBackSet.html',
			queryParams: {
				'loanNo':loanNo
			},
			success : function(data) {
				var obj = $.parseJSON(data);
				if (obj.success) {
					$.messager.alert('提示','保存成功！<a href="'+path+'/crowdfund.auditing.html">返回列表</a>');   
				}
				if (!obj.success) {
					if (obj.msg) {
						$.messager.alert('提示', obj.msg);
					}
				}
			}
		});}
    }); 
	$('#saveBtn').click(function() {
		$('#userForm').submit();
	});
	
	createWebUploader('photoBtn','photoDiv','photo');
});

function getUserLevel(){
	//获取标的类型
	$.ajax({
		url: path + "/dictionary/getDic.html",
		type: "post",
		dataType: "json",
		data: {"type": "user_level"},
		success: function(data){
			getLoanTypeFn("userLevel", data["rows"]);
		},
		error: function(){
			console.log("获取用户等级异常");
		}
	});
}
