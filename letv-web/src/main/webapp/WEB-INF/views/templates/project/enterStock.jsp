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
</style>
<div class="back_colh">
  <div class="box gy_info">
   <h3>非公开融资</h3>
   <ul class="lch_nav clearfix">
    <li class="lch1">1. 填写项目基本信息</li>
	<li class="lch2">2. 融资信息</li>
	<li class="lch2">3. 创始人信息</li>
	<li class="lch3">4. 完成</li>
   </ul>
   <div class="gy_infom" style="width:990px;margin-left:200px;">
    <div class="mb18 clearfix cn_txt">
	 <input type="checkbox" class="ipt_check fl" id="promise" nullMessage="请阅读并同意承诺书相关内容"/>
	 <p class="fl col_blue ">承诺书：我承诺以上信息均为真实信息，如填写虚假信息，我将永远失去申请会员的资格，由虚假信息所带来的一切后果，将由我个人承担全部责任。</p>
	</div>
    <div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 项目名称：</span>
	 <input type="text"  placeholder="请输入项目名称" class="fl" id="projectName" />
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 项目一句话简介：</span>
	 <input type="text"  placeholder="请输入项目一句话简介（限制20字）" class="fl" id="loanIntroduction"  nullMessage="请输入项目一句话简介"  />
		<p class="fl" style="padding-left:10px;color: #888;padding-top: 15px;">限制20字以内。</p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 项目头图：</span>
	 <div class="upd_load up_load fl">
	  <div class="upd-pA" id="imgheadLi"><div class="deleteImg"><img src="<%=path %>/images/letv/deleteImg.png"></div><img id="imghead" width="150" height="150"/></div>
	  <p class="upd-p" id="image_file" style="width:130px;" nullMessage="请上传项目头图" ><a>点击上传</a></p>
	 </div>
	 <p class="fl col_gray" style="padding-left:10px;">建议尺寸：（132*132px），支持jpg、jpeg、png，大小不超过5M。</p>
	 <input type="hidden" id="loan_logo_url"/>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"> <em>*</em> 项目所在地： </span>
	 <select id="province" nullMessage="请选择省份"><option>请选择省</option></select>
	 <select id="city" nullMessage="请选择城市"><option>请选择城市</option></select>
	 <select id="county" nullMessage="请选择县"><option>请选择县</option></select>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"> <em>*</em> 项目阶段： </span>
	 <select class="wid250" id="projectStage" nullMessage="请选择项目阶段"><option>项目阶段</option></select>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"> <em>*</em> 行业： </span>
	 <select class="wid250" id="ptrade_one" nullMessage="请选择行业"></select>
	</div>
