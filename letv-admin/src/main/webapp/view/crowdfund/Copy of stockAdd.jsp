<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditor/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/stockAdd.js"></script>


	<div id="add" style="display: block; overflow: auto; padding: 0;">
		<div id="baseInfo" style="padding-top:20px;padding-bottom:20px;border:1px solid #00b7ee;">  
			<form id="baseForm" method="post">
			
			
<!-- 				   <div class="x-form-item">
						<label class="x-form-item-label">项目类型:</label>
						<div class="x-form-element">
							<select id=loanType name="loanType" style="width:260px"></select>
						</div>
					</div> -->
					<input type="hidden" name="loanType" value="stock">
		        	<div class="x-form-item">
						<label class="x-form-item-label">项目名称:</label>
						<div class="x-form-element">
							<input type="text" name="loanName" />
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">发起人:</label>
						<div class="x-form-element">
							<input type="hidden" id="loanUser" name="loanUser" />
							<input type="text" id="loanUserText" name="loanUserName" readOnly="true"/>
							<a id="getLoanUserBtn" style="width: 80px; height:28px; margin-left: -2px; margin-top:-3px; border-left: 0;" class="easyui-linkbutton">选择</a>
						</div>
					</div>
					
					<!-- <div class="x-form-item">
						<label class="x-form-item-label">合同模版:</label>
						<div class="x-form-element">
							<input type="hidden" id="contractTplNo" name="contractTplNo" />
							<input type="text" id="contractTplNoText" name="contractTplNoText" readonly="readonly"/>
							<a id="getContractTplBtn" style="width: 80px; height:28px; margin-left: -2px; margin-top:-3px; border-left: 0;" class="easyui-linkbutton">选择</a>
						</div>
					</div>	 -->
					<div class="x-form-item">
						<label class="x-form-item-label">筹资金额:</label>
						<div class="x-form-element">
							<input type="text" name="fundAmt"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">是否超募:</label>
						<div class="x-form-element">
							<select id="can_over" name="canOver">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
					</div>
					<div class="x-form-item" id="overFundAmtDiv" style="display: none">
						<label class="x-form-item-label">超募上限:</label>
						<div class="x-form-element">
							<input type="text" name="overFundAmt" id="overFundAmt"/>元
						</div>
					</div>						
					<div class="x-form-item">
						<label class="x-form-item-label">筹资天数:</label>
						<div class="x-form-element">
							<input type="text" name="fundDays"/>
						</div>
					</div>
					<!--  <div class="x-form-item">
						<label class="x-form-item-label">是否为平台项目:</label>
						<div class="x-form-element">
							<select id="is_system" name="isSystem">
								<option></option>
							</select>
						</div>
					</div>-->
					<div class="x-form-item">
						<label class="x-form-item-label">行业类别:</label>
						<div class="x-form-element">
							 <select id=super_industry name="superIndustry" style="width:260px"></select>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">项目所在地:</label>
						<div class="x-form-element">
						    <select id="pro_provice" name="province" style="width:100px"><option></option></select>
						    <select id="pro_city" name="city" style="width:100px"><option></option></select>
						    <select id="pro_county" name="county" style="width:100px"><option></option></select>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">项目负责人:</label>
						<div class="x-form-element">
							<input type="hidden" id="loanManager" name="loanManager" />
							<input type="text" id="loanManagerName" name="loanManagerName" readOnly="true"/>
							<a id="getLoanManagerBtn" style="width: 80px; height:28px; margin-left: -2px; margin-top:-3px; border-left: 0;" 
							class="easyui-linkbutton">选择</a>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">收取服务费比例:</label>
						<div class="x-form-element">
							<input type="text" name="chargeFee" id="chargeFee"/>
							<span style="color:red">(提示:如果比例为1%，请输入0.01)</span>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">发起人预支付金额:</label>
						<div class="x-form-element">
							<input type="text" name="prepayAmt" id="prepayAmt"/>
						</div>
					</div>
					<!-- <div class="x-form-item">
						<label class="x-form-item-label">认购定金限制:</label>
						<div class="x-form-element">
							<input type="text" name="orderAmt"/>
						</div>
					</div> -->
					<!-- <div class="x-form-item">
						<label class="x-form-item-label">领投人最小投资额度:</label>
						<div class="x-form-element">
							<input type="text" name="leadMinAmt"/>
						</div>
					</div> -->
					
					<div class="x-form-item">
					    <label class="x-form-item-label">一句话描述项目:</label>
					    <div class="x-form-element">
					        <textarea rows="4" name="loanIntroduction" style="width: 500px;"></textarea>
					    </div>
					</div>					
					
					<div class="x-form-item">
					    <label class="x-form-item-label">项目简介:</label>
					    <div class="x-form-element">
					        <textarea rows="8" name="loanDes" style="width: 500px;"></textarea>
					    </div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">项目阶段:</label>
						<div class="x-form-element">
							 <select id=loan_stage name="loanStage" style="width:260px"></select>
						</div>
					</div>					
					<div class="x-form-item">
					    <label class="x-form-item-label">核心竞争力:</label>
					    <div class="x-form-element">
					        <textarea rows="8" name="competence" style="width: 500px;"></textarea>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label">盈利模式:</label>
					    <div class="x-form-element">
					        <textarea rows="8" name="profitModel" style="width: 500px;"></textarea>
					    </div>
					</div>					
					<div class="x-form-item">
						<label class="x-form-item-label">官方网址:</label>
						<div class="x-form-element">
							<input type="text" name="website"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">官方微博:</label>
						<div class="x-form-element">
							<textarea rows="2" name="weibo" style="width: 500px;"></textarea>
						</div>
					</div>	
					<label>微信：</label>
						<input type="hidden" name="weixin"/>
						<div class="x-form-element">
							<div id="weixinDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="weixinBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>					
					<!--公司logo或者项目logo  -->
					<label>Logo：</label>
						<input type="hidden" name="logo"/>
						<div class="x-form-element">
							<div id="companyLogoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="companyLogoBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>					
														
					<div class="x-form-item">
					    <label class="x-form-item-label">银行信息:</label>
					    <div class="x-form-element">
					        <textarea rows="8" name="bankInfo" style="width: 500px;"></textarea>
					    </div>
					</div>	 				
					
					<label>项目封面：</label>
						<input type="hidden" name="loanLogo"/>
						<div class="x-form-element">
							<div id="logoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="logoBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						
					   <label>项目头图：</label>
						<input type="hidden" name="loanPhoto"/>
						<div class="x-form-element">
							<div id="photoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>						
						
						
			</form>    	
			<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="basicBtn">保存</a>
			</div>
		</div>
		<div id="detailInfo" style="padding-top:20px;padding-bottom:20px;border:1px solid #00b7ee;">
			<form id="detailForm" method="post">
		    		<!-- <div class="x-form-item">
						<label class="x-form-item-label">公司成立日期:</label>
						<div class="x-form-element">
							<input type="text" name="companyFundDate" id="companyFundDate" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="margin-left:0; width:240px;"/>
						</div>
					</div> -->
					<div class="x-form-item">
						<label class="x-form-item-label">创始人姓名:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">创始人团队中职位:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>	
					
					<div class="x-form-item">
						<label class="x-form-item-label">创始人工作经历:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>	
												
					<div class="x-form-item">
						<label class="x-form-item-label">创始人创业经历:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>						
					<div class="x-form-item">
						<label class="x-form-item-label">创始人教育背景:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>					
