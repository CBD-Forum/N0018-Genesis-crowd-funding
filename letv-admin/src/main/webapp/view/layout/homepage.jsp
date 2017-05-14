<%@page import="com.fbd.core.app.user.model.AdminUserDetails"%>
<%@page import="com.fbd.admin.web.MyRequestContextHolder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
AdminUserDetails adminUser = MyRequestContextHolder.getCurrentUser();
%>
<script type="text/javascript">
var adminId = '<%=adminUser.getAdminId() %>';
var postCode = '<%=adminUser.getPostCode()%>';
</script>
	<script type="text/javascript" src="<%=path%>/js/layout/homepage.js"></script>
	<div class="htmlW">
		<div class="content clearfix">
	    	<div class="personInfo left">
	        	<div class="title tit1"><span>个人信息</span></div>
	            <ul class="ul1">
	            	<li>用户名<span><%=adminUser.getAdminId() %></span></li>
	                <li>真实姓名<span><%=adminUser.getRealName() %></span></li>
	                <li>部门<span><%=adminUser.getDeptText() %></span></li>
	                <li>状态<span><%=adminUser.getStatusName() %></span></li>
	                <li>员工号<span><%=adminUser.getEmployeeNo() %></span></li>
	                <li>邮箱<span id="emailLi"><%=adminUser.getEmail() %></span></li>
	                <li>手机号<span id="phoneLi"><%=adminUser.getMobile() %></span></li>
	                <li>职位<span title="<%=adminUser.getPost() %>" style="width:190px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" ><%=adminUser.getPost() %></span></li>
	            </ul>
	            <a href="javascript:void(0);" class="a_1" id="emailBtn">修改邮箱</a><a id="phoneBtn" href="javascript:void(0);" class="a_2">修改手机号</a><a id="passBtn" href="javascript:void(0);" class="a_3">修改密码</a>
	        </div>
	        <div class="personInfo left info2">
	        	<div class="title tit2"><span>待办事项</span></div>
	            <div class="ul2">
	            	<table id="todoTable"></table>
	            </div>
	        </div>
	        <div class="personInfo left info2">
	        	<div class="title tit3"><span>最新操作日志</span></div>
	            <div class="ul2">
	            	<table id="operateLog"></table>
	            </div>
	        </div>
	    </div>
	    <div id="statistic" class="tongjiDiv clearfix">
	    	<div class="tongji left tj1">
	            <div class="title "><span class="tit_1">用户统计</span></div>
	            <p class="tj1">
	                	会员:<label id="totalUser"></label>&nbsp;&nbsp;
				今日新注册投资人:<label id="todayUser"></label>&nbsp;&nbsp;
				总管理员:<label id="adminUser"></label>&nbsp;&nbsp;
				认证会员:<label id="countAuthed"></label>&nbsp;&nbsp;
				领投人:<label id="countLead"></label>&nbsp;&nbsp;
	            </p>
	        </div>
	       <div class="tongji left tj2 tongjiM">
	            <div class="title "><span class="tit_2">项目统计</span></div>
	            <p class="tj2">
	               	 总众筹项目:<label id="totalLoan"></label>&nbsp;&nbsp;
				已发布众筹项目:<label id="publishLoan"></label>&nbsp;&nbsp;
				未发布众筹项目:<label id="unpublishedLoan"></label>&nbsp;&nbsp;
				正在筹款的项目:<label id="fundingLoan"></label>&nbsp;&nbsp;
				众筹完成的项目:<label id="repayedLoan"></label>&nbsp;&nbsp;
	            </p>
	        </div>
	        <div class="tongji right tj3 tongjiM">
	            <div class="title "><span class="tit_3">财务统计</span></div>
	            <p class="tj3">
	                	投资总额:<label id="invest_amt"></label>&nbsp;&nbsp;
		           	 充值总额:<label id="recharge_amt"></label>&nbsp;&nbsp;
					已提现总额:<label id="withdraw_amt"></label>
	            </p>
	        </div>
	    </div>
	</div>
	
	<div id="homePageDiv">
		
		<input id="userId" type="hidden" value="<%=adminUser.getId() %>"/>
	
		<div id="emailWin" class="add">
			<form id="emailForm" method="post">
				<div class="x-form-item">
					<label class="x-form-item-label">原邮箱:</label>
					<div class="x-form-element">
						<input type="text" id="oldEmail" disabled="disabled" value="<%=adminUser.getEmail() %>"/>
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">邮箱验证码:</label>
					<div class="x-form-element authCode">
						<span class="yzm"><input type="text" id="emailOldVerifyCode" name="emailOldVerifyCode" class="authInput" maxlength="6" placeholder="邮箱验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getOldEmailAuthCode()"/></span>
					</div>
					<div class="errorMsg"></div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">新邮箱:</label>
					<div class="x-form-element">
						<input type="text" id="newEmail" name="newEmail" placeholder="新邮箱"/>
					</div>
					<div class="errorMsg"></div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">邮箱验证码:</label>
					<div class="x-form-element authCode">
						<span class="yzm"><input type="text" id="emailNewVerifyCode" name="emailNewVerifyCode" class="authInput" maxlength="6" placeholder="邮箱验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getNewEmailAuthCode()"/></span>
					</div>
					<div class="errorMsg"></div>
				</div>
				
				<div class="psb">
					<a id="emailSaveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="emailCloseBtn" class="easyui-linkbutton searchBtn">取消</a>
				</div>
			</form>
		</div>
		<div id="phoneWin" class="add">
			<form id="phoneForm" method="post">
				<div class="x-form-item">
					<label class="x-form-item-label">原手机号:</label>
					<div class="x-form-element">
						<input type="text" id="oldMobile" disabled="disabled" value="<%=adminUser.getMobile() %>"/>
					</div>
					<div class="errorMsg"></div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">短信验证码:</label>
					<div class="x-form-element authCode">
						<span class="yzm"><input type="text" id="oldVerifyCode" name="oldVerifyCode" class="authInput" placeholder="手机验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getOldAuthCode();"/></span>
					</div>
					<div class="errorMsg"></div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">新手机号:</label>
					<div class="x-form-element">
						<input type="text" id="newMobile" name="newMobile" placeholder="新手机号"/>
					</div>
					<div class="errorMsg"></div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">短信验证码:</label>
					<div class="x-form-element authCode">
						<span class="yzm"><input type="text" id="newVerifyCode" name="newVerifyCode" class="authInput" placeholder="手机验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getNewAuthCode();"/></span>
					</div>
					<div class="errorMsg"></div>
				</div>
				
				<div class="psb">
					<a id="phoneSaveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
				</div>
			</form>
		</div>
		<div id="passWin" class="add">
			<form id="passForm" method="post">
				<div class="x-form-item">
					<label class="x-form-item-label">用户名:</label>
					<div class="x-form-element">
						<label><%=adminUser.getAdminId() %></label>
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">员工号:</label>
					<div class="x-form-element">
						<label><%=adminUser.getEmployeeNo() %></label>
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">旧密码:</label>
					<div class="x-form-element">
						<input type="password" id="oldPassword" name="oldPassword" placeholder="旧密码"/>
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">新密码:</label>
					<div class="x-form-element">
						<input type="password" id="newPassword" name="newPassword" placeholder="新密码"/>
					</div>
				</div>
				<div class="x-form-item">
					<label class="x-form-item-label">确认密码:</label>
					<div class="x-form-element">
						<input type="password" id="confirmPassword" name="confirmPassword" placeholder="重复密码"/>
					</div>
				</div>
				
				<div class="psb">
					<a id="pwdSaveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="pwdCloseBtn" class="easyui-linkbutton searchBtn">取消</a>
				</div>
			</form>
		</div>
	</div>
	