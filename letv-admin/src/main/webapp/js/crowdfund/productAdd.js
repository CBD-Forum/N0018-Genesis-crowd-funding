var sData={};
var loanNo;
$(function(){
	$('#add').height(bodyHeight-150);
	$('#add').width(bodyWidth-160);
	showChargeFeeScale();
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
	
	
	getProvice("user_provice", "user_city",function(){
		if(sData.province){
			$("#user_provice").val(sData.userProvince);
		}
	});
	
	//项目所属省份改变，城市改变
	$("#user_provice").change(function(){
		getCitys($(this).val(), "user_city");
	});
	
	
	showCrowdfundType();
	showBisType();
	//选择借款用户
	$('#getLoanUserBtn').click(function(){
		getLoanUser('loanUserText','loanUser');
	});
	
     //	//选择项目负责人
	$('#getLoanManagerBtn').click(function(){
		getLoanManager();
	});
	//选择合同模版
	$('#getContractTplBtn').click(function(){
		getContractTpl();
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
	//保存基本信息
	$('#basicBtn').click(function() {
		
		var industry = $("#super_industry").val();
		if(!industry){
			$.messager.alert('提示', '请选择行业类别！');
			return false;
		}
		
		var logoDiv = $("#logoValidateID").html();
		var photoDiv = $("#photoValidateID").html();
		if(!logoDiv){
			$.messager.alert('提示', '请上传项目封面！');
			return false;
		}
		if(!photoDiv){
			$.messager.alert('提示', '请上传项目头图！');
			return false;
		}		
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
			fundDays :  {
				required:true,
				numberCustom : true,
				range:[10,90]
			},
/*			prizeNum :  {
				required:true,
				number:true
			},*/
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
				number:true,
				range:[0,1]
			},
			loanIntroduction :{
				required : true,
				minlength:0,
				maxlength:20
			},
			province : "required",
//			city : "required",
//			county : "required",
			/*,
			loanDes : "required"*/
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
/*			prizeNum : {
				required:"请填写抽奖名额"
			},*/
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
			/*,
			loanDes : "请填写项目简介"*/
			loanIntroduction:{
				required : "请填写一句话介绍"
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
    	if (!loanNo) {
    		$.messager.alert('提示', '请先保存基本信息');
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
	
	
//	发起人信息保存 sponsorSaveBtn sponsorForm
	
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
	
	createWebUploader('logoBtn','logoDiv','loanLogo','baseForm');
	createWebUploader('photoBtn','photoDiv','loanPhoto','baseForm');
	
	createWebUploaderMore('businessLicenseBtn','businessLicenseDiv','businessLicense','sponsorForm');
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