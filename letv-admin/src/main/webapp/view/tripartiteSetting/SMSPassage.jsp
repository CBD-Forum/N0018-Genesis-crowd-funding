<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/tripartiteSetting/SMSPassage.js"></script>
	<div id="add" style="display: block; overflow: auto; height: auto; width: 90%;">
		<form id="sms_1_server" method="post" style="margin-top:20px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">主短信服务器地址:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
		</form>
		<form id="sms_1_account" method="post" style="margin-top:10px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">主用户名:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
		</form>
		<form id="sms_1_password" method="post" style="margin-top:10px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">主密码:</label>
				<div class="x-form-element">
					<input name="code" type="password"/>
				</div>
			</div>
		</form>
		
		<form id="sms_2_server" method="post" style="margin-top:20px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">备用短信服务器地址:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
		</form>
		<form id="sms_2_account" method="post" style="margin-top:10px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">备用用户名:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
		</form>
		<form id="sms_2_password" method="post" style="margin-top:10px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">备用密码:</label>
				<div class="x-form-element">
					<input name="code" type="password"/>
				</div>
			</div>
		</form>
		
	</div>
	<div class="psb" style="margin-top:30px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a></div>