<!-- 					<div class="x-form-item">
						<label class="x-form-item-label">创始人头像:</label>
						<input type="hidden" name="founderphoto"/>
						<div class="x-form-element">
							<div id="founderphotoDiv" style="margin-left: 10px;">
							    用来存放item
							    <div class="filelist"></div>
							    <div id="founderphotoBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
					</div> -->
					<!-- <div class="x-form-item">
						<label class="x-form-item-label">上季度营业收入:</label>
						<div class="x-form-element">
							<input type="text" name="lastQuarterIncome" id="lastQuarterIncome"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">上季度营业利润:</label>
						<div class="x-form-element">
							<input type="text" name="lastQuarterProfit" id="lastQuarterProfit"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">上年营业收入:</label>
						<div class="x-form-element">
							<input type="text" name="lastYearIncome" id="lastYearIncome"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">上年营业利润:</label>
						<div class="x-form-element">
							<input type="text" id="lastYearProfit" name="lastYearProfit"/>
						</div>
					</div>
		    		<div class="x-form-item">
					     <label class="x-form-item-label">融资预算:</label>
					     <div name="ueditor" style="margin-left:25px">
						    <script id="finance_budget" type="text/plain" style="width:800px;height:500px;margin-left:90px;"></script>
						 </div>
				    </div>
					<div class="x-form-item">
					    <label class="x-form-item-label">项目详情:</label>
				    	 <div name="ueditor" style="margin-left:25px">
						    <script id="loan_detail" type="text/plain" style="width:800px;height:500px;margin-left:90px;"></script>
						 </div>
				    </div> -->
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="detailSaveBtn">保存</a>
				</div>
			</div>
			
			
		  <!-- 公司信息 -->
		  <div id="companyInfo" style="padding-top:20px;padding-bottom:20px;border:1px solid #00b7ee;">
			<form id="companyForm" method="post">
					<div class="x-form-item">
						<label class="x-form-item-label">团队介绍:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">营业执照:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>	
					
					<div class="x-form-item">
						<label class="x-form-item-label">法人身份证(正反面):</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>	
												
					<!-- <div class="x-form-item">
						<label class="x-form-item-label">融资金额:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>		 -->				
					<div class="x-form-item">
						<label class="x-form-item-label">出让股份比例:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>	
					<div class="x-form-item">
						<label class="x-form-item-label">退出方案:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>						
					<div class="x-form-item">
						<label class="x-form-item-label">资金用途:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>						
					<div class="x-form-item">
						<label class="x-form-item-label">行业分析:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>	
					<div class="x-form-item">
						<label class="x-form-item-label">风险管控:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>	
					<div class="x-form-item">
						<label class="x-form-item-label">融资计划:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>	
					<div class="x-form-item">
						<label class="x-form-item-label">商业计划书:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>						
					<div class="x-form-item">
						<label class="x-form-item-label">其他附件:</label>
						<div class="x-form-element">
							<input type="text" name="founder" id="founder"/>
						</div>
					</div>					
																	
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="detailSaveBtn">保存</a>
				</div>
			</div>			
			
			
			
			
			
			
			
			
			
			
			
			
			
			<div id="loanVideo1" style="padding-top:20px;border-left:1px solid #00b7ee;border-right:1px solid #00b7ee;">
				<form id="videoForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">项目视频:</label>
					    <div class="x-form-element">
					     <textarea rows="3" name="loanVideo" id="loan_video" style="width:500px"></textarea>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">视频简介:</label>
					    <div class="x-form-element">
					    	<textarea rows="8" name="videoDes" id="video_des" style="width: 500px;"></textarea>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">手机项目视频:</label>
					    <div class="x-form-element">
					     <textarea rows="3" name="houseDeveloper" id="house_developer" style="width:500px"></textarea>
					    </div>
					</div>
					<div id="loanVideoDisplay"></div>
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="videoSaveBtn">保存</a>
				</div>
		 	</div>
		</div>
