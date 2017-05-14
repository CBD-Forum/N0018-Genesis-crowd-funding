if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getUserInfo();
	
	$("#phone").blur(function(){
		if(!$(this).val()){
			AlertDialog.show("请输入手机号码", $(this).attr("id"), 0, 50);
			return false;
		}
		if(!Valify.phone($(this).val(), $(this).attr("id"))){
			AlertDialog.show("请输入正确的手机号码", $(this).attr("id"), 0, 50);
			return false;
		}else{
			AlertDialog.hide();
		}
	});
	$("#valicode").blur(function(){
		if(!$(this).val()){
			AlertDialog.show("请输入验证码", $(this).attr("id"), 0, 30);
			return false;
		}
		if(!Valify.valiCode($(this).val(), true, $(this).attr("id"))){
			AlertDialog.show("请输入正确的验证码", $(this).attr("id"), 0, 30);
			return false;
		}else{
			AlertDialog.hide();
		}
	});
	//发送邀请码
	$("#inviteBtn").unbind("click").click(sendInviteCode);

	var $tab = $("#invite span");
	$.each($tab,function(k,v){
		$(v).click(function(){
			$tab.removeClass("cur");
			$(this).addClass("cur");
			$("#inviteBody>div").hide();
			$("#"+$(v).attr("id")+"Body").show();
		});
	});
	$tab.first().click();
	$("#yqjg").click(function(){
		getInitDate();
		getInviteData(1, $("#dateStart").val(), $("#dateEnd").val());
		$("#inviteBtn2").click(function(){
			getInviteData(1, $("#dateStart").val(), $("#dateEnd").val());
		});
	});
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
});

function sendInviteCode(){
	if(Valify.phone($("#phone").val(), "phone")){
		AlertDialog.hide();
		if(Valify.valiCode($("#valicode").val(), false, "valicode")){
			AlertDialog.hide();
			$.ajax({
				url: path + "/verifycode/sendInviteCodeFundPool.html",
				type: "post",
				dataType: "json",
				data: {
					"mobile": $("#phone").val(),
					"valicode": $("#valicode").val()
				},
				success: function(data){
					if(data["success"]){
						$("#inviteBtn").unbind("click");
						countDown(60, "inviteBtn", function(){
							$("#inviteBtn").unbind("click").css({"background":" #1ea5ff","color":"#FFF"}).text("免费发送").click(sendInviteCode);
						});
						AlertDialog.tip("发送邀请码成功");
					}else{
						AlertDialog.tip(data["msg"]);
					}
				},
				error: function(request){
					console.log("获取邀请码异常");
				}
			});
		}else{
			AlertDialog.show("请输入正确的验证码", "valicode", 0, 30);
		}
	}else{
		AlertDialog.show("请输入正确的手机号码", "phone", 0, 50);
	}
}
//获取个人信息
function getUserInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			
			$("#recommendUrl").text("http://10.60.118.233:9000/letv-web/common/register.html?inviteCode="+data["user"]["mobile"]);
			//生成二维码
			$("#eweima").qrcode({ 
				render: "table", //table方式 
				width: 196, //宽度 
				height:196, //高度 
				text: $("#recommendUrl").text() //任意内容 
			});
			//查看短信内容
			$("#showInfo").click(function(){
				$("#agreeContent").html('我是 '+data["user"]["realName"]+' ，邀请你注册"乐视金融"，百度“乐视金融”，注册填写邀请码人手机号&nbsp;&nbsp;<span style="color:#ea5505;">'+data["user"]["mobile"]+'</span>【乐视金融】。');
				$("#bgpop").show();
				var al = (cv["winW"]-580)/2, at = (cv["winH"]-$("#agree_box").height())/2;
				$("#agree_box").css({"left":al+"px", "top":at+"px"}).show();
				$(".agree_close").click(function(){
					$("#agree_box").fadeOut();
					$("#bgpop").hide();
				});
			});
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

//获取默认显示的时间值
function getInitDate(){
	//获取前一个月时间展示
	$("#dateStart").val(myDate.getLastMonthYestdy());
	$("#dateEnd").val(myDate.getTodayDate());
}
/**
 * 获取邀请结果信息
 * @param createStartTime: 注册开始时间
 * @param createEndTime： 注册结束时间
 */
function getInviteData(page, createStartTime, createEndTime){
	$.ajax({
		url: path + "/invite/getInviteResult.html",
		type: "post",
		dataType: "json", 
		data: {
			"createStartTime": createStartTime,
			"createEndTime": createEndTime,
			"inviteUser":siteUserId,
			"page": page,
			"rows": 6
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var totalData = data["totalData"], rowsData = data["msg"]["rows"], rl = rowsData.length;
			var sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			var aArr = [], aStr = '';
			
			aArr.push('<li class="clearfix tab_tit">');
			aArr.push('<span class="wd1">用户昵称</span>');
			aArr.push('<span class="wd2">真实姓名</span>');
			aArr.push('<span class="wd3">手机号</span>');
			aArr.push('<span class="wd4">注册时间</span>');
			aArr.push('</li>');
			
			for(var i=0;i<rl;i++){
				aArr.push('<li class="clearfix">');
				aArr.push('<span class="wd1">'+rowsData[i]["nickName"]+'</span>');
				if(rowsData[i]["realName"]){
					aArr.push('<span class="wd2">'+rowsData[i]["realName"]+'</span>');
				}else{
					aArr.push('<span class="wd2">--</span>');
				}
				
				aArr.push('<span class="wd3">'+rowsData[i]["mobile"]+'</span>');
				aArr.push('<span class="wd4">'+rowsData[i]["registerTime"]+'</span>');
				aArr.push('</li>');
				
			}
			if(rl == 0){
				$("#inviteTable").html('<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br>抱歉，暂无数据哦~</div>');
				$(".letvPage").hide();
				$("#invitationNum").text(rl);
				return false;
			}
			aStr = aArr.join("");
			$("#inviteTable>ul").html(aStr);
			$("#invitationNum").text(rl);
			pagePause = 0;
			
			//分页设置
			if(page < 2){
				$("div.letvPage").jPages({
					containerID : "inviteTable",
					first:false,
					last:false,
					previous:" ",
					next:" ",
					clickStop   : true,
					perPage	: 6,
					allSumPage : sumPage,
					callback: ajaxPageData
				});
			}
		},
		error: function(request){
			console.log("获取邀请结果异常");
		}
	});
}
//分页回调函数
function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getInviteData(obj["current"], $("#dateStart").val(), $("#dateEnd").val());
}