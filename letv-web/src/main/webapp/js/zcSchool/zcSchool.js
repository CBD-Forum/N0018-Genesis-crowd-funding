$(function(){
	showBanner();//展示banner图
	getClasslist();//查询课程安排
	getZczsList("crowdfund_know"); //众筹知识列表
	getHyzxList("industry_news"); //行业资讯列表
	getTeacherList(); //明星导师列表
});
//查询课程安排
function getClasslist(){
	var cArr = [], cStr = '', l,iArr=[],iStr='';
	$.ajax({
		url: path + "/coursePlan/list.html",
		type: "post",
		dataType: "json",
		success: function(data){
			l = data["rows"].length, list = data["rows"];
			for(var i=0;i<l;i++){
				var startDate =list[i]["startDate"].substring(0,4)+"."+list[i]["startDate"].substring(5,7)+"."+list[i]["startDate"].substring(8,10);
				//var endDate = list[i]["endDate"].substring(0,4)+"."+list[i]["endDate"].substring(5,7)+"."+list[i]["endDate"].substring(8,10);
				cArr.push('<li id="n'+i+'"><a href="javascript:void(0);">'+startDate+'<br /><span>（'+list[i]["courseName"]+'）</span></a></li>');
				
				iArr.push('<ul id="n'+i+'Body" class="zcxy_cont clearfix">');
				iArr.push('<div class="zcxy_ttle">讲师简介：</div>');
				iArr.push('<li class="li_left">');
				iArr.push('<div class="left_img"><img src="'+cv["fileAddress"]+list[i]["teacherLogo"]+'" width="260"/></div>');
				iArr.push('<p>'+list[i]["teacherName"]+'<br /><span>( '+list[i]["teacherTitle"]+')</span></p>');
				iArr.push('<a href="javascript:void(0);" planid="'+list[i]["id"]+'" onclick="enroll(event)" class="but_bm">预约报名</a>');
				iArr.push('</li>');
				iArr.push('<li class="li_mid">');
				iArr.push('<span>讲师资料</span>');
				var des = list[i]["teacherDes"].length;
				var str = "";
				if(des > 310){
					str = list[i]["teacherDes"].substring(0,310)+"...";
				}else{
					str = list[i]["teacherDes"]?list[i]["teacherDes"]:"暂无简介信息";
				}
				iArr.push('<p>'+str+'</p>');
				iArr.push('</li>');
				iArr.push('<li class="li_right">');
				iArr.push('<span>课程简介</span>');
				var des2 = list[i]["courseDes"].length;
				var str2 = "";
				if(des2 > 410){
					str2 = list[i]["courseDes"].substring(0,410)+"...";
				}else{
					str2 = list[i]["courseDes"]?list[i]["courseDes"]:"暂无简介信息";
				}
				iArr.push('<p>'+str2+'</p>');
				iArr.push('</li>');
				iArr.push('</ul>');
			}
			cStr = cArr.join("");
			iStr = iArr.join("");
			$("#courseTitle").html(cStr);
			$("#teacherInfo").html(iStr);
			
			var $tab = $("#courseTitle li");
			$.each($tab,function(k,v){
				$(v).click(function(){
					$tab.find("a").removeClass("a_home");
					$(v).children("a").addClass("a_home");
					$("#teacherInfo>ul").hide();
					$("#"+$(v).attr("id")+"Body").show();
				});
			});
			$tab.first().click();
			
			$("#mr_frbox").slide({
				titCell: "",
				mainCell: "#mr_frUl ul",
				autoPage: true,
				effect: "leftLoop",
				autoPlay: false,
				vis: 4
			});
		},
		error: function(request){
			console.log("查询课程安排列表异常");
		}
	});
}
//展示banner图片效果
function showBanner(){
	//$("#Banner").css({"margin-left":-ml+"px"});
	$.ajax({
		url: path + "/banner/getBannerByCode.html",
		type: "post",
		dataType: "json",
		data: {"code": "course_index"},
		success: function(data){
			var bArr = [], bStr = '', l = data.length;
			for(var i=0;i<l;i++){
				if(!data[i]["url"]){
					bArr.push('<li style="background:url('+cv.fileAddress + data[i]["picture"]+') no-repeat center center;height:341px;" title="'+data[i]["title"]+'">');
					//bArr.push('<img src="' + fileAddress + data[i]["picture"]+'" alt="'+data[i]["title"]+'" />');
				}else{
					bArr.push('<li>');
					bArr.push('<a href ="'+data[i]["url"]+'" target="_blank" style="background:url('+cv.fileAddress + data[i]["picture"]+') no-repeat center center;height:341px;" alt="'+data[i]["title"]+'">');
					//bArr.push('<img src="' + fileAddress + data[i]["picture"]+'" alt="'+data[i]["title"]+'" />');
					bArr.push('</a>');
				}
				bArr.push('</li>');
			}
			bStr = bArr.join("");
			$("#banner_pig").html(bStr);
			setTimeout(function(){
				$.focus("#index_pic");//调用banner幻灯片效果
			},500);
			
		},
		error: function(request){
			console.log("获取banner图片信息异常");
		}
	});
}

