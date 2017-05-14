if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	//日期初始化赋值
	$("#dateStart").val(myDate.getLastMonthYestdy());
	$("#dateEnd").val(myDate.getTodayDate());
	tradeChangeTab();
	//筛选按钮事件
	$("#searchBtn").click(searchList);
//	$("#withdrawBtn").click(go2withdraw);
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
});
//交易列表tab标签事件
function tradeChangeTab(){
	$("#tradeTab").find("a").click(function(){
		$("#tradeTab").find("a").removeClass("a_home");
		$(this).addClass("a_home");
		$("#tradeBody>div").hide();
		$("#"+$(this).attr("name")+"Body").show();
		getTradeDetail($(this).attr("name"), $(this).attr("vtype"));
	});
	$("#tradeTab").find("a").first().click();
}
//获取交易成功详情信息
function getTradeDetail(type, id){
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": id},
		success: function(data){
			getTypeList(type,data);//动态添加
			var tradeType = $("#" + id + "_tradeType").val(), tradeStartTime = $("#dateStart").val(),
				tradeEndTime = $("#dateEnd").val();
//			if(type == "bill"){
//				getBillList(tradeType, tradeStartTime, tradeEndTime,id);
//			}else if(type == "getDraw"){
//				getDrawList(tradeType, tradeStartTime, tradeEndTime, type);
//			}
			if(type == "success"){
				getSuccessList(tradeType, tradeStartTime, tradeEndTime, type);
			}else if(type == "process"){
				getProcessList(tradeType, tradeStartTime, tradeEndTime, type);
			}else if(type == "fail"){
				getFailList(tradeType, tradeStartTime, tradeEndTime, type);
			}
		},
		error: function(request){
			console.log("获取个人收支明细类型异常");
		}
	});
}
//动态添加交易查询条件
function getTypeList(id ,data){
	$("#tradeSearchDiv select").hide();
	//生成
	var l = data.length, tArr = [], tStr = '';
	if(id == "success"){
		tArr.push('<option value="">全部类型</option>');
		for(var i=0;i<l;i++){
			//if(data[i]["code"]!="withdraw_freeze")
				tArr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
		}
	}else{
		tArr.push('<option value="recharge">充入</option>');
		tArr.push('<option value="withdraw">提出</option>');
	}
	tStr = tArr.join("");
	$("#" + id + "_tradeType").html(tStr).show();
}
/**
 * 获取-交易失败-记录列表
 * @param type 交易类型
 * @param startTime 交易开始时间
 * @param endTime 交易结束时间
 * @param id 与tab标签对应的tab body的id
 */
