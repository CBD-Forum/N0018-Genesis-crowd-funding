if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
var type = window.location.search.substring(window.location.search.indexOf("type=")+5);
var trigger = window.location.search.substring(1);
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	var $tab = $("#rzTab li");
	$.each($tab,function(k,v){
		$(v).click(function(){
			$tab.removeClass("cur");
			$(this).addClass("cur");
			$("#rzBody>div").hide();
			var idStr = $(this).attr("id");
			$("#"+idStr+"Body").show();
		});
	});
	//判断认证状态
	if(type == "gtr"){ //投资人认证，需要赋值
		$("#tip_div").hide();
		$("#rzTab li").removeClass("cur");
		$("#gtr").addClass("cur");
		$("#ltgtRZDiv>div").hide();
		$("#gtrBody").fadeIn();
	}else if(type == "ltr"){ //领投人认证，需要赋值
		$("#tip_div").hide();
		$("#rzTab li").removeClass("cur");
		$("#ltr").addClass("cur");
		$("#ltgtRZDiv>div").hide();
		$("#ltrBody").fadeIn();
	}else{
		$tab.first().click();
	}
	if(trigger == 'trigger'){
		$("#rzTab li").removeClass("cur");
		$("#ltr").addClass("cur");
		$("#ltgtRZDiv>div").hide();
		$("#ltrBody").fadeIn();
	}
	//专业投资人认证、领投人
	$("#subLTRZBtn").unbind("click").click(subLTRZ);
	//合格投资人认证 、跟投人
	$("#subInvestBtn").unbind("click").click(subInvestRZ);
	crowdfundUserTzr(); //投资人查询
	getIndustry();//获取投资领域
	
	$("#ltLi").find("input").click(function(){
		$("#personRZDiv").hide("fast");
		$("#institutionRZDiv").hide("fast");
		$("#" + $(this).val() + "RZDiv").show("slow");
	});
});
//获取投资领域
function getIndustry(ind, type, type1){
	var gArr = [], gStr = '', l;
	var c1 = '', c2 = ''; //两个感兴趣的投资赋值
	var indLength, indArr = [];
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": "concernIndustry"},
		success: function(data){
			l = data.length;
			for(var i=0;i<l;i++){
				gArr.push('<div style="float:left;width:100px;">');
				gArr.push('<input type="checkbox" name="tl" id="tl'+i+'" value="'+data[i]["displayName"]+'" style="height:auto;"/>');
				gArr.push('<label for="tl'+i+'" style="position:relative;top:-2px;margin-left:3px;">'+data[i]["displayName"]+'</label>');
				gArr.push('</div>');
			}
			gStr = gArr.join("");
			$("#concern_industryBox").html(gStr);
			$("#concern_industryBox1").html(gStr);
			//如果是查询，投资领域赋值
			if(ind){
				indArr = ind.split(","), indLength = indArr.length;
				for(var j=0;j<indLength;j++){
					$("#concern_industryBox").find("input[value="+indArr[j]+"]").prop("checked", "checked");
					$("#concern_industryBox1").find("input[value="+indArr[j]+"]").prop("checked", "checked");
				}
				$("#concern_industry").val(ind);
				$("#concern_industry1").val(ind);
			}
			if(type){
				if(type1){
					$("#concern_industryBox1").find("input").prop("disabled", "disabled");
				}else{
					$("#concern_industryBox").find("input").prop("disabled", "disabled");
//					$("#concern_industryBox1").find("input").prop("disabled", "disabled");
				}
			}
		},
		error: function(request){
			console.log("获取投资领域异常");
		}
	});
}

