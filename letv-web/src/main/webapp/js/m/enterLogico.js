var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.length);
var logicoNum = 1;
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	getLogicoList(logicoNum);
});

//获取支持列表,录入物流信息
function getLogicoList(page){
	var lArr = [], lStr = '', l, sumPage;
	$.ajax({
		url: path + "/crowdfunding/getSupportList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo,
			"state": "sl",
			"page": page,
			"rows": 5
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length,total = data["msg"]["total"];
			data = data["msg"]["rows"];
			if(l == 0){
				lStr = '<span style="color:red;display:inline-block;padding:10px;">暂无待发货数据</span>' ;
				$("#startData>div").html(lStr).show();
				return false;
			}
			for(var i=0;i<l;i++){
				lArr.npush('<ul>');
				lArr.npush('<li><span>支持者：</span>#0</li>',[data[i]["supportUser"]]);
				lArr.npush('<li><span>支持金额：</span>￥#0</li>',[data[i]["supportAmt"]]);
				lArr.npush('<li><span>回报项：</span>#0</li>',[data[i]["backContent"]]);
				lArr.npush('<li><span>收货人：</span>#0</li>',[data[i]["postUser"]]);
				lArr.npush('<li><span>收货人手机：</span>#0</li>',[data[i]["postMobile"]]);
				lArr.npush('<li><span>地址：</span>#0#1#2</li>',[data[i]["provinceName"],data[i]["cityName"],data[i]["adressDetail"]]);
				$.ajax({
					url: path + "/letter/getPrivateLetterList.html",
					type: "post",
					dataType: "json",
					async: false,
					success: function(adata){
						if(!adata["success"]){
							return false;
						}
						var ll = adata["msg"]["rows"].length, ldata = adata["msg"]["rows"];
						for(var j=0;j<ll;j++){
							if(ldata[j]["loanNo"] == data[i]["loanNo"] && ldata[j]["sendUser"] == data[i]["supportUser"]){
								lArr.npush('<li><span>发货留言：</span>#0</li>',[ldata[j]["sendContent"]]);
								break;
							}
						}
					},
					error: function(request){
						console.log("获取异常");
					}
				});
				if(data[i]["state"] != "sending"){
					lArr.npush('<li class="caozuo"><a eid="'+data[i]["id"]+'" href="javascript:void(0);" onclick="showEnterBox(event);">录入物流信息</a></li>');
				}else{
					lArr.npush('<li class="caozuo"><a href="javascript:void(0);" style="cursor:default;">已发货</a></li>');
				}
				lArr.npush('</ul>');
			}
			lStr = lArr.join("");
			$("#startData").html(lStr).show();
			$("#startPage").show();
			//判断如果已经没有可加载的项目，隐藏查看更多按钮
			if($("#startData ul").length == total){
				$("#allMoreList").remove();
			}else{
				$("#allMoreList").show();
			}
		},
		error: function(request){
			console.log("获取支持列表异常");
		}
	});
}
//分页回调函数
//function ajaxPageData(obj){
//	if(pagePause == 0){
//		return false;
//	}
//	getLogicoList(obj["current"]);
//}
//展示录入物流信息弹出框
function showEnterBox(event){
	var obj = event.srcElement || event.target;
	var bid = $(obj).attr("eid");
	var ol = (cv["winW"]-420)/2, lt = (cv["winH"]-190)/2;
	$("#bgpop").show();
	if($("a[eid=\""+bid+"\"]").parent().children().hasClass('.ylr')){
		$("#logicoNo").val("");
		$("#logicoComp").val("");
	}
	$("#enterDiv").css({"left":"2%", "top":lt}).show();
	$("#logicoNo").blur(function(){
		isNull($(this).val(), $(this).attr("id"),10,120);
	});
	$("#logicoComp").blur(function(){
		isNull($(this).val(), $(this).attr("id"),10,120);
	});
	//确定录入按钮事件
	$("#addLogicoBtn").unbind("click").click(addLogico);
	//取消录入按钮事件
	$("#canelLogicoBtn").click(function(){
		AlertDialog.hide();
		$("#bgpop").hide();
		$("#enterDiv").hide();
	});
	$("#bgpop").click(function(){
		AlertDialog.hide();
		$(this).hide();
		$("#enterDiv").hide();
	});
	//确定添加
	function addLogico(){
		$("a[eid=\""+bid+"\"]").parent().find(".ylr").remove();
		$("a[eid=\""+bid+"\"]").parent().find(".ylr").next().remove();
		if(isNull($("#logicoNo").val(), "logicoNo",10,120)){
			if(isNull($("#logicoComp").val(), "logicoComp",10,120)){
				$.ajax({
					url: path + "/crowdfunding/updateSendInfo.html",
					type: "post",
					dataType: "json",
					data: {
						"id": bid,
						"sendOrderId": $("#logicoNo").val(),
						"sendDelivery": $("#logicoComp").val()
					},
					success:function(data){
						$(".bgpop").hide();
						$("#enterDiv").hide();
//						$("a[eid=\""+bid+"\"]").text("已录入").removeAttr("onclick").css({"color":"#104267", "text-decoration":"none"});
						$("a[eid=\""+bid+"\"]").before("<a href='javascript:void(0);' class='ylr'>已录入&nbsp;</a>");
						$("a[eid=\""+bid+"\"]").text("修改");
					},
					error: function(request){
						console.log("录入物流信息异常");
					}
				});
			}
		}
	}
}

//检测为空数据
function isNull(str, id,top,left){
	var reg = /^\s+$/;
	var result  = reg.test(str);
	if(!str || result ){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, top, left);
		$("#" + id).css("border", "1px solid red");
		return false;
	}
	str = str.replace(/^\s\s*/g, '').replace(/\s\s*$/g, '');//去掉收尾空格
	$("#" + id).css("border", "1px solid #CCC");
	return true;
}
