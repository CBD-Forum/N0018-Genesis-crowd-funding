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
.lch_nav .lch5{cursor: pointer;}
</style>
<div class="bai_box">
<div class="box pt30 gy_info">
   <h3>产品众筹</h3>
   <ul class="lch_nav clearfix">
    <li class="lch5">1. 填写项目基本信息</li>
	<li class="lch4">2. 添加回报设置</li>
	<li class="lch2">3. 发起人信息</li>
	<li class="lch3">4. 完成</li>
   </ul>
   <ul class="hb-table" style="margin-bottom:0;">
    <li class="hb_title clearfix">
	 <span class="wd1">回报</span>
	 <span class="wd2">支持金额</span>
	 <span class="wd3">限定名额</span>
	 <span class="wd4">回报内容</span>
	 <span class="wd5">操作</span>
	</li>
   </ul>
   <ul class="hb-table" id="soprtList" style="margin-top:0;"></ul>
   <div class="gy_infom" id="backSetPauseBox" style="width:825px;">
    <div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em> 支持金额：</span>
	 <input type="text" placeholder="请输入金额 " class="fl" id="price" nullMessage="请输入支持金额">
	 <i class="tian yuan" style="left: 540px;"><b class="companyCode"></b></i>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 回报内容：</span>
	 <textarea class="fl" id="backContent" nullMessage="回报内容不能为空"  ></textarea>
	 <p class="fl col_gray" style="margin-top:87px;">文字不超过200字</p>
	 <!-- <div class="fl shr_box">
	 	<div class="fl" name="ueditor" style="width:510px;height:436px;overflow:auto;">
        	<script id="proDetail" type="text/plain" style="width:490px;height:370px;"></script>
        	<div class="fl" id="proDetail_1"  nullMessage="回报内容不能为空"></div>
        </div>
	 </div> -->
	</div>
	<div class="mb18 clearfix">
	 <span class="fl">说明图片：</span>
	 <div class="upd_load up_load fl">
	   <div class="upd-pA" id="imgheadLi"><div class="deleteImg"><img src="<%=path %>/images/letv/deleteImg.png"></div><img id="imghead" width="150" height="150"/></div>
		   <p class="upd-p" id="image_file" nullMessage="项目头图不能为空" style="width:130px;"><a>点击上传</a></p>
	   <input type="hidden" id="loan_logo_url"/>
	  </div>
	 <p class="fl col_gray">建议尺寸：（640*480px）支持jpg、jpeg、png格式，大小不超过5M。</p>
	</div>
	<div class="mb18 clearfix rel" style="display:none;">
	 <span class="fl"><em>*</em>  参与抽奖：</span>
	 <div id="backTypeC" cj="1">
		 <input class="ipt_radio" type="radio" id="cy" name="cjType" checked="checked" /><label for="cy" class="lg34 mr25">参与抽奖（每个项目仅有一个抽奖档位）</label>
		 <input class="ipt_radio" type="radio" id="bcy" name="cjType" /><label for="bcy" class="lg34 mr25">不参与抽奖</label> 
	 	<a target="_blank" href="<%=path %>/common/nodeList_info.html?nodeType=cjgzsm" class="col_blue" >了解具体抽奖规则>></a>
	 </div>
	</div>
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em>  限定名额：</span>
	 <input type="text" placeholder="请输入限定名额" class="fl" style="width:200px;" id="limitPeople" value="" nullMessage="请输入限定名额" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" >
	 <i class="tian tian2">个</i>
	</div>
	<div id="backTypeDiv" style="display:none;">
		<div class="mb18 clearfix rel">
		 <span class="fl"><em>*</em> 中奖名额：</span>
		 <input type="text" placeholder="请输入中奖名额 " class="fl" style="width:200px;" id="prizeNum" value="" nullMessage="请输入中奖名额" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		 <i class="tian tian2">个</i>
		</div>
		<div class="mb18 clearfix rel">
		 <span class="fl"><em>*</em>  购买上限：</span>
		 <input type="text" placeholder="请输入单人购买上限 " class="fl" style="width:200px;" id="prizeInvestNum" value="" nullMessage="请输入购买上限" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		 <i class="tian tian2">个</i>
		</div>
		<div class="mb18 clearfix rel">
		 <span class="fl"><em>*</em>  满额抽奖：</span>
		 <input type="text" placeholder="请输入大于中奖人数的名额" class="fl" style="width:200px;" id="prizeFullNum" value="" nullMessage="请输入满额抽奖" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		 <i class="tian tian2">个</i>
		 <span class="fl col_gray" style="width:auto;margin:0; padding-left: 10px;">实际投资人数大于等于满额抽奖份数时进行抽奖，不满足时不抽奖</span>
		</div>
	</div>
