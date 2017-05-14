$(function(){
	$('#tt').tabs({
		height:bodyHeight-190,
	    onSelect:function(title){   
	    }   
	});
	$('#add').width(bodyWidth-165);
	$('#add').height(bodyHeight-190);
	
	if (id) {
		
		
		//领头人认证资料
		userAuthInfo("leadInvestorForm", "leadInvestor", id)
		//投资人认证资料
		userAuthInfo("investorForm", "investor", id)
		//领投机构认证资料
		userAuthInfo("orgLeadInvestorForm", "orgLeadInvestor", id)
		//跟投机构认证资料
		userAuthInfo("orgInvestorForm", "orgInvestor", id)
		
		
		$.ajax({
			type : "POST",
			url : path + '/user/getLoanUserDetail.html',
			dataType:'json',
			data : {
				'userId' : id
			},
			success : function(data) {
				
				$("#userForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
					if(this.id == 'isBorrower'){
						$(this).text(data.user[this.id]=='0'?'不是':'是');
					}
					if(this.id == 'sex'){
						$(this).text(data.user[this.id]=='M'?'男':'女');
					}
					if(this.id == 'certificateType'){
						$(this).text(data.user[this.id]=='00'?'身份证':'其他');
					}
				});
				$("#financialCountForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
				});
				
				$("#detailForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
				});
				
				$("#loanCountForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
				});
				
				$("#loanMoneyForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
				});
				
				if (data.user.photo) {
					$('#photoDiv .filelist').html('<img src="'+fileUrl+data.user.photo+'" style="width:200px; height:200px;"/>');
				}
				var userFiles = data.userFiles;
				for ( var i = 0; i < userFiles.length; i++) {
					$('<tr><td>'+userFiles[i].fileTypeName+'</td><td>'+'<img src="'+fileUrl+userFiles[i].fileUrl+'" style="width:80px; height:80px;"/>'+'</td><td>'+(userFiles[i].fileStateName)+'</td><td>'+(userFiles[i].auditTime == null?'':userFiles[i].auditTime)+'</td></tr>').appendTo('#userFiles');
				}
//				
//				for(var i=0;i<data.userStuff.length;i++){
//					if(data.userStuff[i].authType=='investor'){
//						$("#financialForm .x-form-item .x-form-element label").each(function(){
//							$(this).text('');
//							$(this).text(data.userStuff[i][this.id]);
//						});
//						
//						if (data.userStuff[i].cardPhoto) {
//							$('#financialForm div[id="cardPhotoDiv"] .filelist').html('<img src="'+fileUrl+data.userStuff[i].cardPhoto+'" style="width:200px; height:200px;"/>');
//						}
//					}
//					if(data.userStuff[i].authType=='leadInvestor'){
//						$("#leadInvestorForm .x-form-item .x-form-element label").each(function(){
//							$(this).text('');
//							$(this).text(data.userStuff[i][this.id]);
//						});
//						if (data.userStuff[i].cardPhoto) {
//							$('#leadInvestorForm div[id="cardPhotoDiv"] .filelist').html('<img src="'+fileUrl+data.userStuff[i].cardPhoto+'" style="width:200px; height:200px;"/>');
//						}
//						
//						if(data.userStuff[i]["assetsCredentials"]){
//							var assetsCredentials = data.userStuff[i].assetsCredentials.split(",");
//							for(var j=0;j<assetsCredentials.length;j++){
//								$("#leadInvestorForm div[id='assetsCredentialsDiv']").append("<a href="+fileUrl+assetsCredentials[j]+" target='_black'>查看文件"+(parseInt(j)+1)+"</a>&nbsp;&nbsp");
//							}
//						}
//						if(data.userStuff[i]["historicalInvestmentData"]){
//							var historicalInvestmentData = data.userStuff[i].historicalInvestmentData.split(",");
//							for(var t=0;t<historicalInvestmentData.length;t++){
//								$("#leadInvestorForm div[id='historicalInvestmentDataDiv']").append("<a href="+fileUrl+historicalInvestmentData[t]+" target='_black'>查看文件"+(parseInt(t)+1)+"</a>&nbsp;&nbsp");
//							}
//						}
//						
//					}
//					if(data.userStuff[i].authType=='orgInvestor'){
//						$("#orgInvestorForm .x-form-item .x-form-element label").each(function(){
//							$(this).text('');
//							$(this).text(data.userStuff[i][this.id]);
//						});
//						
//						if(data.userStuff[i]["cardPhoto"]){
//							var cardPhoto = data.userStuff[i].cardPhoto.split(",");
//							for(var f=0;f<cardPhoto.length;f++){
//								$("#orgInvestorForm div[id='cardPhotoDiv']").append("<a href="+fileUrl+cardPhoto[f]+" target='_black'>查看文件"+(parseInt(f)+1)+"</a>&nbsp;&nbsp");
//							}
//						}
//						
//					}
//					
//					
//					if(data.userStuff[i].authType=='orgLeadInvestor'){
//						$("#orgLeadInvestorForm .x-form-item .x-form-element label").each(function(){
//							$(this).text('');
//							$(this).text(data.userStuff[i][this.id]);
//						});
//						if(data.userStuff[i]["cardPhoto"]){
//							var cardPhoto = data.userStuff[i].cardPhoto.split(",");
//							for(var g=0;g<cardPhoto.length;g++){
//								$("#orgLeadInvestorForm div[id='cardPhotoDiv']").append("<a href="+fileUrl+cardPhoto[g]+" target='_black'>查看文件"+(parseInt(g)+1)+"</a>&nbsp;&nbsp");
//							}
//						}
//						
//						if(data.userStuff[i]["assetsCredentials"]){
//							var assetsCredentials = data.userStuff[i].assetsCredentials.split(",");
//							for(var j=0;j<assetsCredentials.length;j++){
//								$("#orgLeadInvestorForm div[id='assetsCredentialsDiv']").append("<a href="+fileUrl+assetsCredentials[j]+" target='_black'>查看文件"+(parseInt(j)+1)+"</a>&nbsp;&nbsp");
//							}
//						}
//						if(data.userStuff[i]["historicalInvestmentData"]){
//							var historicalInvestmentData = data.userStuff[i].historicalInvestmentData.split(",");
//							for(var y=0;y<historicalInvestmentData.length;y++){
//								$("#orgLeadInvestorForm div[id='historicalInvestmentDataDiv']").append("<a href="+fileUrl+historicalInvestmentData[y]+" target='_black'>查看文件"+(parseInt(y)+1)+"</a>&nbsp;&nbsp");
//							}
//						}
//					}
//				}
//				
//				if(data.userStuff.assetsCredentials){
//					var assetsCredentials = data.userStuff.assetsCredentials.split(",");
//					for(var i=0;i<assetsCredentials.length;i++){
//						$("#assetsCredentialsDiv").append("<a href="+fileUrl+assetsCredentials[i]+" target='_black'>查看文件"+(parseInt(i)+1)+"</a>&nbsp;&nbsp");
//					}
//				}
				
				$("#srealName").text(data.user["realName"]);
				$("#smobile").text(data.user["mobile"]);
			}
		});
	}
});


