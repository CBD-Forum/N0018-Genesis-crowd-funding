var param = window.location.search;
var LoanType = "", loanName = "", nodeType="";
if(param){
	var base64 = new Base64();
	LoanType = getQueryString('type');
	nodeType = getQueryString('nodeType');
	loanName = base64.decode(getQueryString('loanName'));
}
$(function(){
	searchType();//通过条件查询项目列表信息
});
//通过点击查询条件查询项目列表
function searchType(){
	$.each($("#searchType").find("a"),function(k, v){
		$(v).click(function(){
			loanName ='';
			$(this).parent().find("a").removeClass("cur");
			$(this).addClass("cur");
			getProjectList(1);
		});
	});
	//获取url内容，筛选列表
	if(LoanType){
		$("#searchType").find("a[code='"+LoanType+"']").click();
	}else{
		getProjectList(1); //查询项目列表
	}
}
/**
 *   查询项目列表
 */
function getProjectList(page){
	var loanType = "", loanProcess = "", sort = "defaultSort";
	loanType = $("#loanType").find("a.cur").attr("code");
	loanProcess = $("#loanProcess").find("a.cur").attr("code");
	if(loanType == "public"){
		$("#loanProcess").find("a[code='preheat']").hide();
		if(loanProcess == "preheat"){
			$("#loanProcess").find("a[code='']").addClass("cur");
		}
	}else{
		$("#loanProcess").find("a[code='preheat']").show();
	}
	//如果选择了热门项目
	if($("#loanProcess").find("a[code='hot']").hasClass("cur")){
		loanProcess = "";
		sort = "hot";
	}else{
		loanProcess = $("#loanProcess").find("a.cur").attr("code");
		sort = $("#sort").find("a.cur").attr("code");
	}
	var lArr = [], lStr = '', l, sumPage;
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType": loanType,
			"loanProcess": loanProcess,
			"loanName":loanName,
			"sort": sort,
			"order":"desc",
			"page": page,
			"rows": 12
		},
		success: function(data){
			if(!data["success"]){
				lStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png"><br/>还没有项目记录哦，先去浏览其他项目吧~<br/><a href="'+path+'/common/projectList.html">查看全部项目</a><a href="'+path+'/common/index.html">返回首页</a></div>';
				$("#projectList>ul").html(lStr);
				$("div.letvPage").hide();
				return false;
			}else{
				sumPage = (data["msg"]["total"]%12 == 0) ? data["msg"]["total"]/12 : Math.floor(data["msg"]["total"]/12) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					lStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png"><br/>还没有项目记录哦，先去浏览其他项目吧~<br/><a href="'+path+'/common/projectList.html">查看全部项目</a><a href="'+path+'/common/index.html">返回首页</a></div>';
					$("#projectList>ul").html(lStr);
					$(".letvPage").hide();
					return false;
				}else{
					for(var i=0;i<l;i++){
						if((i+1)%4==0){
							lArr.push('<div class="productBenefit mag">');
						}else{
							lArr.push('<div class="productBenefit">');
						}
						lArr.push('<div class="Benefit-follow" follow="'+data[i]["loanNo"]+'" onclick="attentionLoan(this)">');
				        if(data[i]["attentionFlag"]){
				        	lArr.push('<div class="div2"><i class="red"></i><span>'+data[i]["attentionNum"]+'</span></div>');
				    	}else{
				    		lArr.push('<div class="div2"><i></i><span>'+data[i]["attentionNum"]+'</span></div>');
				    	}
				        lArr.push('<div class="div1"></div>');
				        lArr.push('</div>');
				        lArr.push('<div class="productBenefit-img">');
				        if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "product" || data[i]["loanType"] == "public"){ //如果是产品项目
				        	lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
				        }else{
				        	lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
						}
				    	
				        lArr.push('<img src="'+cv["fileAddress"]+'/'+data[i]["loanLogo"]+'">');
				        lArr.push('</a>');
				        lArr.push('</div>');
				        lArr.push('<div class="benefit-data">');
				        
				        if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "product" || data[i]["loanType"] == "public"){ //如果是产品项目
				        	lArr.push('<h3 class="benefit-tit"><a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'" title="'+data[i]["loanName"]+'">'+data[i]["loanName"]+'</a></h3>');
				        }else{
				        	lArr.push('<h3 class="benefit-tit"><a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" title="'+data[i]["loanName"]+'">'+data[i]["loanName"]+'</a></h3>');
						}
				        if(data[i]["loanIntroduction"] && data[i]["loanIntroduction"].length>18){
				        	lArr.push('<p class="benefit-introduce" title="'+data[i]["loanIntroduction"]+'">'+data[i]["loanIntroduction"].substring(0,18)+'...</p>');
				    	}else{
				    		lArr.push('<p class="benefit-introduce">'+data[i]["loanIntroduction"]+'</p>');
				    	}
				        lArr.push('<div class="benefit-addressType">');
				        lArr.push('<span class="fr address">'+data[i]["loanProvinceName"]+'</span>');
				        if(data[i]["superIndustryName"]){
				        	lArr.push('<span class="fl type">'+data[i]["superIndustryName"]+'</span>');
				        }else{
				        	lArr.push('<span class="fl type">'+data[i]["loanTypeName"]+'</span>');
				        }
				        lArr.push('</div>');
				        lArr.push('<div class="benefit-setbacks">');
				        lArr.push('<span class="span1"></span>');
				        if(data[i]["supportRatio"]*100 > 100){
				        	lArr.push('<span class="span2" style=" width:100%;"></span>');
						}else{
							lArr.push('<span class="span2" style=" width:'+(data[i]["supportRatio"]*100).toFixed(2)+'%;"></span>');
						}
				        if(data[i]["supportRatio"]*100<=20){
				        	var supportRatio = 0;
				        }else if(data[i]["supportRatio"]*100>20 && data[i]["supportRatio"]*100<=100){
				        	var supportRatio = (data[i]["supportRatio"]*100-20).toFixed(2);
				        }else if(data[i]["supportRatio"]*100>100){
				        	var supportRatio = 80;
				        }else{
				        	var supportRatio = 0;
				        }
				        if(data[i]["supportRatio"]){
				        	lArr.push('<span class="span3" style="left:'+supportRatio+'%">'+(data[i]["supportRatio"]*100).toFixed(2)+'%</span>');
					        
				        }else{
				        	lArr.push('<span class="span3" style="left:'+supportRatio+'%">0%</span>');
					        
				        }
				        
				        lArr.push('</div>');
				        lArr.push('<ul class="benefit-ul clearfix">');
//				        if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
//							remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
//						}else{
//							remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
//						}
				        remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
				        if(data[i]["loanType"] == "product"){ //如果是产品项目
				        	if(data[i]["loanState"] == "preheat"){
				        		lArr.push('<li><em>'+data[i]["praiseNum"]+'人</em>点赞人数</li>');
				        	}else{
				        		lArr.push('<li><em>'+data[i]["supportNum"]+'人</em>支持人数</li>');
				        	}
				        	if(data[i]["approveAmt"]>9999){
					        	var numA = (data[i]["approveAmt"]/10000).toFixed(2);
					            //var numM = numA.substring(0,numA.indexOf(".") + 0);
				        		lArr.push('<li><em>'+numA+'万</em>融资金额</li>');
				        	}else{
				        		lArr.push('<li><em>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</em>融资金额</li>');
				        	}
					        
					        if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
					        	lArr.push('<li class="bak" ><em>0天</em>剩余天数</li>');
							}else{
								if(data[i]["loanState"] == "preheat"){
					        		lArr.push('<li><em>'+data[i]["attentionNum"]+'人</em>收藏人数</li>');
					        	}else{
					        		lArr.push('<li class="bak" ><em>'+remainDays+'天</em>剩余天数</li>');
					        	}
							}
				        }else if(data[i]["loanType"] == "stock"){
				        	if(data[i]["stockPartAmt"]){
				        		if(data[i]["stockPartAmt"]>9999){
						        	var numB = (data[i]["stockPartAmt"]/10000).toFixed(2);
						            //var numM = numA.substring(0,numA.indexOf(".") + 0);
					        		lArr.push('<li><em>'+numB+'万</em>起投金额</li>');
					        	}else{
					        		lArr.push('<li><em>'+data[i]["stockPartAmt"].toFixed(2)+companyCode+'</em>起投金额</li>');
					        	}
				        		//lArr.push('<li><em>'+data[i]["stockPartAmt"].toFixed(2)+companyCode+'</em>起投金额</li>');
				        	}else{
				        		lArr.push('<li><em>0'+companyCode+'</em>起投金额</li>');
				        	}
				        	if(data[i]["approveAmt"]>9999){
					        	var numC = (data[i]["approveAmt"]/10000).toFixed(2);
					            //var numM = numA.substring(0,numA.indexOf(".") + 0);
				        		lArr.push('<li><em>'+numC+'万</em>已筹金额</li>');
				        	}else{
				        		lArr.push('<li><em>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</em>已筹金额</li>');
				        	}
					        //lArr.push('<li><em>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</em>已筹金额</li>');
					        if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
					        	lArr.push('<li class="bak"><em>0天</em>剩余天数</li>');
							}else{
								if(data[i]["loanState"] == "preheat"){
					        		lArr.push('<li><em>'+data[i]["attentionNum"]+'人</em>收藏人数</li>');
					        	}else{
					        		lArr.push('<li class="bak" ><em>'+remainDays+'天</em>剩余天数</li>');
					        	}
							}
					    }else if(data[i]["loanType"] == "public"){
					    	lArr.push('<li><em>'+data[i]["supportNum"]+'个</em>支持人数</li>');
					        if(data[i]["approveAmt"]>9999){
					        	var numA = (data[i]["approveAmt"]/10000).toFixed(2);
					            //var numM = numA.substring(0,numA.indexOf(".") + 0);
					        	lArr.push('<li><em>'+numA+'万</em>已筹金额</li>');
				        	}else{
				        		lArr.push('<li><em>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</em>已筹金额</li>');
				        	}
					        if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
					        	lArr.push('<li class="bak"><em>0天</em>剩余天数</li>');
							}else{
								lArr.push('<li class="bak"><em>'+remainDays+'天</em>剩余天数</li>');
							}
					    }
				        
				        
				        lArr.push('</ul>');
				        lArr.push('</div>');
				        lArr.push('</div>');
				    
						
					}
					lStr = lArr.join("");
					$("#projectList>ul").html(lStr);
					$("div.letvPage").show();
					
					//分页设置
					pagePause = 0;
					if(page < 2){
						$("div.letvPage").jPages({
							
							containerID : "projectList",
							first:false,
							last:false,
							previous:" ",
							next:" ",
							clickStop   : true,
							perPage	: 12,
							allSumPage : sumPage,
							callback: ajaxPageData
						});
					}
				}
			}
		},
		error: function(request){
			console.log("获取项目列表异常");
		}
	});
}
//分页回调函数
function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getProjectList(obj["current"]);
}
//收藏事件
function attentionLoan(_this){
	if(siteUserId == "null"){
		//AlertDialog.tip("您还没有登录，请登录！");
		AlertDialog.confirm(go2Login,null,"您还没有登录，请先登录","登录","取消",null);
		return false;
	}
	var loanNo = $(_this).attr("follow");
	$.ajax({
		url: path + "/crowdfunding/attentionLoan.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				AlertDialog.confirm(DelAttentionLoan, null, "您确定要取消收藏吗？", "确定", "取消", loanNo);
				return false;
			}else{
				$(_this).find("div.div2>span").text(Number($(_this).find("div.div2>span").text())+1);
				AlertDialog.tip("收藏成功");
				$(_this).find("i").addClass("red");
			}
		},
		error: function(request){
			console.log("收藏项目异常");
		}
	});
	function DelAttentionLoan(){
		$.ajax({
			url: path + "/crowdfunding/cancelAttention.html",
			type: "post",
			dataType: "json",
			data: {"loanNo": loanNo},
			success: function(data){
				if(data["success"]){
					$(_this).find("div.div2>span").text(Number($(_this).find("div.div2>span").text())-1);
					AlertDialog.tip("取消成功");
					
					$(_this).find("i").removeClass("red");
				}
			},
			error: function(request){
				console.log("收藏项目异常");
			}
		});
	}
}
