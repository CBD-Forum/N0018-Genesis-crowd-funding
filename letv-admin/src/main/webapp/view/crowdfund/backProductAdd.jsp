<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = "${loanNo}";
</script>

<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<<style>
<!--
.x-form-item label.x-form-item-label{
	width: 100px;
}
-->
</style>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/backProductAdd.js"></script>
	<div class="prodiv" style="height:450px; overflow: auto;">
			<table id="backTable"></table>	
			<div id="draft_basic" style="padding:20px 10px;border-bottom:1px solid #CCC;">
				<form id="baseForm" method="post">
					<input type="hidden" name="id" id="back_id"/>
				    <input type="hidden" name="loanNo" id="loan_no"/>
		        	<div class="x-form-item">
						<label class="x-form-item-label">支持金额:</label>
						<div class="x-form-element">
							<input type="text" name="amt" />元
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">支持名额:</label>
						<div class="x-form-element">
							<input type="text" name="numberLimits"/>个 <span style="color:#cccccc"></span> 
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">回报时间:</label>
						<div class="x-form-element">
							<input type="text" name="backTime"/>天<span style="color:#cccccc">“0”为项目结束后立即发送</span> 
						</div>
					</div>
<!-- 					<div class="x-form-item">
						<label class="x-form-item-label" style="width:80px;">转让锁定天数:</label>
						<div class="x-form-element">
							<input type="text" name="transferLockPeriod" />天
						</div>
					</div> -->
<!-- 					<div class="x-form-item">
						<label class="x-form-item-label">预计每日收益:</label>
						<div class="x-form-element">
							<input type="text" name="dailyProfit" />元
						</div>
					</div> -->

					<div class="x-form-item">
						<label class="x-form-item-label">参与抽奖标识:</label>
						<div class="x-form-element">
							<input type="radio" name="prizeDrawFlag"  checked="checked" value="0" style="width: 13px;height: 13px;margin: 0 5px;"/>不参与
							<input type="radio" name="prizeDrawFlag"  value="1" style="width: 13px;height: 13px;margin: 0 5px;"/>参与
						</div>
					</div>
					
					<div class="x-form-item" id="prizeDiv" >
						<label class="x-form-item-label">抽奖中奖名额:</label>
						<div class="x-form-element">
							<input type="text" name="prizeNum"/></span> 
						</div>
						
						<label class="x-form-item-label">抽奖人购买上限:</label>
						<div class="x-form-element">
							<input type="text" name="prizeInvestNum"/></span> 
						</div>
						
						<label class="x-form-item-label">激活抽奖份数上限:</label>
						<div class="x-form-element">
							<input type="text" name="prizeFullNum"/></span> 
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">回报类型:</label>
						<div class="x-form-element">
						    <input type="radio" name="backType" checked value="S" style="width: 13px;height: 13px;margin: 0 5px;"/>实物回报(需要快递/邮寄的)
							<input type="radio" name="backType"  value="X" style="width: 13px;height: 13px;margin: 0 5px;"/>虚拟回报(不需要快递/邮寄的,电子照片等)
							 
						</div>
					</div>					
					<div class="x-form-item" id="transFeeDiv" >
						<label class="x-form-item-label">运费:</label>
						<div class="x-form-element">
							<input type="text" name="transFee"/>元<span style="color:#cccccc">“0”为不收运费</span> 
						</div>
					</div>
					
					<!-- <div class="x-form-item">
						<label class="x-form-item-label">回报展示类别:</label>
						<div class="x-form-element">
							<input type="radio" name="backLable" checked value="P" style="width: 13px;height: 13px;margin: 0 5px;"/>普通档
							<input type="radio" name="backLable"  value="M" style="width: 13px;height: 13px;margin: 0 5px;"/>手机档
						</div>
					</div> -->
					
					<input type="hidden" name="backLable" value="P"></input>
					<div class="x-form-item">
					    <label class="x-form-item-label">回报内容:</label>
					    <div class="x-form-element">
					        <textarea rows="10" name="backContent" style="width: 500px;"></textarea>
					    </div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">回报图片:</label>
						<input type="hidden" name="photoUrl1"/>
						<div class="x-form-element">
							<div id="photoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
					</div>
				</form>
				 <div style="text-align:center;width:360px;">
				    <a  class="easyui-linkbutton c7" style="width:120px;margin-top:20px;" id="basicBtn">保存</a>
			     </div>
			   </div>	
		</div> 					
	       
