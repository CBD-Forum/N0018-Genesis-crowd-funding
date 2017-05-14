<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var transferNo='${transferNo}';
</script>

<style type="text/css">
<!--
.x-form-item label.x-form-item-label{
	width: 100px;
}
.errorMsg {
  margin-left: 112px;
  color: #FF0000;
  width: 300px;
}
-->
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript" src="<%=path%>/js/crowdfund/crowdfundTransferEdit.js"></script>
	<div id="add" style="display: block; overflow: hidden; padding: 0;">
		<div id="tt"> 
			
			<div id="baseInfo" title="基本信息" style="padding:20px;">  
				<form id="transferForm" method="post">
					<input type="hidden" name="id" />
					<input type="hidden" name="orderNo" id="orderNo" />
					<div class="x-form-item">
						<label class="x-form-item-label">挂牌编号:</label>
						<div class="x-form-element">
							<label id="transferNo">${transferNo}</label>
						</div>
					</div>
		        	<div class="x-form-item">
						<label class="x-form-item-label">申请人:</label>
						<div class="x-form-element">
							<input type="text" name="userId" disabled="disabled"/>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">挂牌份数:</label>
						<div class="x-form-element">
							<input type="text" name="transferParts" />
						</div>
						<div class="errorMsg"></div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">挂牌价格:</label>
						<div class="x-form-element">
							<input type="text" name="partMoney" id="partMoney"/>
						</div>
						<div class="errorMsg"></div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">挂牌天数:</label>
						<div class="x-form-element">
							<input type="text" name="transferDay" />
						</div>
					</div>
					
					<div class="psb" style="margin-top:30px;"><a id="basicBtn" class="easyui-linkbutton searchBtn">确定</a><a id="returnBtn" href="<%=path %>/crowdfund.crowdfundTransferList.html" class="easyui-linkbutton searchBtn">返回</a></div>
				</form>
		    </div>  
		</div> 					
	</div>
	
