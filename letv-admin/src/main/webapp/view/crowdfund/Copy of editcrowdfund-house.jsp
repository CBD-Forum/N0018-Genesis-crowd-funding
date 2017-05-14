<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = "${loanNo}";
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditor/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/editcrowdfund-house.js"></script>
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
						<label class="x-form-item-label">房屋名称:</label>
						<div class="x-form-element">
							<input type="text" name="loanName" />
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">房产建筑名称:</label>
						<div class="x-form-element">
							<input type="text" name="houseArea" />平方米
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">房产期限:</label>
						<div class="x-form-element">
							<input type="text" name="houseLimit" />年
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
							<input type="text" name="fundAmt"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">筹资天数:</label>
						<div class="x-form-element">
							<input type="text" name="fundDays"/>
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
						<label class="x-form-item-label">平台收取服务费比例:</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<input type="text" name="chargeFee"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">物业地址:</label>
						<div class="x-form-element">
						    <select id="pro_provice" name="province" style="width:100px"><option></option></select>
						    <select id="pro_city" name="city" style="width:100px"><option></option></select>
						    <select id="pro_county" name="county" style="width:100px"><option></option></select>
						</div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">项目简介:</label>
					    <div class="x-form-element">
					        <textarea rows="8" name="loanDes" style="width: 500px;"></textarea>
					    </div>
					</div>
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
			    </form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="logoSaveBtn">保存</a>
				</div>
		    </div>  
		    <div id="loanStuff" title="项目资料" style="padding:20px;">
		    	<form id="stuffForm" method="post">
					<div class="x-form-item">
						<label class="x-form-item-label">房屋类型:</label>
						<div class="x-form-element">
						    <select id="s_houseType" name="houseType">
							    <option value="">请选择</option>
							    <option value="rent">整租</option>
							    <option value="sell">出售</option>
						    </select>
						</div>
					</div>
		        	<div class="x-form-item">
						<label class="x-form-item-label">房产原价格/租金:</label>
						<div class="x-form-element">
							<input type="text" name="houseOldPrice" />元/平方米(不含税)
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">乐会员价格/租金:</label>
						<div class="x-form-element">
							<input type="text" name="housePresentPrice" />元/平方米(不含税)
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">原开发商:</label>
						<div class="x-form-element">
							<input type="text" name="houseDeveloper" />
						</div>
					</div>
		        </form>
		        <div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="stuffSaveBtn">保存</a>
				</div>
		    </div>
		    <div id="detailInfo" title="房产介绍" style="padding:20px;">
		    	<form id="detailForm" method="post">
					<div class="x-form-item">
					    <label class="x-form-item-label" >项目详情:</label>
				    	 <div name="ueditor" style="margin-left:25px">
						    <script id="loan_detail" type="text/plain" style="width:800px;height:500px;margin-left:90px;"></script>
						 </div>
				    </div>
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="detailSaveBtn">保存</a>
				</div>
		     </div>  
		      <div id="d_priceCompare" title="价格对比" style="padding:20px;">
					<div class="x-form-item">
					    <label class="x-form-item-label">价格对比:</label>
				    	 <div name="ueditor" style="margin-left:25px">
						    <script id="price_compare" type="text/plain" style="width:800px;height:500px;margin-left:90px;"></script>
						 </div>
				    </div>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="priceSaveBtn">保存</a>
				</div>
		     </div>  
		     <div id="d_house_manager" title="管理方" style="padding:20px;">
					<div class="x-form-item">
					    <label class="x-form-item-label" style="margin-left:25px">管理方:</label>
				    	 <div name="ueditor">
						    <script id="house_manager" type="text/plain" style="width:800px;height:500px;margin-left:90px;"></script>
						 </div>
				    </div>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="managerSaveBtn">保存</a>
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
					    <label class="x-form-item-label">视频简介:</label>
					    <div class="x-form-element">
					    	<textarea rows="8" name="videoDes" id="video_des" style="width: 500px;"></textarea>
					    </div>
					</div>
					<div id="loanVideoDisplay"></div>
				</form>
				<div style="text-align:center;width:360px;">
					<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="videoSaveBtn">保存</a>
				</div>
		 	</div>

		 	<div id="loanDetail2" title="项目图片" style="padding:20px;">
				<label>项目图片：</label>
				<div id="loanphoto_pigup">
				    <!--用来存放item-->
				    <div class="filelist"></div>
				     <!--<a id="loanPhotoPicBtn" class="easyui-linkbutton searchBtn">上传图片</a>-->
				</div>
		 	</div>
		 	
		</div> 					
	</div>
	<!-- 
	<div class="psb" style="margin-top:30px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a><a id="returnBtn" class="easyui-linkbutton searchBtn">返回</a></div>
	 -->