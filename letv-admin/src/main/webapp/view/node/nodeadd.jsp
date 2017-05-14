<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var nodeId='${id}';
</script>
	
	<div id="nodeDiv" class="add" style="display: block; overflow:auto; width: 98%;">
		<form id="nodeForm" method="post" style="margin-top:20px;">
			
			<div class="x-form-item">
				<label class="x-form-item-label">编号:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">节点类型:</label>
				<div class="x-form-element">
					<input id="nodeType_id" class="easyui-combobox"  panelHeight="200px;"  url="<%=path %>/node/getTypeList.html" valueField="code"  textField="name" panelHeight="auto" data-options="emptyItem:{name:'-=请选择=-',code:'',selected:true}" />
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">标题:</label>
				<div class="x-form-element">
					<input name="title" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">副标题:</label>
				<div class="x-form-element">
					<input name="subtitle" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">语言:</label>
				<div class="x-form-element">
					<input name="language" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">关键词:</label>
				<div class="x-form-element">
					<input name="keywords" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">状态:</label>
				<div class="x-form-element">
					<input name="status" class="easyui-combobox" url="<%=path %>/dictionary/node_node_status.html" panelHeight="auto"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">图片:</label>
				<input type="hidden" name="thumb"/>
				<div id="logoDiv" style="margin-left: 10px;">
					 <!--用来存放item-->
					<div class="filelist"></div>
					<div id="logoBtn" style="margin-left: 100px;">选择图片</div>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">描述:</label>
				<div class="x-form-element">
					<textarea rows="8" name="description" style="width: 800px;" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">文章内容:</label>
				<div class="x-form-element">
					<script id="body" type="text/plain" style="width:950px;height:500px;"></script>
				</div>
			</div>
		</form>
	</div>
	<div class="psb" style="margin-top:10px; margin-bottom: 10px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a><a id="returnBtn" href="<%=path %>/node.node.html" class="easyui-linkbutton searchBtn">返回</a></div>
	<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
	<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
	<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/node/nodeadd.js"></script>