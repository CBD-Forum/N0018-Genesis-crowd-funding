var sData = {};
$(function() {
	$('#add').height(bodyHeight - 150);
	$('#add').width(bodyWidth - 160);
	showChargeFeeScale();
	// 获取字典数据
	var columns = [ [ {
		field : 'name',
		title : '姓名',
		width : 200,
		align : 'center'
	}, {
		field : 'position',
		title : '职位',
		width : 200,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 220,
		align : 'center',
		formatter : operateData
	} ] ];

	var addBtn = {
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			showFounderAdd();
		}
	};
	var operateBtns = [];
	operateBtns.push(addBtn);
	if (operateBtns.length == 0) {
		operateBtns = null;
	}

	$('#founderTable').datagrid({
		//url : path + "/crowdfundingFounder/selectFounderPageList.html?loanNo=" + loanNo,
		columns : columns,
		pagination : true,
		singleSelect : true,
		height : 350,
		rownumbers : true,
		toolbar : operateBtns
	});

	// 添加创始人
	$('#saveFounderBtn').click(function() {
		$('#founderForm').submit();
	});

	// 添加创始人工作经历
	$('#saveFounderWorkBtn').click(function() {
		$('#founderWorksForm').submit();
	});

	// 添加创始人创业经历
	$('#saveFounderBusinessBtn').click(function() {
		$('#founderBusinessForm').submit();
	});
	// 添加创始人教育经历
	$('#saveFounderEducationsBtn').click(function() {
		$('#founderEducationsForm').submit();
	});

	$('#closeFounderBtn').click(function() {
		$("#founderAdd").show().dialog('close');
	});
	
	
	$('#saveOperateBtn').click(function() {
		$('#operateForm').submit();
	});

	$('#closeOperateBtn').click(function() {
		$("#operateAdd").show().dialog('close');
	});


	$("#founderForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 32
			},
			position : {
				required : true,
				maxlength : 32
			}
		},
		messages : {
			displayName : {
				required : "请填写姓名"
			},
			code : {
				required : "请填写职位"
			}
		},
		submitHandler : function(form) {
			var url = path + '/crowdfundingFounder/saveFounder.html';

			$('#founderForm').form('submit', {
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$("#founderAdd").show().dialog('close');
//						queryToGrid("founderTable", "list_search");
						$('#founderTable').datagrid('options').url = path + "/crowdfundingFounder/selectFounderPageList.html";
						$('#founderTable').datagrid( "reload",{'loanNo' :loanNo});
					} else {
						$.messager.alert('提示', obj.msg);
					}
				}
			});
		}
	});

	// ===============创始人工作经历===================

	// 获取字典数据
	var worksColumns = [ [ {
		field : 'company',
		title : '单位',
		width : 200,
		align : 'center'
	}, {
		field : 'startTime',
		title : '开始时间',
		width : 120,
		align : 'center'
	}, {
		field : 'endTime',
		title : '结束时间',
		width : 120,
		align : 'center'
	}, {
		field : 'position',
		title : '职     位',
		width : 80,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 100,
		align : 'center',
		formatter : operateWorksData
	} ] ];
	var operateBtns = [];
	// operateBtns.push(addBtn);
	if (operateBtns.length == 0) {
		operateBtns = null;
	}
	$('#founderWorksTable').datagrid({
		columns : worksColumns,
		pagination : false,
		singleSelect : true,
		height : 250,
		rownumbers : true,
		toolbar : operateBtns
	});

	$("#founderWorksForm").validate({
		rules : {
			company : {
				required : true,
				maxlength : 32
			},
			startTime : {
				required : true,
				maxlength : 32
			},
			endTime : {
				required : true,
				maxlength : 32
			},
			position : {
				required : true,
				maxlength : 32
			}
		},
		messages : {
			company : {
				required : "请填写公司"
			},
			position : {
				required : "请填写职位"
			}
		},
		submitHandler : function(form) {
			var url = path
					+ '/crowdfundingFounder/saveFounderWorks.html';
			if ($('#founderWorks_id').val() != '') {
				url = path+ '/crowdfundingFounder/updateFounderWorks.html';
			}
			$('#founderWorksForm').form('submit', {
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$('#founderWorksTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderWorksList.html";
						$('#founderWorksTable').datagrid( "reload",{'founderId' : $("#founderWorks_founderId").val()});
					} else {
						$.messager.alert('提示',obj.msg);
					}
				}
			});
		}
	});

	// ==============================创业经历========================================

	// 获取字典数据
	var businessColumns = [ [ {
		field : 'company',
		title : '公司名称',
		width : 200,
		align : 'center'
	}, {
		field : 'startTime',
		title : '开始时间',
		width : 120,
		align : 'center'
	}, {
		field : 'endTime',
		title : '结束时间',
		width : 120,
		align : 'center'
	}, {
		field : 'position',
		title : '职     位',
		width : 80,
		align : 'center'
	}, {
		field : 'description',
		title : '描述',
		width : 80,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 100,
		align : 'center',
		formatter : operateBusinessData
	} ] ];
	var operateBtns = [];
	if (operateBtns.length == 0) {
		operateBtns = null;
	}
	$('#founderBusinessTable').datagrid({
		columns : businessColumns,
		pagination : false,
		singleSelect : true,
		height : 250,
		rownumbers : true,
		toolbar : operateBtns
	});

	$("#founderBusinessForm").validate({
		rules : {
			company : {
				required : true,
				maxlength : 32
			},
			startTime : {
				required : true,
				maxlength : 32
			},
			endTime : {
				required : true,
				maxlength : 32
			},
			position : {
				required : true,
				maxlength : 32
			}
		},
		messages : {
			company : {
				required : "请填写公司"
			},
			position : {
				required : "请填写职位"
			}
		},
		submitHandler : function(form) {
			var url = path + '/crowdfundingFounder/saveFounderBusiness.html';
			if ($('#founderWorks_id').val() != '') {
				url = path + '/crowdfundingFounder/updateFounderBusiness.html';
			}
			$('#founderBusinessForm').form('submit',{
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$('#founderBusinessTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderBusinessList.html";
						$('#founderBusinessTable').datagrid("reload",{'founderId' : $("#founderBusiness_founderId").val()});
					} else {
						$.messager.alert('提示',obj.msg);
					}
				}
			});
		}
	});

	// ==============================教育经历========================================

	// 获取字典数据
	var educationsColumns = [ [ {
		field : 'school',
		title : '学校',
		width : 200,
		align : 'center'
	}, {
		field : 'startTime',
		title : '开始时间',
		width : 120,
		align : 'center'
	}, {
		field : 'endTime',
		title : '结束时间',
		width : 120,
		align : 'center'
	}, {
		field : 'graduated',
		title : '专业',
		width : 80,
		align : 'center'
	}, {
		field : 'degree',
		title : '学位',
		width : 80,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 100,
		align : 'center',
		formatter : operateEducationsData
	} ] ];
	var operateBtns = [];
	// operateBtns.push(addBtn);
	if (operateBtns.length == 0) {
		operateBtns = null;
	}
	$('#founderEducationsTable').datagrid({
		columns : educationsColumns,
		pagination : false,
		singleSelect : true,
		height : 250,
		rownumbers : true,
		toolbar : operateBtns
	});

	$("#founderEducationsForm").validate({
		rules : {
			company : {
				required : true,
				maxlength : 32
			},
			startTime : {
				required : true,
				maxlength : 32
			},
			endTime : {
				required : true,
				maxlength : 32
			},
			position : {
				required : true,
				maxlength : 32
			}
		},
		messages : {
			company : {
				required : "请填写公司"
			},
			position : {
				required : "请填写职位"
			}
		},
		submitHandler : function(form) {
			var url = path
					+ '/crowdfundingFounder/saveFounderEducations.html';
			if ($('#founderEducations_id').val() != '') {
				url = path + '/crowdfundingFounder/updateFounderEducations.html';
			}
			$('#founderEducationsForm').form('submit',{
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$('#founderEducationsTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderEducationsList.html";
						$('#founderEducationsTable').datagrid("reload", {'founderId' : $("#founderEducations_founderId").val() });
					} else {
						$.messager.alert('提示',obj.msg);
					}
				}
			});
		}
	});

	getProvice("pro_provice", "pro_city", function() {
		if (sData.province) {
			$("#pro_provice").val(sData.province);
		}
	});

	// 项目所属省份改变，城市改变
	$("#pro_provice").change(function() {
		getCitys($(this).val(), "pro_city");
	});

	// 城市改变，查询县
	$("#pro_city").change(function() {
		getCitys($(this).val(), "pro_county");
	});

	showCrowdfundType();
	showBisType();
	showLoanStage();
	// 选择借款用户
	$('#getLoanUserBtn').click(function() {
		getLoanUserStock('loanUserText', 'loanUser');
	});

	// //选择项目负责人
	$('#getLoanManagerBtn').click(function() {
		getLoanManager();
	});
	// 选择合同模版
	$('#getContractTplBtn').click(function() {
		getContractTpl();
	});

	// 选择是否超募
	$("#can_over").change(function() {
		var overFundAmt =""; //募集上线
		if ($(this).val() && $(this).val() == '1') {
			$("#overFundAmtDiv").show();
			var fundAmt = $("#fundAmt").val();
			if(fundAmt){
				$("#overFundAmt").val(fundAmt);
//				$("#stockPartAmt").val(fundAmt);
//				$("#stockNum").val(1);
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

	// 保存基本信息
	$('#basicBtn').click(function() {
		
		
		//验证合同模板
		var contractTplNo = $("#contractTplNo").val();
		if(!contractTplNo){
			$.messager.alert('提示',"请选择合同模板");
			return false;
		}
		
		var competence = UE.getEditor("competence_ue").getContent()
		if(!competence){
			$.messager.alert('提示',"请填写核心竞争力！");
			return false;
		}
		var profitModel = UE.getEditor("profitModel_ue").getContent()
		if(!profitModel){
			$.messager.alert('提示',"请填写盈利模式！");
			return false;
		}
		stockDataChange();
		
		$('#baseForm').submit();
	});

	$("#baseForm").validate({
		errorPlacement : function(error, element) {
			if (element.is(":radio")) {
				error.appendTo(element.parent());
			} else if (element.is(":checkbox")) {
				error.appendTo(element.parent());
			} else {
				error.appendTo(element.parent());
			}
		},
		rules : {
			loanName : "required",
			loanUserName : "required",
			fundAmt : {
				required : true,
				number : true,
				min:1
			},
			fundDays : {
				required : true,
				numberCustom : true,
				range:[10,90]
			},
			chargeFee : {
				required : true,
				number : true,
				range:[0,1]
			},
			/*prepayAmt : {
				required : true,
				number : true
			},*/
			overFundAmt : {
				required : true,
				number : true
			},
			stockNum : {
				required : true,
				number : true,
				range:[1,200]
			},
			dailyEarningsForecast : {
				required : true,
				number : true
			},
			stockPartAmt : {
				required : true,
				number : true
			},
			province : "required",
			// city : "required",
			// county : "required",
/*			loanDes : "required",*/
			contractNo : "required",
			contractTplNo:"required",
			loanIntroduction :{
				required : true,
				minlength:0,
				maxlength:20
			},  
			loanStage : "required",
			superIndustry : "required",
			website : "required" 			
		},
		messages : {
			// loanType:"请选择项目类型",
			loanName : "请填写项目名称",
			loanUserText : "请填写发起人",
			fundAmt : {
				required : "请填写筹资金额"
			},
			fundDays : {
				required : "请填写筹资天数"
			},
			chargeFee : {
				required : "请填写服务费比例"
			},
			dailyEarningsForecast :{
				required : "请填写预计每日收益",
				number : "清填写正确的预计每日收益"
			},
			province : "请填写项目所在省",
			loanIntroduction:{
				required : "请填写一句话介绍"
			}
		},
		submitHandler : function(form) {
			url = path + '/crowdfunding/saveCrowdFund.html';
			if ($("#chargeFee").val() < 0 || $("#chargeFee").val() > 1) {
				$.messager.alert('提示', '服务费比例必须大于0小于1');
				return;
			}
			if ($("#dailyEarningsForecast").val() < 0 || $("#dailyEarningsForecast").val() > 1) {
				$.messager.alert('提示', '预计每日收益必须大于0小于1');
				return;
			}
			var competence = UE.getEditor("competence_ue").getContent();
			var profitModel = UE.getEditor("profitModel_ue").getContent();
			$("#competence").val(competence);
			$("#profitModel").val(profitModel);
			$('#baseForm').form('submit', {
				url : url, 
				success : function(data) {
					data = $.parseJSON(data);
					if (data["success"]) {
						$.messager.alert('提示', '保存成功');
						loanNo = data["msg"];
						$('#basicBtn').css({
							"background" : "#ccc",
							"border" : "#ccc"
						})
						$('#basicBtn').unbind("click");
					} else {

					}
				}
			});
		}
	});

	$("#detailSaveBtn").click(function() {
		if (!loanNo) {
			$.messager.alert('提示', '请先保存基本信息');
			return false;
		}

		$.ajax({
			url : path + '/crowdfunding/updateCrowdFundDetail.html',
			type : "post",
			dataType : "json",
			async : false,
			data : {
				"loanNo" : loanNo,
				"loanDetail" : UE.getEditor("loan_detail").getContent(),
				"financeBudget" : UE.getEditor("finance_budget").getContent(),
				"founder" : $("#founder").val(),
				"founderPhoto" : $('#detailForm input[name="founderphoto"]').val(),
				"lastQuarterIncome" : $("#lastQuarterIncome").val(),
				"lastQuarterProfit" : $("#lastQuarterProfit").val(),
				"lastYearIncome" : $("#lastYearIncome").val(),
				"lastYearProfit" : $("#lastYearProfit").val(),
				"companyFundDate" : $("#companyFundDate").val()
			},
			success : function(data) {
				if (data["success"]) {
					$.messager.alert('提示', '保存成功');
					$('#detailSaveBtn').css({
						"background" : "#ccc",
						"border" : "#ccc"
					})
					$('#detailSaveBtn').unbind("click");
				}
			},
			error : function() {

			}
		});
	});
	
	
	$("#businessProposalChangeUpload").click(function(){
		if (!loanNo) {
			$.messager.alert('提示', '请先保存基本信息');
			return false;
		}
		$("#businessProposalFileToUpload").click();
	});
	var gg=0;
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
            		$("#businessProposalUploadInFo").append("<p><span style='color:red;'>上传成功！</span>" +
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
            		$("#otherAccessoriesUploadInFo").append("<p><span style='color:red;'>上传成功！</span>" +
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
            		$("#exitSchemeUploadInFo").append("<p><span style='color:red;'>上传成功！</span>" +
            				"<a href="+fileUrl+data.msg+" target='_black'>"+data.fileName+"</a>" +
            						"<input type='hidden' name='exitScheme' value="+data.msg+" />" +
            						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(data.id)+"' onclick='removeFile(this);'>删除</a>" +
            								"</p>");
				}else{
					$.messager.alert("提示","上传失败！");
//					$("#otherAccessoriesUploadInFo").append("<span style='color:red;'>上传失败！</span>");
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
	
	
	
	$("#companySaveBtn").click(function() {
		if (!loanNo) {
			$.messager.alert('提示', '请先保存基本信息');
			return false;
		}
		//营业执照
		var businessLicense="";
		$("input[name='businessLicense']").each(function(i,v){
			businessLicense += $(v).val()+",";
		});
		businessLicense = businessLicense.substring(0, businessLicense.length-1);
		//身份证件
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
		
//		var businessProposal="";
//		$("input[name='businessProposal']").each(function(i,v){
//			businessProposal += $(v).val()+",";
//		});
//		businessProposal = businessProposal.substring(0, businessProposal.length-1);
//		var otherAccessories="";
//		$("input[name='otherAccessories']").each(function(i,v){
//			otherAccessories += $(v).val()+",";
//		});
//		otherAccessories = otherAccessories.substring(0, otherAccessories.length-1);
		$.ajax({
			url : path + '/crowdfunding/updateCrowdFundDetail.html',
			type : "post",
			dataType : "json",
			async : false,
			data : {
				"loanNo" : loanNo,
				"loanTeam" : UE.getEditor("loan_team").getContent(),
				"businessLicense" : businessLicense,
				/*"businessProposal":businessProposal,
				"otherAccessories":otherAccessories,*/
				"legalCard" :legalCard,
				"licenseIdentityInformation" :licenseIdentityInformation,
				"transferRatio" : $('#transferRatio').val(),
				/*"exitScheme" : $("textarea[name='exitScheme']").val(),
				"capitalUsed" : $("textarea[name='capitalUsed']").val(),
				"industryAnalysis" :$("textarea[name='industryAnalysis']").val(),
				"riskMeasure" :$("textarea[name='riskMeasure']").val(),
				"financePlan" :$("textarea[name='financePlan']").val()*/
				"capitalUsed" : UE.getEditor("capitalUsed").getContent(),
				"industryAnalysis" :UE.getEditor("industryAnalysis").getContent(),
				"riskMeasure" :UE.getEditor("riskMeasure").getContent(),
				"financePlan" :UE.getEditor("financePlan").getContent(),
				"lastValuation":$('#lastValuation').val()
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

	
	
	

	$("#videoSaveBtn").click(function() {
		if(!loanNo){
			$.messager.alert("提示","请先保存项目基本信息");
			return false;
		}
		if(!$("#loan_video").val()){
			$.messager.alert("提示","请填写视频地址！");
			return false;
		}
		$.ajax({
			url : path + '/crowdfunding/updateCrowdFundDetail.html',
			type : "post",
			dataType : "json",
			async : false,
			data : {
				"loanNo" : loanNo,
				"loanVideo" : $("#loan_video").val(),
				"videoDes" : $("#video_des").val(),
				"mobileVideo" : $("#mobile_video").val()
			},
			success : function(data) {
				if (data["success"]) {
					$.messager.alert('提示', '保存成功');
					$('#videoSaveBtn').css({
						"background" : "#ccc",
						"border" : "#ccc"
					})
					$('#videoSaveBtn').unbind("click");
				}
			},
			error : function() {

			}
		});
	});
	
	
	
	// 获取字典数据
	var operateColumns = [ [ 
	  /*  {field : 'registerNumByDay',title : '日注册用户数', width : 100,align : 'center'}, 
	    {field : 'userNumByDay',title : '日活跃用户数', width : 100,align : 'center'}, 
	    {field : 'retentionByNextDay',title : '次日留存率', width : 100,align : 'center'}, 
	    {field : 'registerNumByMonth',title : '月注册用户数', width : 100,align : 'center'}, 
	    {field : 'userNumByMonth',title : '月活跃用户数', width : 100,align : 'center'}, 
	    {field : 'retentionByNextMonth',title : '月日留存率', width : 100,align : 'center'}, 
	    {field : 'payUserNum',title : '付费用户数', width : 100,align : 'center'}, */
	    {field : 'expectedReturn',title : '内容', width : 600,align : 'center'},
	    {field : 'createTime', title : '创建时间', width : 100,align : 'center'}, 
	    {field : 'operat',title : '操作',width : 220,align : 'center',formatter : operateData2 } 
	    ] ];

	var addOperateBtn = {
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			showOperateAdd();
		}
	};
	var operateBtns2 = [];
	operateBtns2.push(addOperateBtn);
	if (operateBtns2.length == 0) {
		operateBtns2 = null;
	}

	$('#operateTable').datagrid({
		columns : operateColumns,
		pagination : true,
		singleSelect : true,
		height : 350,
		rownumbers : true,
		toolbar : operateBtns2
	});
	
	
	
	$("#operateForm").validate({
		rules : {
		/*	registerNumByDay : {
				required : true 
			},
			userNumByDay : {
				required : true 
			},
			retentionByNextDay : {
				required : true 
			},
			registerNumByMonth : {
				required : true 
			},
			userNumByMonth : {
				required : true 
			},
			retentionByNextMonth : {
				required : true 
			},
			payUserNum : {
				required : true 
			}*/
			expectedReturn:{
				required : true 
			}
		},
		messages : {
		 
		},
		submitHandler : function(form) {
			var url = path + '/crowdfundingOperateData/saveOperateData.html';
			$('#operateForm').form('submit', {
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$("#operateAdd").show().dialog('close');
						$('#operateTable').datagrid('options').url = path + "/crowdfundingOperateData/selectPageList.html";
						$('#operateTable').datagrid( "reload",{'loanNo' :loanNo});
					} else {
						$.messager.alert('提示', obj.msg);
					}
				}
			});
		}
	});	
	
	
	//失去焦点事件
	$("#fundAmt").blur(function(){  //募集金额
		stockDataChange();
	});
	$("#overFundAmt").blur(function(){  //超募上线
		var fundAmt = $("#fundAmt").val();
		
		var overFundAmt = $("#overFundAmt").val();
		if(overFundAmt<fundAmt){
			$.messager.alert('提示',"超募上线不能小于募集金额");
			return false;
		}
		
		stockDataChange();
	});	
	$("#stockPartAmt").blur(function(){
		stockDataChange();
	});	
	
	
	 

	createWebUploader('founderphotoBtn', 'founderphotoDiv', 'founderphoto','detailForm');
	createWebUploader('logoBtn', 'logoDiv', 'loanLogo', 'baseForm');
	createWebUploader('photoBtn', 'photoDiv', 'loanPhoto', 'baseForm');
	createWebUploader('logoBtn2', 'logoDiv2', 'logo2', 'loanLogoForm');
	createWebUploader('weixinBtn', 'weixinDiv', 'weixin', 'baseForm');
	createWebUploader('companyLogoBtn', 'companyLogoDiv', 'logo', 'baseForm');
	
	createWebUploaderMore('businessLicenseBtn','businessLicenseDiv','businessLicense','companyForm');
	createWebUploaderMore('legalCardBtn','legalCardDiv','legalCard','companyForm');
	createWebUploaderMore('licenseIdentityInformationBtn','licenseIdentityInformationDiv','licenseIdentityInformation','companyForm');
	
});

// 展示行业类别
function showBisType() {
	var cArr = [], cStr = '', cl = 0;
	$.ajax({
		url : path + "/dictionary/getDic.html",
		type : "post",
		dataType : "json",
		data : {
			"type" : "concernIndustry"
		},
		success : function(data) {
			getLoanTypeFn("super_industry", data["rows"]);
			if (sData.superIndustry) {
				$("#super_industry").val(sData.superIndustry);
			}
		},
		error : function(request) {
			console.log("获取项目类型异常");
		}
	});
}

// 项目阶段
function showLoanStage() {
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
		},
		error : function(request) {
			console.log("获取项目阶段异常");
		}
	});
}
// 展示项目类型
function showCrowdfundType() {
	var cArr = [], cStr = '', cl = 0;
	$.ajax({
		url : path + "/dictionary/getDic.html",
		type : "post",
		dataType : "json",
		data : {
			"type" : "crowdFund_type_stock"
		},
		success : function(data) {
			getLoanTypeFn("loanType", data["rows"]);
		},
		error : function(request) {
			console.log("获取项目类型异常");
		}
	});
}

// 选择合同模版
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

// 显示添加创始人
function showFounderAdd() {
	if (!loanNo) {
		$.messager.alert('提示', '请先保存基本信息');
		return false;
	}
	$("#founder_loanNo").val(loanNo);
	$("#founderAdd").show().dialog({
		title : "添加创始人",
		height : 300,
		width : 500,
		modal : true,
		onClose : function() {
			$("#founderForm").form('clear');
		}
	});
}

/**
 * 删除创始人信息
 */
function deleteFounder(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingFounder/deleteFounder.html',
				data : {
					'id' : id
				},
				success : function() {
//					queryToGrid("founderTable", "list_search");
					$('#founderTable').datagrid('options').url = path + "/crowdfundingFounder/selectFounderPageList.html";
					$('#founderTable').datagrid( "reload",{'loanNo' :loanNo});
				}
			});
		}
	});
}