function getFailList(type, startTime, endTime, id, page){
	page = page ? page : 1;
	var ttype = $("#" + id + "_tradeType").val();
	if(ttype == "withdraw"){//提出
		var url = path + "/crowdfundUserCenter/getUserWithDrawList.html";
	}else{//充入
		var url = path + "/crowdfundUserCenter/getUserRechargeList.html";
	}
	$.ajax({
		url: url,
		type: "post",
		dataType: "json",
		data: {
				"stateIn": "fail",
				"createStartTime": startTime,
				"createEndTime": endTime,
				"page": page,
				"rows": 6
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var allSumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;//分页总数
			var tData = data["msg"]["rows"], l=tData.length, dataArr = [], dataStr = '';
			for(var i=0;i<l;i++){
				dataArr.push('<li>');
				if(ttype == "withdraw"){//提出
					dataArr.push('<p style="width:25%">提出</p>');
					dataArr.push('<p style="width:25%">'+tData[i]["amt"].toFixed(2)+companyCode+'</p>');
					dataArr.push('<p style="width:25%">'+tData[i]["applyTime"]+'</p>');
				}else if(ttype == "recharge"){//充入
					dataArr.push('<p style="width:25%">充入</p>');
					dataArr.push('<p style="width:25%">'+tData[i]["rechargeAmt"].toFixed(2)+companyCode+'</p>');
					dataArr.push('<p style="width:25%">'+tData[i]["createTime"]+'</p>');
				}else{
					dataArr.push('<p style="width:25%">--</p>');
				}
				dataArr.push('<p style="width:25%">');
				dataArr.push('<a tid="'+tData[i]["id"]+'" ttype="'+ttype+'" onclick="getBonusMon(event)" >详情</a>');
				if(tData[i]["tradeType"]=="recharge"||tData[i]["tradeType"]=="withdraw"){
					dataArr.push('<a target="_blank" href="'+path+'/common/pdfTrade.html?tradeId='+tData[i]["tradeId"]+'&type='+tData[i]["tradeType"]+'" >查看合同</a>');	
				}
				dataArr.push('</p>');
				dataArr.push('</li>');
			}
			dataStr = dataArr.join("");
			if(l == 0){
				$("#"+id+"ListBody>ul").hide();
				$("#"+id+"ListBody>div").html('<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>当前没有交易记录哦，快点做点什么吧~</div>').show();
				$("#"+id+"ListPage").hide();
			}else{
				$("#"+id+"ListBody>div").hide();
				$("#"+id+"ListBody>ul").html(dataStr).show();
				$("#"+id+"ListPage").show();
			}
			pagePause = 0;
			//分页设置
			if(page < 2){
				$("#"+id+"ListPage").jPages({
					containerID : ""+id+"ListBody",
					clickStop   : true,
					first: false,
			        previous: " ",
			        next: " ",
			        last: false,
					perPage	: 6,
					allSumPage : allSumPage,
					callback: ajaxPageData_fail
				});
			}
		},
		error: function(){
			console.log("获取提出记录列表数据异常");
		}
	});
}
/**
 * 获取-交易中-记录列表
 * @param type 交易类型
 * @param startTime 交易开始时间
 * @param endTime 交易结束时间
 * @param id 与tab标签对应的tab body的id
 */
function getProcessList(type, startTime, endTime, id, page){
	page = page ? page : 1;
	var ttype = $("#" + id + "_tradeType").val();
	if(ttype == "withdraw"){//提出
		var url = path + "/crowdfundUserCenter/getUserWithDrawList.html";
	}else{//充入
		var url = path + "/crowdfundUserCenter/getUserRechargeList.html";
	}
	$.ajax({
		url: url,
		type: "post",
		dataType: "json",
		data: {
				"stateIn": "inTransaction",
				"tradeType": type,
				"createStartTime": startTime,
				"createEndTime": endTime,
				"page": page,
				"rows": 6
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var allSumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6: Math.floor(data["msg"]["total"]/6) + 1;//分页总数
			var tData = data["msg"]["rows"], l=tData.length, dataArr = [], dataStr = '';
			for(var i=0;i<l;i++){
				dataArr.push('<li>');
				if(ttype == "withdraw"){//提出
					dataArr.push('<p style="width:25%">提出</p>');
					dataArr.push('<p style="width:25%">'+tData[i]["amt"].toFixed(2)+companyCode+'</p>');
					dataArr.push('<p style="width:25%">'+tData[i]["applyTime"]+'</p>');
				}else if(ttype == "recharge"){//充入
					dataArr.push('<p style="width:25%">充入</p>');
					dataArr.push('<p style="width:25%">'+tData[i]["rechargeAmt"].toFixed(2)+companyCode+'</p>');
					dataArr.push('<p style="width:25%">'+tData[i]["createTime"]+'</p>');
				}else{
					dataArr.push('<p style="width:25%">--</p>');
				}
				dataArr.push('<p style="width:25%">');
				dataArr.push('<a tid="'+tData[i]["id"]+'" ttype="'+ttype+'" onclick="getBonusMon(event)" >详情</a>');
				if(tData[i]["tradeType"]=="recharge"||tData[i]["tradeType"]=="withdraw"){
					dataArr.push('<a target="_blank" href="'+path+'/common/pdfTrade.html?tradeId='+tData[i]["tradeId"]+'&type='+tData[i]["tradeType"]+'" >查看合同</a>');	
				}
				dataArr.push('</p>');
				dataArr.push('</li>');
			}
			dataStr = dataArr.join("");
			if(l == 0){
				$("#"+id+"ListBody>ul").hide();
				$("#"+id+"ListBody>div").html('<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>当前没有交易记录哦，快点做点什么吧~</div>').show();
				$("#"+id+"ListPage").hide();
			}else{
				$("#"+id+"ListBody>div").hide();
				$("#"+id+"ListBody>ul").html(dataStr).show();
				$("#"+id+"ListPage").show();
			}
			pagePause = 0;
			//分页设置
			if(page < 2){
				$("#"+id+"ListPage").jPages({
					containerID : ""+id+"ListBody",
					clickStop   : true,
					first: false,
			        previous: " ",
			        next: " ",
			        last: false,
					perPage	: 6,
					allSumPage : allSumPage,
					callback: ajaxPageData_draw
				});
			}
		},
		error: function(){
			console.log("获取提出记录列表数据异常");
		}
	});
}
/**
 * 获取收支明细列表
 * @param tradeType 交易类型
 * @param tradeStartTime 交易开始时间
 * @param tradeEndTime 交易结束时间
 * @param id 与tab标签对应的tab body的id
 */
