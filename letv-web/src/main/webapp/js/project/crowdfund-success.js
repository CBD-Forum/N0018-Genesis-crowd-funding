if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = getQueryString("loanNo");
var backNo = getQueryString("backNo");
var ctype = getQueryString("ctype");
var orderId = getQueryString("orderId");
$(function(){
	getDetail();
	if(ctype == "1"){
		$("#wdzcBtu").attr("href",path+"/common/administrationProduct.html");
	}else{
		$("#wdzcBtu").attr("href",path+"/common/administrationPublic.html");
	}
	var ttime = 1;
	$("#whetherSuccess").text("支付中请稍等....");
    var interval = setInterval(function(){
    	$.ajax({
    		url: path + "/crowdfunding/selectPayIsSuccess.html",
    		type: "post",
    		dataType: "json",
    		async: false,
    		data: {
    			"orderId":orderId
    		},
    		success: function(data){
    			if(ttime>100){
    				clearInterval(interval);
    				$("#whetherSuccess").text("支付失败！");
    				var time=5;
    				var timer = window.setInterval(function(){
    					time--;
    					if (time==0) {
    						window.clearInterval(timer);
    						window.location.href=path+'/common/myCenter.html';
    					}else if(time > 0){
    						document.getElementById('timeShow').innerHTML=time+'S';
    					}
    				},1000);
    				return false;
    			}else{
    				if(data["success"]){
        				clearInterval(interval);
        				$("#whetherSuccess").text("恭喜您！已经支付成功！");
        				var time=5;
        				var timer = window.setInterval(function(){
        					time--;
        					if (time==0) {
        						window.clearInterval(timer);
        						window.location.href=path+'/common/myCenter.html';
        					}else if(time > 0){
        						document.getElementById('timeShow').innerHTML=time+'S';
        					}
        				},1000);
        			}else{
        				ttime++;
        			}
    			}
    		},
    		error: function(request){
    			console.log("验证领投请求异常");
    		}
    	});
    },2000);
});

//获取支持详情
function getDetail(){
	var postAddressNo;
	$.ajax({
		url: path + "/crowdfunding/getBackSetList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo,
			"backNo": backNo
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"]["rows"][0];
			$("#loanName").text(data["loanName"]);
			
		},
		error: function(request){
			console.log("获取筹资项目详情信息异常");
		}
	});
	
}

function time(){
	var time=5;
	var timer = window.setInterval(function(){
		time--;
		if (time==0) {
			window.clearInterval(timer);
			window.location.href=path+'/common/myCenter.html';
		}else if(time > 0){
			document.getElementById('timeShow').innerHTML=time+'S';
		}
	},1000);
}
