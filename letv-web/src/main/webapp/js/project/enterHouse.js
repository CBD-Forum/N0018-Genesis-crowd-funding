if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.length);
$(function(){
	moduleCotrl();
	showProvince();
//	showBisType();
	getIndustry();// 获取投资领域
	//如果是编辑跳转过来的，进行项目详细信息赋值
	if(loanNo){
		//initLoanInfo();
		$("a[name='enterPause']").attr("pause","1"); //展开按钮可点
		$("a[name='enterPause']").attr("pause","1");  //展开按钮可点
		$("a[name='backSetPause']").attr("pause","1");  //展开按钮可点
		$("a[name='proDataPause']").attr("pause","1");  //展开按钮可点
		$("a[name='companyRZPause']").attr("pause","1");  //展开按钮可点
		$("#projectLoanNo").val(loanNo);//loanNo赋值
		
		initCrowdfundInfo(); //项目基本信息
		initUserInfo(); //项目基本信息
		initCompanyInfo(); //公司认证信息
		initBackSet(); //回报设置
		initCrowdfundDetailInfo(); //项目资料详情
	}else{//如果是编辑录入项目，初始化富文本内容
		initProDetail();
	}
	
	ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项目封面图片
	ecreateWebUploader("licenceFrontBtn", "licenceFrontImage", "licenceFront_url", "licenceFrontDiv");//上传证件正面图片
	ecreateWebUploader("licenceReveseBtn", "licenceReveseImage", "licenceRevese_url", "licenceReveseDiv");//上传证件反面图片
	
	recreateWebUploader("frsfzBtn", "frsfz_image", "frsfzBtn_url", "frsfzLi", "legal_user_card");//法定代表人身份证
	recreateWebUploader("frgrxxbgBtn", "frgrxxbg_image", "frgrxxbg_url", "frgrxxbgLi", "credit_report");//法人代表个人信用报告
	recreateWebUploader("yyzzBtn", "yyzz_image", "yyzz_url", "yyzzLi", "busi_licence");//营业执照
	recreateWebUploader("yyzzfbBtn", "yyzzfb_image", "yyzzfb_url", "yyzzfbLi", "busi_licence_copy");//营业执照副本
	recreateWebUploader("swdjzBtn", "swdjz_image", "swdjz_url", "swdjzLi", "tax_reg");//税务登记证
	recreateWebUploader("swdjzfbBtn", "swdjzfb_image", "swdjzfb_url", "swdjzfbLi", "tax_reg_copy");//税务登记副本
	recreateWebUploader("zzjgdmzBtn", "zzjgdmz_image", "zzjgdmz_url", "zzjgdmzLi", "org_code");//组织机构代码证
	recreateWebUploader("zzjgdmzfbBtn", "zzjgdmzfb_image", "zzjgdmzfb_url", "zzjgdmzfbLi", "org_code_copy");//组织机构代码证副本
	recreateWebUploader("gszpphotoBtn", "gszpphoto_image", "gszpphoto_url", "gszpphotoLi", "company_photo");//公司照片
	recreateWebUploader("cdzlhtBtn", "cdzlht_image", "cdzlht_url", "cdzlhtLi", "tenancy_contract");//场地租赁合同
	recreateWebUploader("cwbbPhotoBtn", "cwbbPhoto_image", "cwbbPhoto_url", "cwbbPhotoLi", "finance_report");//财务报表
	recreateWebUploader("wsxkzBtn", "wsxkz_image", "wsxkz_url", "wsxkzLi", "hygienic_license");//卫生许可证
	
	ecreateWebUploader("csrPhotoBtn", "csrPhoto_img", "csrPhoto_url", "csrPhoto_div");//创始人头像
	ecreateWebUploader("proPhotoBtn", "pro5", "proPhoto_url", "proImageLi");//项目图片，最多5张
	
	$("#saveDataBtn").click(saveCrowdFunding);//保存项目基本信息
	$("#saveEnterBtn").click(updateUserStuff);//保存发起人基本信息
	$("#saveCompanyRZ").click(updateCompanyStuff);//保存认证照片基本信息
	$("#saveBackSet").click(saveStockBackSet);//保存回报设置信息
	$("#svaeProData").click(saveCrowdFundDetail);//保存项目资料详情
	$("#subLoan").click(submitLoan); //提交审核
	
	limitSet();//股权权限设置
});

