if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getCenterInfo();//查询项目统计
	getUserAccount();
	getProjectInfo();
	getUserDetail();
	$("#billClose").click(function(){
		$(this).parent().parent().hide();
	});
	var $tab = $("#projectTit>li>a");
	$.each($tab,function(k,v){
		$(v).click(function(){
			$tab.removeClass("cur");
			$(this).addClass("cur");
			$("#projectBody>ul").hide();
			$("#"+$(this).attr("vname")+"_body").show();
		});
	});
	$tab.first().click();
	$("#counterFee-hover").hover(function(){
		$("#counterFee-popup").toggle();
	});
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
});
//获取个人信息
function getUserDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
				$("#blockChainAddress-Btn").css("margin-left","15px");
				$("#blockChainAddress").text(data["user"]["blockChainAddress"]);
				$("#blockChainAddress-Btn").click(function(){
					AlertDialog.tip("请选中文本，使用Ctrl+C复制");
				});
			}else{
				$("#blockChainAddress").text(data["user"]["blockChainAddress"]);
				var copyCon = data["user"]["blockChainAddress"];
				var flashvars = {
					content: encodeURIComponent(copyCon),
					uri: path+'/images/letv/flashCopy_btn.png'
				};
				var params = {
					wmode: "transparent",
					allowScriptAccess: "always"
				};
				swfobject.embedSWF(path+"/js/common/copy/clipboard.swf", "blockChainAddress-Btn", "70", "30", "9.0.0", null, flashvars, params);
			}
			
			if(!data["isAuth"]){
				$("#tx_btn,#cz_btn").click(function(){
					window.location.href = path + "/common/realNameRZ.html";
				})
//				$("#tx_btn").attr("href",path+"/common/realNameRZ.html");
//				$("#cz_btn").attr("href",path+"/common/realNameRZ.html");
			}else if(!data["memberState"]){
//				$("#tx_btn").attr("href",path+"/common/accountSecurity.html");
//				$("#cz_btn").attr("href",path+"/common/accountSecurity.html");
				$("#tx_btn,#cz_btn").click(function(){
					AlertDialog.tip("请先开通会员！");
					setTimeout(function(){
						window.location.href = path + "/common/accountSecurity.html";
					},2000)
				})
			}else{
				$("#tx_btn").click(function(){
					window.location.href = path + "/common/withdraw.html";
				});
				$("#cz_btn").click(function(){
					window.location.href = path + "/common/recharge.html";
				});
			}
			if(data["noAccountNotice"]){
				$("#notAccountRecord").show();
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
function copySuccess(){
    //flash回调
    AlertDialog.tip("复制成功！");
}
//查询项目统计
function getCenterInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectUserCenterInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#totalAssets").text(data["totalAssets"]);//总资产
			$("#availableAssets").text(data["availableAssets"]);//可用资产
			$("#collectAssets").text(data["collectAssets"]);//待收资产
			$("#frozenAssets").text(data["frozenAssets"]);//冻结资产
			$("#yesterdayProfit").text(data["yesterdayProfit"]);//昨日收益
			$("#cumulativeProfit").text(data["cumulativeProfit"]);//累计收益
			$("#historyInvest").text(data["historyInvest"]);//历史投资
			$("#transferingAssets").text(data["transferingAssets"]);//转让中资产
			$("#canTransferAssets").text(data["canTransferAssets"]);//可转让资产
			$("#alreadyTransferAssets").text(data["alreadyTransferAssets"]);//已转让资产
		},
		error: function(request){
			console.log("查询项目统计异常");
		}
	});
}
//获取个人账户
function getUserAccount(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getAccountInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#balance").text(data["balance"].toFixed(2)) ; //可用余额
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//查询项目统计
function getProjectInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectUserProjectInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#productSupportCount").text(data["productSupportCount"]);//产品项目支持的项目数
			$("#productApplyCount").text(data["productApplyCount"]);//产品项目申请的项目数
			$("#productTransferCount").text(data["productTransferCount"]);//产品转让项目
			$("#productKeepCount").text(data["productKeepCount"]);//产品项目收藏
			$("#stockSupportCount").text(data["stockSupportCount"]);//非公开融资项目支持的项目数
			$("#stockApplyCount").text(data["stockApplyCount"]);//非公开融资项目 申请的项目数
			$("#stockKeepCount").text(data["stockKeepCount"]);//非公开融资项目收藏的数量
			$("#publicSupportCount").text(data["publicSupportCount"]);//公益项目支持的项目数
			$("#publicApplyCount").text(data["publicApplyCount"]);//公益项目 申请的项目数
			$("#publicKeepCount").text(data["publicKeepCount"]);//公益项目收藏的数量
		},error: function(request){
			console.log("查询项目统计异常");
		}
	});
}


