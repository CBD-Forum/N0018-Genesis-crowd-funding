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
	/*if(siteUserId == "null"){
		window.location.href = path + "/common/login.html";
	}else{
		if(!thirdNo){//需要绑定用户
			$("body").append($('<div class="bgpop"></div>'));
			AlertDialog.tip("绑定用户后才可以发起众筹");
			setTimeout(function(){
				window.location.href = path + "/common/realBindUser.html?type=b";
			},1800);
		}
	}*/
</script>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
.lch_nav .lch5,.lch_nav .lch2{cursor: pointer;}
</style>
<div class="bai_box">
<div class="box pt30 gy_info">
   <h3>产品众筹</h3>
   <ul class="lch_nav clearfix">
    <li class="lch5">1. 填写项目基本信息</li>
	<li class="lch2">2. 添加回报设置</li>
	<li class="lch4">3. 发起人信息</li>
	<li class="lch3">4. 完成</li>
   </ul>
   <div class="gy_infom gy_wd700" style="width:730px;">
    <div class="mb18 clearfix">
	 <span class="fl"><em>*</em>  真实姓名：</span>
	 <input type="text" placeholder="请输入真实姓名" class="fl" style="width:138px;background: #eeeeee;" id="founder" readonly="readonly" nullMessage="真实姓名不能为空">
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"> <em>*</em> 所在地：</span>
	 <select id="userProvince" nullmessage="请选择省份"></select>
	 <select id="userCity" nullmessage="请选择城市"><option value="">请选择市</option></select>
	</div>
	
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>  联系电话：</span>
	 <input type="text" placeholder="请输入联系电话" class="fl wid240"  style="background: #eeeeee;" id="mobile"readonly="readonly" nullMessage="联系电话不能为空">
	</div>
	<div class="mb18 clearfix">
	 <span class="fl">  公司名称 ：</span>
	 <input type="text" placeholder="请输入公司名称" class="fl wid240" id="company" >
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"> 证件上传 ：</span>
	 <div class="fl" style="width:620px;">
	  <div class="clearfix">
	   <a class="fl add_img"  id="changeUpload">添加图片</a>
<!-- 	   <p class="fl col_red mt17">支持pdf、word格式，大小不超过2M。</p> -->
	   <p class="fl col_gray mt17">支持jpg、jpeg、png格式，大小不超过5M。</p>
	   <input id="fileToUpload" type="file" style="display:none" name="file">
	  </div>
	  <div id="uploadInFo"></div>
	 </div>
	</div>
	<div class="mb18 clearfix ml90" style="display:none;">
	 <div class="fl rel mr20">
	  <img src="<%=path %>/images/letv/letv-34.png">
	  <a class="close_btn"><img src="<%=path %>/images/letv/close2.png"></a>
	 </div>
	 <div class="fl rel">
	  <img src="<%=path %>/images/letv/letv-34.png">
	 </div>
	</div>
	<div class="clearfix operat_btn wid255" style="margin-top:50px;">
	<a id="saveEnterSrc" target="_blank">预览</a>
	 <a id="saveEnterBtn">保存</a>
	 <a id="completeBtu" class="cur" style="    margin: 0;">提交</a>
	</div>
	<div class="tishi_div">
	 <p>温馨提示：</p>
	 <p>1.您可以通过预览操作查看项目详情，方便您进行申请内容的调整；</p>
	 <p>2.您可以通过保存操作保存草稿信息，可根据您的需要在个人中心-我申请的-草稿处继续编辑等操作；</p>
	 <p>3.点击提交，默认信息项全部确认完成，会直接提交至后台进行审核。我们会在2-3个工作日完成审核，请您耐心等待。</p>
	</div>
   </div>
</div>
</div>
<div class="bgpop" id="bgpop"></div>
<input type="hidden" id="orgLoanReceiveProve"/>
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditor/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/enterRewardTr.js"></script>