//投资人查询
function crowdfundUserTzr(){
	$.ajax({
		url: path + "/userAuth/getCrowdfundUserByUserId.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(!data[0]["companyName"]){
				return false;
			}
			//跟投人认证信息赋值
			$("#realName").val(data[0]["companyName"]);
			$("#realName1").val(data[0]["companyName"]);
			/*if(data[0]["handCardFront"]){
				$("#imghead").attr("src", cv["fileAddress"] + "/" + data[0]["handCardFront"]);
				$("#imghead1").attr("src", cv["fileAddress"] + "/" + data[0]["handCardFront"]);
				$("#imgheadLi").show();
				$("#imgheadLi1").show();
				$("#loan_logo_url").val(data[0]["handCardFront"]);
				$("#loan_logo_url1").val(data[0]["handCardFront"]);
			}else{
				$("#imghead").attr("src", "");
				$("#imghead1").attr("src", "");
			}*/
			$("#concern_city").val(data[0]["concernCity"]);
			$("#concern_city1").val(data[0]["concernCity"]);
			$("#cardCode").val(data[0]["certNo"]);
			$("#cardCode1").val(data[0]["certNo"]);
			$("#iPrcent").val(data[0]["investRatio"]);
			$("#iPrcent1").val(data[0]["investRatio"]);
			$("input[name='hi1'][value='"+data[0]["isInvest"]+"']").prop("checked", "checked");
			$("input[name='hi'][value='"+data[0]["isInvest"]+"']").prop("checked", "checked");
			if(data[0]["investExperience"]){
				$("#loanName").val(data[0]["investExperience"]);
				$("#loanName1").val(data[0]["investExperience"]);
			}
			$("input[name='hj1'][value='"+data[0]["hasInvestPlan"]+"']").prop("checked", "checked");
			$("input[name='hj'][value='"+data[0]["hasInvestPlan"]+"']").prop("checked", "checked");
			if(data[0]["investPlanNum"]){
				$("#planLoanNum").val(data[0]["investPlanNum"]);
				$("#planLoanNum1").val(data[0]["investPlanNum"]);
			}
			if(data[0]["investPlanAmt"]){
				$("#playInvestWan").val(data[0]["investPlanAmt"]);
				$("#playInvestWan1").val(data[0]["investPlanAmt"]);
			}
			
			if(data[0]["userLevel"] == "common"){
				if(data[0]["leadAuthState"] == "auditing"){
					//投资跟投人通过,投资人认证下的内容不可操作
					$("#gtrBody").find("input").prop("disabled", "disabled");
					$("#gtrBody").find("select").prop("disabled", "disabled");
					$("#gtrBody").find("textarea").prop("disabled", "disabled");
					$("#ltrBody").find("input").prop("disabled", "disabled");
					$("#ltrBody").find("select").prop("disabled", "disabled");
					$("#ltrBody").find("textarea").prop("disabled", "disabled");
					$("#subInvestBtn").css("background", "#CCC").text("领投人认证审核中").unbind("click");
					$("#subLTRZBtn").css("background", "#CCC").text("领投人认证审核中").unbind("click");
					getIndustry(data[0]["concernIndustry"], 1);
				}else if(data[0]["investAuthState"] == "auditing"){
					//投资跟投人通过,投资人认证下的内容不可操作
					$("#gtrBody").find("input").prop("disabled", "disabled");
					$("#gtrBody").find("select").prop("disabled", "disabled");
					$("#gtrBody").find("textarea").prop("disabled", "disabled");
					$("#ltrBody").find("input").prop("disabled", "disabled");
					$("#ltrBody").find("select").prop("disabled", "disabled");
					$("#ltrBody").find("textarea").prop("disabled", "disabled");
					$("#subInvestBtn").css("background", "#CCC").text("跟投人认证审核中").unbind("click");
					$("#subLTRZBtn").css("background", "#CCC").text("跟投人认证审核中").unbind("click");
					getIndustry(data[0]["concernIndustry"], 1);
				}else{
					getIndustry(data[0]["concernIndustry"]);
				}
			}else if(data[0]["userLevel"] == "authed"){
				$("#pass1").append('<i style="font-size:16px;color:#DF5E15;margin-left:20px;">已通过</i>');
				$("#subInvestBtn").parent().remove();
				//投资跟投人通过,投资人认证下的内容不可操作
				$(".clause").eq(0).hide();
				$("#gtrBody").find("input").prop("disabled", "disabled");
				$("#gtrBody").find("select").prop("disabled", "disabled");
				$("#subInvestBtn").parent().remove();
				if(data[0]["leadAuthState"] == "auditing"){
					$("#subLTRZBtn").css("background", "#CCC").text("领投人认证审核中").unbind("click");
					//认证领头人通过，内容不可操作
					$("#gtrBody").find("input").prop("disabled", "disabled");
					$("#gtrBody").find("select").prop("disabled", "disabled");
					$("#ltrBody").find("input").prop("disabled", "disabled");
					$("#ltrBody").find("select").prop("disabled", "disabled");
					$("#ltrBody").find("textarea").prop("disabled", "disabled");
					getIndustry(data[0]["concernIndustry"], 1);
				}else{
					$("#gtrBody").find("input").prop("disabled", "disabled");
					$("#gtrBody").find("select").prop("disabled", "disabled");
					getIndustry(data[0]["concernIndustry"], 1, 1);
				}
			}else if(data[0]["userLevel"] == "lead"){
				$("#pass1").append('<i style="font-size:16px;color:#DF5E15;margin-left:20px;">已通过</i>');
				$("#pass2").append('<i style="font-size:16px;color:#DF5E15;margin-left:20px;">已通过</i>');
				$("#subInvestBtn").parent().remove();
				$("#subLTRZBtn").parent().remove();
				//认证领头人通过，内容不可操作
				$(".clause").hide();
				$("#gtrBody").find("input").prop("disabled", "disabled");
				$("#gtrBody").find("select").prop("disabled", "disabled");
				$("#ltrBody").find("input").prop("disabled", "disabled");
				$("#ltrBody").find("select").prop("disabled", "disabled");
				$("#ltrBody").find("textarea").prop("disabled", "disabled");
				
				getIndustry(data[0]["concernIndustry"], 1);
			}
			$("input[name='igj'][value='"+data[0]["userIdentity"]+"']").click();
			if(data[0]["userIdentity"] == "person"){ //个人
				$("#person_capital").val(data[0]["personCapital"]);
				$("#year_income").val(data[0]["yearIncome"]);
				$("#user_experience").val(data[0]["userExperience"]);
				$("#user_experience1").val(data[0]["userExperience"]);
			}else if(data[0]["userIdentity"] == "institution"){ //机构
				$("#institution_industry").val(data[0]["institutionIndustry"]);
				$("#company_address").val(data[0]["companyAddress"]);
				$("#capital_des").val(data[0]["capitalDes"]);
				$("#user_experience").val(data[0]["userExperience"]);
				$("#user_experience1").val(data[0]["userExperience"]);
			}
		},
		error: function(request){
			console.log("获取认证信息异常");
		}
	});
}

