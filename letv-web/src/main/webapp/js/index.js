$(function(){
	showBanner();//展示banner图
	getIndexLoanList("", "hot", "indexHotList"); //首页热门项目
	getIndexLoanList("product", "productSort", "indexFundingList"); //首页产品收益
	getIndexLoanList("public", "publicSort", "indexPreheatList"); //首页公益众筹 
	if(siteUserId != "null"){
		$("#bannerRegiter").hide();
	}
	showIndexNotice();
	
	getWebUserCount();//统计加入的小伙伴
});
//统计加入的小伙伴
function getWebUserCount(){
	$.ajax({
		url: path + "/user/getWebUserCount.html",
		type: "post",
		dataType: "json",
		data: {},
		success: function(data){
			if(data["success"]){
				var bArr = [], bStr = '';
				var Scont = data["count"].toString();
				bArr.push('<i>0</i><i>0</i><i>0</i><i>0</i>');
				for(var i = 0;i < Scont.length;i++){
					bArr.push('<i>'+Scont[i]+'</i>');
				}
				bStr = bArr.join("");
				$("#index_cont").html(bStr);
				var bl = (1130-$("#index_cont").width())/2;
				$("#index_cont").css({"left":bl+"px"});
			}else{
				return false;
			}
		},
		error: function(request){
			console.log("获取banner图片信息异常");
		}
	});
	$.ajax({
		url: path + "/statistics/getWebStatistics.html",
		type: "post",
		dataType: "json",
		data: {},
		success: function(data){
			$("#fundingLoanCount").text(data["fundingLoanCount"]);
			$("#succcessLoanAmt").text(data["succcessLoanAmt"]);
			$("#successLoanCount").text(data["successLoanCount"]);
			$("#userCount").text(data["userCount"]);
			$(".indexBom-none").click(function(){
				$(".indexBom").hide();
			});
		},
		error: function(request){
			console.log("获取banner图片信息异常");
		}
	});
}

//展示banner图片效果
function showBanner(){
	$.ajax({
		url: path + "/banner/getBannerByCode.html",
		type: "post",
		dataType: "json",
		data: {"code": "index"},
		success: function(data){
			var bArr = [], bStr = '', l = data.length;
			for(var i=0;i<l;i++){
				if(!data[i]["url"]){
					bArr.push('<li style="background:url('+cv.fileAddress + data[i]["picture"]+') no-repeat center center;height:460px;">');
					//bArr.push('<img src="' + fileAddress + data[i]["picture"]+'" alt="'+data[i]["title"]+'" />');
				}else{
					bArr.push('<li>');
					bArr.push('<a href ="'+data[i]["url"]+'" target="_blank" style="background:url('+cv.fileAddress + data[i]["picture"]+') no-repeat center center;height:460px;">');
					//bArr.push('<img src="' + fileAddress + data[i]["picture"]+'" alt="'+data[i]["title"]+'" />');
					bArr.push('</a>');
				}
				bArr.push('</li>');
			}
			bStr = bArr.join("");
			$("#banner_pig").html(bStr);
			$.focus("#index_pic");//调用banner幻灯片效果
		},
		error: function(request){
			console.log("获取banner图片信息异常");
		}
	});
	/*$.ajax({
		url: path + "/banner/getBannerByCode.html",
		type: "post",
		dataType: "json",
		data: {"code": "hzhb"},
		success: function(data){
			var bArr = [], bStr = '', l = data.length;
			bArr.push('<ul class="mechanismList clearfix">');
			for(var i=0;i<l;i++){
				
				if(!data[i]["url"]){
					bArr.push('<li><img src="'+cv.fileAddress + data[i]["picture"]+'"></li>');
				}else{
					bArr.push('<li><a href="'+data[i]["url"]+'" target="_blank" ><img src="'+cv.fileAddress + data[i]["picture"]+'"></a></li>');
				}
				if((i+1)%16==0){
					bArr.push('</ul>');
					bArr.push('<ul class="mechanismList clearfix">');
				}
				
			}
			bArr.push('</ul>');
			bStr = bArr.join("");
			$("#banner_hzhb").html(bStr);
			mechanismRoll();
		},
		error: function(request){
			console.log("获取banner图片信息异常");
		}
	});*/
}

/**
 * 获取首页的项目列表 ， 默认loanType为全部
 * loanProcess : 项目进程
 * sort: 排序
 * id: 预加载的html id
 */
