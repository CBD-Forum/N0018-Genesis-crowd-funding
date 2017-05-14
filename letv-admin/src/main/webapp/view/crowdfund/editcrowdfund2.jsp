<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = "${loanNo}";
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript" src="<%=path%>/js/common/upload/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/upload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/upload/jquery.fileupload.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditor/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/editcrowdfund.js"></script>
	<div class="prodiv" style="height:500px; overflow: auto;">
				<form id="baseForm" method="post" enctype="multipart/form-data">
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
							<input type="text" id="loanUserText" name="loanUserText" />
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
						<label class="x-form-item-label">项目类型:</label>
						<div class="x-form-element">
							<select id="loan_type" name="loanType"></select>
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
						<label class="x-form-item-label">项目所在省:</label>
						<div class="x-form-element">
						    <select id="pro_provice" name="province"><option></option></select>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">项目所在市:</label>
						<div class="x-form-element">
							<select id="pro_city" name="city"><option></option></select>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">项目所在县:</label>
						<div class="x-form-element">
						    <select id="pro_county" name="county"><option></option></select>
						</div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">项目封面:</label>
					    <div class="x-form-element">
					        <input type="file" id="loan_logo" name="file"/>
					        <input type="hidden" id="loan_logo_url" name="loanLogo" />
					        <a id="previewBtn" style="width: 80px; height:28px; margin-left: -2px; margin-top:-3px; border-left: 0;" class="easyui-linkbutton">预览</a>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">项目简介:</label>
					    <div class="x-form-element">
					        <textarea rows="8" name="loanDes" style="width: 500px;"></textarea>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">项目视频:</label>
					    <div class="x-form-element">
					     <input type="text" name="loanVideo"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">视频简介:</label>
					    <div class="x-form-element">
					     <input type="text" name="videoDes"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">项目详情:</label>
				    	 <div name="ueditor">
						    <script id="loan_detail" type="text/plain" style="width:800px;height:600px;margin-left:90px;"></script>
						 </div>
				    </div>
				    <div class="x-form-item">
					    <label class="x-form-item-label">项目展示图片:</label>
				    	 <div class="x-form-element" id="projectPic" style="padding:20px;">
				
		 	             </div>
				    </div>
				</form>
				<div style="text-align:center;width:360px;">
				    <a  class="easyui-linkbutton c7" style="width:120px;margin-top:20px;" id="basicBtn">保存</a>
			     </div>	
		</div> 					
	       
