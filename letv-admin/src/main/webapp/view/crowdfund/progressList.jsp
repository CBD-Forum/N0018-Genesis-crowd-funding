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
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/progressList.js"></script>



<%-- 	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input id="loanNo" name="loanNo" type="text"/></div>
			<div><label>状态:</label>
			<input id="state" type="text" style="height: 24px;" name="state" class="easyui-combobox" url="<%=path %>/dictionary/crowdfund_audit_state.html" panelHeight="auto"/>
			</div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form> --%>
	<table id="progectTable"></table>
	
	
	
	
	<div id="crowdfund_progress_detail" style="display: none;">
		<form id="ProgressDetailForm" method="post" action="" style="margin-top:10px;">
		    <input type="hidden" name="id" id="progress_id">
			<div class="x-form-item">
				<label class="x-form-item-label" style="width:118px;">项目编号:</label>
				<div class="x-form-element">
				    <input type="hidden" name="loanNo" id="form_loanNo">
					<label id="show_loanNo"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label" style="width:118px;">时间节点:</label>
				<div class="x-form-element">
					 <input type="text" name="timeNode" id="timeNode" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="margin-left:0; width:240px;"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label" style="width:118px;">项目进展:</label>
				<div class="x-form-element">
					<textarea id="enterContent" name="enterContent" rows="8" cols="80"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
			    <label class="x-form-item-label"  style="width:118px;">图片资料:</label>
			    <input name="imgFiles" type="hidden"/>
			    <div class="x-form-element">
			        <div id="imgFilesDiv" style="margin-left: 10px;">
			            <div class="filelist"></div>
			            <a href="javascript:void(0)" id="imgFilesBtn" class="uploadBtn">上传图片</a>
			         </div>
			    </div>
		   </div>
		 <!--   <style>
		   .vedio .operate{position: absolute; top: 0; left: 0; width: 100%; height: 0px; background: rgba( 0, 0, 0, 0.5 ); z-index: 300;}
			.vedio .operate span{background: url('../images/icons.png') no-repeat; width: 24px; height: 0px; cursor: pointer; float: right; overflow: hidden; margin: 5px 1px 1px;}
			.vedio .operate span.cancel{background-position: -48px -24px;}
			.vedio .operate span.cancel:hover { background-position: -48px 0;}
		   </style>
		   <div class="x-form-item">
			    <label class="x-form-item-label"  style="width:118px;">视频资料:</label>
			    <input name="vedioFile" type="hidden"/>
			    <div class="x-form-element">
			        <div id="idCardScanDiv" style="margin-left: 10px;">
			            <div class="vedioFilelist clearfix" id="vedioFilelist">
			              <video id="imghead" src="" width="320" height="240" controls="controls"></video>
			              <video id="imghead" src="" width="320" height="240" controls="controls"></video>
			            </div>
			            <a href="javascript:void(0)" id="fileUpload" class="uploadBtn">上传视频</a>
			         </div>
			    </div>
		   </div> -->
		   <div class="psb" style="margin-top:30px;"><a id="ProgressBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a></div>
		</form>
		
		<form id="imgForm">
			<input id="vedioFile" type="file" name="file" accept="video/*" style="display:none;"/>
		</form>
	</div>
	
	
	
	
	<div id="crowdfund_progress_detail_div" style="display: none;">
		<div class="x-form-item">
			<label class="x-form-item-label" style="width:118px;">项目编号:</label>
			<div class="x-form-element">
				<label id="show_detail_loanNo"></label>
			</div>
		</div>
		<div class="x-form-item">
			<label class="x-form-item-label" style="width:118px;">时间节点:</label>
			<div class="x-form-element">
		    	<label id="show_timeNode"></label>
			</div>
		</div>
		<div class="x-form-item">
			<label class="x-form-item-label" style="width:118px;">项目进展:</label>
			<div class="x-form-element">
				<label id="show_enterContent"></label>
			</div>
		</div>
		
		<div class="x-form-item">
		    <label class="x-form-item-label"  style="width:118px;">图片资料:</label>
		    <div class="x-form-element">
		        <div id="imgFilesDiv" style="margin-left: 10px;">
		            <div class="filelistShow" id="filelistShow"></div>
		         </div>
		    </div>
	   </div>
	  <!--  <style>
	   .vedio .operate{position: absolute; top: 0; left: 0; width: 100%; height: 0px; background: rgba( 0, 0, 0, 0.5 ); z-index: 300;}
		.vedio .operate span{background: url('../images/icons.png') no-repeat; width: 24px; height: 0px; cursor: pointer; float: right; overflow: hidden; margin: 5px 1px 1px;}
		.vedio .operate span.cancel{background-position: -48px -24px;}
		.vedio .operate span.cancel:hover { background-position: -48px 0;}
	   </style>
	   <div class="x-form-item">
		    <label class="x-form-item-label"  style="width:118px;">视频资料:</label>
		    <div class="x-form-element">
		        <div id="idCardScanDiv" style="margin-left: 10px;">
		            <div class="vedioFilelist clearfix" id="vedioFilelistShow">
		            </div>
		         </div>
		    </div>
	   </div> -->
	</div>	
	
	
	
	
			
		
	
	
	
	
	
	
	
    <security:authorize access="hasPermission(null, 'security.operation.crowdfund_progress_add')">
		<input type="hidden" id="crowdfund_progress_add"/>
	</security:authorize>
	
	
	<script type="text/javascript">
	function operateData(val,row,index){
		var returnStr = '';
		var state = row["state"];
		if(state == 'auditing'){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_progress_pass\')"><a onclick=pass("'+row["id"]+'")>通过</a></security:authorize>';
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_progress_refuse\')"><a onclick=refuse("'+row["id"]+'")>拒绝</a></security:authorize>';
		}
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_progress_delete\')"><a onclick=deleteProgress("'+row["id"]+'")>删除</a></security:authorize>';
		
		returnStr+='<a onclick=showDetail("'+row["id"]+'")>查看</a>';
		return returnStr;
	}
	</script>