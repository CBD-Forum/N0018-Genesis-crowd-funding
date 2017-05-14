var id="";
var sData={};
$(function(){
	$('#add').height(bodyHeight-150);
	$('#add').width(bodyWidth-160);
	
	$('#tt').tabs({
		height:bodyHeight-150,
	    onSelect:function(title){
	    	
	    }   
	});
	
	
	
	$("#can_over").change(function(){
		if($(this).val()==0){
			$("#overFundAmt").val($("#fundAmt").val());
			$("#overFundAmt").attr("readonly","readonly");
			$("#overFundAmtDiv").hide();
		}else{
			$("#overFundAmt").removeAttr("readonly");
			$("#overFundAmtDiv").show();
		}
	});
	
	$("#fundAmt").keyup(function(){
		if($("#can_over").val()==0){
			$("#overFundAmt").val($("#fundAmt").val());
			$("#overFundAmt").attr("readonly","readonly");
		}else{
			$("#overFundAmt").removeAttr("readonly");
		}
	});
	
	
	
	showBisType();
	getProvice("pro_provice", "pro_city",function(){
		if(sData.province){
			$("#pro_provice").val(sData.province);
		}
	});
	//项目所属省份改变，城市改变
	$("#pro_provice").change(function(){
		getCitys($(this).val(), "pro_city");
	});
	
	$("#pro_city").change(function(){
		getCitys($(this).val(), "pro_county");
	});
	
	
	getProvice("user_provice", "user_city",function(){
		if(sData.province){
			$("#user_provice").val(sData.userProvince);
		}
	});
	
	//项目所属省份改变，城市改变
	$("#user_provice").change(function(){
		getCitys($(this).val(), "user_city");
	});
	
	
//	//选择借款用户
	$('#getLoanUserBtn').click(function(){
		getLoanUser('loanUserText','loanUser');
	});
	
	if (loanNo) {
		$.ajax({
			type : "POST",
			url : path + '/crowdfunding/getCrowdDetail.html',
			dataType:'json',
			data : {
				'loanNo' : loanNo
			},
			success : function(data) {
				id=data.id;
				getCitys(data.province, "pro_city",function(){
					$('#pro_city').val(data.city);
				});
				getCitys(data.city, "pro_county",function(){
					$('#pro_county').val(data.county);
				});
				if(data.canOver && data.canOver=='1'){
					$("#overFundAmtDiv").show();
				}
				if(!data.chargeFee){
					showChargeFeeScale();
				}
				getCitys(data.userProvince, "user_city",function(){
					$('#user_city').val(data.userCity);
				});
				$("#loanTypeName").text(data.loanTypeName);
				$("#baseForm").form('load',data);
				$("#sponsorForm").form('load',data);
				if (data.loanLogo) {
					$('#loanLogoForm input[name="logo"]').val(data.loanLogo);
					$('#logoDiv .filelist').html('<img src="'+fileUrl+data.loanLogo+'" style="width:200px; height:200px;"/>');
				}
				if (data.loanMobileLogo) {
					$('#loanLogoForm input[name="logo2"]').val(data.loanMobileLogo);
					$('#logoDiv2 .filelist').html('<img src="'+fileUrl+data.loanMobileLogo+'" style="width:200px; height:200px;"/>');
				}
				
				if (data.loanPhoto) {
					$('#loanLogoForm input[name="loanPhoto"]').val(data.loanPhoto);
					$('#loanPhotoDiv .filelist').html('<img src="'+fileUrl+data.loanPhoto+'" style="width:200px; height:200px;"/>');
				}
				
				if(data.loanDetail){
					UE.getEditor("loan_detail").ready(function(){
						UE.getEditor("loan_detail").setContent(data.loanDetail);
					});
				}
				
				if(data.orgCode){
					$("#welfareInfoForm input[name='orgCode']").val(data.orgCode);
				}
				
				if (data.orgCertificate) {
					$('#welfareInfoForm input[name="orgCertificate"]').val(data.orgCertificate);
					$('#orgCertificateDiv .filelist').html('<img src="'+fileUrl+data.orgCertificate+'" style="width:200px; height:200px;"/>');
				}
				
				if (data.promoterIdentitySign) {
					$('#welfareInfoForm input[name="promoterIdentitySign"]').val(data.promoterIdentitySign);
					$('#promoterIdentitySignDiv .filelist').html('<img src="'+fileUrl+data.promoterIdentitySign+'" style="width:200px; height:200px;"/>');
				}
				
				if (data.promoterIdentityPhoto) {
					$('#welfareInfoForm input[name="promoterIdentityPhoto"]').val(data.promoterIdentityPhoto);
					$('#promoterIdentityPhotoDiv .filelist').html('<img src="'+fileUrl+data.promoterIdentityPhoto+'" style="width:200px; height:200px;"/>');
				}
				if(data.orgLoanReceiveProve){
					$("#welfareInfoForm input[name='orgLoanReceiveProve']").val(data.msg);
					$("#uploadInFo").append("<a href="+fileUrl+data.orgLoanReceiveProve+" target='_black'>查看上传文件</a>");
				}
				
        		
				
				$("#videoForm").form('load',data);
				if(data.loanVideo){
					var str = '<embed style="height:450px; width:500px;margin-left:50px;" src="'+data.loanVideo+'" quality="high" align="middle"  mode="transparent" type="application/x-shockwave-flash"></embed>';
					$("#loanVideoDisplay").html(str);
				}
				
				if(data.businessLicense){
//					loadPhoto(data.orgCode,"orgCode","orgCodeDiv");
					var urlSplit = data.businessLicense.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="businessLicense" value="'+urlSplit[j]+'"/>'+
//						'<input type="hidden" name="id" value="orgCode'+j+'"/>'+
					 	'<div id="orgCode'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}

					$(picStr).appendTo('#businessLicenseDiv .filelist');
					
				}
				
				
				//项目图片
				if (data.photoUrls) {
					var loanPics = data.photoUrls.split(',');
					for ( var i = 0; i < loanPics.length; i++) {
						loadPhoto('a0001'+i,loanPics[i],'项目图片'+(i+1), 'loanphoto_pigup');
					}
				}
			}
		});
	}
	
	//保存基本信息
	$('#basicBtn').click(function() {
		$('#baseForm').submit();
	});
	
	$("#baseForm").validate({
		errorPlacement : function(error, element) {
			if ( element.is(":radio") ){
				error.appendTo (element.parent());
			}else if (element.is(":checkbox") ){
		    	error.appendTo (element.parent());
		    }else{
		        error.appendTo(element.parent());
			}
    	},
		rules : {
			loanName : "required",
			loanUserName : "required",
			fundAmt :  {
				required:true,
				number:true
			},
			fundDays :  {
				required:true,
				number:true
			},
			prizeNum :  {
				required:true,
				number:true
			},
			lockDay :  {
				required:true,
				number:true
			},
			dailyEarningsForecast:{
				required:true,
				number:true
			},
			chargeFee:  {
				required:true,
				number:true
			},
			province : "required",
//			city : "required",
//			county : "required",
/*			loanDes : "required",*/
			loanIntroduction:{
				required:true,
				minlength:0,
				maxlength:20
			}
		},
		messages : {
			loanName : "请填写项目名称",
			loanUserText : "请填写发起人",
			fundAmt : {
				required:"请填写筹资金额"
			},
			fundDays : {
				required:"请填写筹资天数"
			},
			prizeNum : {
				required:"请填写抽奖名额"
			},
			lockDay:{
				required:"请填写锁定天数"
			},
			dailyEarningsForecast:{
				required:"请填写预计每日收益天数"
			},
			chargeFee : {
				required:"请填写服务费比例"
			},
			province : "请填写项目所在省",
//			city : "请填写项目所在市",
//			county : "请填写项目所在县",
/*			loanDes : "请填写项目简介",*/
			loanIntroduction  : {
				required:"请填写一句话描述项目" 
			}
		},
        submitHandler:function(form){
        	if (loanNo) {
        		url=path + '/crowdfunding/updateCrowdFunding.html';
        	}
        	if($("#chargeFee").val()<0 || $("#chargeFee").val()>1){
        		$.messager.alert('提示', '服务费比例必须大于0小于1');
        		return;
        	}
        	if($("#can_over").val()==1){
        		if(Number($("#overFundAmt").val()) < Number($("#fundAmt").val())){
	        		$.messager.alert('提示', '超募金额上限不能小于筹资金额');
	        		return;
        		}
        	} 
        	if($("#can_over").val()==1){
        		if(Number($("#overFundAmt").val()) < Number($("#fundAmt").val())){
	        		$.messager.alert('提示', '超募金额上限不能小于筹资金额');
	        		return;
        		}
        	}
        	$('#baseForm').form('submit', {
    			url : url,
    			queryParams: {
    				"id":id,
    			},
    			success : function(data) {
    				data = $.parseJSON(data);
    				if(data["success"]){
    					$.messager.alert('提示', '保存成功');
    				}else{
    					$.messager.alert('提示', data["msg"]);
    				}
    			}
    		});
        }
    }); 
	
	
	
	$("#logoSaveBtn").click(function(){
		
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFunding.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"id": id,
					"loanLogo": $('#loanLogoForm input[name="logo"]').val(),
					"loanMobileLogo" :$('#loanLogoForm input[name="logo2"]').val(),
					"loanPhoto" :$('#loanLogoForm input[name="loanPhoto"]').val(),
				},
			success: function(data){
				if(data["success"]){
					$.messager.alert('提示', '更新logo成功');
				}
			},
			error: function(){
				
			}
		});
		
	});
	
	$("#detailSaveBtn").click(function(){
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"loanDetail": UE.getEditor("loan_detail").getContent()
				},
			success: function(data){
				if(data["success"]){
					$.messager.alert('提示', '更新成功');
				}
			},
			error: function(){
				
			}
		});
	});
	
	$("#videoSaveBtn").click(function(){
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"loanVideo": $("#loan_video").val(),
					"mobileVideo": $("#mobile_video").val(),
					"videoDes": $("#video_des").val()
				},
			success: function(data){
				if(data["success"]){
					$.messager.alert('提示', '更新成功');
				}
			},
			error: function(){
				
			}
		});
	});
	
	
	$("#sponsorSaveBtn").click(function(){
		if(!loanNo){
			$.messager.alert("提示","请先保存项目基本信息");
			return false;
		}
		if(!$("#founder").val()){
			$.messager.alert("提示","请填写真实姓名！");
			return false;
		}
		if(!$("#user_provice").val()){
			$.messager.alert("提示","请填写所在省！");
			return false;
		}
		if(!$("#user_city").val()){
			$.messager.alert("提示","请填写所在市！");
			return false;
		}
		if(!$("#userMobile").val()){
			$.messager.alert("提示","请填写联系电话！");
			return false;
		}
		
		var businessLicense="";
		$("input[name='businessLicense']").each(function(i,v){
			businessLicense += $(v).val()+",";
		});
		businessLicense = businessLicense.substring(0, businessLicense.length-1);
		
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"founder": $("#founder").val(),
					"userProvince": $("#user_provice").val(),
					"userCity": $("#user_city").val(),
					"userMobile": $("#userMobile").val(),
					"company": $("#company").val(),
					"businessLicense": businessLicense
				},
			success: function(data){
				if(data["success"]){
					$.messager.alert('提示', '保存成功');
					$('#videoSaveBtn').css({
						"background":"#ccc",
						"border":"#ccc"
					})
					$('#videoSaveBtn').unbind("click");
				}
			},
			error: function(){
				
			}
		});
	});
	
	createWebUploaderMore('businessLicenseBtn','businessLicenseDiv','businessLicense','sponsorForm');
	
	createWebUploader('logoBtn','logoDiv','logo','loanLogoForm');
	createWebUploader('logoBtn2','logoDiv2','logo2','loanLogoForm');
	createWebUploader('loanPhotoBtn','loanPhotoDiv','loanPhoto','loanLogoForm');
	
});

