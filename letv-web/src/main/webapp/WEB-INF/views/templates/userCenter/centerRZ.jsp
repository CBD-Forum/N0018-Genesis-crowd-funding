<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>
<div class="bai_box">
 <div class="box mt30 clearfix">
  <div class="fl xt_news">
	   <div class="cont_xq">
	    <%-- <a href="<%=path %>/common/modifyData.html"><img id="centerUserPhoto" class="rightImg"></a> --%>
	    <div class="tx" id="image_file">
    		<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
        	<a href="javascript:void(0);" class="photo"><img id="imgheadPhoto" src="" width="113px" height="113px" style="border-radius:50%;"/></a>
            <div class="cover" nullMessage="请上传项目封面" style="height:3px"></div>
            <input type="hidden" id="loan_logo_url"/>
        </div>
		<p class="mt15 ft16" id="userRealName"></p>
		<p class="rzh_cz"><a class="rzh1 cur" href="<%=path %>/common/accountSecurity.html"></a><a class="rzh2" id="realNameRZ"></a><a class="rzh3" id="bankCardRZ"></a></p>
	   </div>
	   <ul class="lf_nav">
		   <li class="bor_top cur"><a href="<%=path %>/common/myCenter.html">账户总览</a></li>
		   <li class="rel hg225"><a id="rightProjectList">项目管理</a>
		    <div class="abs_div">
			 <p class="bor_top"><a href="<%=path %>/common/administrationProduct.html">产品众筹</a></p>
			 <p><a href="<%=path %>/common/administrationPublic.html">非公开融资</a></p>
			 <p><a href="<%=path %>/common/administrationStock.html">公益众筹</a></p>
			</div>
		   </li>
		   <li><a href="<%=path %>/common/myTrade.html">交易记录</a></li>
		   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
		   <li><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
		   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
		   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
	   </ul>
	</div>
  <div class="fr rg_side">
   <ul class="clearfix top_nav" id="rzTab">
    <li type="investor"><a class="cur">跟投人认证</a></li>
	<li type="leadInvestor"><a>领投人认证</a></li>
	<li type="orgInvestor"><a>跟投机构认证</a></li>
	<li type="orgLeadInvestor"><a>领投机构认证</a></li>
   </ul>
   <div id="rzTab-list">
	   <div>
		   <h4>跟投人需符合条件：</h4>
		   <p class="col6">1.应充分认同平台的发展原则及方向；对平台推介项目保持关注，最近一个月约谈过3个项目以上</p>
		   <p class="col6">2.在某个行业或领域有丰富的经验，独立的判断力，丰富的行业资源和影响力，很强的风险承受能力；协助项目发起人完成融<br/>资。</p>
		   <p class="col6">3.具有投资管理能力以及投后管理能力；</p>
		   <p class="col6">4.领投人有投资项目经历，或领投人具有自主成功创业的经验；</p>
		   <p class="col6">5.最近三年个人年均收入不低于50万元人民币；金融资产(包括银行存款、股票、债券、基金份额、资产管理计划、银行理财<br/>产品、信托计划、保险产品、期货权益等)不低于100 万元人民币。`</p>
		   <h4 class="mrt5">认证资料填写：</h4>
		   <div class="clearfix mb30">
		    <div class="fl">
			 <span class="ft_bold ft_bold_em"><em>*</em>真实姓名：</span>
			 <input type="text"  placeholder="" class="rzh_info" id="realName"  style="background: #eee;" nullmessage="输入内容不能为空"/>
			</div>
			<div class="fr mr60">
			 <span class="ft_bold ft_bold_em"><em>*</em>身份证号：</span>
			 <input type="text"  placeholder="" class="rzh_info wd278" id="certNo" style="background: #eee;" nullmessage="证件号不能为空"/>
			</div>
		   </div>
		   <div class="mb30">
			 <span class="ft_bold ft_bold_em"> 公司名称：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="companyName" />
			</div>
			<div class="mb30 clearfix">
			 <div class="fl ">
			  <span class="ft_bold ft_bold_em"> 公司职位：</span>
			  <input type="text"  placeholder="" class="rzh_info" id="position" />
			  <p class="mrb8 mt30"><span class="ft_bold">感兴趣的城市：</span></p>
			  <input type="text"  placeholder="" class="rzh_info wd220" id="concernCity" />
			 </div>
			 <div class="fl clearfix" style="margin-left: 160px;width:390px;">
			  <span class="ft_bold ft_bold_em fl"> 上传名片：</span>
			  <div class="fl upd_load" id="image_file">
			   <p class="upd-p" nullMessage="身份证/公司营业执照不能为空"><a>点击上传</a></p>
			   <p class="upd-pA" id="imgheadLi"><img id="imghead" width="150" height="150"/></p>
			   <input type="hidden" id="loan_logo_url"/>
			  </div>
			  <div class="aaa">
			  	图片尺寸：200*400px<br />图片格式：png、jpg<br />图片的大小：5M以内
			  </div>
			 </div>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>感兴趣的领域：</span></p>
			 <div id="concern_industryBox" nullMessage="请选择感兴趣的领域">
				 
			 </div>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>准备使用多少资金来进行投资？</span></p>
			 <input type="text"  placeholder="" class="rzh_info wd220" nullMessage="请填写准备使用多少资金来进行投资" id="investRatio" />
			</div>
			<div class="mb30 ">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>投资经历：</span></p>
			 <input type="radio" name="tzjl" checked="checked" num="0" /> <span class="mr15">我投资过项目</span><input type="radio" num="1" name="tzjl" /> 我还没投资过项目
			 <p class="mtb25">
			 	<textarea name="a" style="width:830px;height:100px;padding:10px;" nullMessage="请填写我投资过项目" id="investExperience"  placeholder="输入项目名称"  ></textarea>
			 </p>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>计划投资：</span></p>
			 <p class="mtb25"> <span class="fl" style="line-height: 30px;">未来一年计划投资项目 </span> <input type="text" id="investPlanNum" nullMessage="计划投资项目个数不能为空"  placeholder="" class="rzh_info wd28" onchange="this.value=this.value.substring(0, 4)" onkeydown="this.value=this.value.substring(0, 4)" onkeyup="this.value=this.value.substring(0, 4)"   /> <span style="line-height: 30px;">个</span></p> 
			 <p class="mtb25"><span class="fl" style="line-height: 30px;">未来一年计划投资</span> <input type="text"  placeholder="" nullMessage="计划投资项目金额不能为空"  class="rzh_info wd28" id="investPlanAmt"  /> <span style="line-height: 30px;">万元</span></p>
			</div>

			<p class="clearfix mt10"><input type="checkbox" class="fl" id="read" style="margin:6px 5px 0 0" /><span class="fl color_bl">阅读并同意 《<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=investment_service_agreement">乐视金融众筹认证服务协议</a>》及其附件《<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=risk_disclosure">风险揭示书</a>》</span></p>
			<div class="agreen_p">
			<p class="col9">1.我认识到参与众筹是投资行为，并愿意承担投资失败带来的损失。 </p>
			<p class="col9">2.我知道乐视金融并不保证众筹平台上项目的盈利能力，我自己会做好尽职调查。</p>
			<p class="col9">3.如果我要进行投资，我会遵循相关法律规定。 </p>
			</div>
			<p><a class="log_btn submit_shq" id="subInvestBtnBc">保存</a><a class="log_btn submit_shq" id="subInvestBtnCz">重置</a><a class="log_btn submit_shq" id="subInvestBtn">完成</a></p>
	   </div>
   	   <div style="display:none;">
   	   		<h4>领投人需符合条件：</h4>
		   <p class="col6">1.应充分认同平台的发展原则及方向；对平台推介项目保持关注，最近一个月约谈过3个项目以上</p>
		   <p class="col6">2.在某个行业或领域有丰富的经验，独立的判断力，丰富的行业资源和影响力，很强的风险承受能力；协助项目发起人完成融<br/>资。</p>
		   <p class="col6">3.具有投资管理能力以及投后管理能力；</p>
		   <p class="col6">4.领投人有投资项目经历，或领投人具有自主成功创业的经验；</p>
		   <p class="col6">5.最近三年个人年均收入不低于50万元人民币；金融资产(包括银行存款、股票、债券、基金份额、资产管理计划、银行理财<br/>产品、信托计划、保险产品、期货权益等)不低于100 万元人民币。`</p>
		   <h4 class="mrt5">认证资料填写：</h4>
		   <div class="clearfix mb30">
		    <div class="fl">
			 <span class="ft_bold ft_bold_em"><em>*</em>真实姓名：</span>
			 <input type="text"  placeholder="" class="rzh_info" id="realName1" style="background: #eee;" nullmessage="输入内容不能为空"/>
			</div>
			<div class="fr mr60">
			 <span class="ft_bold ft_bold_em"><em>*</em>身份证号：</span>
			 <input type="text"  placeholder="" class="rzh_info wd278" id="certNo1" style="background: #eee;" nullmessage="证件号不能为空"/>
			</div>
		   </div>
		   <div class="mb30">
			 <span class="ft_bold ft_bold_em"> 公司名称：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="companyName1"  />
			</div>
			<div class="mb30 clearfix">
			 <div class="fl ">
			  <span class="ft_bold ft_bold_em"> 公司职位：</span>
			  <input type="text"  placeholder="" class="rzh_info" id="position1" />
			  <p class="mrb8 mt30"><span class="ft_bold">感兴趣的城市：</span></p>
			  <input type="text"  placeholder="" class="rzh_info wd220" id="concernCity1" />
			 </div>
			 <div class="fl clearfix" style="margin-left: 160px;width:390px;">
			  <span class="ft_bold ft_bold_em fl"> 上传名片：</span>
			  <div class="fl upd_load" id="image_file1">
			   <p class="upd-p" nullMessage="身份证/公司营业执照不能为空"><a>点击上传</a></p>
			   <p class="upd-pA" id="imgheadLi1"><img id="imghead1" width="150" height="150"/></p>
			   <input type="hidden" id="loan_logo_url1"/>
			  </div>
			  <div class="aaa">
			  	图片尺寸：200*400px<br />图片格式：png、jpg<br />图片的大小：5M以内
			  </div>
			 </div>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>感兴趣的领域：</span></p>
			 <div id="concern_industryBox1" nullMessage="请选择感兴趣的领域">
				 
			 </div>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>准备使用多少资金来进行投资？</span></p>
			  <input type="text" class="rzh_info wd220" id="investRatio1" nullMessage="请输入准备使用多少资金来进行投资" />
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>投资经历：</span></p>
			 <input type="radio" name="tzjl1" num="0" checked="checked" /> <span class="mr15">我投资过项目</span><input type="radio" name="tzjl1" num="1"  /> 我还没投资过项目
			 <p class="mtb25">
			 	<textarea name="a" style="width:830px;height:100px;padding:10px;" nullMessage="请输入我投资过项目" id="investExperience1"  placeholder="输入项目名称" ></textarea>
			 </p>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>计划投资：</span></p>
			 <p class="mtb25"> <span class="fl" style="line-height: 30px;">未来一年计划投资项目 </span> <input type="text" nullMessage="计划投资项目个数不能为空" id="investPlanNum1" placeholder="" class="rzh_info wd28" onchange="this.value=this.value.substring(0, 4)" onkeydown="this.value=this.value.substring(0, 4)" onkeyup="this.value=this.value.substring(0, 4)"  /> <span style="line-height: 30px;">个</span></p> 
			 <p class="mtb25"><span class="fl" style="line-height: 30px;">未来一年计划投资 </span> <input type="text" nullMessage="计划投资金额不能为空" placeholder="" class="rzh_info wd28" id="investPlanAmt1" /> <span style="line-height: 30px;">万元</span></p>
			</div>
			<div class="clearfix">
			 <div class="fl clearfix" style="width:420px;">
			  <p style="margin-bottom: 10px;"><span class="ft_bold ft_bold_em"><em>*</em>个人资产认证资料（股票、房产、银行资产等）</span></p>
			  <div class="fl" style="width:200px;height:200px;border: 1px solid #e1e1e1;">
			  <div class="fl upd_load2 " style="width: 100px; height: 100px;margin:0;margin-left: 50px;">
			   <p class="upd-p" style="line-height: 180px!important;"><a id="changeUpload">选择文件</a><input id="fileToUpload" type="file" style="display:none" name="file" style="float:right;"></p>
			  </div>
			  <div class="fl bbb" style="padding-top:0px!important;font-size: 12px;">
			  	图片格式：png、jpg、gjf、jpeg<br />图片大小：5M以内<br />附件格式：doc、docx、pdf<br />附件大小：5M以内
			  	</div>
			  </div>
			  <p class="zl-p fl" id="uploadInFo" style="margin:0;"></p>
			  
			 </div>
			 <div class="fl" style="width:420px;">
			  <p style="margin-bottom: 10px;"><span class="ft_bold ft_bold_em"><em>*</em>历史投资资料（投资合同等）</span></p>
			  <div class="fl" style="width:200px;height:200px;border: 1px solid #e1e1e1;">
			  <div class="fl upd_load2" style="width: 100px; height: 100px;margin:0;margin-left: 50px;">
			   <p class="upd-p" style="line-height: 180px!important;"><a id="changeUpload1">选择文件</a><input id="fileToUpload1" type="file" style="display:none" name="file" style="float:right;"></p>
			 </div>
			 <div class="fl bbb" style="padding-top:0px!important;font-size: 12px;">
			  	图片格式：png、jpg、gjf、jpeg<br />图片大小：5M以内<br />附件格式：doc、docx、pdf<br />附件大小：5M以内
			  	</div>
			 </div>
			  <p class="zl-p fl" id="uploadInFo1" style="margin:0;"></p>
			  
			 </div>
			</div>
			<p class="clearfix mt10"><input type="checkbox" id="read1" class="fl" style="margin:6px 5px 0 0" /><span class="fl color_bl">阅读并同意 《<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=investment_service_agreement">乐视金融众筹认证服务协议</a>》及其附件《<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=risk_disclosure">风险揭示书</a>》</span></p>
			<div class="agreen_p">
			<p class="col9">1.我认识到参与众筹是投资行为，并愿意承担投资失败带来的损失。 </p>
			<p class="col9">2.我知道乐视金融并不保证众筹平台上项目的盈利能力，我自己会做好尽职调查。</p>
			<p class="col9">3.如果我要进行投资，我会遵循相关法律规定。 </p>
			</div>
			<p><a class="log_btn submit_shq" id="subLTRZBtnBc">保存</a><a class="log_btn submit_shq" id="subLTRZBtCz">重置</a><a class="log_btn submit_shq" id="subLTRZBtn">完成</a></p>
   	   </div>
   	   <div style="display:none;">
		   <h4>跟投机构需符合条件：</h4>
		   <p class="col6">1.应充分认同平台的发展原则及方向；对平台推介项目保持关注，最近一个月约谈过3个项目以上</p>
		   <p class="col6">2.在某个行业或领域有丰富的经验，独立的判断力，丰富的行业资源和影响力，很强的风险承受能力；协助项目发起人完成融<br/>资。</p>
		   <p class="col6">3.具有投资管理能力以及投后管理能力；</p>
		   <p class="col6">4.领投人有投资项目经历，或领投人具有自主成功创业的经验；</p>
		   <p class="col6">5.最近三年个人年均收入不低于50万元人民币；金融资产(包括银行存款、股票、债券、基金份额、资产管理计划、银行理财<br/>产品、信托计划、保险产品、期货权益等)不低于100 万元人民币。`</p>
		   <h4 class="mrt5">认证资料填写：</h4>
		   <div class="mb30">
			 <span class="ft_bold size ft_bold_em"><em>*</em>机构性质：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="institution" style="width:160px;" nullmessage="机构性质不能为空" />
			</div>
			<div class="mb30">
			 <span class="ft_bold size ft_bold_em"><em>*</em>机构名称：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="realName3" style="width:300px;background: #eee;" nullmessage="机构名称不能为空" />
			</div>
			<div class="mb30">
			 <span class="ft_bold size ft_bold_em"><em>*</em>机构证件号码：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="certNo3" style="background: #eee;" />
			</div>
			<div class="mb30 clearfix">
			 <span class="ft_bold size ft_bold_em fl"><em>*</em>上传机构证件：</span>
			 <div class="fl" style="width:200px;height:200px;border: 1px solid #e1e1e1;">
				  <div class="upd_load2" style="width: 100px; height: 100px;margin:0;margin-left: 50px;">
				   <p class="upd-p" style="line-height: 180px!important;"><a id="changeUpload3">选择文件</a><input id="fileToUpload3" type="file" style="display:none" name="file" style="float:right;"></p>
				 </div>
				 <div class="aaa" style="padding-top: 10px;font-size: 14px;height: 70px; margin-left: 20px;">
				  	图片尺寸：200*400px<br />图片格式：png、jpg<br />图片的大小：5M以内
				  </div>
			</div>
			  <p class="zl-p fl" style="width:350px;    margin: 0;" id="uploadInFo3"></p>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold size ft_bold_em"><em>*</em>感兴趣的领域：</span></p>
			 <div id="concern_industryBox3" nullmessage="请选择感兴趣的领域" >
				 
			 </div>
			</div>
			<div class="mb30">
			 <span class="ft_bold size ft_bold_em"><em>*</em>感兴趣的城市：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="concernCity3" style="width:200px;" nullmessage="请输入感兴趣的城市"  />
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>准备使用多少资金来进行投资？</span></p>
			 <input type="text"  placeholder="" class="rzh_info wd220" id="investRatio3" nullmessage="请输入准备使用多少资金来进行投资" />
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>投资经历：</span></p>
			 <input type="radio"  name="tzjl2" num="0"  checked="checked" /> <span class="mr15">我投资过项目</span><input type="radio"  name="tzjl2" num="1"  /> 我还没投资过项目
			 <p class="mtb25">
			 	<textarea name="a" style="width:830px;height:100px;padding:10px;" id="investExperience3" nullmessage="请输入我投资过项目" placeholder="输入项目名称" ></textarea>
			 </p>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>计划投资：</span></p>
			 <p class="mtb25"> <span class="fl" style="line-height: 30px;">未来一年计划投资项目 </span> <input type="text" id="investPlanNum3" nullmessage="计划投资项目个数不能为空" placeholder="" class="rzh_info wd28" onchange="this.value=this.value.substring(0, 4)" onkeydown="this.value=this.value.substring(0, 4)" onkeyup="this.value=this.value.substring(0, 4)"  /> <span style="line-height: 30px;">个</span></p> 
			 <p class="mtb25"><span class="fl" style="line-height: 30px;">未来一年计划投资</span> <input type="text" nullmessage="计划投资金额不能为空" placeholder="" class="rzh_info wd28" id="investPlanAmt3" /> <span style="line-height: 30px;">万元</span></p>
			</div>

			<p class="clearfix mt10"><input type="checkbox" class="fl" id="read3" style="margin:6px 5px 0 0" /><span class="fl color_bl">阅读并同意 《<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=investment_service_agreement">乐视金融众筹认证服务协议</a>》及其附件《<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=risk_disclosure">风险揭示书</a>》</span></p>
			<div class="agreen_p">
			<p class="col9">1.我认识到参与众筹是投资行为，并愿意承担投资失败带来的损失。 </p>
			<p class="col9">2.我知道乐视金融并不保证众筹平台上项目的盈利能力，我自己会做好尽职调查。</p>
			<p class="col9">3.如果我要进行投资，我会遵循相关法律规定。 </p>
			</div>
			<p><a class="log_btn submit_shq" id="bodiesGTRZBtnBc">保存</a><a class="log_btn submit_shq" id="bodiesGTRZBtnCz">重置</a><a class="log_btn submit_shq" id="bodiesGTRZBtn">完成</a></p>
	   </div>
   	   <div style="display:none;">
   	   		<h4>领投机构需符合条件：</h4>
		   <p class="col6">1.应充分认同平台的发展原则及方向；对平台推介项目保持关注，最近一个月约谈过3个项目以上</p>
		   <p class="col6">2.在某个行业或领域有丰富的经验，独立的判断力，丰富的行业资源和影响力，很强的风险承受能力；协助项目发起人完成融<br/>资。</p>
		   <p class="col6">3.具有投资管理能力以及投后管理能力；</p>
		   <p class="col6">4.领投人有投资项目经历，或领投人具有自主成功创业的经验；</p>
		   <p class="col6">5.最近三年个人年均收入不低于50万元人民币；金融资产(包括银行存款、股票、债券、基金份额、资产管理计划、银行理财<br/>产品、信托计划、保险产品、期货权益等)不低于100 万元人民币。`</p>
		   <h4 class="mrt5">认证资料填写：</h4>
		   <div class="mb30">
			 <span class="ft_bold size ft_bold_em"><em>*</em>机构性质：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="institution4" style="width:160px;" nullmessage="机构性质不能为空"  />
			</div>
			<div class="mb30">
			 <span class="ft_bold size ft_bold_em"><em>*</em>机构名称：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="realName4" style="width:300px;background: #eee;" nullmessage="机构名称不能为空" />
			</div>
			<div class="mb30">
			 <span class="ft_bold size ft_bold_em"><em>*</em>机构证件号码：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="certNo4" style="background: #eee;"  />
			</div>
			<div class="mb30 clearfix">
			 <span class="ft_bold size ft_bold_em fl"><em>*</em>上传机构证件：</span>
			  
			  <div class="fl" style="width:200px;height:200px;border: 1px solid #e1e1e1;">
				  <div class="upd_load2" style="width: 100px; height: 100px;margin:0;margin-left: 50px;">
				   <p class="upd-p" style="line-height: 180px!important;"><a id="changeUpload4">选择文件</a><input id="fileToUpload4" type="file" style="display:none" name="file" style="float:right;"></p>
				 </div>
				 <div class="aaa" style="padding-top: 10px;font-size: 14px;height: 70px; margin-left: 20px;">
				  	图片尺寸：200*400px<br />图片格式：png、jpg<br />图片的大小：5M以内
				  </div>
			</div>
			  <p class="zl-p fl" style="width:350px;margin: 0;" id="uploadInFo4"></p>
			  
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold size ft_bold_em"><em>*</em>感兴趣的领域：</span></p>
			 <div id="concern_industryBox4" nullmessage="请选择感兴趣的领域" >
				 
			 </div>
			</div>
			<div class="mb30">
			 <span class="ft_bold size ft_bold_em"><em>*</em>感兴趣的城市：</span>
			 <input type="text"  placeholder="" class="rzh_info wd623" id="concernCity4" style="width:200px;" nullmessage="请输入感兴趣的城市" />
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>准备使用多少资金来进行投资？</span></p>
			 <input type="text"  placeholder="" class="rzh_info wd220" id="investRatio4" nullmessage="请输入准备使用多少资金来进行投资" />
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>投资经历：</span></p>
			 <input type="radio" name="tzjl3" num="0"  checked="checked" /> <span class="mr15">我投资过项目</span><input type="radio" name="tzjl3" num="1"  /> 我还没投资过项目
			 <p class="mtb25">
			 	<textarea name="a" style="width:830px;height:100px;padding:10px;" nullmessage="请输入我投资过项目" id="investExperience4"  placeholder="输入项目名称" ></textarea>
			 </p>
			</div>
			<div class="mb30">
			 <p class="mrb8"><span class="ft_bold ft_bold_em"><em>*</em>计划投资：</span></p>
			 <p class="mtb25"><span class="fl" style="line-height: 30px;">未来一年计划投资项目 </span><input type="text" id="investPlanNum4" placeholder="" class="rzh_info wd28" nullmessage="计划投资项目个数不能为空" onchange="this.value=this.value.substring(0, 4)" onkeydown="this.value=this.value.substring(0, 4)" onkeyup="this.value=this.value.substring(0, 4)"   /> <span style="line-height: 30px;"><span style="line-height: 30px;">个</span></span></p> 
			 <p class="mtb25"><span class="fl" style="line-height: 30px;">未来一年计划投资</span> <input type="text"  placeholder="" nullmessage="计划投资金额不能为空" class="rzh_info fl wd28" id="investPlanAmt4"  /> <span style="line-height: 30px;">万元</span></p>
			</div>
			
			<div class="clearfix">
			 <div class="fl clearfix" style="width:420px;">
			  <p style="margin-bottom: 10px;"><span class="ft_bold ft_bold_em"><em>*</em>机构资产认证资料（股票、房产、银行资产等）</span></p>
			  <div class="fl" style="width:200px;height:200px;border: 1px solid #e1e1e1;">
			  <div class="fl upd_load2 " style="width: 100px; height: 100px;margin:0;margin-left: 50px;">
			   <p class="upd-p" style="line-height: 180px!important;"><a id="changeUpload5">选择文件</a><input id="fileToUpload5" type="file" style="display:none" name="file" style="float:right;"></p>
			  </div>
			  <div class="fl bbb" style="padding-top:0px!important;font-size: 12px;">
			  	图片格式：png、jpg、gjf、jpeg<br />图片大小：5M以内<br />附件格式：doc、docx、pdf<br />附件大小：5M以内
			  	</div>
			  </div>
			  <p class="zl-p fl" id="uploadInFo5" style="margin:0;"></p>
			  
			 </div>
			 <div class="fl" style="width:420px;">
			  <p style="margin-bottom: 10px;"><span class="ft_bold ft_bold_em"><em>*</em>历史投资资料（投资合同等）</span></p>
			  <div class="fl" style="width:200px;height:200px;border: 1px solid #e1e1e1;">
			  <div class="fl upd_load2" style="width: 100px; height: 100px;margin:0;margin-left: 50px;">
			   <p class="upd-p"style="line-height: 180px!important;"><a id="changeUpload6">选择文件</a><input id="fileToUpload6" type="file" style="display:none" name="file" style="float:right;"></p>
			 </div>
			 <div class="fl bbb" style="padding-top:0px!important;font-size: 12px;">
			  	图片格式：png、jpg、gjf、jpeg<br />图片大小：5M以内<br />附件格式：doc、docx、pdf<br />附件大小：5M以内
			  	</div>
			 </div>
			  <p class="zl-p fl" id="uploadInFo6" style="margin:0;"></p>
			  
			 </div>
			</div>
			<p class="clearfix mt10"><input type="checkbox" id="read4" class="fl" style="margin:6px 5px 0 0" /><span class="fl color_bl">阅读并同意 《<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=investment_service_agreement">乐视金融众筹认证服务协议</a>》及其附件《<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=risk_disclosure">风险揭示书</a>》</span></p>
			<div class="agreen_p">
			<p class="col9">1.我认识到参与众筹是投资行为，并愿意承担投资失败带来的损失。 </p>
			<p class="col9">2.我知道乐视金融并不保证众筹平台上项目的盈利能力，我自己会做好尽职调查。</p>
			<p class="col9">3.如果我要进行投资，我会遵循相关法律规定。 </p>
			</div>
			<p><a class="log_btn submit_shq" id="bodiesLTRZBtnBc">保存</a><a class="log_btn submit_shq" id="bodiesLTRZBtnCz">重置</a><a class="log_btn submit_shq" id="bodiesLTRZBtn">完成</a></p>
   	   </div>
  </div>
</div>
</div>
<div class="bgpop"></div>

<input type="hidden" id="concern_industry"/>
<input type="hidden" id="concern_industry1"/>
<input type="hidden" id="concern_industry3"/>
<input type="hidden" id="concern_industry4"/>
<input type="hidden" id="orgLoanReceiveProve1"/>
<input type="hidden" id="orgLoanReceiveProve2"/>
<input type="hidden" id="orgLoanReceiveProve3"/>
<input type="hidden" id="orgLoanReceiveProve4"/>
<input type="hidden" id="orgLoanReceiveProve5"/>
<input type="hidden" id="orgLoanReceiveProve6"/>
<input id="indexFor" type="hidden" namefor="centerRZ"/>
<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/centerRZ.js"></script>