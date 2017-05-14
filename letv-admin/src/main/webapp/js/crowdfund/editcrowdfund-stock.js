var id="";
var sData={};
var projectFinanceAmt=0.0;
$(function(){
	$('#add').height(bodyHeight-150);
	$('#add').width(bodyWidth-160);
	
	$('#tt').tabs({
		height:bodyHeight-150,
	    onSelect:function(title){
	    }   
	});
	
	getProvice("pro_provice", "pro_city",function(){
		if(sData.province){
			$("#pro_provice").val(sData.province);
		}
	});
	
	//项目所属省份改变，城市改变
	$("#pro_provice").change(function(){
		getCitys($(this).val(), "pro_city");
	});
	
	//城市改变，查询县
	$("#pro_city").change(function(){
		getCitys($(this).val(), "pro_county");
	});
	
	// 选择合同模版
	$('#getContractTplBtn').click(function() {
		getContractTpl();
	});
	//showBisType();
	showLoanStage();
//	//选择借款用户
	$('#getLoanUserBtn').click(function(){
		getLoanUser('loanUserText','loanUser');
	});
	
     //	//选择项目负责人
	$('#getLoanManagerBtn').click(function(){
		getLoanManager();
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
				sData = data;
				id=data.id;
				
				if("stock"==data.loanType){
					//非公开
					$("#backInfoDiv").hide();
				}else{
					$("#backInfoDiv").show();
				}
				
				projectFinanceAmt = data.projectFinanceAmt;
				getCitys(data.province, "pro_city",function(){
					$('#pro_city').val(data.city);
				});
				getCitys(data.city, "pro_county",function(){
					$('#pro_county').val(data.county);
				});
				$("#loanTypeName").text(data.loanTypeName);
				if(data.canOver && data.canOver=='1'){
					$("#overFundAmtDiv").show();
				}
				if(!data.chargeFee){
					showChargeFeeScale();
				}
				$("#baseForm").form('load',data);
				
				$("#detailForm").form('load',data);
				
				if(data.contractTplName){
					//合同模版
					$('#contractTplNoText').val(data.contractTplName);
				}
				//行业类别
				getIndustry(function(){
					if(data.superIndustry){
						$('#super_industry').val(data.superIndustry);
					}
				});
				//项目阶段
				showLoanStage(function(){
					if(data.superIndustry){
						$('#loan_stage').val(data.loanStage);
					}
				});
				
				
				
				if (data.loanLogo) {
					$('#loanLogoForm input[name="logo"]').val(data.loanLogo);
					$('#logoDiv .filelist').html('<img src="'+fileUrl+data.loanLogo+'" style="width:200px; height:200px;"/>');
				}
				if (data.loanPhoto) {
					$('#loanLogoForm input[name="photo"]').val(data.loanPhoto);
					$('#photoDiv .filelist').html('<img src="'+fileUrl+data.loanPhoto+'" style="width:200px; height:200px;"/>');
				}
				if (data.weixin) {
					$('#baseForm input[name="weixin"]').val(data.weixin);
					$('#weixinDiv .filelist').html('<img src="'+fileUrl+data.weixin+'" style="width:200px; height:200px;"/>');
				}
				
				if (data.logo) {
					$('#baseForm input[name="logo"]').val(data.logo);
					$('#companyLogoDiv .filelist').html('<img src="'+fileUrl+data.logo+'" style="width:200px; height:200px;"/>');
				}				
				if (data.loanMobileLogo) {
					$('#loanLogoForm input[name="logo2"]').val(data.loanMobileLogo);
					$('#logoDiv2 .filelist').html('<img src="'+fileUrl+data.loanMobileLogo+'" style="width:200px; height:200px;"/>');
				}
				if(data.founderPhoto){
					$('#founderphotoDiv .filelist').html('<img src="'+fileUrl+data.founderPhoto+'" style="width:200px; height:200px;"/>');
				}
				if(data.competence){
					UE.getEditor("competence_ue").ready(function(){
						UE.getEditor("competence_ue").setContent(data.competence);
					});
				}
				if(data.profitModel){
					UE.getEditor("profitModel_ue").ready(function(){
						UE.getEditor("profitModel_ue").setContent(data.profitModel);
					});
				}				
				if(data.financeBudget){
					UE.getEditor("finance_budget").ready(function(){
						UE.getEditor("finance_budget").setContent(data.financeBudget);
					});
				}
				if(data.loanDetail){
					UE.getEditor("loan_detail").ready(function(){
						UE.getEditor("loan_detail").setContent(data.loanDetail);
					});
				}
				if(data.loanTeam){
					UE.getEditor("loan_team").ready(function(){
						UE.getEditor("loan_team").setContent(data.loanTeam);
					});
				}
				//资金用途
				if(data.capitalUsed){
					UE.getEditor("capitalUsed").ready(function(){
						UE.getEditor("capitalUsed").setContent(data.capitalUsed);
					});
				}
				//行业分析
				if(data.industryAnalysis){
					UE.getEditor("industryAnalysis").ready(function(){
						UE.getEditor("industryAnalysis").setContent(data.industryAnalysis);
					});
				}
				//分险管控
				if(data.riskMeasure){
					UE.getEditor("riskMeasure").ready(function(){
						UE.getEditor("riskMeasure").setContent(data.riskMeasure);
					});
				}
				//融资计划
				if(data.financePlan){
					UE.getEditor("financePlan").ready(function(){
						UE.getEditor("financePlan").setContent(data.financePlan);
					});
				}
				//附件
				if(data.businessProposalFiles){
					var oArr = [], oStr = '',l=data["businessProposalFiles"].length;
					for(var i=0;i<l;i++){
						var row = data["businessProposalFiles"][i];
						oArr.push("<p><a href="+fileUrl+row.fileUrl+" target='_black'>"+row.fileName+"</a>" +
	            						"<input type='hidden' name='exitScheme' value="+row.fileUrl+" />" +
	            						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(row.id)+"' onclick='removeFile(this);'>删除</a>" +
	            								"</p>");
					}
					oStr = oArr.join("");
					$("#businessProposalUploadInFo").html(oStr);
				}
				if(data.exitSchemeFiles){
					var oArr = [], oStr = '',l=data["exitSchemeFiles"].length;
					for(var i=0;i<l;i++){
						var row = data["exitSchemeFiles"][i];
						oArr.push("<p><a href="+fileUrl+row.fileUrl+" target='_black'>"+row.fileName+"</a>" +
	            						"<input type='hidden' name='exitScheme' value="+row.fileUrl+" />" +
	            						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(row.id)+"' onclick='removeFile(this);'>删除</a>" +
	            								"</p>");
					}
					oStr = oArr.join("");
					$("#exitSchemeUploadInFo").html(oStr);
				}				
				
				if(data.otherAccessoriesFiles){
					var oArr = [], oStr = '',l=data["otherAccessoriesFiles"].length;
					for(var i=0;i<l;i++){
						var row = data["otherAccessoriesFiles"][i];
						oArr.push("<p><a href="+fileUrl+row.fileUrl+" target='_black'>"+row.fileName+"</a>" +
	            						"<input type='hidden' name='exitScheme' value="+row.fileUrl+" />" +
	            						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(row.id)+"' onclick='removeFile(this);'>删除</a>" +
	            								"</p>");
					}
					oStr = oArr.join("");
					$("#otherAccessoriesUploadInFo").html(oStr);
				}					
				
				$("#videoForm").form('load',data);
				if(data.loanVideo){
					var str = '<embed style="height:450px; width:500px;margin-left:50px;" src="'+data.loanVideo+'" quality="high" align="middle"  mode="transparent" type="application/x-shockwave-flash"></embed>';
					$("#loanVideoDisplay").html(str);
				}
				 
				$("#companyForm").form('load',data);
				if(data.businessLicense){
					var urlSplit = data.businessLicense.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="businessLicense" value="'+urlSplit[j]+'"/>'+
					 	'<div id="businessLicense'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}
					$(picStr).appendTo('#businessLicenseDiv .filelist');
				}
				
				if(data.legalCard){
					var urlSplit = data.legalCard.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="legalCard" value="'+urlSplit[j]+'"/>'+
					 	'<div id="legalCard'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}
					$(picStr).appendTo('#legalCardDiv .filelist');
				}
				if(data.licenseIdentityInformation){
					var urlSplit = data.licenseIdentityInformation.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="licenseIdentityInformation" value="'+urlSplit[j]+'"/>'+
//						'<input type="hidden" name="id" value="orgCode'+j+'"/>'+
					 	'<div id="licenseIdentityInformation'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}
					$(picStr).appendTo('#licenseIdentityInformationDiv .filelist');
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
	

	// 选择是否超募
	$("#can_over").change(function() {
		
		var overFundAmt =""; //募集上线
		if ($(this).val() && $(this).val() == '1') {
			$("#overFundAmtDiv").show();
			var fundAmt = $("#fundAmt").val();
			if(fundAmt){
				$("#overFundAmt").val(fundAmt);
				$("#stockPartAmt").val(fundAmt);
				$("#stockNum").val(1);
			}else{
				$("#fundAmt").val(0);
				$("#overFundAmt").val(0);
				$("#stockPartAmt").val(0);
				$("#stockNum").val(0);
			}
		}else{
			$("#overFundAmt").val(0);
			var fundAmt = $("#fundAmt").val();
			var stockPartAmt = $("#stockPartAmt").val();
			if(fundAmt){
				if(stockPartAmt){
					$("#stockNum").val(fundAmt/stockPartAmt);
				}else{
					$("#stockPartAmt").val(fundAmt);
					$("#stockNum").val(1);
				}
			}else{
				$("#fundAmt").val(0);
				$("#stockPartAmt").val(0);
				$("#stockNum").val(0);
			}
			$("#overFundAmtDiv").hide();
		}
	});
	
	
	//失去焦点事件
	$("#fundAmt").blur(function(){
		stockDataChange();
	});
	$("#overFundAmt").blur(function(){
		stockDataChange();
	});	
	$("#stockPartAmt").blur(function(){
		stockDataChange();
	});	
	
	
	
	$("#businessProposalChangeUpload").click(function(){
		if (!loanNo) {
			$.messager.alert('提示', '请先保存基本信息');
			return false;
		}
		$("#businessProposalFileToUpload").click();
	});
	//选择文件之后执行上传  
    $('#businessProposalFileToUpload').on('change', function() {
    	var sendLoanMask=getMask('正在上传，请稍后...');
        $.ajaxFileUpload({  
        	url:path+'/fileUpload/uploadFile.html?jq_random='+Math.random().toFixed(5)+'&type=businessProposal&parentId='+loanNo,  
            secureuri:false,  
            fileElementId:'businessProposalFileToUpload',//file标签的id  
            dataType: 'json',//返回数据的类型  
            success: function (data, status) {
            	if(data["success"]){
            		$.messager.alert('提示', '上传成功');
            		sendLoanMask.dialog('close');
            		$("#businessProposalUploadInFo").append("<p>" +
            				"<a href="+fileUrl+data.msg+" target='_black'>"+data.fileName+"</a>" +
	    						"<input type='hidden' name='businessProposal' value="+data.msg+" />" +
	    						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(data.id)+"' onclick='removeFile(this);'>删除</a>" +
	    								"</p>");
				}else{
					$.messager.alert("提示","上传失败！");
				}
            },  
            error: function (data, status, e) {  
                alert(e);
            },
            complete:function(XMLHttpRequest,textStatus){
	        	sendLoanMask.dialog('close');
	        }
        });  
    });  
    
    
    
    $("#otherAccessoriesChangeUpload").click(function(){
    	if (!loanNo) {
    		$.messager.alert('提示', '请先保存基本信息');
    		return false;
    	}
		$("#otherAccessoriesFileToUpload").click();
	});
	//选择文件之后执行上传  
    $('#otherAccessoriesFileToUpload').on('change', function() {
    	var sendLoanMask=getMask('正在上传，请稍后...');
        $.ajaxFileUpload({  
        	url:path+'/fileUpload/uploadFile.html?jq_random='+Math.random().toFixed(5)+'&type=otherAccessories&parentId='+loanNo,  
            secureuri:false,  
            fileElementId:'otherAccessoriesFileToUpload',//file标签的id  
            dataType: 'json',//返回数据的类型  
            success: function (data, status) {
            	if(data["success"]){
            		sendLoanMask.dialog('close');
            		$.messager.alert('提示', '上传成功');
            		$("#otherAccessoriesUploadInFo").append("<p>" +
            				"<a href="+fileUrl+data.msg+" target='_black'>"+data.fileName+"</a>" +
	    						"<input type='hidden' name='otherAccessories' value="+data.msg+" />" +
	    						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(data.id)+"' onclick='removeFile(this);'>删除</a>" +
	    								"</p>");
				}else{
					$.messager.alert("提示","上传失败！");
				}
            },  
            error: function (data, status, e) {  
                alert(e);
            },
            complete:function(XMLHttpRequest,textStatus){
	        	sendLoanMask.dialog('close');
	        }
        });  
    });
    $("#exitSchemeChangeUpload").click(function(){
    	if (!loanNo) {
    		$.messager.alert('提示', '请先保存基本信息');
    		return false;
    	}
		$("#exitSchemeFileToUpload").click();
	});
	//选择文件之后执行上传  
    $('#exitSchemeFileToUpload').on('change', function() {
    	var sendLoanMask=getMask('正在上传，请稍后...');
        $.ajaxFileUpload({  
            url:path+'/fileUpload/uploadFile.html?jq_random='+Math.random().toFixed(5)+'&type=exitScheme&parentId='+loanNo,  
            secureuri:false,  
            fileElementId:'exitSchemeFileToUpload',//file标签的id  
            dataType: 'json',//返回数据的类型   
            success: function (data, status) {
            	if(data["success"]){
            		sendLoanMask.dialog('close');
            		$.messager.alert('提示', '上传成功');
            		$("#exitSchemeUploadInFo").append("<p>" +
            				"<a href="+fileUrl+data.msg+" target='_black'>"+data.fileName+"</a>" +
            						"<input type='hidden' name='exitScheme' value="+data.msg+" />" +
            						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(data.id)+"' onclick='removeFile(this);'>删除</a>" +
            								"</p>");
				}else{
					$.messager.alert("提示","上传失败！");
				} 
            },  
            error: function (data, status, e) {  
                alert(e);
            },
            complete:function(XMLHttpRequest,textStatus){
	        	sendLoanMask.dialog('close');
	        }
        });  
    });
	
 
	
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
			chargeFee:  {
				required:true,
				number:true
			},
			contractNo : {
				required : true, 
				minlength:0,
				maxlength:20
			},
			prepayAmt:  {
				required:true,
				number:true
			},
			dailyEarningsForecast : {
				required : true,
				number : true
			},
			province : "required",
			superIndustry:"required",
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
			chargeFee : {
				required:"请填写服务费比例"
			},
			prepayAmt:  {
				required:"请填写发起人预支付金额"
			},
			dailyEarningsForecast :{
				required : "请填写预计每日收益",
				number : "清填写正确的预计每日收益"
			},
			province : "请填写项目所在省", 
			loanIntroduction  : {
				required:"请填写一句话描述项目" 
			}
		},
        submitHandler:function(form){
        	if (loanNo) {
        		url=path + '/crowdfunding/updateCrowdFunding.html';
        	}
        	
        	if ($("#chargeFee").val() < 0 || $("#chargeFee").val() > 1) {
				$.messager.alert('提示', '服务费比例必须大于0小于1');
				return;
			}
        	
/*        	if ($("#dailyEarningsForecast").val() < 0 || $("#dailyEarningsForecast").val() > 1) {
				$.messager.alert('提示', '预计每日收益必须大于0小于1');
				return;
			}*/
        	
        	
/*        	if($("#prepayAmt").val()>projectFinanceAmt){
        		$.messager.alert('提示', '发起人预付金额不能大于发起人出资总金额:'+projectFinanceAmt);
        		return;
        	}*/
        	
        	if(stockDataChange()){
    			var competence = UE.getEditor("competence_ue").getContent();
    			var profitModel = UE.getEditor("profitModel_ue").getContent();
    			$("#competence").val(competence);
    			$("#profitModel").val(profitModel);
    			
    			var contractTplNo = $('#contractTplNo').val();
    			if(!contractTplNo){
    				$.messager.alert('提示', '请选择合同模板！');
    				return false;
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
        }
    }); 
	
	//上传项目相关图片
	$('#logoPicBtn').click(function(){
		var logoLen = $('#logo_pigup input[name="loanPhoto"]').length;
		if(logoLen == 1){
			$.messager.alert('提示', 'logo只能上传一张');
			return;
		}
		var iframeStr='<iframe id="proIframe" class="uploadIframe" src="'+path+'/js/common/ueditor/dialogs/image/uploadImage.html?callback=logoCallback" width="690" height="400" frameborder="0" scrolling="no"></iframe>';
		$(iframeStr).dialog({
			title: "多图上传",
			height: 400,
			width: 700,
			modal : true,
			onClose: function () {
				 $('#proIframe').remove();
			}
		});
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
					"loanPhoto" :$('#loanLogoForm input[name="photo"]').val()
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
	
	
	
	$("#detailSaveBtn").click(function(){
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"loanDetail": UE.getEditor("loan_detail").getContent(),
					"financeBudget": UE.getEditor("finance_budget").getContent(),
					"founder": $("#founder").val(),
					"founderPhoto":  $('#detailForm input[name="founderphoto"]').val(),
					"lastQuarterIncome": $("#lastQuarterIncome").val(),
					"lastQuarterProfit": $("#lastQuarterProfit").val(),
					"lastYearIncome": $("#lastYearIncome").val(),
					"lastYearProfit": $("#lastYearProfit").val(),
					"companyFundDate":$("#companyFundDate").val()
					
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
					"videoDes": $("#video_des").val(),
					"houseDeveloper": $("#house_developer").val()
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
	
	 
	
	$("#companySaveBtn").click(function() {
		if (!loanNo) {
			$.messager.alert('提示', '请先保存基本信息');
			return false;
		}
		
		var businessLicense="";
		$("input[name='businessLicense']").each(function(i,v){
			businessLicense += $(v).val()+",";
		});
		businessLicense = businessLicense.substring(0, businessLicense.length-1);
		
		var legalCard="";
		$("input[name='legalCard']").each(function(i,v){
			legalCard += $(v).val()+",";
		});
		legalCard = legalCard.substring(0, legalCard.length-1);
		
		
		//身份证件
		var licenseIdentityInformation="";
		$("input[name='licenseIdentityInformation']").each(function(i,v){
			licenseIdentityInformation += $(v).val()+",";
		});
		licenseIdentityInformation = licenseIdentityInformation.substring(0, licenseIdentityInformation.length-1);
		var businessProposal="";
		$("input[name='businessProposal']").each(function(i,v){
			businessProposal += $(v).val()+",";
		});
		businessProposal = businessProposal.substring(0, businessProposal.length-1);
		var otherAccessories="";
		$("input[name='otherAccessories']").each(function(i,v){
			otherAccessories += $(v).val()+",";
		});
		otherAccessories = otherAccessories.substring(0, otherAccessories.length-1);
		$.ajax({
			url : path + '/crowdfunding/updateCrowdFundDetail.html',
			type : "post",
			dataType : "json",
			async : false,
			data : {
				"loanNo" : loanNo,
				"loanTeam": UE.getEditor("loan_team").getContent(),
				"businessLicense" : businessLicense,
				"businessProposal":businessProposal,
				"otherAccessories":otherAccessories,
				"legalCard" :legalCard,
				"licenseIdentityInformation":licenseIdentityInformation,
				"transferRatio" : $('#transferRatio').val(),
				"exitScheme" : $("textarea[name='exitScheme']").val(),
				"capitalUsed" : $("textarea[name='capitalUsed']").val(),
				"industryAnalysis" :$("textarea[name='industryAnalysis']").val(),
				"riskMeasure" :$("textarea[name='riskMeasure']").val(),
				"financePlan" :$("textarea[name='financePlan']").val(),
                "capitalUsed":UE.getEditor("capitalUsed").getContent(),
                "industryAnalysis":UE.getEditor("industryAnalysis").getContent(),
                "riskMeasure":UE.getEditor("riskMeasure").getContent(),
                "financePlan":UE.getEditor("financePlan").getContent()
			},
			success : function(data) {
				if (data["success"]) {
					$.messager.alert('提示', '保存成功');
					$('#detailSaveBtn').css({
						"background" : "#ccc",
						"border" : "#ccc"
					})
					$('#companySaveBtn').unbind("click");
				}
			},
			error : function() {

			}
		});
	});
	
	
	
	
	createWebUploader('founderphotoBtn','founderphotoDiv','founderphoto','detailForm');
	
	createWebUploader('logoBtn','logoDiv','logo','loanLogoForm');
	createWebUploader('logoBtn2','logoDiv2','logo2','loanLogoForm');
	
	createWebUploader('weixinBtn', 'weixinDiv', 'weixin', 'baseForm');
	createWebUploader('companyLogoBtn', 'companyLogoDiv', 'logo', 'baseForm');
	
	createWebUploader('photoBtn','photoDiv','photo','loanLogoForm');
	
	
	createWebUploaderMore('businessLicenseBtn','businessLicenseDiv','businessLicense','companyForm');
	createWebUploaderMore('legalCardBtn','legalCardDiv','legalCard','companyForm');
	createWebUploaderMore('licenseIdentityInformationBtn','licenseIdentityInformationDiv','licenseIdentityInformation','companyForm');
	
	
	
});

/*function logoCallback(pics){
	for ( var i = 0; i < pics.length; i++) {
		loadPhoto(pics[i].title.substring(0,pics[i].title.indexOf('.')),pics[i].src,pics[i].alt,'logo_pigup');
	}
	$('#proIframe').dialog('close');
}

function fundLogoCallback(pics){
	for ( var i = 0; i < pics.length; i++) {
		loadPhoto(pics[i].title.substring(0,pics[i].title.indexOf('.')),pics[i].src,pics[i].alt,'founderPhoto_pigup');
	}
	$('#proIframe').dialog('close');
}*/

/*function loadPhoto(id,url,name, renderId){
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
}*/


function searchUser(){
	queryToGrid("loanUserTable", "list_search");
}



//选择项目负责人
function getLoanManager(){
	$('<div id="loanManagerDiv"><table id="loanManagerTable"></table></div>').hide().appendTo('body');
	
	//获取字典数据
	var columns = [[
					{field:'adminId',title:'用户名',width:150,align:'center'},
					{field:'employeeNo',title:'员工号',width:150,align:'center'},
					{field:'realName',title:'真实姓名',width:150,align:'center'},
					{field:'mobile',title:'手机号',width:150,align:'center'}
				]];
	$('#loanManagerTable').datagrid({
		url: path + "/auth/user/getUserByRole.html?roleCode=loan_manager",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:364,
		pagination: true,
		onDblClickRow:function(rowIndex, row){
			$('#loanManagerName').val(row.realName);
			$('#loanManager').val(row.adminId);
			$("#loanManagerDiv").dialog('close');
		}
	});
	$("#loanManagerDiv").show().dialog({
		title: "选择项目负责人",
		height: 400,
		width:650,
		modal: true,
		onClose: function () {
			$("#loanManagerDiv").remove();
        }
	});
	
}



//展示行业类别
/*function showBisType(){
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
}*/
//展示行业子类别
/*function showChildType(tid){
	var cArr = [], cStr = '', cl;
	$.ajax({
		url: path + "/dictionary/getDic.html",
		type: "post",
		dataType: "json",
		data: {"type": tid},
		success: function(data){
			if(data.rows.length == 0){
				$("#child_industry").hide();
			}else{
				$("#child_industry").show();
			}
			getLoanTypeFn("child_industry", data["rows"]);
			if(sData.childIndustry){
				$("#child_industry").val(sData.childIndustry);
			}
		},
		error: function(request){
			console.log("获取项目子类型异常");
		}
	});
}*/

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

 
function removeFile(aid){
	// 删除
	$.messager.confirm( '提示','您确定要删除文件吗？',function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/fileUpload/removeFile.html',
				data : {
					'id' : $(aid).attr('id')
				},
				success : function() {
					$('#'+$(aid).attr("id")).parent().remove();
				}
			});
		}
	});
}
//项目阶段
function showLoanStage(callback) {
	var cArr = [], cStr = '', cl = 0;
	$.ajax({
		url : path + "/dictionary/getDic.html",
		type : "post",
		dataType : "json",
		data : {
			"type" : "crowdFund_loan_stage"
		},
		success : function(data) {
			getLoanTypeFn("loan_stage", data["rows"]);
			if (sData.superIndustry) {
				$("#loan_stage").val(sData.superIndustry);
			}
			if(callback){
				callback();
			}
		},
		error : function(request) {
			console.log("获取项目阶段异常");
		}
	});
}
 