/**
 * 查询领头人认证详情
 */
function userAuthInfo(formId,authType,userId){
	$.ajax({
		type : "POST",
		url : path + '/userAuth/getUserAuthInfoByUserId.html',
		dataType:'json',
		data : {
			'userId' : userId,
			'authType':authType
		},
		success : function(data) {
			if(data.success){
				if("leadInvestorForm" == formId){
					leadInvestorInfo(data);
				}else if("investorForm" == formId){
					investorInfo(data);
				}else if("orgLeadInvestorForm" == formId){
					orgLeadInvestorInfo(data)
				}else if("orgInvestorForm" == formId){
					orgInvestorInfo(data);
				}
			}
		}
	});	
}

/**
 * 领头人认证资料
 * @param data
 */
function leadInvestorInfo(data){
	
	
	$("#leadInvestorForm .x-form-item .x-form-element label").each(function(){
		$(this).text('');
		if(data.msg){
			$(this).text(data.msg[this.id]);
		}
	});
	if(data.msg){
		//名片
		if(data.msg.cardPhoto){
		     $('#leadInvestorForm div[id="cardPhotoDiv"] .filelist').html('<img src="'+fileUrl+data.msg.cardPhoto+'" style="width:200px; height:200px;"/>');
		}
		//个人资产
//		if(data.msg.assetsCredentials){
//			var assetsCredentials = data.msg.assetsCredentials.split(",");
//			for(var j=0;j<assetsCredentials.length;j++){
//				$("#leadInvestorForm div[id='assetsCredentialsDiv']").append("<a href="+fileUrl+assetsCredentials[j]+" target='_black'>查看文件"+(parseInt(j)+1)+"</a>&nbsp;&nbsp</br>");
//			}
//		}
		var assetsCredentialsList = data.msg.assetsCredentialsList;
		if(assetsCredentialsList && assetsCredentialsList.length >0){
			for(var f=0;f<assetsCredentialsList.length;f++){
				var file = assetsCredentialsList[f];
				$("#leadInvestorForm div[id='assetsCredentialsDiv']").append("<a href="+fileUrl+file.fileUrl+" target='_black'>"+file.fileName+"</a>");
			}
		}
		
		//历史投资资料
//		if(data.msg.historicalInvestmentData){
//			var historicalInvestmentData = data.msg.historicalInvestmentData.split(",");
//			for(var t=0;t<historicalInvestmentData.length;t++){
//				$("#leadInvestorForm div[id='historicalInvestmentDataDiv']").append("<a href="+fileUrl+historicalInvestmentData[t]+" target='_black'>查看文件"+(parseInt(t)+1)+"</a>&nbsp;&nbsp");
//			}
//		}
		
		var historicalInvestMentList = data.msg.historicalInvestMentList;
		if(historicalInvestMentList && historicalInvestMentList.length >0){
			for(var f=0;f<historicalInvestMentList.length;f++){
				var file = historicalInvestMentList[f];
				$("#leadInvestorForm div[id='historicalInvestmentDataDiv']").append("<a href="+fileUrl+file.fileUrl+" target='_black'>"+file.fileName+"</a>");
			}
		}
		
		
	}
}