//预约报名
function enroll(event){
	if(siteUserId == "null" || siteUserId == null){ //未登录
		go2Login();
		return false;
	}
	var obj = event.scrElement || event.target;
	var planid = $(obj).attr("planid");
	$.ajax({
		url: path + "/courseOrder/list.html",
		type: "post",
		dataType: "json",
		data: {"coursePlanId": planid},
		success: function(data){
			if(data["total"] == 0 ){
				$.ajax({
					url: path + "/courseOrder/save.html",
					type: "post",
					dataType: "json",
					data: {"coursePlanId": planid},
					success: function(data){
						if(data["success"] == false){
							AlertDialog.tip(data["msg"]);
							return false;
						}else{
							AlertDialog.tip("预约成功");
						}
					},
					error: function(request){
						console.log("预约报名异常");
					}
				});
			}else{
				AlertDialog.tip("已成功预约");
				return false;
			}
		},
		error: function(request){
			console.log("查询预约异常");
		}
	});
	/*$.ajax({
		url: path + "/courseOrder/save.html",
		type: "post",
		dataType: "json",
		data: {"coursePlanId": planid},
		success: function(data){
			if(data["success"] == false){
				AlertDialog.tip(data["msg"]);
				return false;
			}else{
				AlertDialog.tip("预约成功");
			}
		},
		error: function(request){
			console.log("预约报名异常");
		}
	});*/
}
function getTeacherList(){
	var tArr = [],tStr="",l,list;
	$.ajax({
		url: path + "/course_teacher/list.html",
		type: "post",
		dataType: "json",
		data:{
			"page":1,
			"rows":4
		},
		success: function(data){
			l = data["rows"].length, list = data["rows"];
			if(l == 0){
				tStr = '<span style="padding:20px;color:#c30d23;">暂无数据</span>';
				$("#teacherList").html(tStr);
				return false;
			}
			for(var i =0;i<l;i++){
				tArr.push('<dl class="clearfix">');
				tArr.push('<dt><img src="'+cv["fileAddress"]+list[i]["teacherLogo"]+'" width="75"/></dt>');
				tArr.push('<dd>');
				tArr.push('<span><a href="'+path+'/common/zcTeacher.html">'+list[i]["teacherName"]+'</a></span>');
				tArr.push('<p>简介：</p>');
				var des = list[i]["teacherDes"].length;
				var str = "";
				if(des > 30){
					str = list[i]["teacherDes"].substring(0,30)+"...";
				}else{
					str = list[i]["teacherDes"]?list[i]["teacherDes"]:"暂无简介信息";
				}
				tArr.push('<p>'+str+'</p>');
				tArr.push('</dd>');
				tArr.push('</dl>');
			}
			tStr = tArr.join("");
			$("#teacherList").html(tStr);
		},
		error: function(request){
			console.log("获取明星导师列表异常");
		}
	});
}

//查询众筹知识
function getZczsList(code){
	var zArr = [],zStr="",l;
	$.ajax({
		type : "post",
		url : path + '/node/getNode.html',
		data : {
			"nodeType":code,
			"page":1,
			"rows":6
		},
		success : function(data) {
			if(!data["success"]){
				zStr = '<span style="padding:20px;color:#c30d23;">暂无数据</span>';
				$("#zczsList").html(zStr);
				return false;
			}
			l = data["msg"].length,list = data["msg"];
			for(var i=0;i<l;i++){
				if(i > 3){
					break;
				}
				zArr.push('<li><a title="'+list[i]["title"]+'" href="'+path+'/common/zcKnowDetail.html?id='+list[i]["id"]+'">'+list[i]["title"]+'</a></li>');
			}
			zStr = zArr.join("");
			$("#zczsList").html(zStr);
		},
		error:function(){
			
		}
	});
}
//行业资讯
function getHyzxList(code){
	var zArr = [],zStr="",l;
	$.ajax({
		type : "post",
		url : path + '/node/getNode.html',
		data : {
			"nodeType":code,
			"page":1,
			"rows":5
		},
		success : function(data) {
			if(!data["success"]){
				zStr = '<span style="padding:20px;color:#c30d23;">暂无数据</span>';
				$("#hyzxList").html(zStr);
				return false;
			}
			l = data["msg"].length,list = data["msg"];
			for(var i=0;i<l;i++){
				if(i > 3){
					break;
				}
				var createTime = list[i]["createTime"].substring(0,4)+"."+list[i]["createTime"].substring(5,7)+"."+list[i]["createTime"].substring(8,10);
				zArr.push('<li><p>'+createTime+'</p><a title="'+list[i]["title"]+'" href="'+path+'/common/zcKnowZXDetail.html?id='+list[i]["id"]+'">'+list[i]["title"]+'</a></li>');
			}
			zStr = zArr.join("");
			$("#hyzxList").html(zStr);
		},
		error:function(){
			
		}
	});
}