/**
 * 删除运营数据信息
 */
function deleteoperateData(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingOperateData/deleteOperateData.html',
				data : {
					'id' : id
				},
				success : function() {
					$('#operateTable').datagrid('options').url = path + "/crowdfundingOperateData/selectPageList.html";
					$('#operateTable').datagrid( "reload",{'loanNo' :loanNo});
				}
			});
		}
	});
}


// 显示创始人工作经历
function addFounderWorks(id) {

	$("#founderWorks_founderId").val(id);
	$("#founderWorks_LoanNo").val(loanNo);
	$("#founderWorksAdd").show().dialog({
		title : "创始人工作经历",
		height : 600,
		width : 800,
		modal : true,
		onClose : function() {
			$("#founderWorksForm").form('clear');
		}
	});
	$('#founderWorksTable').datagrid('options').url = path
			+ "/crowdfundingFounder/getFounderWorksList.html";
	$('#founderWorksTable').datagrid("reload", {
		'founderId' : id
	});

}

/**
 * 删除创始人工作经历信息
 */
function deleteFounderWorks(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingFounder/deleteFounderWorks.html',
				data : {
					'id' : id
				},
				success : function() {
					$('#founderWorksTable').datagrid('options').url = path
							+ "/crowdfundingFounder/getFounderWorksList.html";
					$('#founderWorksTable').datagrid("reload", {
						'founderId' : $("#founderWorks_founderId").val()
					});

				}
			});
		}
	});
}

