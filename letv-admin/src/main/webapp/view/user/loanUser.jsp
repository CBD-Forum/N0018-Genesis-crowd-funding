<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/user/loanUser.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>真实姓名:</label><input name="realName" type="text"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>邮箱:</label><input name="email" type="text"/></div>
			<div><label>注册开始时间:</label><input type="text" name="startCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>注册结束时间:</label><input type="text" name="endCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="userTable"></table>
	<div id="userDetail" style="display: none;">
		<div id="user">
		<fieldset  style="width: 350px; float:left; display: inline;">
			<legend>基本信息</legend>
		                用户名: <label id="user_id"></label> <br/>
		                第三方账户: <label id="third_account"></label> <br/>
		                邮箱: <label id="email"></label> <br/>
		                真实姓名: <label id="real_name"></label> <br/>
		                昵称: <label id="nick_name"></label> <br/>
		                性别: <label id="sex"></label> <br/>
		                生日: <label id="birthday"></label> <br/>
		              微博账号: <label id="weibo_no"></label> <br/>
		     手机号     : <label id="mobile"></label> <br/>
		     电话号     : <label id="tel"></label> <br/>
		      证件类型    : <label id="certificate_type"></label> <br/>
		      证件号    : <label id="certificate_no"></label> <br/>
		        所在国家  : <label id="country"></label> <br/>
		      所在省    : <label id="provinceName"></label> <br/>
		      所在城市    : <label id="cityName"></label> <br/>
		      所在县    : <label id="countyName"></label> <br/>
		     家庭住址     : <label id="home_address"></label> <br/>
		      头像    : <img id="photo" alt="头像" style="width: 200px; height: 200px;" /> <br/>
		     认证时间     : <label id="auth_time"></label> <br/>
		        用户等级  : <label id="user_level"></label> <br/>
		     开户时间     : <label id="open_acc_time"></label> <br/>
		     邀请码     : <label id="invite_code"></label> <br/>
		    创建时间      : <label id="create_time"></label> <br/>
		    是否为借款人      : <label id="is_borrower"></label> <br/>
		    紧急联系人      : <label id="emergency_contact"></label> <br/>
		   紧急联系人电话       : <label id="emergency_phone"></label> <br/>
		     紧急联系人关系     : <label id="emergency_relation"></label> <br/>
		      家庭住址邮编    : <label id="post_code"></label> <br/>
  		</fieldset>
		
		<fieldset style="width: 300px; float:left; display: inline;">
			<legend>财务统计</legend>
		            可用余额: <label id="balance"></label> <br/>
		             冻结金额: <label id="frozen_amt"></label> <br/>
		             总投资额: <label id="investMoney"></label> <br/>
		             总待还本金: <label id="repayingPrincipal"></label> <br/>
		             累积收益: <label id="repayedInterest"></label> <br/>
		             待收收益: <label id="repayingInterest"></label> <br/>
		             总充值金额: <label id="actual_amt"></label> <br/>
		             已提现金额: <label id="amt"></label> <br/>
  		</fieldset>
  		
  		<fieldset style="width: 300px; float:left; display: inline;">
			<legend>标的统计</legend>
		            总借款项目:<label id="totalLoan"></label> <br/>
			已发布借款项目:<label id="publishLoan"></label> <br/>
			未发布借款项目:<label id="unpublishedLoan"></label> <br/>
			正在筹款的项目:<label id="fundingLoan"></label> <br/>
			还款完成的项目:<label id="repayedLoan"></label> <br/>
			账单逾期次数:<label id="overdue_times"></label> <br/>
			坏账标数:<label id="baddebtLoan"></label> <br/>
  		</fieldset>
  		
  		<fieldset style="width: 300px; float:left; display: inline;">
			<legend>安全信息</legend>
		            安全问题1: <label id="security_question1"></label> <br/>
		             答案1: <label id="security_answer1"></label> <br/>
		            安全问题2: <label id="security_question2"></label> <br/>
		             答案2: <label id="security_answer2"></label> <br/>
		             上次登录时间: <label id="last_login_time"></label> <br/>
		             当日密码错误次数: <label id="login_failed_num"></label> <br/>
		             登录失败日期: <label id="login_failed_time"></label> <br/>
		             禁用时间: <label id="disable_time"></label> <br/>
		             用户类型: <label id="userTypeText"></label> <br/>
		             状态: <label id="statusName"></label> <br/>
  		</fieldset>
		</div>
		<fieldset id="detail" style="width: 300px; float:left; display: inline;">
			<legend>个人信息</legend>
				用户编号:<label id="userId"></label><br />
				籍贯:<label id="nativePlace"></label><br />
				户口所在地:<label id="huKouPlace"></label><br />
				学历:<label id="degree"></label><br />
				毕业院校:<label id="school"></label><br />
				入学年份:<label id="schoolYear"></label><br />
				婚姻状况:<label id="marriageInfo"></label><br />
				是否有孩子:<label id="childrenInfo"></label><br />
				是否有房子:<label id="hasHouse"></label><br />
				是否有房贷:<label id="hasHousingLoan"></label><br />
				是否有车:<label id="hasCar"></label><br />
				是否有车贷:<label id="hasCarLoan"></label><br />
				直属亲属:<label id="relativeName"></label><br />
				直系亲属关系:<label id="relativeRelation"></label><br />
				直系亲属联系方式:<label id="relativePhone"></label><br />
				其他联系人:<label id="otherRelative"></label><br />
				其他联系人关系:<label id="otherRelation"></label><br />
				其他联系人联系方式:<label id="otherContactPhone"></label><br />
				信用等级:<label id="creditLevel"></label><br />
				信用额度:<label id="creditLimit"></label><br />
				风险等级:<label id="riskLevel"></label><br />
				风险系数:<label id="riskFactor"></label><br />
				审核是否通过:<label id="auditState"></label><br />
				审核信息:<label id="auditOpinion"></label><br />
				审核人编号:<label id="auditor"></label><br />
				审核时间:<label id="auditTime"></label><br />
		</fieldset>		
			

		<fieldset id="authFile"  style="width: 300px; float:left; display: inline;">
			<legend>证件资料</legend>
			用户编号:<label id="userId"></label><br/>
			手持身份证照片:<img id="idCardPhoto" alt="手持身份证照片" title="手持身份证照片" style="width: 200px; height: 200px;"/><br/>
			身份证扫描件:<img id="idCardScan" alt="身份证扫描件" title="身份证扫描件" style="width: 200px; height: 200px;"/><br/>
			银行征信报告:<img id="bankCreditReport" alt="银行征信报告" title="银行征信报告" style="width: 200px; height: 200px;"/><br/>
			户口卡:<img id="huKouScan" alt="户口卡" title="户口卡" style="width: 200px; height: 200px;"/><br/>
			手持户口卡照片:<img id="huKouPhoto" alt="手持户口卡照片" title="手持户口卡照片" style="width: 200px; height: 200px;"/><br/>
			学历证书扫描件:<img id="educationScan" alt="学历证书扫描件" title="学历证书扫描件" style="width: 200px; height: 200px;"/><br/>
			收入证明:<img id="incomeProof" alt="收入证明" title="收入证明" style="width: 200px; height: 200px;"/><br/>
			账户流水扫描件:<img id="accountFlow" alt="账户流水扫描件" title="账户流水扫描件" style="width: 200px; height: 200px;"/><br/>
			工作证件扫描件:<img id="workCertificate" alt="工作证件扫描件" title="工作证件扫描件" style="width: 200px; height: 200px;"/><br/>
			学生证:<img id="studentCard" alt="学生证" title="学生证" style="width: 200px; height: 200px;"/><br/>
			职称证书:<img id="titlesCertificate" alt="职称证书" title="职称证书" style="width: 200px; height: 200px;"/><br/>
			房产证明:<img id="houseProof" alt="房产证明" title="房产证明" style="width: 200px; height: 200px;"/><br/>
			车辆证明:<img id="carProof" alt="车辆证明" title="车辆证明" style="width: 200px; height: 200px;"/><br/>
			结婚证:<img id="marriageCertificate" alt="结婚证" title="结婚证" style="width: 200px; height: 200px;"/><br/>
			其他财产证明:<img id="otherPropertyPoof" alt="其他财产证明" title="其他财产证明" style="width: 200px; height: 200px;"/><br/>
			其他居住地证明:<img id="otherHouseProof" alt="其他居住地证明" title="其他居住地证明" style="width: 200px; height: 200px;"/><br/>
			其他可确认身份的证件:<img id="otherIdentityCard" alt="其他可确认身份的证件" title="其他可确认身份的证件" style="width: 200px; height: 200px;"/><br/>
			其他能证明稳定收入的材料:<img id="otherIncomeProof" alt="其他能证明稳定收入的材料" title="其他能证明稳定收入的材料" style="width: 200px; height: 200px;"/><br/>
			企业营业执照:<img id="companyLicense" alt="企业营业执照" title="企业营业执照" style="width: 200px; height: 200px;"/><br/>
			企业流水账户信息:<img id="companyAcctFlow" alt="企业流水账户信息" title="企业流水账户信息" style="width: 200px; height: 200px;"/><br/>
			微博认证:<img id="microblogInfo" alt="微博认证" title="微博认证" style="width: 200px; height: 200px;"/><br/>
			是否审核通过:<label id="auditState"></label><br/>
			审核意见:<label id="auditOpinion"></label><br/>
			审核人:<label id="auditor"></label><br/>
			审核时间:<label id="auditTime"></label><br/>
		</fieldset>
		
		<fieldset id="workFinancial" style="width: 300px; float:left; display: inline;">
			<legend>财务状况</legend>
			用户编号:<label id="userId"></label><br/>
			公司规模:<label id="companySize"></label><br/>
			实习经历:<label id="workExperience"></label><br/>
			工作职位:<label id="workPosition"></label><br/>
			工作邮箱:<label id="workEmail"></label><br/>
			月收入:<label id="monthlyIncome"></label><br/>
			职业情况:<label id="workCondition"></label><br/>
			公司行业:<label id="companyIndustry"></label><br/>
			就读年级:<label id="schoolingGrade"></label><br/>
			就读专业:<label id="schoolingMajor"></label><br/>
			就读院校:<label id="schoolingName"></label><br/>
			网点联系方式:<label id="shopContact"></label><br/>
			网店地址:<label id="shopUrl"></label><br/>
			单位名称:<label id="companyName"></label><br/>
			单位地址:<label id="companyAddress"></label><br/>
			现单位工作年限:<label id="currentWorkYeas"></label><br/>
			单位电话:<label id="companyPhone"></label><br/>
			单位类型:<label id="companyType"></label><br/>
			就业状态:<label id="employStatus"></label><br/>
			工作年限:<label id="workYeas"></label><br/>
			工作地点:<label id="workAddress"></label><br/>
			审核是否通过:<label id="auditState"></label><br/>
			审核信息:<label id="auditOpinion"></label><br/>
			审核时间:<label id="auditTime"></label><br/>
			审核人:<label id="auditor"></label><br/>
		</fieldset>
	</div>
	
	<!-- 判断权限   begin -->
	<!-- 判断是否用【详细】的权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_loanUser_detail')">
		<input type="hidden" id="detail_auth"/>
	</security:authorize>
	
	<!-- 判断是否有【编辑】的权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_loanUser_modify')">
		<input type="hidden" id="modify_auth"/>
	</security:authorize>
	
	<!-- 判断是否有 【重置密码】 的权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_loanUser_resetPwd')">
		<input type="hidden" id="resetPwd_auth"/>
	</security:authorize>
	
	<!-- 判断是否有 【启用/禁用】 的权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_loanUser_changeStatus')">
		<input type="hidden" id="changeStatus_auth"/>
	</security:authorize>
	<!-- 判断权限   end -->
	
<script type="text/javascript">
<!--
//添加操作列
function operateData(val,row,index){
	//详情、、重置密码、锁定（恢复） normal lock disable
	var returnStr='';
	//判断是否用【详细】的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_loanUser_detail\')"><a href="'+path+'/user.loanUserDetail.html?id='+row.userId+'">详细</a></security:authorize>';
	//判断是否有【编辑】的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_loanUser_modify\')"><a href="'+path+'/user.loanUserEdit.html?id='+row.userId+'">编辑</a></security:authorize>';
	//判断是否有 【重置密码】 的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_loanUser_resetPwd\')"><a onClick=resetPassword("'+row.id+'")>重置密码</a></security:authorize>';
	//判断是否有 【启用/禁用】 的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_loanUser_changeStatus\')">';
	if (row.status=='normal') {
		returnStr+='<a onClick=modifySecurity("'+row.userId+'","disable")>禁用</a>';
	}else{
		returnStr+='<a onClick=modifySecurity("'+row.userId+'","normal")>启用</a>';
	}
	returnStr+='</security:authorize>';
	return returnStr;
}
//-->
</script>
