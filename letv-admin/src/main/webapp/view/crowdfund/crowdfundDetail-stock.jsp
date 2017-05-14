<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/crowdfundDetail-stock.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/crowdfundDetail-stock-founder.js"></script>

	<div id="add" style="display: block; overflow: hidden; padding: 0;">
		<div id="tt"> 
			<div id="baseInfo" title="基本信息" style="padding:20px;">  
				<form id="baseForm" method="post">
		        	<div class="x-form-item">
						<label class="x-form-item-label">项目名称:</label>
						<div class="x-form-element">
							<label id="loanName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">发起人:</label>
						<div class="x-form-element">
							<label id="loanUser"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">发起人真实姓名:</label>
						<div class="x-form-element">
							<label id="loanUserName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">筹资天数:</label>
						<div class="x-form-element">
							<label id="fundDays"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">筹资金额:</label>
						<div class="x-form-element">
							<label id="fundAmt"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">超募上限:</label>
						<div class="x-form-element">
							<label id="overFundAmt"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">每份金额:</label>
						<div class="x-form-element">
							<label id="stockPartAmt"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">募集总份数:</label>
						<div class="x-form-element">
							<label id="stockNum"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">收取服务费比例:</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<label id="chargeFee"></label>
						</div>
					</div>
				<!-- 	<div class="x-form-item">
						<label class="x-form-item-label">预计每日收益:</label>
						<div class="x-form-element">
							<label id="dailyEarningsForecast"></label>
						</div>
					</div> -->
					<div class="x-form-item">
						<label class="x-form-item-label">项目类型:</label>
						<div class="x-form-element">
							<label id="loanTypeName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">行业类别:</label>
						<div class="x-form-element">
							<label id="superIndustryName"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">项目所在省:</label>
						<div class="x-form-element">
							<label id="loanProvinceName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">项目所在市:</label>
						<div class="x-form-element">
							<label id=loanCityName></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">项目所在县:</label>
						<div class="x-form-element">
							<label id=loanCountyName></label>
						</div>
					</div>
<!-- 					<div class="x-form-item">
						<label class="x-form-item-label">项目负责人:</label>
						<div class="x-form-element">
							<label id="loanManager"></label>
						</div>
					</div> -->
					<div class="x-form-item">
						<label class="x-form-item-label">项目等级:</label>
						<div class="x-form-element">
							<label id="loanLevel"></label>
						</div>
					</div>