//查询行业
function getIndustry(callback){
	$.ajax({
		url : path + "/dictionary/getDic.html",
		type : "post",
		dataType : "json",
		data : {
			"type" : "concernIndustry"
		},
		success: function(data){
			getLoanTypeFn("super_industry", data["rows"]);
			if (sData.superIndustry) {
				$("#super_industry").val(sData.superIndustry);
			}
			if (callback) {
				callback();
			}
		},
		error: function(){
			console.log("获取项目所在城市异常");
		}
	});
}



//选择合同模版
function getContractTpl() {
	$('<div id="contractTplDiv"><table id="contractTplTable"></table></div>')
			.hide().appendTo('body');
	// 获取字典数据
	var columns = [ [ {
		field : 'contractNo',
		title : '模板编号',
		width : 150,
		align : 'center',
		sortable : true
	}, {
		field : 'contractName',
		title : '模板名称',
		width : 180,
		align : 'center'
	}, {
		field : 'contractTypeName',
		title : '模板类型',
		width : 180,
		align : 'center'
	}, {
		field : 'statusName',
		title : '状态',
		align : 'center'
	}, {
		field : 'description',
		title : '描述',
		width : 250,
		align : 'center'
	}, {
		field : 'createTime',
		title : '录入时间',
		width : 130,
		align : 'center'
	}, {
		field : 'updateTime',
		title : '发表时间',
		width : 130,
		align : 'center'
	} ] ];
	$('#contractTplTable').datagrid({
		url : path + "/contract/getlist.html?isTpl=0&status=1",
		columns : columns,
		rownumbers : true,
		singleSelect : true,
		height : 364,
		pagination : true,
		onDblClickRow : function(rowIndex, row) {
			$('#contractTplNoText').val(row.contractName);
			$('#contractTplNo').val(row.contractNo);
			$("#contractTplDiv").dialog('close');
		}
	});
	$("#contractTplDiv").show().dialog({
		title : "选择合同模版",
		height : 400,
		width : 900,
		modal : true,
		onClose : function() {
			$("#contractTplDiv").remove();
		}
	});
}
 
