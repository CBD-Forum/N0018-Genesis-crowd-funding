<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<script type="text/javascript">
var loanNo = '${loanNo}';
</script>
<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/investAfter.js"></script>

	<table id="investAfterTable"></table>
	
	<div id="add" style="display: none;">
		<form id="investAfterForm" method="post" action="" style="margin-top:10px;">
		    <input type="hidden" name="id" id="investAfter_id">
			<div class="x-form-item">
				<label class="x-form-item-label" style="width:118px;">项目编号:</label>
				<div class="x-form-element">
				    <input type="hidden" name="loanNo" id="form_loanNo">
					<label id="show_loanNo"></label>
				</div>
			</div>
			<!-- <div class="x-form-item">
				<label class="x-form-item-label" style="width:118px;">时间节点:</label>
				<div class="x-form-element">
					 <input type="text" name="timeNode" id="timeNode" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="margin-left:0; width:240px;"/>
				</div>
			</div> -->
			<div class="x-form-item">
				<label class="x-form-item-label" style="width:118px;">内容:</label>
				<div class="x-form-element">
					<textarea id="content" name="content" rows="8" cols="80"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
			    <label class="x-form-item-label"  style="width:118px;">图片资料:</label>
			    <input name="picUrl" type="hidden"/>
			    <div class="x-form-element">
			        <div id="picUrlDiv" style="margin-left: 10px;">
			            <div class="filelist"></div>
			            <a href="javascript:void(0)" id="picUrlBtn" class="uploadBtn">上传图片</a>
			         </div>
			    </div>
		    </div>  
			<div class="x-form-item">
				<label class="x-form-item-label"  style="width:118px;">其他附件:</label>
				<div class="x-form-element">
					<a id="otherFileChangeUpload" class="webuploader-pick" style="margin-left:27px;width:72px;">选择文件</a>
					<div id="otherFileUploadInFo" ></div>
					<input id="otherFileToUpload" type="file" style="display:none" name="file" style="float:right;"> 
				</div>
			</div>	
		   <div class="psb" style="margin-top:30px;"><a id="investAfterBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a></div>
		</form>
	</div>
	
	
	
	<div id="crowdfund_investAfter_detail_div" style="display: none;">
	  <br/>
	  <br/>
		<div class="x-form-item">
			<label class="x-form-item-label" style="width:118px;">项目编号:</label>
			<div class="x-form-element">
				<label id="show_detail_loanNo"></label>
			</div>
		</div>
		<div class="x-form-item">
			<label class="x-form-item-label" style="width:118px;">内容:</label>
			<div class="x-form-element">
				<label id="show_content"></label>
			</div>
		</div>
		
		<div class="x-form-item">
		    <label class="x-form-item-label"  style="width:118px;">图片资料:</label>
		    <div class="x-form-element">
		        <div id="picUrlDiv2" style="margin-left: 10px;">
		            <div class="filelistShow clearfix" id="picFilelistShow"></div>
		         </div>
		    </div>
	   </div>
	   
		<div class="x-form-item">
		    <label class="x-form-item-label"  style="width:118px;">附件资料:</label>
		    <div class="x-form-element">
		        <div id="picUrlDiv" style="margin-left: 10px;">
		            <div class="filelistShow" id="otherFilelistShow">
		            </div>
		         </div>
		    </div>
	   </div>	   
	   
	</div>	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    <security:authorize access="hasPermission(null, 'security.operation.crowdfund_investAfter_add')">
		<input type="hidden" id="crowdfund_investAfter_add"/>
	</security:authorize> 
	
	
	
	
	<script type="text/javascript">
	function operateData(val,row,index){
		var returnStr = '';
		var state = row["state"];
/* 		if(state == 'auditing'){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_progress_pass\')"><a onclick=pass("'+row["id"]+'")>通过</a></security:authorize>';
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_progress_refuse\')"><a onclick=refuse("'+row["id"]+'")>拒绝</a></security:authorize>';
		} */
		//returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_investAfter_delete\')"><a onclick=deleteProgress("'+row["id"]+'")>删除</a></security:authorize>';
		
		returnStr+='<a onclick=deleteInvestAfter("'+row["id"]+'")>删除</a>';
		
		returnStr+='<a onclick=showDetail("'+row["id"]+'")>查看</a>';
		return returnStr;
	}
	</script>