if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getUserBank();//查询绑定的银行卡
	$("#deleteBtn").click(deleteBankcard);//删除银行卡
	
});

//删除银行卡
function deleteBankcard(_this){
	AlertDialog.confirm(modifyDelete, null, "您确定要删除吗?","确定", "取消");
	function modifyDelete(){
		$.ajax({
			url: path + "/sxyPay/bindBnak/unbundlingBank.html",
			type: "post",
			dataType: "json",
			data:{
				"id":$(_this).attr("aid")
			},
			success: function(data){
				if(data["success"]){
					AlertDialog.tip("删除成功！");
					window.location.reload();
				}else{
					AlertDialog.tip("删除失败！");
					return false;
				}
			},
			error: function(request){
				console.log("删除收货地址异常");
			}
		});
	}
}

function getUserBank(){
	var bArr = [], bStr = '', l;
	$.ajax({
		url: path + "/bank/getUserBank.html",
		type: "post",
		dataType: "json",
		success: function(data){
			l = data["msg"].length, data = data["msg"];
			if(l == 0){
				bArr.push('<div class="clearfix yh_num fl hg_88 tc" style="position: relative;overflow:hidden;">');
				bArr.push('<img src="'+path+'/images/letv/jia5.png" class="mt10">');
				bArr.push('<a class="col9" href="'+path+'/common/bankAdd.html" style="display: block;width:100%; height:100%;top:0;left:0;position:absolute;padding-top:50px;">添加银行卡</a>');
				bArr.push('</div>');
				bStr = bArr.join("");
				$("#bankAccount").html(bStr);
				return false;
			}
			for(var i=0;i<l;i++){
				bArr.push('<div class="clearfix yh_num fl hg_88 cur">');
				bArr.push('<div class="fl">');
				if(data[i]["bankShortName"]){
					bArr.push('<img src="'+path+'/images/letv/bank/'+data[i]["bankShortName"]+'.jpg" width="120">');
				}else{
					bArr.push('<img src="'+path+'/images/letv/bank/default.jpg" width="120">');
				}
				if(data[i]["ownerName"]){
					bArr.push('<p class="mt25 col9 ft_normal"> 持卡人姓名：'+data[i]["ownerName"].substring(0,1)+'**</p>');
				}else{
					bArr.push('<p class="mt25 col9 ft_normal"> 持卡人姓名：--</p>');
				}
				bArr.push('</div>');
				bArr.push('<div class="fr">');
				bArr.push('<span>尾号 '+data[i]["bankAccount"].substr(data[i]["bankAccount"].length-4)+'</span>');
				bArr.push('<p class="mt25 tr ft_normal"><a class="col_blue" aid="'+data[i]["id"]+'" onClick="deleteBankcard(this)">删除</a></p>');
				bArr.push('</div>');
				bArr.push('</div>');
			}
			if(l<5){
				bArr.push('<div class="clearfix yh_num fl hg_88 tc" style="position: relative;overflow:hidden;">');
				bArr.push('<img src="'+path+'/images/letv/jia5.png" class="mt10">');
				bArr.push('<a class="col9" href="'+path+'/common/bankAdd.html" style="display: block;width:100%; height:100%;top:0;left:0;position:absolute;padding-top:50px;">添加银行卡</a>');
				bArr.push('</div>');
			}
			
			bStr = bArr.join("");
			$("#bankAccount").html(bStr);
		},
		error: function(request){
			console.log("获取绑定的银行卡异常");
		}
	});
}
