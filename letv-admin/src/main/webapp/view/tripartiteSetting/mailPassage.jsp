<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/tripartiteSetting/mailPassage.js"></script>
	<div id="add" style="display: block; overflow: auto; width: 100%;">
		<form id="email_emailWebsite" method="post" style="margin-top:20px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">邮箱登陆网址:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
		</form>
		<form id="email_account" method="post" style="margin-top:10px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">Email:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
		</form>
		<form id="email_password" method="post" style="margin-top:10px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">密码:</label>
				<div class="x-form-element">
					<input name="code" type="password"/>
				</div>
			</div>
		</form>
		<form id="email_pop3_server" method="post" style="margin-top:10px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">POP3服务器:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
		</form>
		<form id="email_stmp_server" method="post" style="margin-top:10px;">
			<input name="id" type="hidden" />
			<div class="x-form-item">
				<label class="x-form-item-label">SMTP服务器:</label>
				<div class="x-form-element">
					<input name="code" type="text"/>
				</div>
			</div>
		</form>
	</div>
	<div class="psb" style="margin-top:30px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a></div>
