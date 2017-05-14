<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = "${loanNo}";
</script>
<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditor/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/editcrowdfund-stock.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/editcrowdfund-stock-founder.js"></script>
	<div id="add" style="display: block; overflow: hidden; padding: 0;">
		<div id="tt"> 
			<div id="baseInfo" title="基本信息" style="padding:20px;">  
				<form id="baseForm" method="post">
					<div class="x-form-item">
						<label class="x-form-item-label">项目类型:</label>
						<div class="x-form-element">
							<label id="loanTypeName"></label>
						</div>
					</div>
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
					<div class="x-form-item">
						<label class="x-form-item-label">筹资金额:</label>
						<div class="x-form-element">
							<input type="text" name="fundAmt" id="fundAmt"  precision="2"/>
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
							<input type="text" name="overFundAmt" id="overFundAmt"  precision="2"/>
						</div>
					</div>	
					<div class="x-form-item" id="overFundAmtDiv">
						<label class="x-form-item-label">每份金额:</label>
						<div class="x-form-element">
							<input type="text" name="stockPartAmt" id="stockPartAmt"/> 
						</div>
					</div>					
					<div class="x-form-item" id="overFundAmtDiv">
						<label class="x-form-item-label">募集总份数:</label>
						<div class="x-form-element">
							<input type="text" name="stockNum" id="stockNum"  readonly="readonly"/> 
						</div>
					</div>	
		
					
					<!-- <div class="x-form-item">
						<label class="x-form-item-label">预计每日收益:</label>
						<div class="x-form-element">
							<input type="text" name="dailyEarningsForecast" id="dailyEarningsForecast"/>
							<span style="color:red">(提示:如果比例为1%，请输入0.01)</span>
						</div>
					</div> -->
									
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
						<label class="x-form-item-label">项目等级:</label>
						<div class="x-form-element">
							 <select id="loanLevel" name="loanLevel" style="width:100px">
							    <option value="1">1</option>
							    <option value="2">2</option>
							    <option value="3">3</option>
							    <option value="4">4</option>
							    <option value="5">5</option>
							 </select>
						</div>
					</div> 					
					
					
					
					
					<div class="x-form-item">
						<label class="x-form-item-label">合同编号:</label>
						<div class="x-form-element">
							<input type="text" name="contractNo" />
						</div>
					</div>				
				    <div class="x-form-item">
						<label class="x-form-item-label">合同模版:</label>
						<div class="x-form-element">
							<input type="hidden" id="contractTplNo" name="contractTplNo" />
							<input type="text" id="contractTplNoText" name="contractTplNoText" readonly="readonly"/>
							<a id="getContractTplBtn" style="width: 80px; height:28px; margin-left: -2px; margin-top:-3px; border-left: 0;" class="easyui-linkbutton">选择</a>
						</div>
					</div> 					
					
					<div class="x-form-item">
						<label class="x-form-item-label">行业类别:</label>
						<div class="x-form-element">
							 <select id=super_industry name="superIndustry" style="width:100px"><option></option></select>
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
						<label class="x-form-item-label">收取服务费比例:</label>
						<div class="x-form-element">
							<input type="text" name="chargeFee" id="chargeFee"/>
							<span style="color:red">(提示:如果比例为1%，请输入0.01)</span>
						</div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">一句话描述项目:</label>
					    <div class="x-form-element">
					        <textarea rows="3" name="loanIntroduction" style="width: 500px;"></textarea>
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
					    <div name="ueditor" style="margin-left:25px">
						    <script id="competence_ue" type="text/plain" style="width:800px;height:300px;margin-left:90px;"></script>
						    <input type="hidden" name="competence" id="competence">
						</div>						
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label">盈利模式:</label>
					    <div name="ueditor" style="margin-left:25px">
						    <script id="profitModel_ue" type="text/plain" style="width:800px;height:300px;margin-left:90px;"></script>
						    <input type="hidden" name="profitModel" id="profitModel">
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
					<div class="x-form-item">
					    <label>微信：</label>
							<input type="hidden" name="weixin"/>
							<div class="x-form-element">
								<div id="weixinDiv" style="margin-left: 10px;">
								    <!--用来存放item-->
								    <div class="filelist"></div>
								    <div id="weixinBtn" style="margin-left: 16px;">选择图片</div>
								</div>
							</div>						
					</div>
					
					
			       <div class="x-form-item">
					    <label>Logo：</label>
						<input type="hidden" name="logo"/>
						<div class="x-form-element">
							<div id="companyLogoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="companyLogoBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>	
					</div>					
					<div class="x-form-item" id="backInfoDiv">
					    <label class="x-form-item-label">银行信息:</label>
					    <div class="x-form-element">
					        <textarea rows="8" name="bankInfo" style="width: 500px;"></textarea>
					    </div>
					</div>						
					
											
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="basicBtn">保存</a>
				</div>
		    </div>  
		    <div id="loanLogo" title="项目封面" style="padding:20px;">
		       <form id="loanLogoForm" method="post">
			       <div class="x-form-item">
				    	<label>项目封面：</label>
						<input type="hidden" name="logo"/>
						<div class="x-form-element">
							<div id="logoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="logoBtn" style="margin-left: 16px;">选择图片</div>
							 </div>
					    </div>
					</div>
			       <div class="x-form-item">
				    	<label>项目头图：</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<div id="photoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn" style="margin-left: 16px;">选择图片</div>
							 </div>
					    </div>
					</div>					
					
			    </form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="logoSaveBtn">保存</a>
				</div>
		    </div>  
		    
		    <div id="companyInfo" title="项目详情" style="padding:20px;">
		    	<form id="companyForm" method="post">
					<div class="x-form-item">
					    <label class="x-form-item-label">团队介绍:</label>
				    	 <div name="ueditor" style="margin-left:25px">
						    <script id="loan_team" type="text/plain" style="width:800px;height:300px;margin-left:90px;"></script>
						 </div>
				    </div>
					
		        	<div class="x-form-item">
						<label class="x-form-item-label">营业执照:</label>
						<div class="x-form-element">
							<div id="businessLicenseDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="businessLicenseBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
					</div>
										
		        	<div class="x-form-item">
						<label class="x-form-item-label">法人身份证:</label>
						<div class="x-form-element">
							<div id="legalCardDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="legalCardBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
					</div>	 	
					
					<div class="x-form-item">
						<label class="x-form-item-label">执照身份信息:</label>
						<div class="x-form-element">
							<div id="licenseIdentityInformationDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="licenseIdentityInformationBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
					</div>
					
							
					<div class="x-form-item">
						<label class="x-form-item-label">出让股份比例:</label>
						<div class="x-form-element">
							<input type="text" name="transferRatio" id="transferRatio"/>
						</div>
					</div>	
					<div class="x-form-item">
					    <label class="x-form-item-label">资金用途:</label>
					    <div name="ueditor" style="margin-left:25px">
						    <script id="capitalUsed" type="text/plain" style="width:800px;height:300px;margin-left:90px;"></script>
						 </div>
					</div>					
					<div class="x-form-item">
					    <label class="x-form-item-label">行业分析:</label>
					    <div name="ueditor" style="margin-left:25px">
						    <script id="industryAnalysis" type="text/plain" style="width:800px;height:300px;margin-left:90px;"></script>
						 </div>					    
					    
					</div>		
					<div class="x-form-item">
						<label class="x-form-item-label">风险管控:</label>
					    <div name="ueditor" style="margin-left:25px">
						    <script id="riskMeasure" type="text/plain" style="width:800px;height:300px;margin-left:90px;"></script>
						 </div>						
						
					</div>	
					<div class="x-form-item">
						<label class="x-form-item-label">融资计划:</label>
					    <div name="ueditor" style="margin-left:25px">
						    <script id="financePlan" type="text/plain" style="width:800px;height:300px;margin-left:90px;"></script>
						 </div>						
					</div>	
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="companySaveBtn">保存</a>
				</div>
		    </div>
		    
		    
		    <div id="detailInfo" title="项目附件" style="padding:20px;">
		    	<form id="detailForm" method="post">
		    	    <div class="x-form-item">
						<label class="x-form-item-label">商业计划书:</label>
						<div class="x-form-element">
							<a id="businessProposalChangeUpload" class="webuploader-pick" style="margin-left:27px;width:72px;">选择文件</a>
							<div id="businessProposalUploadInFo" ></div>
							<input id="businessProposalFileToUpload" type="file" style="display:none" name="file" style="float:right;"> 
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">退出方案:</label>
						<div class="x-form-element">
							<a id="exitSchemeChangeUpload" class="webuploader-pick" style="margin-left:27px;width:72px;">选择文件</a>
							<div id="exitSchemeUploadInFo" ></div>
							<input id="exitSchemeFileToUpload" type="file" style="display:none" name="file" style="float:right;"> 
						</div>
					</div>						
											
					<div class="x-form-item">
						<label class="x-form-item-label">其他附件:</label>
						<div class="x-form-element">
							<a id="otherAccessoriesChangeUpload" class="webuploader-pick" style="margin-left:27px;width:72px;">选择文件</a>
							<div id="otherAccessoriesUploadInFo" ></div>
							<input id="otherAccessoriesFileToUpload" type="file" style="display:none" name="file" style="float:right;"> 
						</div>
					</div>	
				</form>
		     </div>  
		     
			<div id="founderInfo" title="创始人信息" style="padding:20px;">
			
		   	 <font size="3">创始人信息：</font>
		 	 <table id="founderTable"></table>
		 	</div>		
		 	
			<div id="operateInfo" title="预计收益" style="padding:20px;">
			
		   	 <font size="3">预计收益信息：</font>
		 	 <table id="operateTable"></table>
		 	</div>			 	     
		     
		      
			<div id="loanVideo1" title="项目视频" style="padding:20px;">
				<form id="videoForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">项目视频:</label>
					    <div class="x-form-element">
					     <textarea rows="3" name="loanVideo" id="loan_video" style="width:500px"></textarea>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">手机项目视频:</label>
					    <div class="x-form-element">
					     <textarea rows="3" name="mobileVideo" id="mobile_video" style="width:500px"></textarea>
					    </div>
					</div> 			
					<!-- <div class="x-form-item">
					    <label class="x-form-item-label">视频简介:</label>
					    <div class="x-form-element">
					    	<textarea rows="8" name="videoDes" id="video_des" style="width: 500px;"></textarea>
					    </div>
					</div> -->
					<!-- <div id="loanVideoDisplay"></div> -->
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="videoSaveBtn">保存</a>
				</div>
		 	</div>

		 	<!-- <div id="loanDetail2" title="项目图片" style="padding:20px;">
				<label>项目图片：</label>
				<div id="loanphoto_pigup">
				    用来存放item
				    <div class="filelist"></div>
				     <a id="loanPhotoPicBtn" class="easyui-linkbutton searchBtn">上传图片</a>
				</div>
		 	</div> -->
		 	
		</div> 					
	</div>
	
	
	
 
	
	
	
	
	   <div id="founderAdd" class="addLoanList" style="display: none">
			<form id="founderForm" method="post">
				<input type="hidden" name="loanNo" id="founder_loanNo"/>
				<br>
				<div class="x-form-item">
					<label class="x-form-item-label">姓名:</label>
					<div class="x-form-element">
						<input type="text" name="name" placeholder="名称" style="width:260px"/>
					</div>
				</div>
				<br>
				<div class="x-form-item">
					<label class="x-form-item-label">职位:</label>
					<div class="x-form-element">
						<input type="text" name="position" placeholder="职位" style="width:260px"/>
					</div>
				</div>
				<br>
				<div class="psb">
					<a id="saveFounderBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeFounderBtn" class="easyui-linkbutton searchBtn">取消</a>
				</div>
			</form>
		</div>
	     <!-- 添加运营数据信息 --> 
		<div id="operateAdd"  class="addLoanList"  style="display: none">
			<form id="operateForm" method="post">
			<br>
				<input type="hidden" name="loanNo" id="operate_loanNo"/>
				<div class="x-form-item">
					<label class="x-form-item-label">预计收益:</label>
					<div class="x-form-element">
						<textarea rows="5" name="expectedReturn" id="expectedReturn" placeholder="预计收益" style="width: 400px;"></textarea>
					</div>
				</div>			
				 
				<div class="psb">
					<a id="saveOperateBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeOperateBtn" class="easyui-linkbutton searchBtn">取消</a>
				</div>
			</form>
		</div>		
		
	   <!-- 添加创始人工作经历 --> 
		<div id="founderWorksAdd"  style="display: none">
		  <table id="founderWorksTable"></table>	
		  <div id="draft_basic" style="padding:20px 10px;border-bottom:1px solid #CCC;">
			<form id="founderWorksForm" method="post">
			    <input type="hidden" name="id" id="founderWorks_id"/>
				<input type="hidden" name="founderId" id="founderWorks_founderId"/>
			    <input type="hidden" name="loanNo" id="founderWorks_LoanNo"/>
	        	<div class="x-form-item">
					<label class="x-form-item-label">所在单位:</label>
					<div class="x-form-element">
						<input type="text" name="company" /> 
					</div>
				</div>
				
				<div class="x-form-item">
					<label class="x-form-item-label">开始时间:</label>
					<div class="x-form-element">
						<input type="text" name="startTime" id="startTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">结束时间:</label>
					<div class="x-form-element">
						<input type="text" name="endTime" id="endTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">职位:</label>
					<div class="x-form-element">
						<input type="text" name="position" /> 
					</div>
				</div>					
			 </form>
			 <div style="text-align:center;width:360px;">
			    <a  class="easyui-linkbutton c7" style="width:120px;margin-top:20px;" id="saveFounderWorkBtn">保存</a>
		     </div>
		    </div>
		</div>	 		
		

	     <!-- 添加创始人创业经历 --> 
		<div id="founderBusinessAdd"  style="display: none">
		  <table id="founderBusinessTable"></table>	
		  <div id="draft_basic" style="padding:20px 10px;border-bottom:1px solid #CCC;">
			<form id="founderBusinessForm" method="post">
			    <input type="hidden" name="id" id="founderBusiness_id"/>
				<input type="hidden" name="founderId" id="founderBusiness_founderId"/>
			    <input type="hidden" name="loanNo" id="founderBusiness_LoanNo"/>
	        	<div class="x-form-item">
					<label class="x-form-item-label">公司名称:</label>
					<div class="x-form-element">
						<input type="text" name="company" /> 
					</div>
				</div>
				
				<div class="x-form-item">
					<label class="x-form-item-label">开始时间:</label>
					<div class="x-form-element">
						<input type="text" name="startTime" id="startTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">结束时间:</label>
					<div class="x-form-element">
						<input type="text" name="endTime" id="endTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/> 
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">职位:</label>
					<div class="x-form-element">
						<input type="text" name="position" /> 
					</div>
				</div>	
				<div class="x-form-item">
					<label class="x-form-item-label">描述:</label>
					<div class="x-form-element">
						<textarea rows="3" name="description" placeholder="描述" style="width: 400px;"></textarea>
					</div>
				</div>				
			 </form>
			 <div style="text-align:center;width:360px;">
			    <a  class="easyui-linkbutton c7" style="width:120px;margin-top:20px;" id="saveFounderBusinessBtn">保存</a>
		     </div>
		    </div>
		</div>	


	     <!-- 添加创始人教育经历 --> 
		<div id="founderEducationsAdd"  style="display: none">
		  <table id="founderEducationsTable"></table>	
		  <div id="draft_basic" style="padding:20px 10px;border-bottom:1px solid #CCC;">
			<form id="founderEducationsForm" method="post">
			    <input type="hidden" name="id" id="founderEducations_id"/>
				<input type="hidden" name="founderId" id="founderEducations_founderId"/>
			    <input type="hidden" name="loanNo" id="founderEducations_LoanNo"/>
	        	<div class="x-form-item">
					<label class="x-form-item-label">学校:</label>
					<div class="x-form-element">
						<input type="text" name="school" /> 
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">开始时间:</label>
					<div class="x-form-element">
						<input type="text" name="startTime" id="startTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/> 
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">结束时间:</label>
					<div class="x-form-element">
						<input type="text" name="endTime" id="endTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
					</div>
				</div>
 			   <div class="x-form-item">
					<label class="x-form-item-label">专业:</label>
					<div class="x-form-element">
						<input type="text" name="graduated" /> 
					</div>
				</div>
 			   <div class="x-form-item">
					<label class="x-form-item-label">学位:</label>
					<div class="x-form-element">
						<input type="text" name="degree" /> 
					</div>
				</div>
			 </form>
			 <div style="text-align:center;width:360px;">
			    <a  class="easyui-linkbutton c7" style="width:120px;margin-top:20px;" id="saveFounderEducationsBtn">保存</a>
		     </div>
		    </div>
		</div>	
	
	<!-- 
	<div class="psb" style="margin-top:30px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a><a id="returnBtn" class="easyui-linkbutton searchBtn">返回</a></div>
	 -->
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
			//添加工作经历
			returnStr+='<a onclick=deleteFounder("'+row.id+'")>删除</a>';
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