//初始化项目基本信息赋值
function initCrowdfundInfo(){
	//成功后表单初始化操作
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": $("#projectLoanNo").val()},
		success: function(bdata){
			if(bdata["success"]){
				$("#projectName").val(bdata["msg"]["loanName"]);
				$("#raiseAmt").val(bdata["msg"]["fundAmt"]);
				$("#totalF").val(bdata["msg"]["fundAmt"]);
				$("#raiseDay").val(bdata["msg"]["fundDays"]);
				$("#ptrade_one").val(bdata["msg"]["superIndustry"]);
//				showChildType(bdata["msg"]["superIndustry"],bdata["msg"]["childIndustry"] )
				//$("#ptrade_two").val(bdata["msg"]["childIndustry"]);
				$("#address").val(bdata["msg"]["loanAddress"]);
				$("#province").val(bdata["msg"]["province"]);
				$("#city").val(bdata["msg"]["city"]);
				$("#county").val(bdata["msg"]["county"]);
				showCity(bdata["msg"]["province"], bdata["msg"]["city"]);
				showCounty(bdata["msg"]["city"], bdata["msg"]["county"]);
				$("#ProjectDption").val(bdata["msg"]["loanDes"]);
				$("#imgheadLi").show();
				$("#loan_logo_url").val(bdata["msg"]["loanLogo"]);
				$('#imghead').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["loanLogo"]);
				//顶部进度条改变
				if(bdata["msg"]["loanName"]){
					$("#stepDiv>dl").eq(1).addClass("cur");
				}
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}
function initCompanyInfo(){
	$.ajax({
		url: path + "/crowdfunding/getCompanyInfo.html",
		type: "post",
		dataType: "json",
		data: {"userId": siteUserId},
		success: function(bdata){
			if(bdata["success"]){
				for(var i=0;i<bdata["msg"].length-1;i++){
					//法定代表人身份证
					if(bdata["msg"][i]["fileType"]=="legal_user_card"){
						$("#frsfzLi").show();
						$("#frsfzBtn_url").val(bdata["msg"][i]["fileType"]);
						$("#frsfz_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//法人代表个人信用报告
					if(bdata["msg"][i]["fileType"]=="credit_report"){
						$("#frgrxxbgLi").show();
						$("#frgrxxbg_url").val(bdata["msg"][i]["fileType"]);
						$("#frgrxxbg_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//营业执照
					if(bdata["msg"][i]["fileType"]=="busi_licence"){
						$("#yyzzLi").show();
						$("#yyzz_url").val(bdata["msg"][i]["fileType"]);
						$("#yyzz_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//营业执照副本
					if(bdata["msg"][i]["fileType"]=="busi_licence_copy"){
						$("#yyzzfbLi").show();
						$("#yyzzfb_url").val(bdata["msg"][i]["fileType"]);
						$("#yyzzfb_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//税务登记证
					if(bdata["msg"][i]["fileType"]=="tax_reg"){
						$("#swdjzLi").show();
						$("#swdjz_url").val(bdata["msg"][i]["fileType"]);
						$("#swdjz_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//税务登记副本
					if(bdata["msg"][i]["fileType"]=="tax_reg_copy"){
						$("#swdjzfbLi").show();
						$("#swdjzfb_url").val(bdata["msg"][i]["fileType"]);
						$("#swdjzfb_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//>组织机构代码证
					if(bdata["msg"][i]["fileType"]=="org_code"){
						$("#zzjgdmzLi").show();
						$("#zzjgdmz_url").val(bdata["msg"][i]["fileType"]);
						$("#zzjgdmz_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//组织机构代码证副本
					if(bdata["msg"][i]["fileType"]=="org_code_copy"){
						$("#zzjgdmzfbLi").show();
						$("#zzjgdmzfb_url").val(bdata["msg"][i]["fileType"]);
						$("#zzjgdmzfb_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//公司照片
					if(bdata["msg"][i]["fileType"]=="company_photo"){
						$("#gszpphotoLi").show();
						$("#gszpphoto_url").val(bdata["msg"][i]["fileType"]);
						$("#gszpphoto_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//场地租赁合同
					if(bdata["msg"][i]["fileType"]=="tenancy_contract"){
						$("#cdzlhtLi").show();
						$("#cdzlht_url").val(bdata["msg"][i]["fileType"]);
						$("#cdzlht_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//财务报表
					if(bdata["msg"][i]["fileType"]=="finance_report"){
						$("#cwbbPhotoLi").show();
						$("#cwbbPhoto_url").val(bdata["msg"][i]["fileType"]);
						$("#cwbbPhoto_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					//卫生许可证
					if(bdata["msg"][i]["fileType"]=="hygienic_license"){
						$("#wsxkzLi").show();
						$("#wsxkz_url").val(bdata["msg"][i]["fileType"]);
						$("#wsxkz_image").attr("src",cv["fileAddress"] + "/" +bdata["msg"][i]["fileUrl"]);
						continue;
					}
					
				}
			}
			
			
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}
function initBackSet(){
	$.ajax({
		url: path + "/crowdfunding/getStockBack.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": $("#projectLoanNo").val()},
		success: function(bdata){
			if(bdata["success"]){
				
				$("#fp").val(bdata["msg"]["projectFinanceAmt"]);
				$("#ip_text").text(bdata["msg"]["investFinanceAmt"]);
				$("#xmr").val(bdata["msg"]["projectBonusRatio"]);
				$("#tmr_text").text(bdata["msg"]["investBonusRatio"]);
				$("#fraction").val(bdata["msg"]["financeNum"]);
				$("#lessF_text").text(bdata["msg"]["miniInvestAmt"]);
				countStockBack(bdata["msg"]["investFinanceAmt"]); //计算股权回报设置
				//顶部步骤进度改变
				if(bdata["msg"]["projectBonusRatio"]){
					$("#stepDiv>dl").eq(4).addClass("cur");
				}
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}

function initCrowdfundDetailInfo(){
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": $("#projectLoanNo").val()},
		success: function(bdata){
			if(bdata["success"]){
				$("#csrName").val(bdata["msg"]["founder"]);
				$("#csrPhoto_url").val(bdata["msg"]["founderPhoto"]);
				$("#csrPhoto_div").show();
				$('#csrPhoto_img').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["founderPhoto"]);
				$("#compDate").val(bdata["msg"]["companyFundDate"]);
				$("#lastQuarterIncome").val(bdata["msg"]["lastQuarterIncome"]);
				$("#lastQuarterProfit").val(bdata["msg"]["lastQuarterProfit"]);
				$("#lastYearIncome").val(bdata["msg"]["lastYearIncome"]);
				$("#lastYearProfit").val(bdata["msg"]["lastYearProfit"]);
				//UE.getEditor("#proDetail").setContent("大事发生的发生的");
				$("#financeBudget").html(bdata["msg"]["financeBudget"]);
				$("#loanVideo").val(bdata["msg"]["loanVideo"]);
				$("#videoDes").html(bdata["msg"]["videoDes"]);
				$("#mobileVideo").val(bdata["msg"]["houseDeveloper"]);
				//顶部步骤进度改变
				if(bdata["msg"]["founder"]){
					$("#stepDiv>dl").eq(5).addClass("cur");
				}
				$.ajax({
					url: path + "/crowdfunding/getCrowdPhotos.html",
					type: "post",
					dataType: "json",
					data: {"loanNo": $("#projectLoanNo").val()},
					success: function(pdata){
						if(pdata["success"]){
							if(pdata["msg"]["loanPhotos"]){
								pArr = pdata["msg"]["loanPhotos"].split(","), pl = pArr.length;
								for(var i=0;i<pl-1;i++){
									$("#proPhoto_url").val(pArr[i]);
									$("#proImageLi").find("img").eq(i).attr("src", cv["fileAddress"] + "/" +  pArr[i]);
									$("#proImageLi>div").eq(i).show();
								}
								$("#proImageLi").show();
							}
						}
					}
				});
				var str=bdata["msg"]["loanDetail"];
				UE.getEditor("proDetail").ready(function(){
					UE.getEditor("proDetail").setContent(str);
				});

			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}
//控制页面每个模块的展开和关闭
function moduleCotrl(){
	$.each($("a[type='pause']"),function(k, v){
		$(v).click(function(){
			if($(this).attr("pause") != "1"){ //上一步未成功
				return false;
			}
			if($("#" + $(this).attr("name")+"Box").css("display") == "none"){ //需要展开
				$("#" + $(this).attr("name")+"Box").fadeIn();
				$(this).children("span").text("收起");
			}else{
				$("#" + $(this).attr("name")+"Box").fadeOut();
				$(this).children("span").text("展开");
			}
		});
	});
}
//展示省份下拉数据
function showProvince(id){
	//获取省份
	var pArr = [], pStr = '', l;
	$.ajax({
		url: path + "/area/getProvince.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			pArr.push('<option value="">请选择省</option>');
			for(var i=0;i<l;i++){
				pArr.push('<option value="'+data[i]["id"]+'">'+data[i]["shortName"]+'</option>');
			}
			pStr = pArr.join("");
			$("#province").html(pStr);
			if(id){
				$("#province").val(id);
			}
			//点击省份后，根据省份id查询城市
			$("#province").change(function(){
				showCity($(this).val());
			});
		},
		error: function(request){
			console.log("获取省份信息异常");
		}
	});
}
//展示城市下拉数据
function showCity(pid, cid){
	var cArr = [], cStr = '', cl, list;
	$.ajax({
		url: path + "/area/getCity.html",
		type: "post",
		dataType: "json",
		data: {"pid": pid},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			cl = data["msg"]["rows"].length, list = data["msg"]["rows"];
			cArr.push('<option value="">请选择市</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+list[i]["id"]+'">'+list[i]["name"]+'</option>');
			}
			cStr = cArr.join("");
			$("#city").html(cStr);
			if(cid){
				$("#city").val(cid);
			}
			//点击城市后，根据城市id查询县城
			$("#city").change(function(){
				showCounty($(this).val());
			});
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}
//展示县城下拉数据
function showCounty(cid, coid){
	var cArr = [], cStr = '', cl, list;
	$.ajax({
		url: path + "/area/getCity.html",
		type: "post",
		dataType: "json",
		data: {"pid": cid},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			cl = data["msg"]["rows"].length, list = data["msg"]["rows"];
			cArr.push('<option value="">请选择县</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+list[i]["id"]+'">'+list[i]["name"]+'</option>');
			}
			cStr = cArr.join("");
			$("#county").html(cStr);
			if(coid){
				$("#county").val(coid);
			}
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}
/*
//展示行业类别
function showBisType(tid){
	var cArr = [], cStr = '', cl = 0;
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": "crowdfund_industry"},
		success: function(data){
			cl = data.length;
			cArr.push('<option value="">请选择</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
			}
			cStr = cArr.join("");
			$("#ptrade_one").html(cStr);
			if(tid){
				$("#ptrade_one").val(tid);
			}
			//点击省份后，根据省份id查询城市
			$("#ptrade_one").change(function(){
				showChildType($(this).val());
			});
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}
//展示行业子类别
function showChildType(tid, cid){
	var cArr = [], cStr = '', cl;
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": tid},
		success: function(data){
			cl = data.length;
			//如果行业类型下没有子类型，隐藏子类型select
			if(cl == 0){
				$("#ptrade_two").hide();
			}else{
				$("#ptrade_two").show();
			}
			cArr.push('<option value="">请选择</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
			}
			cStr = cArr.join("");
			$("#ptrade_two").html(cStr);
			if(cid){
				$("#ptrade_two").val(cid);
			}
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}*/
//验证项目名称
function checkProName(str, id){
	if(!str){
		AlertDialog.show("项目名称不能为空", id, 10, 20, "jump");
		return false;
	}
	if(str.length > 40){
		AlertDialog.show("项目名称不能超过40个字", id, 10, 20, "jump");
		return false;
	}
	AlertDialog.hide();
	return true;
}
//验证筹集金额
function checkRaisAmt(str, id){
	if(isNaN(str)){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}else{
		if(!str || Number(str) < 0){
			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20, "jump");
			return false;
		}else if(!str || Number(str) > 1000000000)
			{
			AlertDialog.show($("#" + id).attr("logicMessage2"), id, 10, 20, "jump");
			return false;
			}
	}
	AlertDialog.hide();
	return true;
}
//验证筹集天数
function checkRaiseDay(str ,id){
	if(isNaN(str)){
		AlertDialog.show("募集天数应该为数字", id, 0, 30, "jump");
		return false;
	}else{
		if(!str || Number(str) < 10 || Number(str) > 90){
			AlertDialog.show("募集天数为10-90天", id, 0, 30, "jump");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
//初始化富文本编辑内容
function initProDetail(){
	var hArr = [], hStr = '';
	hArr.push('<h3>关于我（也可使用个性化小标题）</h3>');
	hArr.push('<p style="font-size:12px;">向支持者介绍你自己或你的团队，并详细说明你与所发起的项目之间的背景，让支持者能够在最短时间内了解你，以拉近彼此之间的距离。</p>');
	hArr.push('<h3>我想要做什么（也可使用个性化小标题）</h3>');
	hArr.push('<p style="font-size:12px;">这是项目介绍中最关键的部分，建议上传5张以上高清图片（宽700、高不限），配合文字来简洁生动地说明你的项目，让支持者对你要做的事情一目了然并充满兴趣。</p>');
	hArr.push('<h3>为什么我需要你的支持及资金用途（也可使用个性化小标题）</h3>');
	hArr.push('<p style="font-size:12px;">请在这一部分说明你的项目不同寻常的特色，为什么需要大家的支持以及详细的资金用途，这会增加你项目的可信度并由此提高筹资的成功率。</p>');
	hArr.push('<h3>可能存在的风险（也可使用个性化小标题）</h3>');
	hArr.push('<p style="font-size:12px;">请在此标注你的项目在实施过程中可能存在的风险，让支持者对你的项目有全面而清晰的认识。</p>');
	hStr = hArr.join("");
	UE.getEditor("proDetail").ready(function(){
		UE.getEditor("proDetail").setContent(hStr);
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
	    fileSizeLimit: 3 * 1024 * 1024,    // 200 M
	    fileSingleSizeLimit: 3 * 1024 * 1024    // 50 M
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json["success"]) {
            	AlertDialog.hide();
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
            	AlertDialog.show(json["msg"], pickId, 10, 30);
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}
function recreateWebUploader(pickId, showId, setValueId, parentId, type){
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
	    server: path + '/crowdfunding/uploadUserFile.html?fileType=' + type,
	    fileNumLimit: 300,
	    fileSizeLimit: 3 * 1024 * 1024,    // 200 M
	    fileSingleSizeLimit: 3 * 1024 * 1024    // 50 M
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json["success"]) {
            	AlertDialog.hide();
            	//预览图片
            	$("#" + parentId).show();
            	$('#'+showId).attr("src", cv["fileAddress"] + "/" + json["msg"]);
        		$("#" + setValueId).val(json["msg"]);
            } else {
            	AlertDialog.show(json["msg"], pickId, 10, 30);
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}
//删除项目展示图片
function delImage(id){
	$("#" + id).prev().removeAttr("src").parent().hide();
	urlArr.splice(Number($(this).attr("i")), 1);
	$("#proPhoto_url").val(urlArr.join(","));
}
//保存项目基本信息
function saveCrowdFunding(){
	if(checkProName($("#projectName").val(), "projectName")){
		if(checkRaisAmt($("#raiseAmt").val(), "raiseAmt")){
			if(checkRaiseDay($("#raiseDay").val(), "raiseDay")){
				if(Valify.isNull($("#ptrade_one").val(), "ptrade_one", 10, 40, "jump")){
					if(Valify.isNull($("#province").val(), "province", 10, 40, "jump")){
//						if(Valify.isNull($("#city").val(), "city", 10, 40, "jump")){
							if(Valify.isNull($("#ProjectDption").val(), "ProjectDption", -20, 40, "jump")){
								if(Valify.isNull($("#loan_logo_url").val(), "image_file", 10, 40, "jump")){
									var url = path + "/crowdfunding/saveCrowdFunding.html";
									if(loanNo){
										url = path + "/crowdfunding/updateCrowdFunding.html";
									}
									if($("#projectLoanNo").val()){
										loanNo = $("#projectLoanNo").val();
										url = path + "/crowdfunding/updateCrowdFunding.html";
									}
									$.ajax({
										url: url,
										type: "post",
										dataType: "json",
										data: {
											"loanNo":loanNo,
											"loanType": "house",
											"loanName": $("#projectName").val(),
											"fundAmt": $("#raiseAmt").val(),
											"fundDays": $("#raiseDay").val(),
											"superIndustry": $("#ptrade_one").val(),
											"childIndustry": $("#ptrade_two").val(),
											"province": $("#province").val(),
											"loanAddress":$("#address").val(),
											"city": $("#city").val(),
											"county": $("#county").val(),
											"loanDes": $("#ProjectDption").val(),
											"loanLogo": $("#loan_logo_url").val()
										},
										success: function(data){
											if(data["success"]){
												$('html,body').animate({
													scrollTop : '300px'
												}, 800);
												$("#dataPauseBox").fadeOut(); //区域隐藏
												
												if(loanNo){
													$("#projectLoanNo").val(loanNo); //
												}else{
													$("#projectLoanNo").val(data["msg"]); //生成loanId
												}
												
												$("#dataPauseTip").text("保存成功").show().animate({
													marginLeft: "10px"
												},1500); //成功效果
												$("#enterPauseBox").fadeIn();
												$("a[name='dataPause']").children().text("展开");
												$("a[name='enterPause']").attr("pause", "1");
												//顶部步骤进度改变
												$("#stepDiv>dl").eq(1).addClass("cur");
												/**
												 * 查询发起人基本信息
												 * 如果查询到了，手机号不可修改，完成按钮可点击
												 * 如果没查询到，不显示完成按钮，要填写然后保存
												 */
												$.ajax({
													url: path + "/crowdfunding/getUserStuffById.html",
													type: "post",
													dataType: "json",
													data: {"userId": siteUserId},
													success: function(data){
														if(!data["success"]){
															return false;
														}
														data = data["msg"];
														
														if(data["mobile"]){
															//展示完成发起人基本信息按钮
															$("#overEnterBtn").removeClass("none").bind("click",function(){
																if(Valify.isNull($("#enterProUser").val(), "enterProUser", 10, 30, "jump")){
																	if(Valify.isNull($("#cardCode").val(), "cardCode", 10, 30, "jump")){
																		if(Valify.isNull($("#licenceFront_url").val(), "licenceFrontBtn", -175, 30, "jump")){
																			if(Valify.isNull($("#licenceRevese_url").val(), "licenceReveseBtn", -175, 30)){
																				if(Valify.phone($("#mobile").val(), "mobile", "jump")){
																					$('html,body').animate({
																						scrollTop : '350px'
																					}, 800);
																					$("#enterPauseBox").fadeOut(); //区域隐藏
																					$("#enterPauseTip").text("保存成功").show().animate({
																						marginLeft: "10px"
																					},1500); //成功效果
																					$("#companyRZPauseBox").fadeIn();
																					$("a[name='enterPause']").children().text("展开");
																					$("a[name='companyRZPause']").children().text("收起");
																					$("a[name='companyRZPause']").attr("pause", "1");
																					//顶部步骤进度改变
																					$("#stepDiv>dl").eq(2).addClass("cur");
																				}
																			}
																		}
																	}
																}
															});
															//发起人信息赋值
															$("#enterProUser").val(data["companyName"]);
															$("#cardCode").val(data["certNo"]);
															$("#licenceFront_url").val(data["idCardFront"]);
															//$("#licenceFrontImage").attr("src",cv["fileAddress"] + "/" + data["idCardFront"]);
															if(data["idCardFront"]){
																$("#licenceFrontImage").attr("src",cv["fileAddress"] + "/" + data["idCardFront"]);
															}else{
																$("#licenceFrontImage").attr("src","");
															}
															$("#licenceFrontDiv").show();
															$("#licenceRevese_url").val(data["idCardBack"]);
															//$("#licenceReveseImage").attr("src",cv["fileAddress"] + "/" + data["idCardFront"]);
															if(data["idCardBack"]){
																$("#licenceReveseImage").attr("src",cv["fileAddress"] + "/" + data["idCardBack"]);
															}else{
																$("#licenceReveseImage").attr("src","");
															}
															$("#licenceReveseDiv").show();
															$("#mobile").val(data["mobile"]).prop("readonly", "readonly") ;
														}
													},
													error: function(request){
														console.log("获取发起人信息异常");
													}
												});
											}else{
												$('html,body').animate({
													scrollTop : '300px'
												}, 800);
												$("#dataPauseTip").text(data["msg"]).show().animate({
													marginLeft: "10px"
												},1500); //失败效果
											}
										},
										error: function(request){
											console.log("保存项目基本信息异常");
										}
									});
								}
							}
//						}
					}
				}
			}
		}
	}
}
//保存发起人基本信息
function updateUserStuff(){
	if(Valify.isNull($("#enterProUser").val(), "enterProUser", 10, 30, "jump")){
		if(Valify.isNull($("#cardCode").val(), "cardCode", 10, 30, "jump")){
			if(Valify.isNull($("#licenceFront_url").val(), "licenceFrontBtn", -155, 30, "jump")){
				if(Valify.isNull($("#licenceRevese_url").val(), "licenceReveseBtn", -155, 30, "jump")){
					if(Valify.phone($("#mobile").val(), "mobile", 10, 30, "jump")){
						$.ajax({
							url: path + "/crowdfunding/updateUserStuff.html",
							type: "post",
							dataType: "json",
							data: {
								"companyName": $("#enterProUser").val(),
								"certNo": $("#cardCode").val(),
								"idCardFront": $("#licenceFront_url").val(),
								"idCardBack": $("#licenceRevese_url").val(),
								"mobile": $("#mobile").val()
							},
							success: function(data){
								if(data["success"]){
									$('html,body').animate({
										scrollTop : '350px'
									}, 800);
									$("#enterPauseBox").fadeOut(); //区域隐藏
									$("#enterPauseTip").text("保存成功").show().animate({
										marginLeft: "10px"
									},1500); //成功效果
									$("#companyRZPauseBox").fadeIn();
									$("a[name='enterPause']").children().text("展开");
									$("a[name='companyRZPause']").attr("pause", "1");
									//顶部步骤进度改变
									$("#stepDiv>dl").eq(2).addClass("cur");
								}
							},
							error: function(request){
								console.log("保存项目基本信息异常");
							}
						});
					}
				}
			}
		}
	}else{
		$('html,body').animate({
			scrollTop : $("#enterProUser").position().top-100
		}, 800);
	}
}
//保存公司认证
function updateCompanyStuff(){
	$('html,body').animate({
		scrollTop : '350px'
	}, 800);
	$("#companyRZPauseBox").fadeOut(); //区域隐藏
	$("#companyRZPauseTip").text("保存成功").show().animate({
		marginLeft: "10px"
	},1500); //成功效果
	//股权回报设置数据计算
	$("#backSetPauseBox").fadeIn(); //回报设置区域展示
	$("#totalF").val(Number($("#raiseAmt").val()).toFixed(2)); //融资总额赋值
	$("#fp").val((Number($("#totalF").val())/100 * Number($("#fpr_text").text())).toFixed(2)); //项目方出资赋值
	$("#ip_text").text((Number($("#totalF").val())/100 * Number($("#ipr_text").text())).toFixed(2)); //投资人出资赋值
	countStockBack(Number($("#ip_text").text()));
	backInputBlur();//股权回报输入框焦点离开事件
	
	$("a[name='companyRZPause']").children().text("展开");
	$("a[name='backSetPause']").attr("pause", "1");
	$("a[name='backSetPause']").children().text("收起");
	//顶部步骤进度改变
	$("#stepDiv>dl").eq(3).addClass("cur");
}
//股权回报份数推荐计算
function countStockBack(num){
	var sArr = [], sStr = '';
	for(var i=1;i<=199;i++){
		if(num%i == 0){ //能够除尽的才会成为份数
			sArr.push('<li>份数：'+i+'<span style="display:inline-block;width:25px;"></span>金额(￥)：'+(num/i)+'</li>');
		}
	}
	sStr = sArr.join("");
	$("#recomCount").html(sStr);
}
//股权回报输入框焦点离开计算事件
function backInputBlur(){
	//融资总额焦点离开
	$("#totalF").blur(function(){
		if(isNaN($(this).val())){
			return false;
		}
		$("#fp").val((Number($("#totalF").val())/100 * Number($("#fpr_text").text())).toFixed(2)); //项目方出资赋值
		$("#ip_text").text((Number($("#totalF").val())/100 * Number($("#ipr_text").text())).toFixed(2)); //投资人出资赋值
		if($("#fraction").val()){
			$("#lessF_text").val((Number($("#ip_text").text())/Number($("#fraction").val())).toFixed(2)); //单投资人最低投资金额
		}
		countStockBack(Number($("#ip_text").text()));
	});
	//项目方出资焦点离开
	$("#fp").blur(function(){
		if(isNaN($(this).val())){
			return false;
		}
		$("#fpr_text").text((Number($("#fp").val())/Number($("#totalF").val())*100).toFixed(2)); //项目方出资比例
		$("#ipr_text").text((100-Number($("#fpr_text").text())).toFixed(2)); //投资人出资比例
		//$("#ip_text").text((Number($("#totalF").val())/100 * Number($("#ipr_text").text())).toFixed(2)); //投资人出资赋值
		if((Number($("#totalF").val())/100 * Number($("#ipr_text").text())).toFixed(2)<0){
			$("#ip_text").text((Number($("#totalF").val())/100 * Number($("#ipr_text").text())).toFixed(2)*-1); //投资人出资赋值
		}else{
			$("#ip_text").text((Number($("#totalF").val())/100 * Number($("#ipr_text").text())).toFixed(2)); //投资人出资赋值
		}
		countStockBack(Number($("#ip_text").text()));
		if($("#fraction").val()){
			$("#lessF_text").text((Number($("#ip_text").text())/Number($("#fraction").val())).toFixed(2)); //单投资人最低投资金额
		}
	});
	//项目方占股比例焦点离开
	$("#xmr").blur(function(){
		if(isNaN($(this).val())){
			return false;
		}
		if(Number($("#xmr").val()) < 1 || Number($("#xmr").val()) >= 100){
			AlertDialog.show("项目方占比不合理", "xmr", 10, 20);
			return false;
		}
		$("#tmr_text").text((100-Number($("#xmr").val())).toFixed(2)); //投资方占股比例
	});
	//认购份数焦点离开计算
	$("#fraction").blur(function(){
		if(isNaN($(this).val())){
			return false;
		}else if($("#fraction").val()){
			$("#lessF_text").text((Number($("#ip_text").text())/Number($("#fraction").val())).toFixed(2)); //单投资人最低投资金额
		}else if($("#fraction").val("")){
			$("#lessF_text").val("");
		}
	});
}
//保存回报设置
function saveStockBackSet(){
	if($("#totalF").val() < 0){
		var num = $("#totalF").val()*-1;
	}else{
		var num = $("#totalF").val();
	}
	if(checkIsNaN($("#totalF").val(), "totalF", 10, 20)){
		if(checkIsNaN($("#fp").val(), "fp", 10, 20)){
			if(Number($("#fp").val())<=Number(num)){
				if(checkIsNaN($("#xmr").val(), "xmr", 10, 20)){
					if(checkHouseBackNum($("#fraction").val(), "fraction", 10, 20)){
						$.ajax({
							url: path + "/crowdfunding/saveStockBackSet.html",
							type: "post",
							dataType: "json",
							data: {
								"loanNo": $("#projectLoanNo").val(),
								"projectFinanceAmt": $("#fp").val(),
								"investFinanceAmt": $("#ip_text").text(),
								"projectBonusRatio": $("#xmr").val(),
								"investBonusRatio": $("#tmr_text").text(),
								"financeNum": $("#fraction").val(),
								"miniInvestAmt": $("#lessF_text").text()
							},
							success: function(data){
								if(data["success"]){
									$('html,body').animate({
										scrollTop : '400px'
									}, 800);
									$("#backSetPauseBox").fadeOut(); //区域隐藏
									$("#backPauseTip").text("保存成功").show().animate({
										marginLeft: "10px"
									},1500); //成功效果
									//股权回报设置数据计算
									$("#proDataPauseBox").fadeIn(); //回报设置区域展示
									$("a[name='backSetPause']").children().text("展开");
									$("a[name='proDataPause']").children().text("收起");
									$("a[name='proDataPause']").attr("pause", "1");
									//顶部步骤进度改变
									$("#stepDiv>dl").eq(4).addClass("cur");
								}else{
									$('html,body').animate({
										scrollTop : '400px'
									}, 800);
									$("#backPauseTip").text(data["msg"]).show().animate({
										marginLeft: "10px"
									},1500); //成功效果
								}
							},
							error: function(request){
								console.log("保存股权回报设置异常");
							}
						});
					}
				}
			}else{
				AlertDialog.show("项目方出资不能大于融资金额", "fp", 15, 20);
			}
		}
	}
}
//验证是否是数字
function checkIsNaN(str, id){
	if(!str || isNaN(str)){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
		return false;
	}
	AlertDialog.hide();
	return true;
}
//检测认购份数
function checkHouseBackNum(str, id){
	//alert(11111);
	if(!str){
		AlertDialog.show($("#" + id).attr("nullmessage"), id, 10, 20);
		return false;
	}
	if(isNaN(str) || (Number(str)<1 || Number(str)>199)){
		AlertDialog.show("认购份数应该为1~199份", id, 10, 20);
		return false;
	}
	AlertDialog.hide();
	return true;
}

//股权权限设置
function limitSet(){
	$("#proLimitDiv").mouseover(function(){
		$(this).children("dd").show();
		$(this).children("dd").find("li").unbind("click").click(function(){
			$("#proLimitDt").text($(this).text());
			$(this).parent().parent().hide();
		});
	}).mouseout(function(){
		$(this).children("dd").hide();
	});
}
//保存项目详细资料
function saveCrowdFundDetail(){
	if(Valify.realName($("#csrName").val(), "csrName", "jump")){
		if(Valify.isNull($("#csrPhoto_url").val(), "csrPhotoBtn", -60, 20, "jump")){
			if(Valify.isNull(UE.getEditor("proDetail").getContent(), "proDetailNull", -440, 40, "jump")){
//				if(checkVideoUrl($("#loanVideo").val(), "loanVideo")){
//					if(Valify.isNull($("#videoDes").val(), "videoDes", -20, 20, "jump")){
						if(Valify.isNull($("#proPhoto_url").val(), "proPhotoBtn", -65, 20, "jump")){
							$.ajax({
								url: path + "/crowdfunding/updateCrowdFundDetail.html",
								type: "post",
								dataType: "json",
								data: {
									"loanNo": $("#projectLoanNo").val(),
									"founder": $("#csrName").val(), //创始人姓名
									"founderPhoto": $("#csrPhoto_url").val(),//创始人头像
									"companyFundDate": $("#compDate").val(),//成立日期
									"lastQuarterIncome": $("#lastQuarterIncome").val(), //上季度营业收入
									"lastQuarterProfit": $("#lastQuarterProfit").val(), //上季度营业利润
									"lastYearIncome": $("#lastYearIncome").val(), //上年度营业收入
									"lastYearProfit": $("#lastYearProfit").val(), //上年度营业利润
									"loanDetail": UE.getEditor("proDetail").getContent(), //项目介绍
									"financeBudget": $("#financeBudget").val(),//融资预算
									"loanVideo": $("#loanVideo").val(),
									"videoDes": $("#videoDes").val(),
									"houseDeveloper" : $("#mobileVideo").val(),
									"loanPhotos": $("#proPhoto_url").val()
								},
								success: function(data){
									if(data["success"]){
										$('html,body').animate({
											scrollTop : '400px'
										}, 800);
										$("#proDataPauseBox").fadeOut(); //区域隐藏
										$("#projectPauseTip").text("保存成功").show().animate({
											marginLeft: "10px"
										},1500); //成功效果
										$("#projectSubDiv").fadeIn(); //回报设置区域展示
										$("a[name='proDataPause']").children().text("展开");
										$("a[name='projectSubPause']").children().text("收起");
										$("a[name='projectSubPause']").attr("pause", "1");
										//顶部步骤进度改变
										$("#stepDiv>dl").eq(5).addClass("cur");
									}else{
										$('html,body').animate({
											scrollTop : '400px'
										}, 800);
										$("#projectPauseTip").text(data["msg"]).show().animate({
											marginLeft: "10px"
										},1500); //成功效果
									}
								},
								error: function(request){
									console.log("检查项目资料详情异常");
								}
							});
						}
//					}
//				}
			}
		}
	}
}
//检测视频链接
function checkVideoUrl(str, id){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}
	if(str.substring(str.length-3, str.length) != "swf"){
		AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20, "jump");
		return false;
	}
	AlertDialog.hide();
	return true;
}
//提交审核
function submitLoan(){
	if($("#checkLoanView").prop("checked")){
		AlertDialog.hide();
		$.ajax({
			url: path + "/crowdfunding/submit.html",
			type: "post",
			dataType: "json",
			data: {"loanNo": $("#projectLoanNo").val()},
			success: function(data){
				if(data["success"]){
					$('html,body').animate({
						scrollTop : '400px'
					}, 800);
					AlertDialog.tip("提交成功！");
					setTimeout(function(){
						window.location.href = path + "/common/myCenter.html";
					},2000);
				}
			},
			error: function(request){
				console.log("提交项目审核异常");
			}
		});
	}else{
		AlertDialog.show("请阅读并勾选服务协议", "checkLoanView", 15, 25);
	}
}


//查看注册协议
function showAgree(){
	$.ajax({
		url: path + "/node/getNode.html",
		type: "post",
		dataType: "json",
		data:{
			nodeType:"zcxy"
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
//			$("#agreeContent").html(data["msg"]["templateContent"]);
//			$("#agreeTime").text(data["msg"]["createTime"].substring(0,10));
			$("#agreeContent").html(data["msg"][0]["body"]);
//			$("#agreeTime").text(data["msg"][0]["createTime"].substring(0,10));
			
//			$("#bgpop").show();
			var al = (cv["winW"]-580)/2, at = (cv["winH"]-$(".agree_box").height())/2;
			$(".agree_box").css({"left":al+"px", "top":at+"px"}).show();
			$(".agree_close").click(function(){
				$(".agree_box").fadeOut();
//				$("#bgpop").hide();
			});
		},
		error: function(request){
			console.log("获取协议范文异常");
		}
	});
}
//发起人基本信息
function initUserInfo(){
	$.ajax({
		url: path + "/crowdfunding/getUserStuffById.html",
		type: "post",
		dataType: "json",
		data: {},
		success: function(bdata){
			if(bdata["success"]){
				$("#enterProUser").val(bdata["msg"]["companyName"]);
				$("#cardCode").val(bdata["msg"]["certNo"]);
				$("#mobile").val(bdata["msg"]["mobile"]);
				$("#licenceFrontDiv").show();
				//$('#licenceFrontImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["idCardFront"]);
				if(bdata["msg"]["idCardFront"]){
					$('#licenceFrontImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["idCardFront"]);	
				}else{
					$('#licenceReveseImage').attr("src", "");	
				}
				$("#licenceReveseDiv").show();
				//$('#licenceReveseImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["idCardBack"]);
				if(bdata["msg"]["idCardFront"]){	
					$('#licenceReveseImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["idCardBack"]);	
				}else{
					$('#licenceReveseImage').attr("src", "");	
				}
				//顶部步骤进度改变
				if(bdata["msg"]["companyName"]){
					$("#stepDiv>dl").eq(2).addClass("cur");
				}
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}

//获取投资领域
function getIndustry(){
	var cArr = [], cStr = '', cl = 0;
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": "concernIndustry"},
		success: function(data){
			cl = data.length;
			cArr.push('<option value="">请选择</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
			}
			cStr = cArr.join("");
			$("#ptrade_one").html(cStr);
		},
		error: function(request){
			console.log("获取投资领域异常");
		}
	});
}
