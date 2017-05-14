<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String thirdNo = (String)session.getAttribute("thirdNo");
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript">
	var thirdNo = "<%=thirdNo%>"; //是否需要绑定用户
</script>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
.lch_nav .lch5,.lch_nav .lch2{cursor: pointer;}
</style>
<div class="bai_box">
<div class="box pt30 gy_info">
   <h3>公益众筹</h3>
   <ul class="lch_nav clearfix">
    <li class="lch5">1. 填写项目基本信息</li>
	<li class="lch2">2. 添加回报设置</li>
	<li class="lch4">3. 发起人信息</li>
	<li class="lch3">4. 完成</li>
   </ul>
   <div class="gy_infom" style="width:805px;">
   	<div class="infomPrompt">
   		<h3>图片和附件上传格式说明</h3>
   		<p class="promptImg">支持jpg、jpeg、png格式，大小不超过5M。</p>
   		<p class="promptFile">支持pdf、word格式，大小不超过5M。</p>
   	</div>
    <div class="mb18 clearfix">
	 <span class="fl" style="width:184px;"><em>*</em>  发起人身份证复印件签名：</span>
	 <div class="fl">
		 <div class="clearfix">
		   <a class="fl add_img" id="changeUpload" nullMessage="请上传发起人身份证复印件签名">添加图片</a>
		   <input id="fileToUpload" type="file" style="display:none" name="file">
		  </div>
		  <div class="clearfix" style="width:600px;" id="uploadInFo"></div>
	</div>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl" style="width:184px;"><em>*</em> 发起人手持身份证照片：</span>
	 <div class="fl">
		 <div class="clearfix">
		   <a class="fl add_img" id="changeUpload1" nullMessage="请上传发起人手持身份证照片">添加图片</a>
		   <input id="fileToUpload1" type="file" style="display:none" name="file">
		  </div>
		  <div class="clearfix" style="width:600px;" id="uploadInFo1"></div>
	</div>
	</div>
	
	<div class="mb18 clearfix">
	 <span class="fl" style="width:184px;"> 公募机构的项目接收证明：</span>
	 <div class="fl">
	  <div class="clearfix">
	   <a class="fl add_img"  id="changeUpload2" nullMessage="请上传公募机构的项目接收证明" >添加附件</a>
	   <input id="fileToUpload2" type="file" style="display:none" name="file">
	  </div>
	  <div id="uploadInFo2"></div>
	 </div>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl" style="width:184px;"> 公益机构登记证书：</span>
	 <div class="fl">
		 <div class="clearfix">
		   <a class="fl add_img" id="changeUpload3" nullMessage="请上传公益机构登记证书">添加图片</a>
		   <input id="fileToUpload3" type="file" style="display:none" name="file">
		  </div>
		  <div class="clearfix" style="width:600px;" id="uploadInFo3"></div>
	</div>
	</div>
	<div class="clearfix">
	 <span class="fl" style="width:184px;"> 组织机构代码：</span>
	 <div class="fl">
		 <div class="clearfix">
		   <a class="fl add_img" id="changeUpload4" nullMessage="请上传组织机构代码">添加图片</a>
		   <input id="fileToUpload4" type="file" style="display:none" name="file">
		  </div>
		  <div class="clearfix" style="width:600px;" id="uploadInFo4"></div>
	</div>
	</div>
	<div class="clearfix operat_btn wid255">
	<a id="saveEnterSrc" target="_blank">浏览</a>
	<a id="saveEnterBtn">保存</a>
	 <a id="completeBtu" class="cur">提交</a>
	 
	</div>
   </div>
</div>
</div>
<div class="bgpop" id="bgpop"></div>

<input type="hidden" id="orgLoanReceiveProve"/>
<input type="hidden" id="orgLoanReceiveProve1"/>
<input type="hidden" id="orgLoanReceiveProve2"/>
<input type="hidden" id="orgLoanReceiveProve3"/>
<input type="hidden" id="orgLoanReceiveProve4"/>
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditor/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/enterPubBenefitTr.js"></script>