/*function logoCallback(pics){
	for ( var i = 0; i < pics.length; i++) {
		loadPhoto(pics[i].title.substring(0,pics[i].title.indexOf('.')),pics[i].src,pics[i].alt,'logo_pigup');
	}
	$('#proIframe').dialog('close');
}

function loadPhoto(id,url,name, renderId){
	var picStr = '<div class="photo">'+
					'<input type="hidden" name="loanPhoto" value="'+url+'"/>'+
					'<input type="hidden" name="id" value="'+id+'"/>'+
				 	'<div id="'+id+'">'+
				 		'<img src="'+fileUrl+url+'">'+
				 		'<div id="testPicOperate" class="operate">'+
				 			'<span class="cancel"></span>'+
				 		'</div>'+
				 	'</div>'+
				 	'<div class="text" id="'+id+'name" title="'+name+'">'+name+'</div>'+
				 	'<div class="textInput" id="'+id+'input" ><input type="text" name="photoName" value="'+name+'"/></div>'+
				 '</div>';

	$(picStr).appendTo('#'+renderId+' .filelist');
	
	//编辑图片名称
	$('#'+id+'name').click(function(){
		$(this).hide();
		$('#'+id+'input').show();
		$('#'+id+'input input').focus().select();
	});
	//停止编辑
	$('#'+id+'input input').blur(function(){
		$(this).parent().hide();
		$('#'+id+'name').attr('title',$(this).val()).html($(this).val());
		$('#'+id+'name').show();
	});
	
	$('#'+id).mouseenter(function(){
		$(this).find('.operate').stop().animate({height: 30});
		$(this).find('.cancel').stop().animate({height: 30});
	}).mouseleave(function(){
		$(this).find('.operate').stop().animate({height: 0});
		$(this).find('.cancel').stop().animate({height: 0});
	});
	
	$('#'+id).find('.cancel').click(function(){
		removePic(id);
	});
}
//删除图片
function removePic(picId){
	$('#'+picId).parent().remove();
}
*/