function getSuccessList(tradeType, tradeStartTime, tradeEndTime, id, page){
	page = page ? page : 1;
	$.ajax({
		url: path + "/bill/getUserBill.html",
		type: "post",
		dataType: "json",
		data: {
				"tradeType": tradeType,
				"tradeStartTime": tradeStartTime,
				"tradeEndTime": tradeEndTime,
				"page": page,
				"rows": 6
//				"tradeTypeFlag" :"tradeTypeFlag"
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var allSumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			var tData = data["msg"]["rows"], l = tData.length, dataArr = [], dataStr = '';
			
			for(var i=0;i<l;i++){
				dataArr.push('<li>');
				if(tData[i]["tradeTypeName"]){
					dataArr.push('<p style="width:25%">'+tData[i]["tradeTypeName"]+'</p>');
				}else{
					dataArr.push('<p style="width:25%">'+tData[i]["tradeDirectionName"]+'</p>');
				}
				dataArr.push('<p style="width:25%">'+Number(tData[i]["displayAmt"]).toFixed(2)+companyCode+'</p>');
				dataArr.push('<p style="width:25%">'+tData[i]["tradeTime"]+'</p>');
				dataArr.push('<p style="width:25%">');
				dataArr.push('<a tid="'+tData[i]["id"]+'" ttype="success" onclick="getBonusMon(event)" >详情</a>');
				if(tData[i]["tradeType"]=="recharge_unFreeze"||tData[i]["tradeType"]=="withdraw"){
					dataArr.push('<a target="_blank" href="'+path+'/common/pdfTrade.html?tradeId='+tData[i]["tradeId"]+'&type='+tData[i]["tradeType"]+'" >查看合同</a>');	
				}
				dataArr.push('</p>');
				dataArr.push('</li>');
			}
			dataStr = dataArr.join("");
			if(l == 0){
				$("#"+id+"ListBody>ul").hide();
				$("#"+id+"ListBody>div").html('<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>当前没有交易记录哦，快点做点什么吧~</div>').show();
				$("#"+id+"ListPage").hide();
			}else{
				$("#"+id+"ListBody>div").hide();
				$("#"+id+"ListBody>ul").html(dataStr).show();
				$("#"+id+"ListPage").show();
			}
			pagePause = 0;
			//分页设置
			if(page < 2){
				$("#"+id+"ListPage").jPages({
					containerID : ""+id+"ListBody",
					clickStop   : true,
					first: false,
			        previous: " ",
			        next: " ",
			        last: false,
					perPage	: 6,
					allSumPage : allSumPage,
					callback: ajaxPageData_bill
				});
			}
		},
		error: function(){
			console.log("获取交易明细列表数据异常");
		}
	});
}