<!-- 	<div class="mb18 clearfix rel"> -->
<!-- 	 <span class="fl"><em>*</em>   每日预计收益：</span> -->
<!-- 	 <input type="text" placeholder="请输入金额  " class="fl" style="width:200px;" id="dailyProfit" value="" nullMessage="每日预计收益不能为空" > -->
<!-- 	 <i class="tian tian2">元</i> -->
<!-- 	</div> -->
	<div class="mb18 clearfix rel">
	 <span class="fl"><em>*</em>   回报时间（x天）：</span>
	 <input type="text" placeholder="0为项目结束后立即发送  " class="fl" style="width:200px;" id="backTime" value="" nullMessage="请输入回报时间" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" >
	 <i class="tian tian2">天</i>
	</div>
	<div class="mb18 clearfix rel" id="carriageTypeDiv">
	 <span class="fl"><em>*</em>  回报类型：</span>
	 <div id="backType">
		 <input class="ipt_radio" type="radio" id="sw" name="backType" checked="checked" /><label for="sw" class="lg34 mr25">实物回报（需要快递/邮寄的）</label>
		 <input class="ipt_radio" type="radio" id="xn" name="backType" /><label for="xn" class="lg34 mr25">虚拟回报（不需要快递/邮寄的，电子照片等）</label> 
	 </div>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl">运费 ：</span>
	 <input id="carriage" value="0" nullMessage="请输入运费" placeholder="0为免运费"/>
<!-- 	 <p class="col9 fl" style="line-height:34px;" id="carriage" value="0" nullMessage="运费应该为数字">0 元</p> -->
	</div>
	<!-- <div class="clearfix rel">
	 <span class="fl"><em>*</em>  回报展示类别：</span>
	 <div id="return_type">
		 <input class="ipt_radio" type="radio" id="pt" name="return_type" checked="checked" /><label for="pt" class="lg34 mr25">普通档</label>
		 <input class="ipt_radio" type="radio" id="sj" name="return_type" /><label for="sj" class="lg34 mr25">手机档</label>
	 </div>
	</div> -->
	<div class="clearfix operat_btn" style="text-align: center;">
	 <a class="cur" id="saveBackSet" num="0">保存</a>
	 <a id="addBackSet">重置</a>
	 <a id="completeBtu">完成</a>
	</div>
	<div class="tishi_div">
	 <p>温馨提示：</p>
	 <p>1.产品众筹可以设置一个抽奖档位，了解具体抽奖规则可在<b><a  target="_blank" href="<%=path %>/common/nodeList_info.html?nodeType=cjgzsm">帮助中心-抽奖规则说明</a></b>处查看，也可联系在线客服人员进行咨询；</p>
	 <p>2.您可以通过保存操作保存草稿信息，可根据您的需要在<b><a  target="_blank" href="<%=path %>/common/administrationProduct.html?type=1">个人中心-我申请的-草稿</a></b>处继续编辑等操作；</p>
	 <p>3.您可以通过重置操作清空当前填充内容，请谨慎操作；</p>
	 <p>4.合理设置项目的回报档位，有助于您的项目更快募集成功。</p>
	</div>
   </div>
</div>
</div>
<div class="bgpop" id="bgpop"></div>
<input id="overFundAmt" type="hidden" />
<input id="priceAmt" type="hidden" value="0" />
<input id="modifyAmt" type="hidden" value="0" />
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditorDltimg/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditorDltimg/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditorDltimg/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/enterRewardTw.js"></script>