// 编辑创始人工作经历
function editFounderWorks(id) {
	$('#founderWorksForm').form('clear');
	$('#founderWorksForm').form('load',
			path + '/crowdfundingFounder/selectFounderWorksById.html?id=' + id);
}

// 显示创始人创业经历
function addFounderBusiness(id) {

	$("#founderBusiness_founderId").val(id);
	$("#founderBusiness_LoanNo").val(loanNo);
	$("#founderBusinessAdd").show().dialog({
		title : "创始人创业经历",
		height : 600,
		width : 850,
		modal : true,
		onClose : function() {
			$("#founderBusinessForm").form('clear');
		}
	});
	$('#founderBusinessTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderBusinessList.html";
	$('#founderBusinessTable').datagrid("reload", { 'founderId' : id });
}

/**
 * 删除创始人创业经历信息
 */
function deleteFounderBusiness(id) {
	// 删除
	$.messager.confirm('提示','您确定要删除已选的信息吗？',
	function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path
						+ '/crowdfundingFounder/deleteFounderBusiness.html',
				data : {
					'id' : id
				},
				success : function() {
				   $('#founderBusinessTable')
							.datagrid('options').url = path
							+ "/crowdfundingFounder/getFounderBusinessList.html";
					$('#founderBusinessTable')
						.datagrid(
								"reload",
								{
									'founderId' : $(
											"#founderBusiness_founderId").val()
								});

				}
			});
		}
	});
}