//获取收支明细列表-交易成功-分页回调函数
function ajaxPageData_bill(obj){
	if(pagePause == 0){
		return false;
	}
	var vname = "success";
	var status = $("#"+vname+"_tradeType").val(), tradeStartTime = $("#dateStart").val(),
		tradeEndTime = $("#dateEnd").val();
	getSuccessList(status, tradeStartTime, tradeEndTime, vname, obj["current"]);
}
//提出记录列表-交易中-分页回调函数
function ajaxPageData_draw(obj){
	if(pagePause == 0){
		return false;
	}
	var vname = "process";
	var status = $("#"+vname+"_tradeType").val(), tradeStartTime = $("#dateStart").val(),
		tradeEndTime = $("#dateEnd").val();
	getProcessList(status, tradeStartTime, tradeEndTime, vname, obj["current"]);
}
//提出记录列表-交易失败-分页回调函数
function ajaxPageData_fail(obj){
	if(pagePause == 0){
		return false;
	}
	var vname = "fail";
	var status = $("#"+vname+"_tradeType").val(), tradeStartTime = $("#dateStart").val(),
		tradeEndTime = $("#dateEnd").val();
	getFailList(status, tradeStartTime, tradeEndTime, vname, obj["current"]);
}

//筛选按钮事件，查询当前标签页结果
function searchList(){
	var id = $("#tradeTab a.a_home").attr("name");//当前选中tab标签
	var type = $("#" + id + "_tradeType").val(), startTime = $("#dateStart").val(),
		endTime = $("#dateEnd").val();
	if(id == "success"){
		getSuccessList(type, startTime, endTime, id);
	}else if(id == "process"){
		getProcessList(type, startTime, endTime, id);
	}else if(id == "fail"){
		getFailList(type, startTime, endTime, id);
	}
}
//查看详情
function getBonusMon(event){
	event.stopPropagation();//阻止冒泡
	var obj = event.srcElement || event.target;
	var tid = $(obj).attr("tid");
	var ttype = $(obj).attr("ttype");
	var vurl;
	if(ttype == "recharge"){
		vurl = path + "/crowdfundUserCenter/getUserRechargeDetail.html";
	}else if(ttype == "withdraw"){
		vurl = path + "/crowdfundUserCenter/getUserWithDrawDetail.html";
	}else{
		vurl = path + "/bill/getUserBillDetail.html";
	}
	var al = (cv["winW"]-500)/2, at = (cv["winH"]-200)/2;
	$(".sbgpop").show();
	$("#bonusPop").css({"left":al+"px", "top":at+"px"}).fadeIn();
	$("#qxBtu").click(function(){
		$("#bonusPop").fadeOut();
		$(".sbgpop").hide();
		AlertDialog.hide();
	});
	$.ajax({
		url: vurl,
		type: "post",
		dataType: "json",
		data: {
			"id":tid
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			if(ttype == "recharge"){
				$("#tradeTypeName").text("充入");
				$("#tradeAmt").text(data["msg"]["rechargeAmt"].toFixed(2));
				$("#tradeTime").text(data["msg"]["createTime"]);
				if(data["msg"]["financialOpinion"]){
					$("#tradeDetail").text(data["msg"]["financialOpinion"]);
				}else if(data["msg"]["operatorOpinion"]){
					$("#tradeDetail").text(data["msg"]["operatorOpinion"]);
				}else{
					$("#tradeDetail").text("--");
				}
			}else if(ttype == "withdraw"){
				$("#tradeTypeName").text("提出");
				$("#tradeAmt").text(data["msg"]["amt"].toFixed(2));
				$("#tradeTime").text(data["msg"]["applyTime"]);
				if(data["msg"]["financialOpinion"]){
					$("#tradeDetail").text(data["msg"]["financialOpinion"]);
				}else if(data["msg"]["operatorOpinion"]){
					$("#tradeDetail").text(data["msg"]["operatorOpinion"]);
				}else{
					$("#tradeDetail").text("--");
				}
			}else{
				$("#tradeTypeName").text(data["msg"]["tradeTypeName"]);
				$("#tradeAmt").text(data["msg"]["amt"].toFixed(2));
				$("#tradeTime").text(data["msg"]["tradeTime"]);
				if(data["msg"]["detail"]){
					$("#tradeDetail").text(data["msg"]["detail"]);
				}else{
					$("#tradeDetail").text("--");
				}
			}
		},
		error: function(){
			console.log("获取交易明细列表数据异常");
		}
	});
}