var sData={};
var loanNo,id;
$(function(){
	$('#add').height(bodyHeight-150);
	$('#add').width(bodyWidth-160);
	
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
	
	showCrowdfundType();
	showBisType();
	showChargeFeeScale();
	//选择借款用户
	$('#getLoanUserBtn').click(function(){
		getLoanUser('loanUserText','loanUser');
	});
	
     //选择项目负责人
	$('#getLoanManagerBtn').click(function(){
		getLoanManager();
	});
	//选择合同模版
	$('#getContractTplBtn').click(function(){
		getContractTpl();
	});	
	//选择是否超募
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
			//loanType:"required",
			loanName : "required",
			loanUserName : "required",
			fundAmt :  {
				required:true,
				number:true,
				min:1
			},
			overFundAmt:  {
				required:true,
				number:true
			},
			fundDays :  {
				required:true,
				numberCustom : true,
				range:[10,90]
			},
			chargeFee:  {
				required:true,
				number:true,
				range:[0,1]
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
		//	loanType:"请选择项目类型",
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
			province : "请填写项目所在省",
//			city : "请填写项目所在市",
//			county : "请填写项目所在县",
/*			loanDes : "请填写项目简介",*/
			loanIntroduction  : {
				required:"请填写一句话描述项目" 
			}
		},
        submitHandler:function(form){
        	url=path + '/crowdfunding/saveCrowdFund.html';
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
        	
        	$('#baseForm').form('submit', {
    			url : url,
    			success : function(data) {
    				data = $.parseJSON(data);
    				if(data["success"]){
    					$.messager.alert('提示', '保存成功');
    					loanNo = data["msg"];
    					id = data["ID"];
    					$('#basicBtn').css({
    						"background":"#ccc",
    						"border":"#ccc"
    					})
    					$('#basicBtn').unbind("click");
    				}else{
    					$.messager.alert('提示', data["msg"]);
    				}
    			}
    		});
        }
    }); 
	
	$("#detailSaveBtn").click(function(){
		if(!loanNo){
			$.messager.alert("提示","请先保存项目基本信息");
			return false;
		}
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
					$.messager.alert('提示', '保存成功');
					$('#detailSaveBtn').css({
						"background":"#ccc",
						"border":"#ccc"
					})
					$('#detailSaveBtn').unbind("click");
				}
			},
			error: function(){
				
			}
		});
	});
	$("#videoSaveBtn").click(function(){
		if(!loanNo){
			$.messager.alert("提示","请先保存项目基本信息");
			return false;
		}
		if(!$("#loan_video").val()){
			$.messager.alert("提示","请填写视频地址！");
			return false;
		}
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"loanVideo": $("#loan_video").val(),
					"videoDes": $("#video_des").val(),
					"mobileVideo" : $("#mobile_video").val()
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
	
	
	$("#welfareInfoBtn").click(function(){
		if(!loanNo){
			$.messager.alert("提示","请先保存项目基本信息");
			return false;
		}
		
		var orgCode="";
		$("input[name='orgCode']").each(function(i,v){
			orgCode += $(v).val()+",";
		});
		orgCode = orgCode.substring(0, orgCode.length-1);
		
		
		var orgCertificate="";
		$("input[name='orgCertificate']").each(function(i,v){
			orgCertificate += $(v).val()+",";
		});
		orgCertificate = orgCertificate.substring(0, orgCertificate.length-1);
		
		var promoterIdentitySign="";
		$("input[name='promoterIdentitySign']").each(function(i,v){
			promoterIdentitySign += $(v).val()+",";
		});
		promoterIdentitySign = promoterIdentitySign.substring(0, promoterIdentitySign.length-1);
		
		
		var promoterIdentityPhoto="";
		$("input[name='promoterIdentityPhoto']").each(function(i,v){
			promoterIdentityPhoto += $(v).val()+",";
		});
		promoterIdentityPhoto = promoterIdentityPhoto.substring(0, promoterIdentityPhoto.length-1);
		
		var orgLoanReceiveProve="";
		$("input[name='orgLoanReceiveProve']").each(function(i,v){
			orgLoanReceiveProve += $(v).val()+",";
		});
		orgLoanReceiveProve = orgLoanReceiveProve.substring(0, orgLoanReceiveProve.length-1);
		
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFunding.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"id": id,
					"orgCode": orgCode,
					"orgCertificate": orgCertificate,
					"promoterIdentitySign": promoterIdentitySign,
					"promoterIdentityPhoto": promoterIdentityPhoto,
					"orgLoanReceiveProve": orgLoanReceiveProve
				},
			success: function(data){
				if(data["success"]){
					$.messager.alert('提示', '保存成功');
					$('#welfareInfoBtn').css({
						"background":"#ccc",
						"border":"#ccc"
					})
					$('#welfareInfoBtn').unbind("click");
				}else{
					$.messager.alert('提示',data['msg']);
				}
			},
			error: function(){
				
			}
		});
	});
	
	
	$("#orgLoanReceiveProveChangeUpload").click(function(){
		if (!loanNo) {
			$.messager.alert('提示', '请先保存基本信息');
			return false;
		}
		$("#orgLoanReceiveProveFileToUpload").click();
	});
	
	//选择文件之后执行上传  
    $('#orgLoanReceiveProveFileToUpload').on('change', function() {
    	var sendLoanMask=getMask('正在上传，请稍后...');
        $.ajaxFileUpload({  
        	url:path+'/fileUpload/uploadFile.html?jq_random='+Math.random().toFixed(5)+'&type=orgLoanReceiveProve&parentId='+loanNo,  
            secureuri:false,  
            fileElementId:'orgLoanReceiveProveFileToUpload',//file标签的id  
            dataType: 'json',//返回数据的类型  
            success: function (data, status) {
            	if(data["success"]){
            		$.messager.alert('提示', '上传成功');
            		sendLoanMask.dialog('close');
            		$("#orgLoanReceiveProveUploadInFo").append("<p><span style='color:red;'>上传成功！</span>" +
            				"<a href="+fileUrl+data.msg+" target='_black'>"+data.fileName+"</a>" +
	    						"<input type='hidden' name='orgLoanReceiveProve' value="+data.msg+" />" +
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
	
	
	
	
	
	createWebUploader('logoBtn','logoDiv','loanLogo','baseForm');
	createWebUploader('photoBtn','photoDiv','loanPhoto','baseForm');
	
	createWebUploaderMore('orgCodeBtn','orgCodeDiv','orgCode','welfareInfoForm');
	createWebUploaderMore('orgCertificateBtn','orgCertificateDiv','orgCertificate','welfareInfoForm');
	createWebUploaderMore('promoterIdentitySignBtn','promoterIdentitySignDiv','promoterIdentitySign','welfareInfoForm');
	createWebUploaderMore('promoterIdentityPhotoBtn','promoterIdentityPhotoDiv','promoterIdentityPhoto','welfareInfoForm');
	createWebUploaderMore('orgLoanReceiveProveBtn','orgLoanReceiveProveDiv','orgLoanReceiveProve','welfareInfoForm');
	
	
	$("#changeUpload").click(function(){
		$("#fileToUpload").click();
	});
	
	//选择文件之后执行上传  
    $('#fileToUpload').on('change', function() {
    	var sendLoanMask=getMask('正在上传，请稍后...');
        $.ajaxFileUpload({  
            url:path+'/crowdfunding/uploadOrgLoanReceiveProve.html?jq_random='+Math.random().toFixed(5),  
            secureuri:false,  
            fileElementId:'fileToUpload',//file标签的id  
            dataType: 'json',//返回数据的类型  
            success: function (data, status) {
            	if(data["success"]){
            		
            		$("#welfareInfoForm input[name='orgLoanReceiveProve']").val(data.msg);
            		$.messager.alert('提示', '上传成功');
            		$("#uploadInFo").text('上传成功！');
            		$("#uploadInFo").append("<a href="+fileUrl+data.msg+" target='_black'>查看上传文件</a>");
				}else{
					$.messager.alert("提示","上传失败！");
					$("#uploadInFo").text('上传失败！');
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
});


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


//展示项目类型
function showCrowdfundType(){
	var cArr = [], cStr = '', cl = 0;
	$.ajax({
		url: path + "/dictionary/getDic.html",
		type: "post",
		dataType: "json",
		data: {"type": "crowdFund_type_entity"},
		success: function(data){
			getLoanTypeFn("loanType", data["rows"]);
		},
		error: function(request){
			console.log("获取项目类型异常");
		}
	});
}


//选择合同模版
function getContractTpl(){
	$('<div id="contractTplDiv"><table id="contractTplTable"></table></div>').hide().appendTo('body');
	//获取字典数据
	var columns = [[
					{field:'contractNo',title:'模板编号',width:150,align:'center',sortable:true},
					{field:'contractName',title:'模板名称',width:180,align:'center'},
					{field:'contractTypeName',title:'模板类型',width:180,align:'center'},
					{field:'statusName',title:'状态',align:'center'},
					{field:'description',title:'描述',width:250,align:'center'},
					{field:'createTime',title:'录入时间',width:130,align:'center'},
					{field:'updateTime',title:'发表时间',width:130,align:'center'}
				]];
	$('#contractTplTable').datagrid({
		url: path + "/contract/getlist.html?isTpl=0&status=1",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:364,
		pagination: true,
		onDblClickRow:function(rowIndex, row){
			$('#contractTplNoText').val(row.contractName);
			$('#contractTplNo').val(row.contractNo);
			$("#contractTplDiv").dialog('close');
		}
	});
	$("#contractTplDiv").show().dialog({
		title: "选择合同模版",
		height: 400,
		width:900,
		modal: true,
		onClose: function () {
			$("#contractTplDiv").remove();
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