function ecreateWebUploader(pickId, showId, setValueId, parentId){
	urlArr = [];
	var uploader=WebUploader.create({
		auto: true,
	    pick: {
	        id: '#'+pickId,
	        multiple: false
	    },
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    },
	    // swf文件路径
	    swf:path + '/js/common/webuploader/Uploader.swf',
	    chunked: true,
	    compress :false,
	    duplicate:true, //可上传重复图片
	    server: path + '/crowdfunding/uploadLoanFile.html',
	    fileNumLimit: 300,
	    fileSizeLimit: 5 * 1024 * 1024,    // 200 M
	    fileSingleSizeLimit: 5 * 1024 * 1024    // 50 M
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json["success"]) {
            	//预览图片
            	$("#" + parentId).show();
            	if(showId == "pro5"){ //项目展示图片，需要5张，情况特殊
            		urlArr.push(json["msg"]);
            		$("#" + setValueId).val(urlArr.join(","));
            		for(var i=0;i<5;i++){
            			if(!$("#proImageLi").find("img").eq(i).attr("src")){ //从前面数，某一个还未添加路径
            				$("#proImageLi").find("img").eq(i).attr("src", cv["fileAddress"] + "/" + json["msg"]) ;
            				$("#" + parentId).children("div").eq(i).show().children("div").attr("i",i);
            				break;
            			}
            		}
            		$.each($("#proImageLi").children("div"),function(k, v){
            			$(v).mouseover(function(){
                			$(this).children("div").show().unbind("click").bind("click",function(){
                				AlertDialog.confirm(delImage, null, "你确定要删除这张图片吗？", "删除", "取消", $(this).attr("id"));
                			});
                		}).mouseout(function(){
                			$(this).children("div").hide();
                		});
            		});
            	}else{
            		$('#'+showId).attr("src", cv["fileAddress"] + "/" + json["msg"]);
            		$("#" + setValueId).val(json["msg"]);
            	}
            } else {
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	uploader.onError = function( code ) {
        if(code=="Q_EXCEED_SIZE_LIMIT")
        	$.messager.alert("上传提示","上传限制为5M，请选择合适的图片");
        else
        	$.messager.alert("上传提示","Error:"+code);
    };
	return uploader;
}
//提交投资认证申请  领投
function subLTRZ(){
	var ci = '',identity="";
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry").val(ci);
	if(MValify.isNull($("#realName").val(), "realName", 5, 160,"jump")){
		if(MValify.isNull($("#cardCode").val(), "cardCode", 5, 160,"jump")){
			if(MValify.isNull($("#concern_city").val(), "concern_city", 5, 160,"jump")){
				if(MValify.isNull($("#concern_industry").val(), "concern_industryBox", -150, 280,"jump")){
					if(checkIsNaN($("#planLoanNum").val(), "planLoanNum",10,20,"jump")){
						if(checkIsNaN($("#playInvestWan").val(), "playInvestWan",10,70,"jump")){
						//	if(MValify.isNull($("#user_experience").val(), "user_experience", -60, 160)){
							//	if(MValify.isNull($("#loanName").val(), "loanName", 5, 160)){
								subLTRZ2();
							//	}
						//	}
						}
					}
				}
			}
		}
	}
}

function subLTRZ2(){
	if($("input[name='igj']:checked").val() == "person"){ 
		if(!$("#read").prop("checked")){
			AlertDialog.show("请阅读协议并勾选", "read", 20, 23);
			return false;
		}else{
			AlertDialog.hide();
			identity = $("input[name='igj']:checked").val();
			$.ajax({
				url: path + "/userAuth/applyLeadInvestor.html",
				type: "post",
				dataType: "json",
				data: {
					"companyName": $("#realName").val(),
					"userIdentity": identity,
					"userExperience": $("#user_experience").val(),
					"concernCity":$("#concern_city").val(),
					"concernIndustry": $("#concern_industry").val(),
					"investRatio": $("#iPrcent").val(),
					"investExperience": $("#loanName").val(),
					"investPlanNum": $("#planLoanNum").val(),
					"userIdentity": "person",
					"personCapital": $("#person_capital").val(),
					"yearIncome": $("#year_income").val(),
					"userExperience": $("#user_experience").val()
				},
				success: function(data){
					if(!data["success"]){
						AlertDialog.tip(data["msg"]);
						return false;
					}
					AlertDialog.tip("提交领投认证申请成功");
					setTimeout(function(){
						window.location.href = path + "/common/m-myCenter.html";
					},1500);
				},
				error: function(request){
					console.log("提交跟投人认证异常");
				}
			});
		}
	}else{
		if(!$("#read").prop("checked")){
			AlertDialog.show("请阅读协议并勾选", "read", 20, 23);
			return false;
		}else{
			AlertDialog.hide();
			identity = $("input[name='igj']:checked").val();
			$.ajax({
				url: path + "/userAuth/applyLeadInvestor.html",
				type: "post",
				dataType: "json",
				data: {
					"companyName": $("#realName").val(),
					"userIdentity": identity,
					"userExperience": $("#user_experience").val(),
					"concernCity":$("#concern_city").val(),
					"concernIndustry": $("#concern_industry").val(),
					"investRatio": $("#iPrcent").val(),
					"investExperience": $("#loanName").val(),
					"investPlanNum": $("#planLoanNum").val(),
					"investPlanAmt": $("#playInvestWan").val(),
					"userIdentity": "institution",
					"institutionIndustry": $("#institution_industry").val(),
					"companyAddress": $("#company_address").val(),
					"capitalDes": $("#capital_des").val()
				},
				success: function(data){
					if(!data["success"]){
						AlertDialog.tip(data["msg"]);
						return false;
					}
					AlertDialog.tip("提交领投认证申请成功");
					setTimeout(function(){
						window.location.href = path + "/common/m-myCenter.html";
					},1500);
				},
				error: function(request){
					console.log("提交跟投人认证异常");
				}
			});
		}
	}
}
//验证是否是数字
function checkIsNaN(str, id,top,right,j){
	if(!str || isNaN(str)){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, top, right);
		if(j){
			var jTop = $("#"+id).offset().top -100;
			$("html,body").animate({
				scrollTop : jTop + "px"
			});
		}
		return false;
	}
	AlertDialog.hide();
	return true;
}
//合格投资人  跟投
function subInvestRZ(){
	var ci = '',identity;
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox1").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox1").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry1").val(ci);
	if(MValify.isNull($("#realName1").val(), "realName1", 5, 160,"jump")){
		if(MValify.isNull($("#cardCode1").val(), "cardCode1", 5, 160,"jump")){
		//	if(MValify.isNull($("#user_experience1").val(), "user_experience1", 5, 160)){
				if(MValify.isNull($("#concern_city1").val(), "concern_city1", 5, 160,"jump")){
					if(MValify.isNull($("#concern_industry1").val(), "concern_industryBox1", -220, 280,"jump")){
						if(checkIsNaN($("#planLoanNum1").val(), "planLoanNum1",10,20,"jump")){
							if(checkIsNaN($("#playInvestWan1").val(), "playInvestWan1",10,70,"jump")){
							 //if(MValify.isNull($("#loanName1").val(), "loanName1", 5, 160)){
									if(!$("#read1").prop("checked")){
										AlertDialog.show("请阅读协议并勾选", "read1", 20, 23);
										return false;
									}else{
										AlertDialog.hide();
										identity = $("input[name='igj1']:checked").val();
										$.ajax({
											url: path + "/userAuth/applyInvestorAuth.html",
											type: "post",
											dataType: "json",
											data: {
												"companyName": $("#realName1").val(),
												"certNo": $("#cardCode1").val(),
												"userIdentity": identity,
											//	"userExperience": $("#user_experience1").val(),
												"concernCity":$("#concern_city1").val(),
												"concernIndustry": $("#concern_industry1").val(),
												"investRatio": $("#iPrcent1").val(),
												"investExperience": $("#loanName1").val(),
												"investPlanNum": $("#planLoanNum1").val(),
												"investPlanAmt": $("#playInvestWan1").val(),
											},
											success: function(data){
												if(!data["success"]){
													AlertDialog.tip(data["msg"]);
													return false;
												}
												AlertDialog.tip("提交跟投认证申请成功");
												setTimeout(function(){
													window.location.href = path + "/common/m-myCenter.html";
												},1500);
											},
											error: function(request){
												console.log("提交跟投认证异常");
											}
										});
									}
								//}
							}
						}
					}
				}
			//}
		}
	}
}
