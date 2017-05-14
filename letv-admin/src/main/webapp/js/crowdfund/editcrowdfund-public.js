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
				$("#loanTypeName").text(data.loanTypeName);
				$("#baseForm").form('load',data);
				if(data.canOver && data.canOver=='1'){
					$("#overFundAmtDiv").show();
				}
				if(!data.chargeFee){
					showChargeFeeScale();
				}
				
				if (data.loanLogo) {
					$('#loanLogoForm input[name="logo"]').val(data.loanLogo);
					$('#logoDiv .filelist').html('<img src="'+fileUrl+data.loanLogo+'" style="width:200px; height:200px;"/>');
				}
				if (data.loanMobileLogo) {
					$('#loanLogoForm input[name="logo2"]').val(data.loanMobileLogo);
					$('#logoDiv2 .filelist').html('<img src="'+fileUrl+data.loanMobileLogo+'" style="width:200px; height:200px;"/>');
				}
				
				if(data.loanDetail){
					UE.getEditor("loan_detail").ready(function(){
						UE.getEditor("loan_detail").setContent(data.loanDetail);
					});
				}
				
				if(data.orgCode){
//					loadPhoto(data.orgCode,"orgCode","orgCodeDiv");
					var urlSplit = data.orgCode.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="orgCode" value="'+urlSplit[j]+'"/>'+
//						'<input type="hidden" name="id" value="orgCode'+j+'"/>'+
					 	'<div id="orgCode'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}

					$(picStr).appendTo('#orgCodeDiv .filelist');
					
				}
				
				if (data.orgCertificate) {
//					loadPhoto(data.orgCertificate,"orgCertificate","orgCertificateDiv");
					var urlSplit = data.orgCertificate.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="orgCertificate" value="'+urlSplit[j]+'"/>'+
//						'<input type="hidden" name="id" value="orgCode'+j+'"/>'+
					 	'<div id="orgCertificate'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}

					$(picStr).appendTo('#orgCertificateDiv .filelist');
				}
				
				if (data.promoterIdentitySign) {
//					loadPhoto(data.promoterIdentitySign,"promoterIdentitySign","promoterIdentitySignDiv");
					
					var urlSplit = data.promoterIdentitySign.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="promoterIdentitySign" value="'+urlSplit[j]+'"/>'+
//						'<input type="hidden" name="id" value="orgCode'+j+'"/>'+
					 	'<div id="promoterIdentitySign'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}

					$(picStr).appendTo('#promoterIdentitySignDiv .filelist');
				}
				
				if (data.promoterIdentityPhoto) {
//					loadPhoto(data.promoterIdentityPhoto,"promoterIdentityPhoto","promoterIdentityPhotoDiv");
					var urlSplit = data.promoterIdentityPhoto.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="promoterIdentityPhoto" value="'+urlSplit[j]+'"/>'+
//						'<input type="hidden" name="id" value="orgCode'+j+'"/>'+
					 	'<div id="promoterIdentityPhoto'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}

					$(picStr).appendTo('#promoterIdentityPhotoDiv .filelist');
				}
				/*if(data.orgLoanReceiveProve){
					var urlSplit = data.orgLoanReceiveProve.split(",");
					var picStr ="";
					for(var j=0;j<urlSplit.length;j++){
						picStr += '<div class="photo">'+
						'<input type="hidden" name="orgLoanReceiveProve" value="'+urlSplit[j]+'"/>'+
//						'<input type="hidden" name="id" value="orgCode'+j+'"/>'+
					 	'<div id="orgLoanReceiveProve'+j+'" onmouseover="uploadFile(this.id)">'+
					 		'<img src="'+fileUrl+urlSplit[j]+'">'+
					 		'<div id="testPicOperate" class="operate" style="height:27px;display:none;">'+
					 			'<span class="cancel" style="height:17px;"></span>'+
					 		'</div>'+
					 	'</div>'+
						 '</div>';
					}

					$(picStr).appendTo('#orgLoanReceiveProveDiv .filelist');
				}*/
				if(data.orgLoanReceiveProveFiles){
					var oArr = [], oStr = '',l=data["orgLoanReceiveProveFiles"].length;
					for(var i=0;i<l;i++){
						var row = data["orgLoanReceiveProveFiles"][i];
						oArr.push("<p><a href="+fileUrl+row.fileUrl+" target='_black'>"+row.fileName+"</a>" +
	            						"<input type='hidden' name='orgLoanReceiveProve' value="+row.fileUrl+" />" +
	            						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(row.id)+"' onclick='removeFile(this);'>删除</a>" +
	            								"</p>");
					}
					oStr = oArr.join("");
					$("#orgLoanReceiveProveUploadInFo").html(oStr);
				}				
				
				$("#videoForm").form('load',data);
				if(data.loanVideo){
					var str = '<embed style="height:450px; width:500px;margin-left:50px;" src="'+data.loanVideo+'" quality="high" align="middle"  mode="transparent" type="application/x-shockwave-flash"></embed>';
					$("#loanVideoDisplay").html(str);
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
	
	
	$("#orgLoanReceiveProveChangeUpload").click(function(){
		alert();
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
            		$("#orgLoanReceiveProveUploadInFo").append("<p><span style='color:red;'></span>" +
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
	
	$("#welfareInfoBtn").click(function(){
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
				}
			},
			error: function(){
				
			}
		});
	});
	
	
	
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
	
    
    createWebUploader('logoBtn','logoDiv','logo','loanLogoForm');
	createWebUploader('logoBtn2','logoDiv2','logo2','loanLogoForm');
	
	createWebUploaderMore('orgCodeBtn','orgCodeDiv','orgCode','welfareInfoForm');
	createWebUploaderMore('orgCertificateBtn','orgCertificateDiv','orgCertificate','welfareInfoForm');
	createWebUploaderMore('promoterIdentitySignBtn','promoterIdentitySignDiv','promoterIdentitySign','welfareInfoForm');
	createWebUploaderMore('promoterIdentityPhotoBtn','promoterIdentityPhotoDiv','promoterIdentityPhoto','welfareInfoForm');
	createWebUploaderMore('orgLoanReceiveProveBtn','orgLoanReceiveProveDiv','orgLoanReceiveProve','welfareInfoForm');
	
	
});

function logoCallback(pics){
	for ( var i = 0; i < pics.length; i++) {
		loadPhoto(pics[i].title.substring(0,pics[i].title.indexOf('.')),pics[i].src,pics[i].alt,'logo_pigup');
	}
	$('#proIframe').dialog('close');
}

function searchUser(){
	queryToGrid("loanUserTable", "list_search");
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