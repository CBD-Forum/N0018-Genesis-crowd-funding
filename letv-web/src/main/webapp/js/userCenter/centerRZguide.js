if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var type = window.location.search.substring(window.location.search.indexOf("type=")+5);
$(function(){
	//获取个人信息
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(data["isAuth"]){
				if(data["memberState"]){
					if(data["userType"] == 0){
						$("#orgLendAuthState a").attr("href","javascript:void(0);");
						$("#orgInvestAuthState a").attr("href","javascript:void(0);");
						$("#orgInvestAuthState a,#orgLendAuthState a").click(function(){
							AlertDialog.tip("您是个人会员，只能认证个人");
						})
						$("#leadAuthState a").click(function(){
							$.ajax({
								url: path + "/userAuth/getCrowdfundUserByUserId.html",
								type: "post",
								dataType: "json",
								data:{
									authType : "investor"
								},
								success: function(data){
									var Rdata = data["msg"];
									if(Rdata){
										if(Rdata["investAuthState"] == "auditing"){
											AlertDialog.tip("您的跟投人认证审核中，请审核结束后再认证！");
										}else{
											window.location.href = path + "/common/centerRZ.html?type=leadInvestor" ;
										}
									}else{
										window.location.href = path + "/common/centerRZ.html?type=leadInvestor" ;
									}
								},
								error: function(request){
									console.log("获取认证信息异常");
								}
							});
						})
						$("#investAuthState a").click(function(){
							$.ajax({
								url: path + "/userAuth/getCrowdfundUserByUserId.html",
								type: "post",
								dataType: "json",
								data:{
									authType : "leadInvestor"
								},
								success: function(data){
									var Rdata = data["msg"];
									if(Rdata){
										if(Rdata["investAuthState"] == "auditing"){
											AlertDialog.tip("您的领投人认证审核中，请审核结束后再认证！");
										}else if(Rdata["investAuthState"] == "passed"){
											AlertDialog.tip("您的领投人认证审核成功，无须认证跟投人！");
										}else{
											window.location.href = path + "/common/centerRZ.html?type=investor" ;
										}
									}else{
										window.location.href = path + "/common/centerRZ.html?type=investor" ;
									}
								},
								error: function(request){
									console.log("获取认证信息异常");
								}
							});
						})
					}else if(data["userType"] == 1){
						$("#leadAuthState a").attr("href","javascript:void(0);");
						$("#investAuthState a").attr("href","javascript:void(0);");
						$("#leadAuthState a,#investAuthState a").click(function(){
							AlertDialog.tip("您是企业会员，只能认证机构");
						})
						$("#orgLendAuthState a").click(function(){
							$.ajax({
								url: path + "/userAuth/getCrowdfundUserByUserId.html",
								type: "post",
								dataType: "json",
								data:{
									authType : "orgInvestor"
								},
								success: function(data){
									var Rdata = data["msg"];
									if(Rdata){
										if(Rdata["investAuthState"] == "auditing"){
											AlertDialog.tip("您的跟投机构认证审核中，请审核结束后再认证！");
										}else{
											window.location.href = path + "/common/centerRZ.html?type=orgLeadInvestor" ;
										}
									}else{
										window.location.href = path + "/common/centerRZ.html?type=orgLeadInvestor" ;
									}
								},
								error: function(request){
									console.log("获取认证信息异常");
								}
							});
						})
						$("#orgInvestAuthState a").click(function(){
							$.ajax({
								url: path + "/userAuth/getCrowdfundUserByUserId.html",
								type: "post",
								dataType: "json",
								data:{
									authType : "orgLeadInvestor"
								},
								success: function(data){
									var Rdata = data["msg"];
									if(Rdata){
										if(Rdata["investAuthState"] == "auditing"){
											AlertDialog.tip("您的领投机构认证审核中，请审核结束后再认证！");
										}else if(Rdata["investAuthState"] == "passed"){
											AlertDialog.tip("您的领投机构认证审核成功，无须认证跟投机构！");
										}else{
											window.location.href = path + "/common/centerRZ.html?type=orgInvestor" ;
										}
									}else{
										window.location.href = path + "/common/centerRZ.html?type=orgInvestor" ;
									}
								},
								error: function(request){
									console.log("获取认证信息异常");
								}
							});
						})

					}
				}else{
					$("#orgInvestAuthState a,#orgLendAuthState a,#leadAuthState a,#investAuthState a").attr("href","javascript:void(0);");
					$("#orgInvestAuthState a,#orgLendAuthState a,#leadAuthState a,#investAuthState a").click(function(){
						AlertDialog.mtip("请先开通会员",href);
					})
				}
				
			}else{
				$("#orgInvestAuthState a,#orgLendAuthState a,#leadAuthState a,#investAuthState a").attr("href","javascript:void(0);");
				$("#orgInvestAuthState a,#orgLendAuthState a,#leadAuthState a,#investAuthState a").click(function(){
					AlertDialog.mtip("请先实名认证",href);
				})
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	function href(){
		window.location.href = path + "/common/accountSecurity.html";
	}

});



