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
.lch_nav .lch5,.lch_nav .lch2{cursor: pointer;}
</style>

<div class="back_colh">
  <div class="box gy_info">
   <h3>非公开融资</h3>
   <ul class="lch_nav clearfix">
    <li class="lch5">1. 填写项目基本信息</li>
	<li class="lch2">2. 融资信息</li>
	<li class="lch4">3. 创始人信息</li>
	<li class="lch3">4. 完成</li>
   </ul>
   <div class="gy_infom clearfix" style="width:990px;">
   <div class="mb18 clearfix">
	 <span class="fl"><em>*</em>  团队介绍：</span>
	 <textarea class="fl" id="companyTeam" nullMessage="团队介绍不能为空"></textarea>
	 <p class="fl col_gray" style="margin-top:87px;">文字不超过300字</p>
	</div>
    <div class="mb18 clearfix fl" style="width: 350px;">
	 <span class="fl"><em>*</em> 姓名：</span>
	 <input type="text"  placeholder="请输入姓名" class="fl wd140" id="loanName"  nullMessage="姓名不能为空" />
	</div>
	<div class="mb18 clearfix fl" style="width: 350px;">
	 <span class="fl"><em>*</em> 团队中的职位：</span>
	 <input type="text"  placeholder="请输入职位" class="fl wd140" id="position"  nullMessage="职位不能为空" />
	</div>
	<div class="mb18 clearfix fl" style="width: 200px;">
	 <a class="save_btn fl" style="margin:0; height: 35px; line-height: 35px; width: 150px;margin-left: 20px;" id="saveFounderBtn">添加</a>
	</div>
	<div class="founder fl" id="founderWorkList">
		
	</div>
	
	
	<div class="mb18 clearfix" style="float: left;width: 100%;">
	<a class="save_btn fl" style="margin: 30px 0 0 50px;" id="browseBlank" target="_blank">浏览</a>
	<a class="save_btn fl" style="margin: 30px 0 0 50px;" id="preservationBtn">保存</a>
	<a class="save_btn fl" style="margin: 30px 0 0 50px;" id="fouderInformationBtn">提交</a>
	 
	 <a class="save_btn fl" style="margin: 30px 0 0 50px;display:none;" id="browseBtn">浏览</a>
	 
	</div>
   </div>
  </div>
 </div>

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
<div id="work" style="display:none;">
<p class="fl">
<span>开始时间：</span>
<input type="text" class="wd165 calend_bg" id="workStart" nullMessage="开始时间不能为空"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'workEnd\')||\'2020-10-01\'}'})"/>
</p>
<p class="fr mr0">
<span>结束时间：</span>
<input  type="text" class="wd165 calend_bg" id="workEnd" nullMessage="结束时间不能为空" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'workStart\')}',maxDate:'2020-10-01'})"/>
</p>
</div>
<div id="startup" style="display:none;">
<p class="fl">
<span>开始时间：</span>
<input type="text" class="wd165 calend_bg" id="startupStart" nullMessage="开始时间不能为空"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'startupEnd\')||\'2020-10-01\'}'})"/>
</p>
<p class="fr mr0">
<span>结束时间：</span>
<input  type="text" class="wd165 calend_bg" id="startupEnd" nullMessage="结束时间不能为空" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startupStart\')}',maxDate:'2020-10-01'})"/>
</p>
</div>
<div id="education" style="display:none;">
<p class="fl">
<span>开始时间：</span>
<input type="text" class="wd165 calend_bg" id="educationStart" nullMessage="开始时间不能为空"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'educationEnd\')||\'2020-10-01\'}'})"/>
</p>
<p class="fr mr0">
<span>结束时间：</span>
<input  type="text" class="wd165 calend_bg" id="educationEnd" nullMessage="结束时间不能为空" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'educationStart\')}',maxDate:'2020-10-01'})"/>
</p>
</div>
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" src="<%=path%>/js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/enterStock-founder.js"></script>