function getIndexLoanList(loanType, sort, id){
	var iArr = [], iStr = '', l,tArr = [], tStr = '';
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType": loanType,
			//"loanState": "add",
			//"loanProcess": loanProcess,
			"sort": sort,
			"page": 1,
			"rows": 6
		},
		success: function(data){
			if(!data["success"]){
				iStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png"><br/>还没有项目记录哦，先去浏览其他项目吧~<br/><a href="'+path+'/common/enterProjectNav.html">我要发起众筹项目</a></div>';
				$("#" + id).html(iStr);
				return false;
			}else{
				var l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					iStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png"><br/>还没有项目记录哦，先去浏览其他项目吧~<br/><a href="'+path+'/common/enterProjectNav.html">我要发起众筹项目</a></div>';
					$("#" + id).html(iStr);
					return false;
				}else{
					if(loanType == ""){
						for(var i=0;i<l;i++){
							if(i<1){
								
								if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "product" || data[i]["loanType"] == "public"){ //如果是产品项目
									tArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
								}else{
									if(data[i]["loanType"] == "stock"){
										tArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
									}else{
										tArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
									}
								}
								
								tArr.push('<img src="'+cv["fileAddress"]+'/'+data[i]["loanLogo"]+'" class="img">');
								tArr.push('<div class="indexHot-mask"></div>');
								tArr.push('<div class="setbacks">');
								tArr.push('<div class="setbacks-bag"></div>');
						        tArr.push('<div class="setbacks-data">');
						        if(data[i]["supportRatio"]*100 >= 100){
							    	var supportRatio = 100
							    }else{
							    	var supportRatio = (data[i]["supportRatio"]*100).toFixed(2);
							    }
							    if(data[i]["supportRatio"]*100 <= 0){
							    	var supportRatioS = 0.00
							    }else if(data[i]["supportRatio"]*100 > 100){
							    	var supportRatioS = 100
							    }else{
							    	var supportRatioS = (data[i]["supportRatio"]*100).toFixed(2);
							    }
						        tArr.push('<div class="fr jd-cicle"><div class="invrate" style="overflow:hidden;height: 100px;"><font style="display:none;">'+supportRatio+'<em>%</em></font><span style="position:relative;display:inline-block;width:100px;text-align:center; height:100px; line-height:90px;font-size:24px;font-family: Arial;color:#44b4ff; left:0; top:-100px;">'+supportRatioS+'<em>%</em><i style="font-size: 14px;top: -65px;position: relative;display: block;font-family: 微软雅黑;">已达</i></span></div></div>');
						        tArr.push('</div>');
						        tArr.push('</div>');
						        tArr.push('<p><span>'+data[i]["loanName"]+'</span>'+data[i]["loanIntroduction"].substring(0,30)+'</p>');
						        tArr.push('</a>');
							}else{
								if(i%2==0){
									iArr.push('<div class="recommendDiv pad">');
								}else{
									iArr.push('<div class="recommendDiv">');
								}
								
								if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "product" || data[i]["loanType"] == "public"){ //如果是产品项目
									iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
								}else{
									if(data[i]["loanType"] == "stock"){
										iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
									}else{
										iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
									}
								}
								
								iArr.push('<img src="'+cv["fileAddress"]+'/'+data[i]["loanLogo"]+'" class="img">');
								iArr.push('<div class="indexHot-mask"></div>');
					            iArr.push('<div class="setbacks">');
					            iArr.push('<div class="setbacks-bag"></div>');
					            iArr.push('<div class="setbacks-data">');
					            if(data[i]["supportRatio"]*100 >= 100){
							    	var supportRatio = 100
							    }else{
							    	var supportRatio = (data[i]["supportRatio"]*100).toFixed(2);
							    }
							    if(data[i]["supportRatio"]*100 <= 0){
							    	var supportRatioS = 0.00
							    }else{
							    	var supportRatioS = (data[i]["supportRatio"]*100).toFixed(2);
							    }
					            iArr.push('<div class="fr jd-cicle"><div class="col_5" style="overflow:hidden;height: 70px;"><font style="display:none;">'+supportRatio+'<em>%</em></font><span style="position:relative;display:inline-block;width:70px;text-align:center; height:70px; line-height:60px;font-size:16px;font-family: Arial;color:#44b4ff; left:0; top:-70px;">'+supportRatioS+'<em>%</em><i style="font-size: 12px;top: -40px;position: relative;display: block;font-family: 微软雅黑;">已达</i></span></div></div>');
						        iArr.push('</div>');
					            iArr.push('</div>');
					            iArr.push('<p><span>'+data[i]["loanName"].substring(0,15)+'</span>'+data[i]["loanIntroduction"].substring(0,30)+'</p>');
					            iArr.push('</a>');
					            iArr.push('</div>');
							}
							
						}
						tStr = tArr.join("");
						$("#indexHotListTop").html(tStr);
						iStr = iArr.join("");
						$("#" + id).html(iStr);
						$("#scriptDiv").html('<script type="text/javascript" src="'+path+'/js/common/raphael.js"></script><script type="text/javascript" src="'+path+'/js/common/cycle.js"></script>');
					}else if(loanType == "product"){
						for(var i=0;i<l;i++){
							if(i<4){
								if(i>2){
									
									iArr.push('<div class="productBenefit mag">');
								}else{
									iArr.push('<div class="productBenefit">');
								}
								
								if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
									iArr.push('<div class="productBenefit-type bak">众筹成功</div>');
								}else{
									iArr.push('<div class="productBenefit-type">'+data[i]["loanStateName"]+'</div>');
								}
						    	iArr.push('<div class="Benefit-follow" follow="'+data[i]["loanNo"]+'" onclick="attentionLoan(this)">');
						    	if(data[i]["attentionFlag"]){
						    		iArr.push('<div class="div2"><i class="red"></i><span>'+data[i]["attentionNum"]+'</span></div>');
						    	}else{
						    		iArr.push('<div class="div2"><i></i><span>'+data[i]["attentionNum"]+'</span></div>');
						    	}
						        
						        iArr.push('<div class="div1"></div>');
						        iArr.push('</div>');
						        iArr.push('<div class="productBenefit-img">');
						        if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "product" || data[i]["loanType"] == "public"){ //如果是产品项目
						        	iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
						        }else{
						        	iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}
						    	
						        iArr.push('<img src="'+cv["fileAddress"]+'/'+data[i]["loanLogo"]+'">');
						        iArr.push('</a>');
						        iArr.push('</div>');
						        iArr.push('<div class="benefit-data">');
						        if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "product" || data[i]["loanType"] == "public"){ //如果是产品项目
									iArr.push('<h3 class="benefit-tit"><a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'" title="'+data[i]["loanName"]+'"  >'+data[i]["loanName"]+'</a></h3>');
						        }else{
									iArr.push('<h3 class="benefit-tit"><a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" title="'+data[i]["loanName"]+'">'+data[i]["loanName"]+'</a></h3>');
								}
						    	if(data[i]["loanIntroduction"] && data[i]["loanIntroduction"].length>18){
						    		iArr.push('<p class="benefit-introduce" title="'+data[i]["loanIntroduction"]+'">'+data[i]["loanIntroduction"].substring(0,18)+'...</p>');
						    	}else{
						    		iArr.push('<p class="benefit-introduce">'+data[i]["loanIntroduction"]+'</p>');
						    	}
						        
						        iArr.push('<div class="benefit-addressType">');
						        iArr.push('<span class="fr address">'+data[i]["loanProvinceName"]+'</span>');
						        if(data[i]["superIndustryName"]){
						        	iArr.push('<span class="fl type">'+data[i]["superIndustryName"]+'</span>');
						        }else{
						        	iArr.push('<span class="fl type">'+data[i]["loanTypeName"]+'</span>');
						        }
						        
						        iArr.push('</div>');
						        iArr.push('<div class="benefit-setbacks">');
						        iArr.push('<span class="span1"></span>');
						        if(data[i]["supportRatio"]*100 > 100){
									iArr.push('<span class="span2" style=" width:100%;"></span>');
								}else{
									iArr.push('<span class="span2" style=" width:'+(data[i]["supportRatio"]*100).toFixed(2)+'%;"></span>');
								}
						        if(data[i]["supportRatio"]*100<20){
						        	var supportRatio = 0;
						        }else if(data[i]["supportRatio"]*100>20 && data[i]["supportRatio"]*100<=100){
						        	var supportRatio = (data[i]["supportRatio"]*100-20).toFixed(0);
						        }if(data[i]["supportRatio"]*100>100){
						        	var supportRatio = 80;
						        }
						        iArr.push('<span class="span3" style="left:'+supportRatio+'%">'+(data[i]["supportRatio"]*100).toFixed(2)+'%</span>');
						        iArr.push('</div>');
						        iArr.push('<ul class="benefit-ul clearfix">');
						        if(data[i]["loanState"] == "preheat"){
						        	iArr.push('<li><em>'+data[i]["praiseNum"]+'人</em>点赞人数</li>');
					        	}else{
					        		iArr.push('<li><em>'+data[i]["supportNum"]+'人</em>支持人数</li>');
					        	}
						        if(data[i]["approveAmt"]>9999){
						        	var numA = (data[i]["approveAmt"]/10000).toFixed(2);
						            //var numM = numA.substring(0,numA.indexOf(".") + 0);
						        	iArr.push('<li><em>'+numA+'万</em>已筹金额</li>');
						        }else{
						        	iArr.push('<li><em>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</em>已筹金额</li>');
						        }
						        
//						        if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
//									remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
//								}else{
//									remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
//								}
						    	remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
								
						        if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
						        	iArr.push('<li class="bak" ><em>0天</em>剩余天数</li>');
								}else{
									if(data[i]["loanState"] == "preheat"){
										iArr.push('<li class="bak" ><em>'+data[i]["attentionNum"]+'人</em>收藏人数</li>');
						        	}else{
						        		iArr.push('<li class="bak" ><em>'+remainDays+'天</em>剩余天数</li>');
						        	}
								}
						        iArr.push('</ul>');
						        iArr.push('</div>');
						        iArr.push('</div>');
							}
							
							
						}
						iStr = iArr.join("");
						$("#" + id).html(iStr);
					}else if(loanType == "public"){
						for(var i=0;i<l;i++){
							if(i<4){
								if(i>2){
									
									iArr.push('<div class="productBenefit mag">');
								}else{
									iArr.push('<div class="productBenefit">');
								}
								
								if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
									iArr.push('<div class="productBenefit-type bak">众筹成功</div>');
								}else{
									iArr.push('<div class="productBenefit-type">'+data[i]["loanStateName"]+'</div>');
								}
						    	iArr.push('<div class="Benefit-follow" follow="'+data[i]["loanNo"]+'" onclick="attentionLoan(this)">');
						        if(data[i]["attentionFlag"]){
						    		iArr.push('<div class="div2"><i class="red"></i><span>'+data[i]["attentionNum"]+'</span></div>');
						    	}else{
						    		iArr.push('<div class="div2"><i></i><span>'+data[i]["attentionNum"]+'</span></div>');
						    	}
						        iArr.push('<div class="div1"></div>');
						        iArr.push('</div>');
						        iArr.push('<div class="productBenefit-img">');
						        if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "product" || data[i]["loanType"] == "public"){ //如果是产品项目
						        	iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
						        }else{
						        	iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}
						    	
						        iArr.push('<img src="'+cv["fileAddress"]+'/'+data[i]["loanLogo"]+'">');
						        iArr.push('</a>');
						        iArr.push('</div>');
						        iArr.push('<div class="benefit-data">');
						        if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "product" || data[i]["loanType"] == "public"){ //如果是产品项目
									iArr.push('<h3 class="benefit-tit"><a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'" title="'+data[i]["loanName"]+'">'+data[i]["loanName"]+'</a></h3>');
						        }else{
									iArr.push('<h3 class="benefit-tit"><a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" title="'+data[i]["loanName"]+'">'+data[i]["loanName"]+'</a></h3>');
								}
						    	
						        if(data[i]["loanIntroduction"] && data[i]["loanIntroduction"].length>18){
						    		iArr.push('<p class="benefit-introduce" title="'+data[i]["loanIntroduction"]+'">'+data[i]["loanIntroduction"].substring(0,18)+'...</p>');
						    	}else{
						    		iArr.push('<p class="benefit-introduce">'+data[i]["loanIntroduction"]+'</p>');
						    	}
						        iArr.push('<div class="benefit-addressType">');
						        iArr.push('<span class="fr address">'+data[i]["loanProvinceName"]+'</span>');
						        if(data[i]["superIndustryName"]){
						        	iArr.push('<span class="fl type">'+data[i]["superIndustryName"]+'</span>');
						        }else{
						        	iArr.push('<span class="fl type">'+data[i]["loanTypeName"]+'</span>');
						        }
						        iArr.push('</div>');
						        iArr.push('<div class="benefit-setbacks">');
						        iArr.push('<span class="span1"></span>');
						        if(data[i]["supportRatio"]*100 > 100){
									iArr.push('<span class="span2" style=" width:100%;"></span>');
								}else{
									iArr.push('<span class="span2" style=" width:'+(data[i]["supportRatio"]*100).toFixed(2)+'%;"></span>');
								}
						        if(data[i]["supportRatio"]*100<20){
						        	var supportRatio = 0;
						        }else if(data[i]["supportRatio"]*100>20 && data[i]["supportRatio"]*100<=100){
						        	var supportRatio = (data[i]["supportRatio"]*100-20).toFixed(0);
						        }if(data[i]["supportRatio"]*100>100){
						        	var supportRatio = 80;
						        }
						        iArr.push('<span class="span3" style="left:'+supportRatio+'%">'+(data[i]["supportRatio"]*100).toFixed(2)+'%</span>');
						        iArr.push('</div>');
						        iArr.push('<ul class="benefit-ul clearfix">');
						        iArr.push('<li><em>'+data[i]["supportNum"]+'个</em>支持人数</li>');
						        
						        if(data[i]["approveAmt"]>9999){
						        	var numA = (data[i]["approveAmt"]/10000).toFixed(2);
						           // var numM = numA.substring(0,numA.indexOf(".") + 0);
						        	iArr.push('<li><em>'+numA+'万</em>已筹金额</li>');
						        }else{
						        	iArr.push('<li><em>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</em>已筹金额</li>');
						        }
//						        if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
//									remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
//								}else{
//									remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
//								}
						    	remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
								
						        if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
						        	iArr.push('<li class="bak"><em>0天</em>剩余天数</li>');
								}else{
									iArr.push('<li class="bak"><em>'+remainDays+'天</em>剩余天数</li>');
								}
						        iArr.push('</ul>');
						        iArr.push('</div>');
						        iArr.push('</div>');
							}
						}	
						
						iStr = iArr.join("");
						$("#" + id).html(iStr);
					}
				}
			}
		},
		error: function(request){
			console.log("获取首页项目列表异常");
		}
	});
}