<!-- 					<div class="x-form-item">
						<label class="x-form-item-label">发起人预支付金额:</label>
						<div class="x-form-element">
							<label id="prepayAmt"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">认购定金限制:</label>
						<div class="x-form-element">
							<label id="orderAmt"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">领投人最小投资额度:</label>
						<div class="x-form-element">
							<label id="leadMinAmt"></label>
						</div>
					</div> -->
					<div class="x-form-item">
						<label class="x-form-item-label">申请时间:</label>
						<div class="x-form-element">
							<label id="createTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">预热截止时间:</label>
						<div class="x-form-element">
							<label id="preheatEndTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">融资截止时间:</label>
						<div class="x-form-element">
							<label id="fundEndTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">状态:</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<label id="loanStateName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">发布时间:</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<label id="releaseTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">放款时间:</label>
						<div class="x-form-element">
							<label id="cashTime"></label>
						</div>
					</div>
					
				</form>
		    </div>  
		    <div id="detailInfo" title="项目详情" style="padding:20px;">
		    	<form id="detailForm" method="post">
					<div class="x-form-item">
					    <label class="x-form-item-label">项目简介:</label>
					    <div class="x-form-element">
					        <div id="loanDes"></div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">一句话介绍项目:</label>
					    <div class="x-form-element">
					        <div id="loanIntroduction"></div>
					    </div>
					</div>					
					<div class="x-form-item">
						<label class="x-form-item-label">核心竞争力:</label>
						<div class="x-form-element">
							<label id="competence"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">盈利模式:</label>
						<div class="x-form-element">
							<label id="profitModel"></label>
						</div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">官方网址:</label>
					    <div class="x-form-element">
					        <div id="website"></div>
					    </div>
					</div>					
					<div class="x-form-item">
					    <label class="x-form-item-label">官方微博:</label>
					    <div class="x-form-element">
					        <div id="weibo"></div>
					    </div>
					</div>					
					<div class="x-form-item">
						<label class="x-form-item-label">微信:</label>
						<div class="x-form-element">
							<div id="weixinDiv" class="clearfix" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							</div>
						</div>
					</div>																
					<!-- <div class="x-form-item">
					    <label class="x-form-item-label">银行信息:</label>
					    <div class="x-form-element">
					        <div id="bankInfo"></div>
					    </div>
					</div>	 -->					
				</form>
		     </div>  
		     
		     <div id="loanLogo" title="项目图片" style="padding:20px;">
			  	<div class="x-form-item">
					<label class="x-form-item-label">项目封面:</label>
					<div class="x-form-element">
						<div id="logoDiv" class="clearfix" style="margin-left: 10px;">
						    <!--用来存放item-->
						    <div class="filelist"></div>
						</div>
					</div>
				</div>
			  	<div class="x-form-item">
					<label class="x-form-item-label">项目头图:</label>
					<div class="x-form-element">
						<div id="photoDiv" class="clearfix" style="margin-left: 10px;">
						    <!--用来存放item-->
						    <div class="filelist"></div>
						</div>
					</div>
				</div>					
					
		 	 </div>
		 	 
		 	 
		 	 
		 	 
			<div id="loanVideo1" title="项目视频" style="padding:20px;">
				<form id="financialForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">项目视频:</label>
					    <div class="x-form-element">
					     <div id="loanVideo"></div>
					    </div>
					</div>	
					<div class="x-form-item">
					    <label class="x-form-item-label">手机视频:</label>
					    <div class="x-form-element">
					     <div id="mobileVideo"></div>
					    </div>
					</div>			
					<!-- <div class="x-form-item">
					    <label class="x-form-item-label">手机项目视频:</label>
					    <div class="x-form-element">
					     <div id="houseDeveloper"></div>
					    </div>
					</div> -->
					<!-- <div class="x-form-item">
					    <label class="x-form-item-label">视频简介:</label>
					    <div class="x-form-element">
					    	<div id="videoDes"></div>
					    </div>
					</div> -->
				</form>
		 	</div>
 
		 	<div id="founderInfo" title="创始人信息" style="padding:20px;">  
		 	      <font size="3">创始人信息：</font>
		 		 <table id="founderTable"></table>
	 
		 		 <!-- 创始人工作经历 --> 
				 <div id="founderWorksAdd">
				    <table id="founderWorksTable"></table>	
				 </div>	
				 <!-- 创始人创业经历 --> 
				 <div id="founderBusinessAdd">
				    <table id="founderBusinessTable"></table>	
				 </div>	
				 <!-- 创始人教育经历 --> 
				 <div id="founderEducationsAdd">
				     <table id="founderEducationsTable"></table>	
				 </div>				 
		   </div>
	    	
			           
	    	
		 	<div id="founderInfo" title="预计收益" style="padding:20px;">  
		   		 <font size="3">预计收益信息：</font>
		 		 <table id="operateTable"></table>
	    	</div>	    	
		 	
		 	<div id="companyInfo" title="公司信息 " style="padding:20px;">
				<form id="companyForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">团队介绍:</label>
					    <div class="x-form-element">
					    	<div id="loanTeam"></div>
					    </div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">营业执照:</label>
						<div class="x-form-element">
							<div id="businessLicenseDiv" class="clearfix" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							</div>
						</div>
					</div>
							
					<div class="x-form-item">
						<label class="x-form-item-label">法人身份证:</label>
						<div class="x-form-element">
							<div id="legalCardDiv" class="clearfix" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							</div>
						</div>
					</div>	
					
					<div class="x-form-item">
						<label class="x-form-item-label">执照身份信息:</label>
						<div class="x-form-element">
							<div id="licenseIdentityInformationDiv" class="clearfix" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							</div>
						</div>
					</div>		
					
					<div class="x-form-item">
						<label class="x-form-item-label">出让股份比例:</label>
						<div class="x-form-element">
							<label id="transferRatio"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">资金用途:</label>
						<div class="x-form-element">
							<label id="capitalUsed"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">行业分析:</label>
						<div class="x-form-element">
							<label id="industryAnalysis"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">风险管控:</label>
						<div class="x-form-element">
							<label id="riskMeasure"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">融资计划:</label>
						<div class="x-form-element">
							<label id="financePlan"></label>
						</div>
					</div>
					
					 
					<div class="x-form-item">
						<label class="x-form-item-label">商业计划书:</label>
						<div class="x-form-element" id="businessProposalDiv">
					    </div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">退出方案:</label>
						<div class="x-form-element" id="exitSchemeDiv">
					    </div>
					</div>					
					<div class="x-form-item">
						<label class="x-form-item-label">其他附件:</label>
						<div class="x-form-element" id="otherAccessoriesDiv">
					    </div>
					</div>
					
				</form>
		 	</div>
		</div> 					
	</div>
	 	

	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
			//添加工作经历
			//returnStr+='<a onclick=deleteFounder("'+row.id+'")>删除</a>';
			//添加工作经历
			returnStr+='<a onclick= addFounderWorks("'+row.id+'")>工作经历</a>';
			//添加创业经历
			returnStr+='<a onclick=addFounderBusiness("'+row.id+'")>创业经历</a>';
			//添加教育背景
			returnStr+='<a onclick=addFounderEducations("'+row.id+'")>教育背景</a>';
			return returnStr;
		}

		function operateData2(val,row,index){
			var returnStr = '';
			//添加工作经历
			returnStr+='<a onclick=deleteoperateData("'+row.id+'")>删除</a>';
			return returnStr;
		}
		function operateWorksData(val,row,index){
			var returnStr = '';
			//删除
			returnStr+='<a onclick=deleteFounderWorks("'+row.id+'")>删除</a>';
			returnStr+='<a onclick=editFounderWorks("'+row.id+'")>编辑</a>';
			return returnStr;
		}
		
		function operateBusinessData(val,row,index){
			var returnStr = '';
			//删除
			returnStr+='<a onclick=deleteFounderBusiness("'+row.id+'")>删除</a>';
			returnStr+='<a onclick=editFounderBusiness("'+row.id+'")>编辑</a>';
			return returnStr;
		}
		function operateEducationsData(val,row,index){
			var returnStr = '';
			//删除
			returnStr+='<a onclick=deleteFounderEducations("'+row.id+'")>删除</a>';
			returnStr+='<a onclick=editFounderEducations("'+row.id+'")>编辑</a>';
			return returnStr;
		}		
	</script>	