function stockDataChange(){
	
	var fundAmt = $("#fundAmt").val();  //募集金额
	var overFundAmt = $("#overFundAmt").val();   //超募金额
	var stockPartAmt = $("#stockPartAmt").val();  //每份金额
	if(!fundAmt){
		$.messager.alert('提示', '请先输入募集金额！');
		return false;
	}
	if(overFundAmt){
		var canOver=$("#can_over").val();
		if(canOver==1){
			if(parseFloat(overFundAmt)<parseFloat(fundAmt)){
				$.messager.alert('提示', '募集上线不能小于募集金额！');
				return false;
			}	
		}else{
			$("#overFundAmt").val(fundAmt);
		}
	}

	var stockNum = 1;
	if(overFundAmt){  //如果允许超募
		if(stockPartAmt){ //如果每份金额有值
			if(overFundAmt % stockPartAmt ==0){
				stockNum = overFundAmt/stockPartAmt;
				if(stockNum>200){
					$.messager.alert('提示', '募集总份数不能超过200份！');
					return false;
				}
				$("#stockNum").val(stockNum);
			}else{
				$.messager.alert('提示', '募集总份数必须为整数！');
				return false;
			}
		}else{
			$("#stockPartAmt").val(overFundAmt);
			$("#stockNum").val(1);
		}
	}else{
		if(stockPartAmt){
			if(fundAmt % stockPartAmt ==0){
				stockNum = fundAmt/stockPartAmt;
				if(stockNum>200){
					$.messager.alert('提示', '募集总份数不能超过200份！');
					return false;
				}
				$("#stockNum").val(stockNum);
			}else{
				$.messager.alert('提示', '募集总份数必须为整数！');
				return false;
			}
		}else{
			$("#stockPartAmt").val(fundAmt);
			$("#stockNum").val(1);
		}
	}
	return true;
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