// 编辑创始人创业经历
function editFounderBusiness(id) {
	$('#founderBusinessForm').form('clear');
	$('#founderBusinessForm').form(
			'load',
			path + '/crowdfundingFounder/selectFounderBusinessById.html?id='
					+ id);
}

// =========================================教育情况=================================
// 显示创始人教育情况
function addFounderEducations(id) {

	$("#founderEducations_founderId").val(id);
	$("#founderEducations_LoanNo").val(loanNo);
	$("#founderEducationsAdd").show().dialog({
		title : "创始人教育背景",
		height : 600,
		width : 800,
		modal : true,
		onClose : function() {
			$("#founderEducationsForm").form('clear');
		}
	});
	$('#founderEducationsTable').datagrid('options').url = path
			+ "/crowdfundingFounder/getFounderEducationsList.html";
	$('#founderEducationsTable').datagrid("reload", {
		'founderId' : id
	});

}

/**
 * 删除创始人创业教育情况
 */
function deleteFounderEducations(id) {
	// 删除
	$.messager.confirm( '提示','您确定要删除已选的信息吗？',function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingFounder/deleteFounderEducations.html',
				data : {
					'id' : id
				},
				success : function() {
					$('#founderEducationsTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderEducationsList.html";
					$('#founderEducationsTable').datagrid("reload",{'founderId' : $("#founderEducations_founderId").val()});
				}
			});
		}
	});
}