<!-- 	<div class="mb18 clearfix"> -->
<!-- 	 <span class="fl"><em>*</em> 项目简介：</span> -->
<!-- 	 <textarea class="fl" nullMessage="请输入项目简介" id="ProjectDption"  maxlength="300" placeholder="最大长度不可超过300"></textarea> -->
<!-- 	 <p class="fl col_red" style="margin-top:87px;">文字不超过300字</p> -->
<!-- 	</div> -->
	<div class="mb18 clearfix" style="position: relative;">
	 <span class="fl"><em>*</em> 行业分析 ：</span>
	 <div class="fl" name="ueditor"  id="industryAnalysis" nullMessage="行业分析不能为空" style=" border: 1px solid #ccc; border-radius: 3px;width:492px;height:436px;overflow:auto;overflow-x: hidden">
      	<script id="proDetail_1" type="text/plain" style="width:490px;height:370px;"></script>
      </div>
	</div>
	<div class="mb18 clearfix" style="position: relative;">
	 <span class="fl"><em>*</em> 风险管控 ：</span>
	 <div class="fl"  id="riskAdministration" nullMessage="风险管控不能为空"  name="ueditor" style=" border: 1px solid #ccc; border-radius: 3px;width:492px;height:436px;overflow:auto;overflow-x: hidden">
      	<script id="proDetail_2" type="text/plain" style="width:490px;height:370px;"></script>
      </div>
	</div>
	<div class="mb18 clearfix" style="position: relative;">
	 <span class="fl"><em>*</em> 核心竞争力：</span>
	 <div class="fl" name="ueditor" id="competence_ue" nullMessage="请输入核心竞争力" style=" border: 1px solid #ccc; border-radius: 3px;width:492px;height:436px;overflow:auto;overflow-x: hidden">
      	<script id="proDetail_3" type="text/plain" style="width:490px;height:370px;"></script>
      </div>
	</div>
	<div class="mb18 clearfix" style="position: relative;">
	 <span class="fl"> <em>*</em> 盈利模式： </span>
	 <div class="fl" name="ueditor" id="profitModel_ue" nullMessage="盈利模式不能为空" style=" border: 1px solid #ccc; border-radius: 3px;width:492px;height:436px;overflow:auto;overflow-x: hidden">
      	<script id="proDetail_4" type="text/plain" style="width:490px;height:370px;"></script>
      </div>
	</div>
	
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>官方网址 ：</span>
	 <input type="text"  placeholder="www.leshizhongchou.com"  nullMessage="官方网址不能为空" class="fl" id="officialWz"/>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>官方微博 ：</span>
	 <input type="text"  placeholder="" class="fl" id="officialWb" nullMessage="官方微博不能为空"/>
	</div>
 	<!-- <div class="mb18 clearfix">
	 <span class="fl">官方微信 ：</span>
	 <input type="text"  placeholder="" class="fl" id="officialWx"/>
	</div> -->
	<div class="mb18 clearfix" style="position: relative;">
	 <span class="fl"><em>*</em>微信Logo ：</span>
	 <div class="upd_load up_load4 fl" style="padding:0;height:100px;overflow: hidden;">
	  <p style=" width: 102px;height: 100%; line-height: 140px;" id="logoWeixin" nullMessage="请上传微信Logo" ><a class="col_blue">点击上传</a></p>
	  <div style="display:none;position: absolute; top: 0;" id="weixinDiv"><div class="deleteImg"><img src="<%=path %>/images/letv/deleteImg.png"></div><img id="weixinImage" width="100" height="100"/></div>
	 </div>
	 <!-- <p class="fl col_gray" style="margin-top:80px;">支持jpg、jpeg、png，大小不超过5M。</p> -->
	 <div class="infomPrompt" style="right: 250px;">
   		<h3>图片和附件上传格式说明</h3>
   		<p class="promptImg">支持jpg、jpeg、png格式，大小不超过5M。</p>
   		<p class="promptFile">支持pdf、word格式，大小不超过5M。</p>
   	</div>
	 <input type="hidden" id="weixin_url"/>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>Logo ：</span>
	 <div class="upd_load up_load4 fl" style="padding:0;height:100px;overflow: hidden;">
	  <p style=" width: 102px;height: 100%; line-height: 140px;"id="logoBtn" nullMessage="请上传Logo" ><a class="col_blue">点击上传</a></p>
	  <div style="display:none;position: absolute; top: 0;" id="logoDiv"><div class="deleteImg"><img src="<%=path %>/images/letv/deleteImg.png"></div><img id="logoImage" width="100" height="100"/></div>
	 </div>
	 <!-- <p class="fl col_gray" style="margin-top:80px;">支持jpg、jpeg、png，大小不超过5M。</p> -->
	 <input type="hidden" id="logo_url"/>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>项目封面 ：</span>
	 <div class="upd_load up_load4 fl" style="padding:0;height:100px;overflow: hidden;">
	  <p style=" width: 102px;height: 100%; line-height: 140px;" id="image_file1" nullMessage="请上传项目图片"><a class="col_blue">点击上传</a></p>
	  <div style="display:none;position: absolute; top: 0;" id="imgheadLi1"><div class="deleteImg"><img src="<%=path %>/images/letv/deleteImg.png"></div><img id="imghead1" width="100" height="100"/></div>
	 </div>
	 <!-- <p class="fl col_gray" style="margin-top:80px;">支持jpg、jpeg、png，大小不超过5M。</p> -->
	 <input type="hidden" id="loan_logo_url1"/>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl">项目视频地址 ：</span>
	 <input type="text"  placeholder="请输入swf格式的视频地址" class="fl" id="loanVideo"  logicMessage="请输入swf格式的视频地址" />
	 <p class="fl" style="padding-left:10px;padding-top: 15px;"><a  target="_blank" href="<%=path %>/common/nodeList_info.html?nodeType=invest_spgs" class="col_blue" >如何正确录入项目视频地址？点我了解真相>></a></p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl">移动项目视频地址 ：</span>
	 <input type="text"  placeholder="请输入移动的视频地址" class="fl" id="mobileVideo"  logicMessage="请输入移动的视频地址" />
	 <p class="fl" style="padding-left:10px;padding-top: 15px;"><a  target="_blank" href="<%=path %>/common/nodeList_info.html?nodeType=invest_spgs" class="col_blue" >如何正确录入移动视频地址？点我了解真相>></a></p>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 商业计划书：</span>
	 <div class="fl">
	  <div class="clearfix">
	   <a class="fl add_img"  id="changeUpload4" nullMessage="请上传商业计划书" >添加附件</a>
	   <!-- <p class="fl col_gray mt10">支持pdf、word格式，大小不超过5M。</p> -->
	   <input id="fileToUpload4" type="file" style="display:none" name="file">
	  </div>
	  <div id="uploadInFo4">
	  	
	  </div>
	 </div>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em>  退出方案：</span>
	 <div class="fl">
	  <div class="clearfix">
	   <a class="fl add_img"  id="changeUpload3" nullMessage="请上传退出方案" >添加附件</a>
	   <!-- <p class="fl col_gray mt17">支持pdf、word格式，大小不超过5M。</p> -->
	   <input id="fileToUpload3" type="file" style="display:none" name="file">
	  </div>
	  <div id="uploadInFo3">
	  	
	  </div>
	 </div>
	</div>
	<div class="mb18 clearfix">
	 <span class="fl">其他附件 ：</span>
	 <div class="fl">
	  <div class="clearfix mb18">
	   <a class="fl add_img"  id="changeUpload5" nullMessage="请上传其他附件" >添加附件</a>
	   <!-- <p class="fl col_gray mt10">支持pdf、word格式，大小不超过5M。</p> -->
	   <input id="fileToUpload5" type="file" style="display:none" name="file">
	  </div>
	  <div id="uploadInFo5">
	  	
	  </div>
	 </div>
	</div>
<!-- 	<div class="mb18 clearfix">
	 <span class="fl"><em>*</em> 银行信息：</span>
	 <input type="text"  placeholder="" class="fl" nullMessage="请输入银行信息" id="bankInformation"/>
	</div> -->
	<div class="mb18 clearfix">
	 <input type="checkbox" class="ipt_check fl" id="read" nullMessage="请阅读并同意乐视金融众筹发起人协议"/>
	 <p class="fl col_blue">阅读并同意《<a class="col_blue" target="_blank" href="<%=path %>/common/FDFagreement.html?pdf=applyStockServiceAgreement">乐视金融众筹发起人协议</a>》</p>
	</div>
	
   </div>
   <div class="mb18 clearfix" style="width:255px;margin:0 auto;margin-bottom:60px;">
	 <span class="fl"> </span>
	 <a class="save_btn fl" style="margin:0;" id="saveDataBtn">保存</a>
	</div>
  </div>
 </div>

<input type="hidden" id="orgLoanReceiveProve3"/>
<input type="hidden" id="orgLoanReceiveProve4"/>
<input type="hidden" id="orgLoanReceiveProve5"/>
<input type="hidden" id="projectLoanNo"/>

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
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/enterStock.js"></script>