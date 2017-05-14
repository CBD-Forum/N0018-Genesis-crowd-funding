<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/banner/banner.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>编号:</label><input name="code" type="text"/></div>
			<div><label>描述:</label><input name="description" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	
	<div id="modelDiv" class="add">
		<form id="bannerForm" method="post">
			<input type="hidden" name="id" id="id" />
			<div class="x-form-item">
				<label class="x-form-item-label">编码:</label>
				<div class="x-form-element">
					<input type="text" id="code" name="code" placeholder="编码"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">描述:</label>
				<div class="x-form-element">
					<textarea rows="3" id="add_area" name="description" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">图片信息:</label>
				<div class="x-form-element">
					<div id="uploadImgDiv">
						<a href="javascript:void(0)" id="uploadImgBtn" class="uploadBtn">上传图片</a>
						<div class="filelist"></div>
					</div>
				</div>
			</div>
		</form>
		<div class="psb" style="margin:10px 0;">
			<a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a>
			<a id="closeBtn" class="easyui-linkbutton searchBtn">返回</a>
		</div>
	</div>
	
	<security:authorize access="hasPermission(null, \'security.operation.spread_banner_add\')">
		<input type="hidden" id="spread_banner_add"/>
	</security:authorize>
	
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
			//修改
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.spread_banner_modify\')"><a onclick=editBanner("'+row.id+'")>修改</a></security:authorize>';
			//删除
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.spread_banner_delete\')"><a onclick=removeBanner("'+row.id+'")>删除</a></security:authorize>';
			return returnStr;
		}
	</script>
	