// 编辑创始人教育情况
function editFounderEducations(id) {
	$('#founderEducationsForm').form('clear');
	$('#founderEducationsForm').form( 'load', path + '/crowdfundingFounder/selectFounderEducationsById.html?id='+ id);
}



//显示添加运营数据信息
function showOperateAdd() {
	if (!loanNo) {
		$.messager.alert('提示', '请先保存基本信息');
		return false;
	}
	$("#operate_loanNo").val(loanNo);
	$("#operateAdd").show().dialog({
		title : "添加预计收益",
		height : 250,
		width : 650,
		modal : true,
		onClose : function() {
			$("#operateForm").form('clear');
		}
	});
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
		//alert(canOver+"___________"+overFundAmt+"____________"+fundAmt+"____"+(parseFloat(overFundAmt)<parseFloat(fundAmt)));
		if(canOver==1){
			if(parseFloat(overFundAmt)<parseFloat(fundAmt)){
				$.messager.alert('提示', '募集上线不能小于募集金额！');
				return false;
			}else{
				if(!overFundAmt){
					$("#overFundAmt").val(fundAmt);
				}
			}	
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
				$("#overFundAmt").val(fundAmt);
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
				$("#overFundAmt").val(fundAmt);
				$.messager.alert('提示', '募集总份数必须为整数！');
				return false;
			}
		}else{
			$("#stockPartAmt").val(fundAmt);
			$("#stockNum").val(1);
		}
	}
}


