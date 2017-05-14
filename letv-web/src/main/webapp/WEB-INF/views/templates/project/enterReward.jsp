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
</style>
<div class="bai_box">
<div class="box pt30 gy_info">
   <h3>产品众筹</h3>
   <ul class="lch_nav clearfix">
    <li class="lch1">1. 填写项目基本信息</li>
	<li class="lch2">2. 添加回报设置</li>
	<li class="lch2">3. 发起人信息</li>
	<li class="lch3">4. 完成</li>
   </ul>
   <div class="gy_infom" style="width:885px;">
    <div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 项目名称：</span>
	 <input type="text" placeholder="请输入项目名称" class="fl" id="loanName">
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 项目头图：</span>
	 	<div class="upd_load up_load fl">
	 	   <div class="upd-pA" id="imgheadLi"><div class="deleteImg"><img src="<%=path %>/images/letv/deleteImg.png"></div><img id="imghead" width="150" height="150"/></div>
		   <p class="upd-p" id="image_file" nullMessage="项目头图不能为空" style="width:130px;"><a>点击上传</a></p>
		   <input type="hidden" id="loan_logo_url"/>
		  </div>
	 <p class="fl col_gray">建议尺寸：（132*132px），支持jpg、jpeg、png，大小不超过5M。</p>
	</div>
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 筹集金额：</span>
	 <input type="text" placeholder="项目的最低目标金额" class="fl" id="raiseAmt" nullMessage="请输入筹资金额" logicMessage="筹资金额应该为大于0的数字">
	 <i class="tian" style="left: 545px;"><b class="companyCode"></b></i>
	</div>
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 最高上限：</span>
	 <input type="text" placeholder="项目的最高目标金额，最高上限可等于筹集金额" class="fl" id="overFundAmt" nullMessage="请输入最高上限金额" logicMessage="最高上限必须大于等于筹集金额">
	 <i class="tian" style="left: 545px;"><b class="companyCode"></b></i>
	</div>
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 募集天数：</span>
	 <input type="text" placeholder="10" class="fl" style="width:85px;" id="raiseDay" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" >
	 <i class="tian">天</i>
	 <p class="fl" style="color:#888;padding:15px 0 0 10px;">募集天数输入区间为10天≦募集天数≦90天。</p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 类别：</span>
	 	<select id="superIndustry" nullMessage="请选择行业类别">
        	<option value="">请选择行业类别</option>
		</select>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 项目地点：</span>
	 	<select id="province" nullMessage="请选择省份">
        	<option value="">请选择省</option>
		</select>
		<select id="city" nullMessage="请选择城市">
			<option value="">请选择市</option>
		</select>
		<select id="county" nullMessage="请选择县">
			<option>请选择县</option>
		</select>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 项目封面：</span>
	 <div class="upd_load up_load fl">
	   <div class="upd-pA" id="imgheadLi1"><div class="deleteImg"><img src="<%=path %>/images/letv/deleteImg.png"></div><img id="imghead1" width="150" height="150"/></div>
		   <p class="upd-p" id="image_file1" nullMessage="项目封面不能为空" style="width:130px;"><a>点击上传</a></p>
	   <input type="hidden" id="loan_logo_url1"/>
	  </div>
	 <p class="fl col_gray">建议尺寸：（640*480px）支持jpg、jpeg、png格式，大小不超过5M。</p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl">视频地址 ：</span>
	 <input type="text" placeholder="请输入swf格式的视频地址" class="fl" id="loanVideo" logicMessage="请输入swf格式的视频地址"  >
	 <p class="fl" style="padding-left:10px;padding-top: 15px;"><a target="_blank" href="<%=path %>/common/nodeList_info.html?nodeType=invest_spgs" class="col_blue" >如何正确录入项目视频地址？点我了解真相>></a></p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl">移动视频地址 ：</span>
	 <input type="text"  placeholder="请输入移动的视频地址" class="fl" id="mobileVideo"  logicMessage="请输入移动的视频地址" />
	 <p class="fl" style="padding-left:10px;padding-top: 15px;"><a  target="_blank" href="<%=path %>/common/nodeList_info.html?nodeType=invest_spgs" class="col_blue" >如何正确录入移动视频地址？点我了解真相>></a></p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 一句话介绍项目 ：</span>
	 <input type="text" class="fl" id="loanIntroduction" nullMessage="一句话介绍项目不能为空" >
	 <p class="fl" style="padding-left:10px;color: #888;padding-top: 15px;">限制20字以内。</p>
	</div>
<!-- 	<div class="mb18 clearfix"> -->
<!-- 	 <span class="fl"><em>*</em> 项目简介：</span> -->
<!-- 	 <textarea class="fl" id="ProjectDption" nullMessage="项目简介不能为空"></textarea> -->
<!-- 	 <p class="fl col_red" style="margin-top:87px;">文字不超过200字</p> -->
<!-- 	</div> -->
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 项目详情：</span>
	 <div class="fl shr_box">
	 	<div class="fl" name="ueditor" style="width:510px;height:436px;overflow:auto;">
        	<script id="proDetail" type="text/plain" style="width:490px;height:370px;"></script>
        	<div class="fl" id="proDetail_1"  nullMessage="项目详情不能为空"></div>
        </div>
	 </div>
	</div>
	<div class="mb18 clearfix" style="position: relative;">
	 <span class="fl">手续费比例 ：</span>
	 <p class="col9 fl" style="line-height:34px;"><i class="fl" id="chargeFee">0</i>
	 	<i class="wenhao fl" style="left: 0;position: relative;" id="counterFee-hover">
	 		<span class="yxj_enter" id="counterFee-popup" style="width:225px;display: none;left: -30px; text-align: left; line-height: 28px;z-index: 9999;">
			 	平台服务费计算方法=筹集资金总金额*<em id="serviceFeeScale"></em>，如若筹款未成功，将不收取平台服务费。
			 </span>
	 	</i>
	 </p>
	 
	</div>
	<div class="mb18 clearfix">
	 <input type="checkbox" style="margin-left:188px;" class="ipt_check fl" id="read" nullmessage="请阅读并同意乐视金融众筹发起人协议"/>
	 <p class="fl col_blue">阅读并同意《<a class="col_blue" target="_blank" href="<%=path %>/common/FDFagreement.html?pdf=applyProductServiceAgreement" >乐视金融众筹发起人协议</a>》</p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"> </span>
	 <a class="save_btn fl" id="saveDataBtn" style="margin-left:188px;">保存</a>
	</div>
	
   </div>
   
</div>
</div>
<div class="bgpop" id="bgpop" style="z-index:1001;"></div>
<input type="hidden" id="projectLoanNo">
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditor/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/enterReward.js"></script>