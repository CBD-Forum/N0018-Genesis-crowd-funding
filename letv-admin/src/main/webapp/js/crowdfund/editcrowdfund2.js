var id="";
var sData={};
$(function(){
	
//	$("#baseForm :text").css('border','1px solid #95b8e7');
//	$("#baseForm select").css('border','1px solid #95b8e7');
//	//获取项目所在省
//	getProvice("pro_provice", "pro_city",function(){
//		if(sData.province){
//			$("#pro_provice").val(sData.province);
//		}
//	});
//	//获取项目类型
//	$.ajax({
//		url: path + "/dictionary/getDic.html",
//		type: "post",
//		dataType: "json",
//		data: {"type": "crowdFundType"},
//		success: function(data){
//			getLoanTypeFn("loan_type", data["rows"]);
//			if(sData.loanType){
//				$("#loan_type").val(sData.loanType);
//			}
//		},
//		error: function(){
//			console.log("获取项目类型异常");
//		}
//	});
//	//获取是否标志
//	$.ajax({
//		url: path + "/dictionary/getDic.html",
//		type: "post",
//		dataType: "json",
//		data: {"type": "is_not_flag"},
//		success: function(data){
//			getLoanTypeFn("is_system", data["rows"]);
//			if(sData.isSystem){
//				$("#is_system").val(sData.isSystem);
//			}
//		},
//		error: function(){
//			console.log("获取是否标志异常");
//		}
//	});
//	
//	
//	
//	//项目所属省份改变，城市改变
//	$("#pro_provice").change(function(){
//		getCitys($(this).val(), "pro_city");
//	});
//	
//	//选择借款用户
//	$('#getLoanUserBtn').click(function(){
//		getLoanUser();
//	});
//	
//	$("#previewBtn").click(function(){
//		previewPhoto();
//	})
//	
//	$("#baseForm").validate({
//		errorPlacement : function(error, element) {
//			if ( element.is(":radio") ){
//				error.appendTo (element.parent());
//			}else if (element.is(":checkbox") ){
//		    	error.appendTo (element.parent());
//		    }else{
//		        error.appendTo(element.parent());
//			}
//    	},
//		rules : {
//			loanName : "required",
//			loanUserText : "required",
//			fundAmt :  {
//				required:true,
//				number:true
//			},
//			fundAmtUpper : {
//				required:true,
//				number:true
//			},
//			fundDays :  {
//				required:true,
//				number:true
//			},
//			loanType : "required",
//			isSystem : "required",
//			province : "required",
//			city : "required",
//			loanDes : "required",
//			loanLogo : "required",
//			loanDetail : "required"
//		},
//		messages : {
//			loanName : "请填写项目名称",
//			loanUserText : "请填写发起人",
//			fundAmt : {
//				required:"请填写筹资金额"
//			},
//			fundAmtUpper:{
//				required:"请填写筹资金额上限"
//			},
//			fundDays : {
//				required:"请填写筹资天数"
//			},
//			loanType : "请填写项目类型",
//			isSystem : "请填写是否为平台项目",
//			province : "请填写项目所在省",
//			city : "请填写项目所在市",
//			loanDes : "请填写项目简介",
//			loanLogo:"请上传项目封面"
//		},
//        submitHandler:function(form){
//        	var url=path + "/crowdfunding/saveCrowdFunding.html";
//        	if (loanNo) {
//        		url=path + '/crowdfunding/updateCrowdFunding.html';
//        	}
//        	$('#baseForm').form('submit', {
//    			url : url,
//    			queryParams: {
//    				"id":id,
//    				"loanDetail":UE.getEditor("loan_detail").getContent()
//    			},
//    			success : function(data) {
//    				data = $.parseJSON(data);
//    				if(data["success"]){
//    					if (data.loanNo) {
//    						loanNo = data.loanNo;
//						}
//    					$.messager.alert('提示', '保存成功');
//    					$("#basicBtn").attr("disabled",false);
//    					window.location.href=path+'/crowdfund.submiting.html';
//    				}else{
//    					
//    				}
//    			}
//    		});
//        }
//    }); 
//	
//	//保存基本信息
//	$('#basicBtn').click(function() {
//		$("#basicBtn").attr("disabled",true);
//		$('#baseForm').submit();
//	});
//	//加载信息
//	if (loanNo) {
//		$.ajax({
//			type : "POST",
//			url : path + '/crowdfunding/getCrowdDetail.html',
//			dataType:'json',
//			data : {
//				'loanNo' : loanNo
//			},
//			success : function(data) {
//				$("#tt .x-form-item .x-form-element label").each(function(){
//					$(this).val('');
//					$(this).val(data[this.id]);
//				});
//				
////				$("#tt .x-form-item .x-form-element div").each(function(){
////					if(this.id == 'loanLogo'){
////						$("#loanLogo2").attr("src",ftpUrl+data[this.id]);
////					}else if(this.id == 'loanVideo'){
////						if(data[this.id]){
////							var str = '<embed style="height:450px; width:780px;margin-left:50px;" src="'+data[this.id]+'" quality="high" align="middle"  mode="transparent" type="application/x-shockwave-flash"></embed>';
////							$("#loanVideo").html(str);
////						}
////					}else{
////						$(this).html('');
////						$(this).html(data[this.id]);
////					}
////				});
//				
//				//借款用户名
//				//$('#loanUserText').val(data.loanUserName);
//				
//				getCitys(data.province, "pro_city",function(){
//					$('#pro_city').val(data.city);
//				});
//				
////				UE.getEditor("loan_detail").ready(function(){
////					UE.getEditor("loan_detail").setContent(data.loanDetail);
////				});
//				
//				//$("#baseForm").form('load',data);
//			}
//		});
//	}
	
	
	
});




function previewPhoto(){
	var photoUrl = $("#loan_logo_url").val();
	if(photoUrl){
		$('<div id="logoDiv"><img src='+ftpUrl+photoUrl+' alt="小雨众筹" height="340" width="320"></div>').hide().appendTo('body');
	    $("#logoDiv").show().dialog({
			height: 380,
			width:342,
			title: "预览图片",
			onClose: function () {
				$("#logoDiv").remove();
	        }
		});
	}else{
		$.messager.alert('提示', '您还未上传图片，无法预览。');
	}
}



function loadPhoto(id,url,renderId){
	var picStr = '<div class="photo">'+
				 	'<div id="'+id+'">'+
				 		'<img src="'+url+'">'+
				 	'</div>'+
				 '</div>';

	$(picStr).appendTo('#'+renderId);
}



//选择借款用户
function getLoanUser(){
	$('<div id="loanUserDiv"><table id="loanUserTable"></table></div>').hide().appendTo('body');
	//获取字典数据
	var columns = [[
					{field:'userId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:130,align:'center',sortable:true},
					{field:'thirdAccount',title:'汇付账号',width:150,align:'center',sortable:true},
					{field:'mobile',title:'手机号',align:'center'},
					{field:'email',title:'邮箱',width:150,align:'center'},
					{field:'createTime',title:'注册时间',align:'center',sortable:true},
					{field:'statusName',title:'状态',width:50,align:'center',sortable:true}
				]];
	$('#loanUserTable').datagrid({
		url: path + "/user/getlist.html?isBorrower=1",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:364,
		pagination: true,
		onDblClickRow:function(rowIndex, row){
			$('#loanUserText').val(row.realName);
			$('#loanUser').val(row.userId);
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
