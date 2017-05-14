$(function(){
	showConcernIndustryList();//投资领域
	ltgt2Search();//领投查询条件查询
	showApproveList("", "", 1);//认证领头人展示
	$("#go2RZ").click(function(){
		if(siteUserId == "null"){ //如果未登录
			go2Login();
		}else{
			window.location.href = path + "/common/centerRZ.html";
		}
	});
});

//领投跟投条件查询
function ltgt2Search(){
	$("#titleApprove").find("a").click(function(){
		$(this).parent().siblings().removeClass("on");
		$(this).parent().addClass("on");
		showApproveList($("#concernIndustryUl li.on a").attr("vval"), $(this).attr("code"), 1);
	});
}
function showApproveList(concernIndustry, userLevel, page){
	$.ajax({
		url: path + "/crowdfundingInvest/getInvestor.html",
		type: "post",
		dataType: "json",
		data: {
			concernIndustry:concernIndustry,
			userLevel:userLevel,
			rows: 15,
			page: page
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var oArr = [], oStr = '', l, sumPage = (data["msg"]["total"]%15 == 0) ? data["msg"]["total"]/15 : Math.floor(data["msg"]["total"]/15) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			for(var i=0;i<l;i++){
				if(i%3==0){
					oArr.push('<div class="ltdw"><ul>');
				}
				oArr.push('<li><div class="ltdw_f">');
				if(data[i]["photo"]){
					oArr.push('<img class="imgC" style="width:100px;height:100px;border-raidus:50%;" src="'+cv["fileAddress"]+data[i]["photo"]+'" />');
				}else{
					oArr.push('<img class="imgC" style="width:100px;height:100px;border-raidus:50%;" src="'+path+'/images/defaultPhoto.png" />');
				}
				oArr.push('<div><span title="'+data[i]["companyName"]+'">'+data[i]["companyName"]+'</span>');
				if(data[i]["provinceName"]){
					oArr.push('<span><img src="'+path+'/images/zuob.png" />'+data[i]["provinceName"]+'</span>');
				}else{
					oArr.push('<span><img src="'+path+'/images/zuob.png" />--</span>');
				}
				if(data[i]["userLevel"] == "lead"){ //如果是领投人
					oArr.push('<span style="font-size:15px;color:#104267;">认证领投人</span>');
				}
				oArr.push('</div>');
				if(data[i]["userLevel"] == "lead"){ //如果是领投人
					oArr.push('<div style="float:right;margin:25px 20px 0 0;"><a href="'+path+'/common/leadDetail.html?luserId='+data[i]["userId"]+'" style="text-decoration:underline;color:#20B3F6;">进入</a></div>');
				}
				oArr.push('</div>');
				oArr.push('<div class="ltdw_s clear">');
				oArr.push('<span>投资项目<br/><i>'+data[i]["supportNum"]+'</i></span>');
				oArr.push('<span>约谈项目<br/><i>'+data[i]["interviewLoanNum"]+' </i></span>');
				if(data[i]["userLevel"] == "lead"){
					oArr.push('<span>跟投人<br/><i>'+data[i]["leadNum"]+'</i></span>');
				}else{
					oArr.push('<span>预约项目<br/><i>'+data[i]["preSupportNum"]+'</i></span>');
				}
				oArr.push('</div>');
				if(data[i]["userLevel"] != "lead"){ //如果是领投人
					oArr.push('<div class="lt_mb" style="display:none;"></div>');
					oArr.push('<div class="lt_mbbox" style="display:none;">');
					oArr.push('<h3>简介</h3>');
					if(data[i]["selfDetail"]){
						oArr.push('<p style="max-height:60px;overflow:hidden;" title="'+data[i]["selfDetail"]+'">'+data[i]["selfDetail"]+'</p>');
					}else{
						oArr.push('<p style="max-height:60px;overflow:hidden;" >--</p>');
						
					}
					oArr.push('<h3>熟悉领域</h3>');
					oArr.push('<p>'+data[i]["concernIndustry"]+'</p>');
					oArr.push('</div>');
				}
				oArr.push('</li>');
				if(i%3==2){
					oArr.push('</ul></div>');
				}
			}
			oStr= oArr.join("");
			$("#ltdw").html(oStr);
			//鼠标上移显示遮盖层
			$("#ltdw").find("li").mouseover(function(){
				$(this).find("div.lt_mb").show();
				$(this).find("div.lt_mbbox").show();
			}).mouseout(function(){
				$(this).find("div.lt_mb").hide();
				$(this).find("div.lt_mbbox").hide();
			});
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#approvePage").jPages({
					containerID : "ltdw",
					clickStop   : true,
					perPage	: 15,
					allSumPage : sumPage,
					callback: pageData
				});
			}
			
		},
		error: function(request){
			console.log("获取个人中心信息列表异常");
		}
	});
}

function showConcernIndustryList(){
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {
			typeCode:'concernIndustry'
		},
		success: function(data){
			if(data==null){
				return false;
			}
			var oArr = [], oStr = '', l;
			l = data.length;
			oArr.push('<li class="on"><a href="javascript:void(0);" vval="">全部</a></li>');
			for(var i=0;i<l;i++){
				oArr.push('<li><a href="javascript:void(0);" vval="'+data[i]["displayName"]+'" id="'+data[i]["displayName"]+'">'+data[i]["displayName"]+'</a></li>');
			}
			oStr= oArr.join("");
			$("#concernIndustryUl").html(oStr);
			$("#concernIndustryUl li").click(function(){
				$("#concernIndustryUl li").removeClass("on");
				$(this).addClass("on");
				$("#concernIndustry").val($(this).children("a").attr("vval"));
				showApproveList($("#concernIndustryUl li.on a").attr("vval") , $("#titleApprove li.on a").attr("code"), 1);
			});
		},
		error: function(request){
			console.log("获取个人中心信息列表异常");
		}
	});
}

function pageData(obj){
	if(pagePause == 0){
		return false;
	}
	showApproveList($("#concernIndustryUl li.on a").attr("vval"), $(this).attr("code"), obj["current"]);
}




