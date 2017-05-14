$(function(){
	
	getWithDrawBalance();
	$("#saveBtn").click(saveWithDraw);
});

function getWithDrawBalance(){
	$.ajax({
		url: path + "/systemWithdraw/getWithDrawBalance.html",
		type: "get",
		dataType: "json",
		data: {
		},
		success: function(data){
			document.getElementById("withdraw_amt").innerHTML=data.balance;
		},
		error: function(request){
		}
 });
}

function saveWithDraw(){
	var url="/systemWithdraw/saveSystemWithDraw.html";
	$('#form').form(
			'submit',{  
        url:path + url,  
        onSubmit:function(){
            return $(this).form('validate');  
        },  
        success:function(data){ 
        	var obj = $.parseJSON(data);
			if (obj.success) {
				$.messager.alert('提示', "申请提现成功!");
			}
			if (!obj.success) {
				$.messager.alert('提示', "申请提现失败!");					
				
			}
        }  
    }); 
}