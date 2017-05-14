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
.lch_nav .lch5{cursor: pointer;}
</style>
 <div class="back_colh">
  <div class="box gy_info">
   <h3>非公开融资</h3>
   <ul class="lch_nav clearfix">
    <li class="lch5">1. 填写项目基本信息</li>
	<li class="lch4">2. 融资信息</li>
	<li class="lch2">3. 创始人信息</li>
	<li class="lch3">4. 完成</li>
   </ul>
   <div class="gy_infom" style="width:990px;margin-left:200px;">
    
    
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 上一轮估值：</span>
	 <input type="text"  placeholder="请输入金额" class="fl" id="lastRound" nullMessage="上一轮估值不能为空"/>
	 <i class="tian yuan2">元</i>
	</div>
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 本次融资金额：</span>
	 <input type="text"  placeholder="请输入金额" class="fl" id="financingAmount" nullMessage="融资金额不能为空" />
	 <i class="tian yuan2">元</i>
	</div>
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 超募后最大金额：</span>
	 <input type="text"  placeholder="请输入金额" class="fl" id="exceedAmount" nullMessage="超募金额不能为空"  />
	 <i class="tian yuan2">元</i>
	</div>
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 每份金额：</span>
	 <input type="text" placeholder="0" class="fl" id="stockPartAmt" nullMessage="每份金额不能为空" />
	 <i class="tian yuan2">元</i>
	</div>
	<div class="mb18 clearfix rel">
	 <span class="fl"> 募集总份额：</span>
	 <input type="text"  placeholder="" class="fl" id="stockNum" readonly="readonly"  />
	 <i class="tian yuan2">份</i>
	</div>
<!-- 	<div class="mb18 clearfix rel"> -->
<!-- 	 <span class="fl"><em>*</em> 预计每日收益：</span> -->
<!-- 	 <input type="text" placeholder="0" class="fl" id="dailyEarningsForecast" nullMessage="预计每日收益不能为空" /> -->
<!-- 	 <i class="tian yuan2">元</i> -->
<!-- 	</div> -->
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 众筹期限：</span>
<!-- 	 <input type="text"  placeholder="" class="fl" id="raiseDay"  nullMessage="众筹期限不能为空" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/> -->
	 <input type="text"  placeholder="" class="fl" id="raiseDay"  nullMessage="众筹期限不能为空" onkeyup="this.value=this.value.replace(/\D/g,'')" onbeforepaste="this.value=this.value.replace(/\D/g,'')"/>
	 <i class="tian yuan2">天</i>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 出让股份比例：</span>
	 <input type="text"  placeholder="" class="fl" id="shareRatio" nullMessage="出让股份比例不能为空" />
	 <p class="fl col_gray" style="margin-top:7px;">如果比例为1%，请输入0.01</p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>  资金用途：</span>
	 <textarea class="fl" id="capitalPurpose" nullMessage="资金用途不能为空" ></textarea>
	 <p class="fl col_gray" style="margin-top:87px;">文字不超过300字</p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>预计收益 ：</span>
	 <div class="fl">
	  <div class="clearfix" style="width:650px;">
	  <textarea placeholder="输入信息....." class="shr-info mb20" id="estimateProfit" nullMessage="请输入预计收益"></textarea>
	  <a id="estimateProfitBtn" class="fr gy_infom_btu" style="background:#1EA5FF;color:#fff;">添加</a>
<!-- 	  <div class="fl rg_oper"><a id="estimateProfitBtn">添加</a></div> -->
	  </div>
	  <div id="estimateProfitList">
	  </div>
	  
	  </div>
	</div>
	<div class="mb18 clearfix" style="position: relative;" >
	 <span class="fl"><em>*</em> 融资计划：</span>
	 <div class="fl" name="ueditor" id="proDetail_1"  nullMessage="融资计划不能为空" style=" border: 1px solid #ccc; border-radius: 3px;width:492px;height:436px;overflow:auto;overflow-x: hidden">
      	<script id="proDetail" type="text/plain" style="width:490px;height:370px;"></script>
      </div>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>  企业营业执照：</span>
	 <div class="fl">
	  <div class="clearfix">
	   <a class="fl add_img" id="changeUpload1" nullMessage="请上传企业营业执照">添加图片</a>
	   <p class="fl col_gray mt17">支持jpg、jpeg、png格式，大小不超过5M。</p>
	   <input id="fileToUpload1" type="file" style="display:none" name="file">
	  </div>
	  <div class="clearfix" style="width:600px;" id="uploadInFo1">
	 	
	  </div>
	 </div>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>  法人身份证的正反面：</span>
	 <div class="fl">
	  <div class="clearfix">
	   <a class="fl add_img" id="changeUpload2" nullMessage="请上传法人身份证的正反面">添加图片</a>
	   <p class="fl col_gray mt17">支持jpg、jpeg、png格式，大小不超过5M。</p>
	   <input id="fileToUpload2" type="file" style="display:none" name="file">
	  </div>
	  <div class="clearfix" style="width:600px;" id="uploadInFo2">
	 	
	  </div>
	 </div>
	</div>
	
	<div class="mb18 clearfix">
	 <span class="fl" style="height: 34px;"> </span>
	 <a class="save_btn fl" id="completeBtn" style="margin:0;">完成</a>
	</div>
   </div>
  </div>
 </div>


<input type="hidden" id="orgLoanReceiveProve1"/>
<input type="hidden" id="orgLoanReceiveProve2"/>


<!-- 背景遮盖层 -->
	<div id="bgpop" class="bgpop" name="bigp"></div>
	<!-- 协议范文 -->
	<div class="agree_box">
		<div style="text-align: center;margin-bottom:-5px;margin-top:5px;font-size:16px;width: 555px;float: left;">用户协议</div>
		<div class="agree_close"></div>
		<div id="agreeContent" class="agree_con"></div>
		<p id="agreeTime" class="agree_date"></p>
	</div>
	
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/ueditor/ueditorDo.js"></script>

<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/enterStock-company.js"></script>