<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var id = '${id}';
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/user/userEdit.js"></script>
	<div id="add" style="display: block; overflow: auto; width: 100%;">
		<form id="userForm" method="post" style="margin-top:20px;">
			
			<div class="x-form-item">
				<label class="x-form-item-label">用户名:</label>
				<div class="x-form-element">
					<input id="userId" name="userId" type="text" disabled="disabled"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">汇付账号:</label>
				<div class="x-form-element">
					<input name="thirdAccount" type="text" disabled="disabled"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">邮箱:</label>
				<div class="x-form-element">
					<input name="email" type="text"/>
				</div>
			</div>
			
			
			<div class="x-form-item">
				<label class="x-form-item-label">真实姓名:</label>
				<div class="x-form-element">
					<input name="realName" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">昵称:</label>
				<div class="x-form-element">
					<input name="nickName" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">性别:</label>
				<div class="x-form-element">
					<input type="radio" name="sex" value="M" style="width: 20px; border: 0; height: auto;"/>男
					<input type="radio" name="sex" value="F" style="width: 20px; border: 0; height: auto;"/>女
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">生日:</label>
				<div class="x-form-element">
					<input type="text" name="birthday" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">QQ账号:</label>
				<div class="x-form-element">
					<input name="qqNo" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">微博账号:</label>
				<div class="x-form-element">
					<input name="weiboNo" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">手机号:</label>
				<div class="x-form-element">
					<input name="mobile" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">座机号:</label>
				<div class="x-form-element">
					<input name="tel" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">身份证号:</label>
				<div class="x-form-element">
					<input name="certificateNo" type="text"/>
				</div>
			</div>
			
			<!-- <div class="x-form-item">
				<label class="x-form-item-label">国家:</label>
				<div class="x-form-element">
					<input name="country" type="text"/>
				</div>
			</div> -->
			
			<div class="x-form-item">
				<label class="x-form-item-label">公司所在省:</label>
				<div class="x-form-element">
					<select id="s_provice" name="province"></select>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">公司所在城市:</label>
				<div class="x-form-element">
					<select id="s_city" name="city"></select>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">县:</label>
				<div class="x-form-element">
					<select id="s_county" name="county"></select>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">家庭住址:</label>
				<div class="x-form-element">
					<input name="homeAddress" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">家庭住址邮编:</label>
				<div class="x-form-element">
					<input name="postCode" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">头像:</label>
				<input type="hidden" name="photo"/>
				<div class="x-form-element">
					<div id="photoDiv" style="margin-left: 10px;">
					    <!--用来存放item-->
					    <div class="filelist"></div>
					    <div id="photoBtn" style="margin-left: 16px;">选择图片</div>
					</div>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">认证时间:</label>
				<div class="x-form-element">
					<input name="authTime" type="text" disabled="disabled"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">用户等级:</label>
				<div class="x-form-element">
					<input id="userLevel" name="userLevel" class="easyui-combobox" url="<%=path %>/dictionary/user_level.html" panelHeight="auto"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">开户时间:</label>
				<div class="x-form-element">
					<input name="openAccTime" type="text" disabled="disabled"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">邀请码:</label>
				<div class="x-form-element">
					<input name="inviteCode" type="text" disabled="disabled"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">创建时间:</label>
				<div class="x-form-element">
					<input name="createTime" type="text" disabled="disabled"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">是否为借款人:</label>
				<div class="x-form-element">
					<select name="isBorrower">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">紧急联系人:</label>
				<div class="x-form-element">
					<input name="emergencyContact" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">紧急联系人电话:</label>
				<div class="x-form-element">
					<input name="emergencyPhone" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">紧急联系人关系:</label>
				<div class="x-form-element">
					<select name="emergencyRelation">
						<option value="">-=请选择=-</option>
						<option value="亲属">亲属</option>
						<option value="朋友">朋友</option>
						<option value="同事">同事</option>
					</select>
				</div>
			</div>
		</form>
	</div>
	<div class="psb" style="margin-top:30px;">
		<a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a>
		<a id="returnBtn" href="<%=path %>/user.userlist.html" class="easyui-linkbutton searchBtn">返回</a>
	</div>
	