if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getUserInfo();
	getAttentionList();
});
//获取个人信息
function getUserInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(data["photo"]){
				$("#centerUserPhoto").attr("src", cv["fileAddress"] + data["photo"]); //头像
			}else{
				$("#centerUserPhoto").attr("src", path + "/images/defaultPhoto.png"); //头像
			}
			$("#userRealName").text(data["realName"]); //真实姓名
			if(data["loanProvinceName"]){
				$("#address").html(data["loanProvinceName"]+"&nbsp;&nbsp;"+data["loanCityName"]); //地址
			}else{
				$("#address").text("--"); //地址
			}
			$("#createTime").text(data["createTime"].substring(0,10));
			if(data["selfDetail"]){
				$("#selfDetail2").html('<span class="C999">简介：</span>'+data["selfDetail"]).attr("title", data["selfDetail"]);
			}else{
				$("#selfDetail2").html('<span class="C999">简介：</span>他很忙，忙的什么都没来及留下。');
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

function getAttentionList(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getMyProjectAttentionList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanUser":siteUserId
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var aArr=[],aStr='',l = data["msg"]["rows"].length,list = data["msg"]["rows"];
			for(var i=0;i<l;i++){
				aArr.push('<dl class="sx_list bb_border clearfix" style="padding-top:0px;text-align:center;">');
				aArr.push('<dt style="width:22%;line-height:26px;">'+list[i]["attentionUser"]+'</dt>');
				aArr.push('<dd style="width:22%;line-height:26px;">'+list[i]["loanName"]+'</dd>');
				if(list[i]["attentionTime"]){
					aArr.push('<dd style="width:22%;">'+list[i]["attentionTime"].substring(0,10)+'</dd>');
				}else{
					aArr.push('<dd style="width:22%;">--</dd>');
				}
				aArr.push('<dd style="width:22%;text-align:center;"><a href="javascript:void(0);" su="'+list[i]["attentionUser"]+'" onclick="toPrivateSale(event);">私信</a></dd>');
				aArr.push('</dl>');
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
	$("#priSaleDiv").css({"top":st+"px","left":sl+"px"}).show();
	$("#bgpop").click(function(){
		$(this).hide();
		$("#priSaleDiv").hide();
		if($("#tip_div").attr("id")){ //如果存在提示，关闭弹框同事关闭提示
			AlertDialog.hide();
		}
	});
	$("#saleBtn").click(function(){
		if(Valify.isNull($("#saleArea").val(), "saleArea", -67, 40)){
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