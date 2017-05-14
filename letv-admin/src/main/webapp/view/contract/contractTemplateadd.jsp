<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var conId='${id}';
</script>
	
	<div id="addContractTpl" style="display: block; overflow: auto;" class="add">
		<form id="contractTemplateForm" method="post">
			<div class="x-form-item">
				<label class="x-form-item-label">合同编号:</label>
				<div class="x-form-element">
					<input name="contractNo" type="text"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">合同名称:</label>
				<div class="x-form-element">
					<input name="contractName" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">合同类型:</label>
				<div class="x-form-element">
					<input name="contractType" class="easyui-combobox" url="<%=path %>/dictionary/contract_template_type.html" />
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">状态:</label>
				<div class="x-form-element">
					<input name="status" class="easyui-combobox" url="<%=path %>/dictionary/node_node_status.html" panelHeight="auto"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">描述:</label>
				<div class="x-form-element">
					<textarea rows="3" name="description" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">模版内容:</label>
				<div class="x-form-element">
					<script id="templateContent" type="text/plain" style="width:950px;height:400px;"></script>
				</div>
			</div>
		</form>
		<div class="psb" style="margin-top:10px; margin-bottom: 10px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a><a id="returnBtn" href="<%=path %>/contract.contractTemplate.html" class="easyui-linkbutton searchBtn">返回</a></div>
	</div>
	
	
	<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/contract/contractTemplateadd.js"></script>
    
    
    <script type="text/javascript">
	    //实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	    var ue = UE.getEditor('templateContent');
    </script>