/**
 * 跟投人认证资料
 * @param data
 */
function investorInfo(data){
	$("#investorForm .x-form-item .x-form-element label").each(function(){
		$(this).text('');
		if(data.msg){
			$(this).text(data.msg[this.id]);
		}
	});
	if(data.msg){
		//名片
		if(data.msg.cardPhoto){
		     $('#investorForm div[id="cardPhotoDiv"] .filelist').html('<img src="'+fileUrl+data.msg.cardPhoto+'" style="width:200px; height:200px;"/>');
		}
	}

}



/**
 * 领投机构认证资料
 * @param data
 */
function orgLeadInvestorInfo(data){
	$("#orgLeadInvestorForm .x-form-item .x-form-element label").each(function(){
		$(this).text('');
		if(data.msg){
			$(this).text(data.msg[this.id]);
		}
	});
	if(data.msg){
		//机构证件
//		if(data.msg.cardPhoto){
//			var cardPhoto = data.msg.cardPhoto.split(",");
//			for(var f=0;f<cardPhoto.length;f++){
//				$("#orgLeadInvestorForm div[id='cardPhotoDiv']").append("<a href="+fileUrl+cardPhoto[f]+" target='_black'>查看文件"+(parseInt(f)+1)+"</a>&nbsp;&nbsp</br>");
//			}
//		}
		
		//机构证件文件列表
		var orgCardPhotoList = data.msg.orgCardPhotoList;
		if(orgCardPhotoList && orgCardPhotoList.length >0){
			for(var f=0;f<orgCardPhotoList.length;f++){
				var file = orgCardPhotoList[f];
				$("#orgLeadInvestorForm div[id='cardPhotoDiv']").append("<a href="+fileUrl+file.fileUrl+" target='_black'>"+file.fileName+"</a>");
			}
		}
		
		//机构资产
//		if(data.msg.assetsCredentials){
//			var assetsCredentials = data.msg.assetsCredentials.split(",");
//			for(var j=0;j<assetsCredentials.length;j++){
//				$("#orgLeadInvestorForm div[id='assetsCredentialsDiv']").append("<a href="+fileUrl+assetsCredentials[j]+" target='_black'>查看文件"+(parseInt(j)+1)+"</a>&nbsp;&nbsp");
//			}
//		}
		
		//机构资产文件列表
		var orgAssetsCredentialsList = data.msg.orgAssetsCredentialsList;
		if(orgAssetsCredentialsList && orgAssetsCredentialsList.length >0){
			for(var f=0;f<orgAssetsCredentialsList.length;f++){
				var file = orgAssetsCredentialsList[f];
				$("#orgLeadInvestorForm div[id='assetsCredentialsDiv']").append("<a href="+fileUrl+file.fileUrl+" target='_black'>"+file.fileName+"</a>");
			}
		}		
		
		//机构历史投资资料
//		if(data.msg.historicalInvestmentData){
//			var historicalInvestmentData = data.msg.historicalInvestmentData.split(",");
//			for(var y=0;y<historicalInvestmentData.length;y++){
//				$("#orgLeadInvestorForm div[id='historicalInvestmentDataDiv']").append("<a href="+fileUrl+historicalInvestmentData[y]+" target='_black'>查看文件"+(parseInt(y)+1)+"</a>&nbsp;&nbsp");
//			}
//		}
		//机构历史投资资料文件列表
		var orgHistoricalInvestMentList = data.msg.orgHistoricalInvestMentList;
		if(orgHistoricalInvestMentList && orgHistoricalInvestMentList.length >0){
			for(var f=0;f<orgHistoricalInvestMentList.length;f++){
				var file = orgHistoricalInvestMentList[f];
				$("#orgLeadInvestorForm div[id='historicalInvestmentDataDiv']").append("<a href="+fileUrl+file.fileUrl+" target='_black'>"+file.fileName+"</a>");
			}
		}
	}
}


