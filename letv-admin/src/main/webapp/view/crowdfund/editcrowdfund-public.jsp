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
<script type="text/javascript" src="<%=path%>/js/crowdfund/editcrowdfund-public.js"></script>
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
							<input type="text" name="overFundAmt" id="overFundAmt"/>令
						</div>
					</div> 	
					
					<div class="x-form-item">
						<label class="x-form-item-label">筹资天数:</label>
						<div class="x-form-element">
							<input type="text" name="fundDays"  precision="2"/>
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
						<label class="x-form-item-label">项目所在地:</label>
						<div class="x-form-element">
						    <select id="pro_provice" name="province" style="width:100px"><option></option></select>
						    <select id="pro_city" name="city" style="width:100px"><option></option></select>
						    <select id="pro_county" name="county" style="width:100px"><option></option></select>
						</div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">一句话描述项目:</label>
					    <div class="x-form-element">
					        <textarea rows="3" name="loanIntroduction" style="width: 500px;"></textarea>
					    </div>
					</div>
<!-- 					<div class="x-form-item">
					    <label class="x-form-item-label">项目简介:</label>
					    <div class="x-form-element">
					        <textarea rows="8" name="loanDes" style="width: 500px;"></textarea>
					    </div>
					</div> -->
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="basicBtn">保存</a>
				</div>
		    </div>  
		    <div id="loanLogo" title="项目封面" style="padding:20px;">
		    	 <form id="loanLogoForm" method="post">
			    	<label>项目封面：</label>
					<input type="hidden" name="logo"/>
							<div class="x-form-element">
								<div id="logoDiv" style="margin-left: 10px;">
								    <!--用来存放item-->
								    <div class="filelist"></div>
								    <div id="logoBtn" style="margin-left: 16px;">选择图片</div>
								</div>
							</div>
							
						<!-- 	<label>项目封面(手机端)：</label> -->
					<!-- <input type="hidden" name="logo2"/>
							<div class="x-form-element">
								<div id="logoDiv2" style="margin-left: 10px;">
								    用来存放item
								    <div class="filelist"></div>
								    <div id="logoBtn2" style="margin-left: 16px;">选择图片</div>
								</div>
							</div> -->
			    </form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="logoSaveBtn">保存</a>
				</div>
		    </div> 
		    
		    
		    <div id="welfareInfo" title="发起人信息" style="padding:20px;">
		    	<form id="welfareInfoForm" method="post">
					<div class="x-form-item">
						<label class="x-form-item-label">组织机构代码:</label>
						<div class="x-form-element">
							<div id="orgCodeDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="orgCodeBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						
					</div>
					
		        	<div class="x-form-item">
						<label class="x-form-item-label">公益机构登记证书:</label>
						<div class="x-form-element">
							<div id="orgCertificateDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="orgCertificateBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						
					</div>					 
					 				
		        	<div class="x-form-item">
						<label class="x-form-item-label">发起人身份证复印件签名:</label>
						<div class="x-form-element">
							<div id="promoterIdentitySignDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="promoterIdentitySignBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
					</div>						
					
		        	<div class="x-form-item">
						<label class="x-form-item-label">发起人手持身份证:</label>
						<div class="x-form-element">
							<div id="promoterIdentityPhotoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="promoterIdentityPhotoBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
					</div>
					
		        	<div class="x-form-item">
						<div class="x-form-item">
							<label class="x-form-item-label">公募机构的项目接收证明:</label>
							<div class="x-form-element">
								<a id="orgLoanReceiveProveChangeUpload" class="webuploader-pick" style="margin-left:17px;width:72px;">选择文件</a>
								<div id="orgLoanReceiveProveUploadInFo" ></div>
								<input id="orgLoanReceiveProveFileToUpload" type="file" style="display:none" name="file" style="float:right;"> 
							</div>
						</div>							
					</div>
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="welfareInfoBtn">保存</a>
				</div>
		     </div> 
		     
		    <div id="detailInfo" title="项目详情" style="padding:20px;">
		    	<form id="detailForm" method="post">
					<div class="x-form-item">
					    <label class="x-form-item-label">项目详情:</label>
				    	 <div name="ueditor" style="margin-left:25px">
						    <script id="loan_detail" type="text/plain" style="width:800px;height:500px;margin-left:90px;"></script>
						 </div>
				    </div>
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="detailSaveBtn">保存</a>
				</div>
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
					<div id="loanVideoDisplay"></div>
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="videoSaveBtn">保存</a>
				</div>
		 	</div>
<!-- 
		 	<div id="loanDetail2" title="项目图片" style="padding:20px;">
				<label>项目图片：</label>
				<div id="loanphoto_pigup">
				    用来存放item
				    <div class="filelist"></div>
				     <a id="loanPhotoPicBtn" class="easyui-linkbutton searchBtn">上传图片</a>
				</div>
		 	</div> -->
		 	
		</div> 					
	</div>
	<!-- 
	<div class="psb" style="margin-top:30px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a><a id="returnBtn" class="easyui-linkbutton searchBtn">返回</a></div>
	 -->