function searchUser(){
	queryToGrid("loanUserTable", "list_search");
}
//展示行业类别
function showBisType(){
	var cArr = [], cStr = '', cl = 0;
	$.ajax({
		url: path + "/dictionary/getDic.html",
		type: "post",
		dataType: "json",
		data: {"type": "concernIndustry"},
		success: function(data){
			getLoanTypeFn("super_industry", data["rows"]);
			if(sData.superIndustry){
				$("#super_industry").val(sData.superIndustry);
			}
		},
		error: function(request){
			console.log("获取项目类型异常");
		}
	});
}


function uploadFile(id){
	$("#"+id).find('.operate').slideDown();
	$("#"+id).hover(function(){
//		$("#"+id).find('.operate').slideDown();
		//$(this).find('.cancel').slideDown();
	},function(){
		$("#"+id).find('.operate').slideUp();
		//$(this).find('.cancel').slideUp();
	});
	
	$('#'+id).find('.cancel').click(function(){
		removePic(id);
	});
}


//删除图片
function removePic(picId){
	$('#'+picId).parent().remove();
}
function showChargeFeeScale(){
	$.ajax({
		url: path + "/crowdfundInvest/getChargeFeeScale.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(data.success){
				$("#chargeFee").val(data.msg.chargeScale);
			}
		},
		error: function(request){
			console.log("获取服务费比列失败");
		}
	});
}