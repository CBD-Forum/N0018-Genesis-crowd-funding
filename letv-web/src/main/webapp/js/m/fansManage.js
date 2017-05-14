if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	getAttentionList();
});

function getAttentionList(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getMyProjectAttentionList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanUser":userId
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var aArr=[],aStr='',l = data["msg"]["rows"].length,list = data["msg"]["rows"];
			for(var i=0;i<l;i++){
				aArr.push('<li>');
				var aUser = list[i]["attentionUser"];
				aArr.push('<p><span>粉&nbsp;丝&nbsp;名&nbsp;：</span>'+aUser.substring(0,3)+"***"+aUser.substring(aUser.length-2,aUser.length)+'</p>');
				aArr.push('<p><span>关注项目：</span>'+list[i]["loanName"]+'</p>');
				if(list[i]["attentionTime"]){
					aArr.push('<p><span>关注时间：</span>'+list[i]["attentionTime"].substring(0,10)+'</p>');
				}else{
					aArr.push('<p><span>关注时间：</span>--</p>');
				}
				aArr.push('<p class="btn"><a su="'+list[i]["attentionUser"]+'" onclick="toPrivateSale(event);" href="javascript:void(0);">私信</a></p>');
				aArr.push('</li>');
			}
			aStr = aArr.join("");
			$("#attentionList").html(aStr);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//发送私信
function toPrivateSale(event){
	var obj = event.srcElement || event.target;
	var loanUser = $(obj).attr("su");
	
	$("#bgpop").show();
	var sl = (cv["winW"]-419)/2, st = (cv["winH"]-204)/2;
	$("#priSaleDiv").css({"top":st+"px","left":"2%"}).show();
	$("#bgpop").click(function(){
		$(this).hide();
		$("#priSaleDiv").hide();
		if($("#tip_div").attr("id")){ //如果存在提示，关闭弹框同事关闭提示
			AlertDialog.hide();
		}
	});
	$("#saleBtn").click(function(){
		if(MValify.isNull($("#saleArea").val(), "saleArea", -67, 120)){
			$.ajax({
				url: path + "/letter/savePrivateLetter.html",
				type: "post",
				dataType: "json",
				data: {
					"receiveUser": loanUser,
					"sendContent": $("#saleArea").val()
					},
				success: function(data){
					if(!data["success"]){
						$(this).hide();
						$("#priSaleDiv").hide();
						if($("#tip_div").attr("id")){ //如果存在提示，关闭弹框同事关闭提示
							AlertDialog.hide();
						}
						AlertDialog.tip(data["msg"]);
						return false;
					}
					$(this).hide();
					$("#priSaleDiv").hide();
					if($("#tip_div").attr("id")){ //如果存在提示，关闭弹框同事关闭提示
						AlertDialog.hide();
					}
					AlertDialog.tip("发送成功");
				},
				error: function(request){
					console.log("发送私信异常");
				}
			});
		}
	});
}