if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = getQueryString("loanNo");
$(function(){
	//$("#completeBtu").attr("href",path+"/common/enterPubBenefitFr.html?loanNo="+loanNo);
	$(".lch_nav li.lch5").click(function(){
		window.location.href = path + "/common/enterPubBenefit.html?loanNo="+loanNo;
	});
	$(".lch_nav li.lch2").click(function(){
		window.location.href = path + "/common/enterPubBenefitTw.html?loanNo="+loanNo;
	});
	initUserInfo();
	$("#saveEnterSrc").attr("href",path+"/common/loanDetail-public.html?loanNo="+loanNo+"&state=add&type=0");
	$("#saveEnterBtn").click(updateUserStuff);//保存发起人基本信息
	$("#completeBtu").click(function(){
		updateUserStuff(1);
	});
	$("#changeUpload").click(function(){
		if($("#orgLoanReceiveProve").val() == ""){
			$("#fileToUpload").click();
		}
	});
	$("#changeUpload1").click(function(){
		if($("#orgLoanReceiveProve1").val() == ""){
			$("#fileToUpload1").click();
		}
	});
	$("#changeUpload2").click(function(){
		$("#fileToUpload2").click();
	});
	$("#changeUpload3").click(function(){
		$("#fileToUpload3").click();
	});
	$("#changeUpload4").click(function(){
		$("#fileToUpload4").click();
	});
});
//发起人基本信息
function initUserInfo(){
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(bdata){
			if(bdata["success"]){
				
				if(bdata["msg"]){
					$("#orgLoanReceiveProve").val(bdata["msg"]["promoterIdentitySign"]);
					if(bdata["msg"]["promoterIdentitySign"]){
						var ksldj = bdata["msg"]["promoterIdentitySign"]
						var Imgarr = ksldj.split(',');
						for(var i=0;i<Imgarr.length;i++){
							$("#uploadInFo").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+Imgarr[i]+'"><img src="'+cv.fileAddress+Imgarr[i]+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo","#orgLoanReceiveProve")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
						}
					}
					
					
					$("#orgLoanReceiveProve1").val(bdata["msg"]["promoterIdentityPhoto"]);
					if(bdata["msg"]["promoterIdentityPhoto"]){
						var ksldj1 = bdata["msg"]["promoterIdentityPhoto"]
						var Imgarr1 = ksldj1.split(',');
						for(var i=0;i<Imgarr1.length;i++){
							$("#uploadInFo1").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+Imgarr1[i]+'"><img src="'+cv.fileAddress+Imgarr1[i]+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo","#orgLoanReceiveProve")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
						}
					}
					
					
					var file2, fileUrl2, orgLoanReceiveProve2 ;
					for(var i=0;i<bdata["msg"]["orgLoanReceiveProveFiles"].length;i++){
						file2 = bdata["msg"]["orgLoanReceiveProveFiles"][i]["fileName"];
						fileUrl2 = bdata["msg"]["orgLoanReceiveProveFiles"][i]["fileUrl"];
						var id = bdata["msg"]["orgLoanReceiveProveFiles"][i]["id"];
						if(bdata["msg"]["orgLoanReceiveProveFiles"].length > 1){
							if(i == 0){
								orgLoanReceiveProve2 = bdata["msg"]["orgLoanReceiveProveFiles"][i]["fileUrl"];
							}else{
								orgLoanReceiveProve2 = orgLoanReceiveProve2+","+bdata["msg"]["orgLoanReceiveProveFiles"][i]["fileUrl"];
							}
		        		}else{
		        			orgLoanReceiveProve2 = bdata["msg"]["orgLoanReceiveProveFiles"][i]["fileUrl"];
		        		}
		        		//$("#uploadInFo2").append('<div class="clearfix mt18" url="'+fileUrl2+'"><a class="fl add_img file_bk" href="'+cv.fileAddress+fileUrl2+'" >'+file2+'</a><a class="fl col_blue mt10" style="padding-left:5px;" vid="'+id+'" onClick=\'fileDeleteWJ(this,"#uploadInFo2","#orgLoanReceiveProve2")\'>删除</a></div>');
		        		$("#uploadInFo2").append('<div class="clearfix mt18 enterFile-style" url="'+fileUrl2+'"><a class="fl" style="padding-right: 15px;" target="_blank" href="'+cv.fileAddress+fileUrl2+'" >'+file2+'</a><a class="fr enterFile-delete" vid="'+id+'" onClick=\'fileDeleteWJ(this,"#uploadInFo4","#orgLoanReceiveProve4")\'></a></div>');
					}
					$("#orgLoanReceiveProve2").val(orgLoanReceiveProve2);
					
					$("#orgLoanReceiveProve3").val(bdata["msg"]["orgCertificate"]);
					if(bdata["msg"]["orgCertificate"]){
						var ksldj3 = bdata["msg"]["orgCertificate"]
						var Imgarr3 = ksldj3.split(',');
						for(var i=0;i<Imgarr3.length;i++){
							$("#uploadInFo3").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+Imgarr3[i]+'"><img src="'+cv.fileAddress+Imgarr3[i]+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo","#orgLoanReceiveProve")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
						}
					}
					
					
					$("#orgLoanReceiveProve4").val(bdata["msg"]["orgCode"]);
					if(bdata["msg"]["orgCode"]){
						var ksldj4 = bdata["msg"]["orgCode"]
						var Imgarr4 = ksldj4.split(',');
						for(var i=0;i<Imgarr4.length;i++){
							$("#uploadInFo4").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+Imgarr4[i]+'"><img src="'+cv.fileAddress+Imgarr4[i]+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo","#orgLoanReceiveProve")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
						}
					}
				}
				
				
				
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}
//保存发起人基本信息
function updateUserStuff(href){
	if(href == "1"){
		var dataStr = {
			"loanNo": loanNo,
			"loanState":"submit",
			"promoterIdentitySign": $("#orgLoanReceiveProve").val(),//发起人身份证复印件签名
			"promoterIdentityPhoto": $("#orgLoanReceiveProve1").val(),//发起人手持身份证
			"orgLoanReceiveProve": $("#orgLoanReceiveProve2").val(),//公募机构的项目接收证明(文件)
			"orgCertificate": $("#orgLoanReceiveProve3").val(),//公益机构登记证书
			"orgCode": $("#orgLoanReceiveProve4").val()//组织机构代码
		}
	}else{
		var dataStr = {
			"loanNo": loanNo,
			"promoterIdentitySign": $("#orgLoanReceiveProve").val(),//发起人身份证复印件签名
			"promoterIdentityPhoto": $("#orgLoanReceiveProve1").val(),//发起人手持身份证
			"orgLoanReceiveProve": $("#orgLoanReceiveProve2").val(),//公募机构的项目接收证明(文件)
			"orgCertificate": $("#orgLoanReceiveProve3").val(),//公益机构登记证书
			"orgCode": $("#orgLoanReceiveProve4").val()//组织机构代码
			}
	}
	if(Valify.isNull($("#orgLoanReceiveProve").val(), "changeUpload", -5, 30, "jump")){
		if(Valify.isNull($("#orgLoanReceiveProve1").val(), "changeUpload1", -5, 30, "jump")){
//			if(Valify.isNull($("#orgLoanReceiveProve2").val(), "changeUpload2", -5, 30, "jump")){
//				if(Valify.isNull($("#orgLoanReceiveProve3").val(), "changeUpload3", -5, 30, "jump")){
//					if(Valify.isNull($("#orgLoanReceiveProve4").val(), "changeUpload4", -5, 30, "jump")){
						$.ajax({
							url: path + "/crowdfunding/updateCrowdFundDetail.html",
							type: "post",
							dataType: "json",
							data: dataStr,
							success: function(data){
								if(data["success"]){
									if(href == "1"){
										window.location.href = path + "/common/enterPubBenefitFr.html?loanNo="+loanNo;
									}else{
										AlertDialog.tip("保存成功！");
									}
								}else{
									AlertDialog.tip(data["msg"]);
								}
							},
							error: function(request){
								console.log("保存项目基本信息异常");
							}
						});
//					}
//				}
//			}
		}
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

$('#fileToUpload').on('change', function() {
    $.ajaxFileUpload({
        url:path+'/userAuth/uploadAuthFile.html',  
        secureuri:false,  
        fileElementId:'fileToUpload',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve").val()+","+data.msg;
        		}else{
        			var orgLoanReceiveProve = data.msg;
        		}
        		$("#orgLoanReceiveProve").val(orgLoanReceiveProve);
        		AlertDialog.mtip('上传成功！');
        		$("#uploadInFo").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+data.msg+'"><img src="'+cv.fileAddress+data.msg+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo","#orgLoanReceiveProve")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
			}else{
				AlertDialog.RZmtip(data.msg);
			}
        },  
        error: function (data, status, e) {  
            alert(e);
        },
        complete:function(XMLHttpRequest,textStatus){
        	
        }
    });  

});
$('#fileToUpload1').on('change', function() {
    $.ajaxFileUpload({
        url:path+'/userAuth/uploadAuthFile.html',  
        secureuri:false,  
        fileElementId:'fileToUpload1',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve1").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve1").val()+","+data.msg;
        		}else{
        			var orgLoanReceiveProve = data.msg;
        		}
        		$("#orgLoanReceiveProve1").val(orgLoanReceiveProve);
        		AlertDialog.mtip('上传成功！');
        		$("#uploadInFo1").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+data.msg+'"><img src="'+cv.fileAddress+data.msg+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo1","#orgLoanReceiveProve1")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
			}else{
				AlertDialog.RZmtip(data.msg);
			}
        },  
        error: function (data, status, e) {  
            alert(e);
        },
        complete:function(XMLHttpRequest,textStatus){
        	
        }
    });  

});
$('#fileToUpload2').on('change', function() {
    $.ajaxFileUpload({
        url:path+'/fileUpload/uploadFile.html?parentId='+loanNo+'&type=orgLoanReceiveProve',
        secureuri:false,  
        fileElementId:'fileToUpload2',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve2").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve2").val()+","+data.fileUrl;
        		}else{
        			var orgLoanReceiveProve = data.fileUrl;
        		}
        		$("#orgLoanReceiveProve2").val(orgLoanReceiveProve);
        		AlertDialog.mtip('上传成功！');
        		//$("#uploadInFo2").append('<div class="clearfix mt18" url="'+data.fileUrl+'"><a class="fl add_img file_bk" href="'+cv.fileAddress+data.fileUrl+'" >'+data.fileName+'</a><a class="fl col_blue mt10" style="padding-left:5px;" vid="'+data.id+'" onClick=\'fileDeleteWJ(this,"#uploadInFo2","#orgLoanReceiveProve2")\'>删除</a></div>');
        		$("#uploadInFo2").append('<div class="clearfix mt18 enterFile-style" url="'+data.fileUrl+'"><a class="fl" style="padding-right: 15px;" target="_blank" href="'+cv.fileAddress+data.fileUrl+'" >'+data.fileName+'</a><a class="fr enterFile-delete" vid="'+data.id+'" onClick=\'fileDeleteWJ(this,"#uploadInFo4","#orgLoanReceiveProve4")\'></a></div>');
        	}else{
				AlertDialog.RZmtip(data.msg);
			}
        },  
        error: function (data, status, e) {  
            alert(e);
        },
        complete:function(XMLHttpRequest,textStatus){
        	
        }
    });

});
$('#fileToUpload3').on('change', function() {
    $.ajaxFileUpload({
        url:path+'/userAuth/uploadAuthFile.html',  
        secureuri:false,  
        fileElementId:'fileToUpload3',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve3").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve3").val()+","+data.msg;
        		}else{
        			var orgLoanReceiveProve = data.msg;
        		}
        		$("#orgLoanReceiveProve3").val(orgLoanReceiveProve);
        		AlertDialog.mtip('上传成功！');
        		$("#uploadInFo3").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+data.msg+'"><img src="'+cv.fileAddress+data.msg+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo3","#orgLoanReceiveProve3")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
			}else{
				AlertDialog.RZmtip(data.msg);
			}
        },  
        error: function (data, status, e) {  
            alert(e);
        },
        complete:function(XMLHttpRequest,textStatus){
        	
        }
    });  

});
$('#fileToUpload4').on('change', function() {
    $.ajaxFileUpload({
        url:path+'/userAuth/uploadAuthFile.html',  
        secureuri:false,  
        fileElementId:'fileToUpload4',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve4").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve4").val()+","+data.msg;
        		}else{
        			var orgLoanReceiveProve = data.msg;
        		}
        		$("#orgLoanReceiveProve4").val(orgLoanReceiveProve);
        		AlertDialog.mtip('上传成功！');
        		$("#uploadInFo4").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+data.msg+'"><img src="'+cv.fileAddress+data.msg+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo4","#orgLoanReceiveProve4")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
			}else{
				AlertDialog.RZmtip(data.msg);
			}
        },  
        error: function (data, status, e) {  
            alert(e);
        },
        complete:function(XMLHttpRequest,textStatus){
        	
        }
    });  

});
//图片删除
function fileDelete(_this,id1,id2){
	$(id2).val("");
	$(_this).parent().remove();
	var _num = $(id1).find("div").length;
	for(var f=0;f<_num;f++){
		if(_num>0){
			if($(id2).val()){
				$(id2).val($(id2).val()+","+$(id1).find("div").eq(f).attr("url"));
			}else{
				$(id2).val($(id1).find("div").eq(f).attr("url"));
			}
		}else if(_num<0){
			$(id2).val("");
		}
	}
}
//文件删除
function fileDeleteWJ(_this,id1,id2){
	$(id2).val("");
	$(_this).parent().remove();
	var _num = $(id1).find("div").length;
	for(var f=0;f<_num;f++){
		if(_num>0){
			if($(id2).val()){
				$(id2).val($(id2).val()+","+$(id1).find("div").eq(f).attr("url"));
			}else{
				$(id2).val($(id1).find("div").eq(f).attr("url"));
			}
		}else if(_num<0){
			$(id2).val("");
		}
	}
	$.ajax({
        url:path+'/fileUpload/removeFile.html', 
		type: "post",
		dataType: "json",
		data:{
			"id" : $(_this).attr("vid")
		},
		success: function(data){
			if(data["success"]){
			}else{
				AlertDialog.tip(data["msg"]);
			}
		},
		error: function(request){
			console.log("删除文件异常");
		}
	});
}