/**
 * 选择用户
 * @param nameObj
 * @param idObj
 * @param fn ： 选择完以后，添加用户相应地址
 */
function getLoanUserStock(nameObj,idObj, fn){
	var squery = '<form id="list_search_user"><div class="seach_div"><div style="float:left;"><label>用户名:</label><input id="userId" name="userId" type="text"/></div>'+
		'<a href="#" id="searchBtn" onclick="searchUser()" style="display:block;width:50px;text-align:center;margin-top:4px;float:left;height:26px;line-height:26px;">查询</a></div></form>';
	
	$('<div id="loanUserDiv">'+squery+'<table id="loanUserTable"></table></div>').hide().appendTo('body');
	
	//获取字典数据
	var columns = [[
					{field:'userId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:130,align:'center',sortable:true},
/*					{field:'thirdAccount',title:'汇付账号',width:150,align:'center',sortable:true},
*/					{field:'mobile',title:'手机号',align:'center'},
					{field:'email',title:'邮箱',width:150,align:'center'},
					{field:'createTime',title:'注册时间',align:'center',sortable:true},
					{field:'statusName',title:'状态',width:50,align:'center',sortable:true}
				]];
	$('#loanUserTable').datagrid({
		url: path + "/user/getlist.html?isAuth=1&isSetSignature=1",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:364,
		pagination: true,
		onDblClickRow:function(rowIndex, row){
			if(row.realName){
				$('#'+nameObj).val(row.realName);
			}else{
				$('#'+nameObj).val(row.userId);
			}
			$('#'+idObj).val(row.userId);
			
			if(fn && $.isFunction(fn)){
				fn(row.userId);
			}
			$("#loanUserDiv").dialog('close');
		}
	});
	$("#loanUserDiv").show().dialog({
		title: "选择借款用户",
		height: 400,
		width:900,
		modal: true,
		onClose: function () {
			$("#loanUserDiv").remove();
        }
	});
}


function searchUser(){
	queryToGrid("loanUserTable", "list_search_user");
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