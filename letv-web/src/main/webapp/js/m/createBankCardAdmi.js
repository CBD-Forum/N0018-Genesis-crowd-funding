if(userId == "null"|| userId == null){
	window.location.href = path + "/common/m-login.html";
}
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	findBindCardList();
});

function findBindCardList(){
	$.ajax({
		url: path + "/fundpool/yeepay/pay/queryAuthbindList.html",
		type: "post",
		dataType: "json",
		success:function(data){
			var jsonList = [];
			var html = [];
			if(data["cardlist"] != "[]"){
				var str = data["cardlist"].replace("[","").replace("]","");
				if(str.indexOf("},{") != -1){
					var list = str.split("},{");
					for(var i = 0;i < list.length;i++){
						if((i+1)%2 == 0){
							jsonList[i] = JSON.parse("{" + list[i])
						}else{							
							jsonList[i] = JSON.parse(list[i]+"}")
						}
					}
				}else{
					jsonList[0] = JSON.parse(str);
				}
				for(var i = 0 ; i < jsonList.length;i++){
					html.push('<li><span>(' + jsonList[i]["card_name"] + ')<br/>' + jsonList[i]["card_top"] + "****" + jsonList[i]["card_last"] + '</span><a bindid="' + jsonList[i]["bindid"] + '" href="javascript:void(0);">解绑</a></li>');
				}
			}else{
				html.push('<li style="text-align:center;">您还没有绑定支付银行卡！</li>');
			}
			$("#bindBankList").html(html.join(""));
			$("#bindBankList a").click(function(){
				unbindBankcard($(this).attr("bindid"))
			});
		},
		error:function(){
			
		}
	});
}

//操作绑定银行卡前验证及保存
function unbindBankcard(bindId){
	$.ajax({
		url  : path + "/fundpool/yeepay/pay/unbindBankcard.html",
		type : "post",
		dataType : "json",
		async: false,
		data : {
			"bindId": bindId
		},
		success:function(data){
			if(data["error_code"]){
					AlertDialog.mtip("解绑失败！");
					return;
			}else{
				AlertDialog.mtip("解绑成功！",function(){
					findBindCardList();
				});
			}
		},
		error:function(){
			
		}
	});
}