/**
 * 跟投机构认证资料
 * @param data
 */
function orgInvestorInfo(data){
	$("#orgInvestorForm .x-form-item .x-form-element label").each(function(){
		$(this).text('');
		if(data.msg){
			$(this).text(data.msg[this.id]);
		}
	});
	
	if(data.msg){
		//机构证件
		if(data.msg.cardPhoto){
			var cardPhoto = data.msg.cardPhoto.split(",");
			for(var f=0;f<cardPhoto.length;f++){
				$("#orgInvestorForm div[id='cardPhotoDiv']").append("<a href="+fileUrl+cardPhoto[f]+" target='_black'>查看文件"+(parseInt(f)+1)+"</a>&nbsp;&nbsp");
			}
		}
		
		//机构证件文件列表
		var orgCardPhotoList = data.msg.orgCardPhotoList;
		if(orgCardPhotoList && orgCardPhotoList.length >0){
			for(var f=0;f<orgCardPhotoList.length;f++){
				var file = orgCardPhotoList[f];
				$("#orgInvestorForm div[id='cardPhotoDiv']").append("<a href="+fileUrl+file.fileUrl+" target='_black'>"+file.fileName+"</a>");
			}
		}
		//机构资产
//		if(data.msg.assetsCredentials){
//			var assetsCredentials = data.msg.assetsCredentials.split(",");
//			for(var j=0;j<assetsCredentials.length;j++){
//				$("#orgInvestorForm div[id='assetsCredentialsDiv']").append("<a href="+fileUrl+assetsCredentials[j]+" target='_black'>查看文件"+(parseInt(j)+1)+"</a>&nbsp;&nbsp");
//			}
//		}
		
		//机构资产文件列表
		var orgAssetsCredentialsList = data.msg.orgAssetsCredentialsList;
		if(orgAssetsCredentialsList && orgAssetsCredentialsList.length >0){
			for(var f=0;f<orgAssetsCredentialsList.length;f++){
				var file = orgAssetsCredentialsList[f];
				$("#orgInvestorForm div[id='assetsCredentialsDiv']").append("<a href="+fileUrl+file.fileUrl+" target='_black'>"+file.fileName+"</a>");
			}
		}	
		//机构历史投资资料
//		if(data.msg.historicalInvestmentData){
//			var historicalInvestmentData = data.msg.historicalInvestmentData.split(",");
//			for(var y=0;y<historicalInvestmentData.length;y++){
//				$("#orgInvestorForm div[id='historicalInvestmentDataDiv']").append("<a href="+fileUrl+historicalInvestmentData[y]+" target='_black'>查看文件"+(parseInt(y)+1)+"</a>&nbsp;&nbsp");
//			}
//		}
		//机构历史投资资料文件列表
		var orgHistoricalInvestMentList = data.msg.orgHistoricalInvestMentList;
		if(orgHistoricalInvestMentList && orgHistoricalInvestMentList.length >0){
			for(var f=0;f<orgHistoricalInvestMentList.length;f++){
				var file = orgHistoricalInvestMentList[f];
				$("#orgInvestorForm div[id='historicalInvestmentDataDiv']").append("<a href="+fileUrl+file.fileUrl+" target='_black'>"+file.fileName+"</a>");
			}
		}
	}
}




function loadPhoto(id,url, renderId){
	var picStr = '<div class="photo">'+
				 	'<div id="'+id+'">'+
				 		'<img src="'+url+'" style="height:150px;">'+
				 		'<div id="testPicOperate" class="operate">'+
				 			'<span class="cancel"></span>'+
				 		'</div>'+
				 	'</div>'+
				 '</div>';

	$(picStr).appendTo('#'+renderId+' .filelist');
}