//公司动态
function showIndexNotice(){
	$.ajax({
		type: "post",
		url : path + "/node/getNode.html",
		data: {
			nodeType:"zxgg"
		},
		dataType: "json",
		success:function(data){
			if(data["success"]){
				if(data["msg"].length > 0){
					var html=[], l = data["msg"].length,data = data["msg"];
					if(l == 0){
						return false;
					}
					var createTime = "";
					for(var i =0;i<l;i++){
						if(data[0]["thumb"]){
							$("#indexImgZXGG").attr("src",cv["fileAddress"]+data[0]["thumb"]);
						}else{
							$("#indexImgZXGG").attr("src",path+"/images/defaultImg.png");
						}
						
						if(i == 0){
							$("#nodeCodeOne").html('<span>最新公告：</span><a href="'+path+'/common/notice_info.html?id='+data[i]["id"]+'&nodeType=gsdt" title="'+data[i]["title"]+'">'+data[i]["title"]+'</a><em>'+data[i]["updateTime"].substring(0,10)+'</em>');
						}
						if(i < 3){
							html.npush('<div><a href="'+path+'/common/notice_info.html?id='+data[i]["id"]+'&nodeType=zxgg">'+data[i]["title"]+'</a></div>');
						}
					}
					$("#noticeList_l").append(html.join(""));
				}else{
					$("#nodeCodeOne").html("<span>最新公告：</span><em>暂无数据！</em>");
					$("#noticeList_l").append("<div>暂无数据！</div>");
				}
			}else{
				console.log("网站公告查询失败");
			}
		},
		error:function(){
			console.log("网站公告查询异常");
		}
	});
	$.ajax({
		type: "post",
		url : path + "/node/getNode.html",
		data: {
			nodeType:"zczx"
		},
		dataType: "json",
		success:function(data){
			if(data["success"]){
				if(data["msg"].length > 0){
					var html=[], l = data["msg"].length,data = data["msg"];
					if(l == 0){
						return false;
					}
					var createTime = "";
					for(var i =0;i<l;i++){
						if(data[0]["thumb"]){
							$("#indexImgZCZX").attr("src",cv["fileAddress"]+data[0]["thumb"]);
						}else{
							$("#indexImgZCZX").attr("src",path+"/images/defaultImg.png");
						}
						
						if(i < 3){
							html.npush('<div><a href="'+path+'/common/notice_info.html?id='+data[i]["id"]+'&nodeType=zczx">'+data[i]["title"]+'</a></div>');
						}
					}
					$("#consultation").append(html.join(""));
				}else{
					$("#consultation").append("<div>暂无数据！</div>");
				}
			}else{
				console.log("网站公告查询失败");
			}
		},
		error:function(){
			console.log("网站公告查询异常");
		}
	});
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

function mechanismRoll(){
	var page=1;//初始化当前的版面为1
	var $show=$("#banner_hzhb");//找到图片展示区域
	var page_count=$show.find("ul").length;
	var $height_box=175;//找到图片展示区域外围的div
	$("#og_next").click(function(){
		if(page<page_count){
			$show.animate({top:'-='+$height_box});
			page++;
			return false;
		}
	})
	$("#og_prev").click(function(){
		if(page>1){
			$show.animate({top:'+='+$height_box});
			page--;
			return false;
		}
	})
}