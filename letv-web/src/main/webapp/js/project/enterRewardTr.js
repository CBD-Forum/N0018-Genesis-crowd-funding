if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = getQueryString("loanNo");
$(function(){
	//$("#completeBtu").attr("href",path+"/common/enterRewardFr.html?loanNo="+loanNo);
	$("#company").keyup(function(){
		if($(this).val().length>30){
			AlertDialog.show("介绍不能超过30个字！", "company",0, 30, "jump");
			$(this).val($(this).val().substring(0,30));
		}
	});
	$(".lch_nav li.lch5").click(function(){
		window.location.href = path + "/common/enterReward.html?loanNo="+loanNo;
	});
	$(".lch_nav li.lch2").click(function(){
		window.location.href = path + "/common/enterRewardTw.html?loanNo="+loanNo;
	});
	showProvince();
	initUserInfo();
	$("#saveEnterSrc").attr("href",path+"/common/loanDetail-product.html?loanNo="+loanNo+"&state=add&type=0");
	$("#saveEnterBtn").click(updateUserStuff);//保存发起人基本信息
	$("#completeBtu").click(updateUserSubmit);
	$("#changeUpload").click(function(){
		$("#fileToUpload").click();
	});
});

//保存发起人基本信息
function updateUserStuff(){
	if(Valify.isNull($("#founder").val(), "founder", 0, 30, "jump")){
		if(Valify.isNull($("#userProvince").val(), "userProvince", 00, 30, "jump")){
			if(Valify.isNull($("#userCity").val(), "userCity", 0, 30, "jump")){
				if(Valify.phone($("#mobile").val(), "mobile", "jump")){
					$.ajax({
						url: path + "/crowdfunding/updateCrowdFundDetail.html",
						type: "post",
						dataType: "json",
						data: {
							"loanNo": loanNo,
							"founder": $("#founder").val(),//真实姓名
							"userProvince": $("#userProvince").val(),//所在省
							"userCity": $("#userCity").val(),//所在市
							"userMobile": $("#mobile").val(),//联系电话
							"company": $("#company").val(),//公司名称
							"businessLicense": $("#orgLoanReceiveProve").val()//证件上传
						},
						success: function(data){
							if(data["success"]){
								AlertDialog.tip("保存成功");
							}else{
								AlertDialog.tip(data["msg"]);
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
}
//提交
function updateUserSubmit(){
	if(Valify.isNull($("#founder").val(), "founder", 0, 30, "jump")){
		if(Valify.isNull($("#userProvince").val(), "userProvince", 00, 30, "jump")){
			if(Valify.isNull($("#userCity").val(), "userCity", 0, 30, "jump")){
				if(Valify.phone($("#mobile").val(), "mobile", "jump")){
					$.ajax({
						url: path + "/crowdfunding/updateCrowdFundDetail.html",
						type: "post",
						dataType: "json",
						data: {
							"loanNo": loanNo,
							"loanState":"submit",
							"founder": $("#founder").val(),//真实姓名
							"userProvince": $("#userProvince").val(),//所在省
							"userCity": $("#userCity").val(),//所在市
							"userMobile": $("#mobile").val(),//联系电话
							"company": $("#company").val(),//公司名称
							"businessLicense": $("#orgLoanReceiveProve").val()//证件上传
						},
						success: function(data){
							if(data["success"]){
								window.location.href = path + "/common/enterRewardFr.html?loanNo="+loanNo;
							}else{
								AlertDialog.tip(data["msg"]);
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
}
//保存回报设置
function saveBackSet(){
	//首先查看是编辑回报设置还是添加回报设置
	var burl, bd = {};
	if(!$("#backSetPauseBox").attr("editp")){ //添加
		burl = path + "/crowdfunding/saveBackSet.html";
		bd = {
				"loanNo": loanNo,
				"amt": $("#price").val(),//支持金额
				"backContent": $("#backContent").val(), //回报内容
				"photoUrl1": $("#backImg_url").val(),//说明图片
				"numberLimits": $("#limitPeople").val(),//限定名额
				"backTime": $("#backTime").val(),//回报时间
				"backType": $("#backType input").prop("checked") ? "S": "X",//回报类型
//				"transFee": $("#carriage").val(),//运费
				"backLable": $("#return_type input").prop("checked") ? "P": "M"//回报展示类别
			};
	}else{
		burl = path + "/crowdfunding/updateBackSet.html";
		bd = {
				"id": $("#backSetPauseBox").attr("editp"),
				"loanNo": loanNo,
				"amt": $("#price").val(),//支持金额
				"backContent": $("#backContent").val(), //回报内容
				"photoUrl1": $("#backImg_url").val(),//说明图片
				"numberLimits": $("#limitPeople").val(),//限定名额
				"backTime": $("#backTime").val(),//回报时间
				"backType": $("#backType input").prop("checked") ? "S": "X",//回报类型
//				"transFee": $("#carriage").val(),//运费
				"backLable": $("#return_type input").prop("checked") ? "P": "M"//回报展示类别
			};
	}
	if(checksportNum($("#price").val(), "price")){
		if(Valify.isNull($("#backContent").val(), "backContent", -40, 30, "jump")){
			if(checksportNum($("#limitPeople").val(), "limitPeople")){
				//if(checksportNum($("#carriage").val(), "carriage")){
					$.ajax({
						url: burl,
						type: "post",
						dataType: "json",
						data: bd,
						success: function(data){
							if(data["success"]){
								AlertDialog.tip("保存回报设置成功！");
								initBackSetList();
							}
						},
						error: function(request){
							console.log("保存项目基本信息异常");
						}
					});
				//}
			}
		}
	}
}
//验证支持金额
function checksportNum(str, id){
	if(!str || isNaN(str)){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}
	AlertDialog.hide();
	return true;
}
//检测视频链接
function checkVideoUrl(str, id){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
		return false;
	}
	if(str.substring(str.length-3, str.length) != "swf"){
		AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20);
		return false;
	}
	AlertDialog.hide();
	return true;
}

function initCrowdfundDetail(){
	var pArr = [], pl;
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": $("#projectLoanNo").val()},
		success: function(bdata){
			if(bdata["success"]){
				var hArr = [], hStr = '';
				hArr.push(bdata["msg"]["loanDetail"]);
				hStr = hArr.join("");
				UE.getEditor("proDetail").ready(function(){
					UE.getEditor("proDetail").setContent(hStr);
				});
				
				$("#vedio").val(bdata["msg"]["loanVideo"]);
				$("#vedioInfo").val(bdata["msg"]["videoDes"]);
				$("#mobileVideo").val(bdata["msg"]["houseDeveloper"]);
				//顶部进度改变
				if(bdata["msg"]["videoDes"]){
					$("#stepDiv>dl").eq(4).addClass("cur");
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
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}
//发起人基本信息
function initUserInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(bdata){
			$("#founder").val(bdata["user"]["realName"]);//真实姓名
			$("#mobile").val(bdata["user"]["mobile"]);
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(bdata){
			if(bdata["success"]){
				//$("#founder").val(bdata["msg"]["founder"]);//真实姓名
				showProvince(bdata["msg"]["userProvince"]);
				showCity(bdata["msg"]["userProvince"], bdata["msg"]["userCity"]);
				//$("#mobile").val(bdata["msg"]["userMobile"]);
				$("#company").val(bdata["msg"]["company"]);
				
				if(bdata["msg"]["businessLicense"]){
					$("#orgLoanReceiveProve").val(bdata["msg"]["businessLicense"]);
					var ksldj = bdata["msg"]["businessLicense"]
					var Imgarr = ksldj.split(',');
					for(var i=0;i<Imgarr.length;i++){
						$("#uploadInFo").append('<div class="fl rel mr20" style="margin-top:20px;" url="'+Imgarr[i]+'"><img src="'+cv.fileAddress+Imgarr[i]+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo","#orgLoanReceiveProve")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
					}
				}
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}

$('#fileToUpload').on('change', function() {
    $.ajaxFileUpload({
        url:path+'/userAuth/uploadAuthFile.html',  
        type:'post',
        async: false,
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
//文件删除
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
			$("#userProvince").html(pStr);
			if(id){
				$("#userProvince").val(id);
			}
			//点击省份后，根据省份id查询城市
			$("#userProvince").change(function(){
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
			$("#userCity").html(cStr);
			if(cid){
				$("#userCity").val(cid);
			}
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}