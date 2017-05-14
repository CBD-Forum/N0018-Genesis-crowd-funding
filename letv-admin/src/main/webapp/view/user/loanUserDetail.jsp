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
<script type="text/javascript" src="<%=path%>/js/user/loanUserDetail.js"></script>
	<div id="add" style="display: block; overflow: auto; width: 100%;">
		<div id="tt">  
			<div id="baseInfo" title="基本信息" style="padding:20px;">  
				<form id="userForm" method="post">
					<input type="hidden" name="id">
		        	<div class="x-form-item">
						<label class="x-form-item-label">用户名:</label>
						<div class="x-form-element">
							<label id="userId"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">汇付账号:</label>
						<div class="x-form-element">
							<label id="thirdAccount"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">邮箱:</label>
						<div class="x-form-element">
							<label id="email"></label>
						</div>
					</div>
					
					
					<div class="x-form-item">
						<label class="x-form-item-label">真实姓名:</label>
						<div class="x-form-element">
							<label id="realName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">昵称:</label>
						<div class="x-form-element">
							<label id="nickName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">性别:</label>
						<div class="x-form-element">
							<label id="sex"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">生日:</label>
						<div class="x-form-element">
							<label id="birthday"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">QQ账号:</label>
						<div class="x-form-element">
							<label id="qqNo"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">微博账号:</label>
						<div class="x-form-element">
							<label id="weiboNo"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">手机号:</label>
						<div class="x-form-element">
							<label id="mobile"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">电话号:</label>
						<div class="x-form-element">
							<label id="tel"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">身份证号:</label>
						<div class="x-form-element">
							<label id="certificateNo"></label>
						</div>
					</div>
					<!-- 
					<div class="x-form-item">
						<label class="x-form-item-label">国家:</label>
						<div class="x-form-element">
							<input name="country" type="text"/>
						</div>
					</div>
					 -->
					<div class="x-form-item">
						<label class="x-form-item-label">公司所在省:</label>
						<div class="x-form-element">
							<label id="provinceName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">公司所在城市:</label>
						<div class="x-form-element">
							<label id="cityName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">县:</label>
						<div class="x-form-element">
							<label id="countyName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">家庭住址:</label>
						<div class="x-form-element">
							<label id="homeAddress"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">家庭住址邮编:</label>
						<div class="x-form-element">
							<label id="postCode"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">头像:</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<div id="photoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							</div>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">认证时间:</label>
						<div class="x-form-element">
							<label id="authTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">用户等级:</label>
						<div class="x-form-element">
							<label id="userLevel"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">开户时间:</label>
						<div class="x-form-element">
							<label id="openAccTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">邀请码:</label>
						<div class="x-form-element">
							<label id="inviteCode"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">注册时间:</label>
						<div class="x-form-element">
							<label id="createTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">是否为借款人:</label>
						<div class="x-form-element">
							<label id="isBorrower"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">紧急联系人:</label>
						<div class="x-form-element">
							<label id="emergencyContact"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">紧急联系人电话:</label>
						<div class="x-form-element">
							<label id="emergencyPhone"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">紧急联系人关系:</label>
						<div class="x-form-element">
							<label id="emergencyRelation"></label>
						</div>
					</div>
					
				</form>
		    </div>  
		    <div id="detailInfo" title="个人信息" style="padding:20px;">
		    	<form id="detailForm" method="post">
					<div class="x-form-item">
					    <label class="x-form-item-label">用户编号:</label>
					    <div class="x-form-element">
					        <label id="userId"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">籍贯:</label>
					    <div class="x-form-element">
					        <label id="nativePlace"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">户口所在地:</label>
					    <div class="x-form-element">
					        <label id="huKouPlace"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">学历:</label>
					    <div class="x-form-element">
					        <label id="degree"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">毕业院校:</label>
					    <div class="x-form-element">
					        <label id="school"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">入学年份:</label>
					    <div class="x-form-element">
					        <label id="schoolYear"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">婚姻状况:</label>
					    <div class="x-form-element">
					        <label id="marriageInfo"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有孩子:</label>
					    <div class="x-form-element">
					        <label id="childrenInfo"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有房子:</label>
					    <div class="x-form-element">
					        <label id="hasHouse"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有房贷:</label>
					    <div class="x-form-element">
					        <label id="hasHousingLoan"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有车:</label>
					    <div class="x-form-element">
					        <label id="hasCar"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有车贷:</label>
					    <div class="x-form-element">
					        <label id="hasCarLoan"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">直属亲属:</label>
					    <div class="x-form-element">
					        <label id="relativeName"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">直系亲属关系:</label>
					    <div class="x-form-element">
					        <label id="relativeRelation"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">直系亲属联系方式:</label>
					    <div class="x-form-element">
					        <label id="relativePhone"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他联系人:</label>
					    <div class="x-form-element">
					        <label id="otherRelative"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他联系人关系:</label>
					    <div class="x-form-element">
					        <label id="otherRelation"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他联系人联系方式:</label>
					    <div class="x-form-element">
					        <label id="otherContactPhone"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">信用等级:</label>
					    <div class="x-form-element">
					    	<label id="creditLevel"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">信用额度:</label>
					    <div class="x-form-element">
					        <label id="creditLimit"></label>元
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">风险等级:</label>
					    <div class="x-form-element">
					    	<label id="riskLevel"></label>
					    </div>
					</div>
					<!-- 
					<div class="x-form-item">
					    <label class="x-form-item-label">风险系数:</label>
					    <div class="x-form-element">
					        <input name="riskFactor" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核是否通过:</label>
					    <div class="x-form-element">
					        <input name="auditState" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核信息:</label>
					    <div class="x-form-element">
					        <input name="auditOpinion" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核人编号:</label>
					    <div class="x-form-element">
					        <input name="auditor" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核时间:</label>
					    <div class="x-form-element">
					        <input name="auditTime" type="text"/>
					    </div>
					</div>
					 -->
				</form>
		     </div>  
		     <div id="authFileInfo" title="认证资料"  style="padding:20px;">
		     	<form id="authFileForm" method="post"> 
					<div class="x-form-item">
					    <label class="x-form-item-label">用户编号:</label>
					    <div class="x-form-element">
					        <label id="userId"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label">手持身份证照片:</label>
					    <input name="idCardPhoto" type="hidden"/>
					    <div class="x-form-element">
					        <div id="idCardPhotoDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">身份证扫描件:</label>
					    <input name="idCardScan" type="hidden"/>
					    <div class="x-form-element">
					        <div id="idCardScanDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">银行征信报告:</label>
					    <input name="bankCreditReport" type="hidden"/>
					    <div class="x-form-element">
					        <div id="bankCreditReportDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">户口卡:</label>
					    <input name="huKouScan" type="hidden"/>
					    <div class="x-form-element">
					        <div id="huKouScanDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">手持户口卡照片:</label>
					    <input name="huKouPhoto" type="hidden"/>
					    <div class="x-form-element">
					        <div id="huKouPhotoDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">学历证书扫描件:</label>
					    <input name="educationScan" type="hidden"/>
					    <div class="x-form-element">
					        <div id="educationScanDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">收入证明:</label>
					    <input name="incomeProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="incomeProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">账户流水扫描件:</label>
					    <input name="accountFlow" type="hidden"/>
					    <div class="x-form-element">
					        <div id="accountFlowDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">工作证件扫描件:</label>
					    <input name="workCertificate" type="hidden"/>
					    <div class="x-form-element">
					        <div id="workCertificateDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">学生证:</label>
					    <input name="studentCard" type="hidden"/>
					    <div class="x-form-element">
					        <div id="studentCardDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">职称证书:</label>
					    <input name="titlesCertificate" type="hidden"/>
					    <div class="x-form-element">
					        <div id="titlesCertificateDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">房产证明:</label>
					    <input name="houseProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="houseProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">车辆证明:</label>
					    <input name="carProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="carProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">结婚证:</label>
					    <input name="marriageCertificate" type="hidden"/>
					    <div class="x-form-element">
					        <div id="marriageCertificateDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他财产证明:</label>
					    <input name="otherPropertyPoof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="otherPropertyPoofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他居住地证明:</label>
					    <input name="otherHouseProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="otherHouseProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他可确认身份的证件:</label>
					    <input name="otherIdentityCard" type="hidden"/>
					    <div class="x-form-element">
					        <div id="otherIdentityCardDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他能证明稳定收入的材料:</label>
					    <input name="otherIncomeProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="otherIncomeProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">企业营业执照:</label>
					    <input name="companyLicense" type="hidden"/>
					    <div class="x-form-element">
					        <div id="companyLicenseDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">企业流水账户信息:</label>
					    <input name="companyAcctFlow" type="hidden"/>
					    <div class="x-form-element">
					        <div id="companyAcctFlowDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">微博认证:</label>
					    <input name="microblogInfo" type="hidden"/>
					    <div class="x-form-element">
					        <div id="microblogInfoDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					         </div>
					    </div>
					</div>
					
					<!-- 
					<div class="x-form-item">
					    <label class="x-form-item-label">是否审核通过:</label>
					    <div class="x-form-element">
					        <input name="auditState" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核意见:</label>
					    <div class="x-form-element">
					        <input name="auditOpinion" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核人:</label>
					    <div class="x-form-element">
					        <input name="auditor" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核时间:</label>
					    <div class="x-form-element">
					        <input name="auditTime" type="text"/>
					    </div>
					</div>
					-->
				</form>
		    </div>  
			<div id="financialInfo" title="财务信息" style="padding:20px;">
				<form id="financialForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">用户编号:</label>
					    <div class="x-form-element">
					        <label id="userId"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">公司规模:</label>
					    <div class="x-form-element">
					        <label id="companySize"></label>
					    </div>
					</div>
					<!-- 
					<div class="x-form-item">
					    <label class="x-form-item-label">实习经历:</label>
					    <div class="x-form-element">
					        <input name="workExperience" type="text"/>
					    </div>
					</div>
					 -->
					<div class="x-form-item">
					    <label class="x-form-item-label">工作职位:</label>
					    <div class="x-form-element">
					        <label id="workPosition"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">工作邮箱:</label>
					    <div class="x-form-element">
					        <label id="workEmail"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">月收入:</label>
					    <div class="x-form-element">
					        <label id="monthlyIncome"></label>
					    </div>
					</div>
					<!--  
					<div class="x-form-item">
					    <label class="x-form-item-label">职业情况:</label>
					    <div class="x-form-element">
					        <input name="workCondition" type="text"/>
					    </div>
					</div>
					-->
					<div class="x-form-item">
					    <label class="x-form-item-label">公司行业:</label>
					    <div class="x-form-element">
					        <label id="companyIndustry"></label>
					    </div>
					</div>
					<!--  
					<div class="x-form-item">
					    <label class="x-form-item-label">就读年级:</label>
					    <div class="x-form-element">
					        <input name="schoolingGrade" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">就读专业:</label>
					    <div class="x-form-element">
					        <input name="schoolingMajor" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">就读院校:</label>
					    <div class="x-form-element">
					        <input name="schoolingName" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">网点联系方式:</label>
					    <div class="x-form-element">
					        <input name="shopContact" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">网店地址:</label>
					    <div class="x-form-element">
					        <input name="shopUrl" type="text"/>
					    </div>
					</div>
					-->
					<div class="x-form-item">
					    <label class="x-form-item-label">单位名称:</label>
					    <div class="x-form-element">
					        <label id="companyName"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">单位地址:</label>
					    <div class="x-form-element">
					        <label id="companyAddress"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">现单位工作年限:</label>
					    <div class="x-form-element">
					        <label id="currentWorkYeas"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">单位电话:</label>
					    <div class="x-form-element">
					        <label id="companyPhone"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">单位类型:</label>
					    <div class="x-form-element">
					        <label id="companyType"></label>
					    </div>
					</div>
					<!--  
					<div class="x-form-item">
					    <label class="x-form-item-label">就业状态:</label>
					    <div class="x-form-element">
					        <input name="employStatus" type="text"/>
					    </div>
					</div>
					-->
					<div class="x-form-item">
					    <label class="x-form-item-label">工作年限:</label>
					    <div class="x-form-element">
					        <label id="workYeas"></label>
					    </div>
					</div>
					<!--  
					<div class="x-form-item">
					    <label class="x-form-item-label">工作地点:</label>
					    <div class="x-form-element">
					        <input name="workAddress" type="text"/>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label">审核是否通过:</label>
					    <div class="x-form-element">
					        <input name="auditState" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核信息:</label>
					    <div class="x-form-element">
					        <input name="auditOpinion" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核时间:</label>
					    <div class="x-form-element">
					        <input name="auditTime" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">审核人:</label>
					    <div class="x-form-element">
					        <input name="auditor" type="text"/>
					    </div>
					</div>
					-->
				</form>
		 	</div>
		 	<div id="financialInfo" title="财务统计" style="padding:20px;">
				<form id="financialCountForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">可用余额:</label>
					    <div class="x-form-element">
					        <label id="balance"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">冻结金额:</label>
					    <div class="x-form-element">
					        <label id="frozen_amt"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">总投资额:</label>
					    <div class="x-form-element">
					        <label id="investMoney"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">总待收本金:</label>
					    <div class="x-form-element">
					        <label id="repayingPrincipal"></label>
					    </div>
					</div> 
					<div class="x-form-item">
					    <label class="x-form-item-label">累积收益:</label>
					    <div class="x-form-element">
					        <label id="repayedInterest"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">待收收益:</label>
					    <div class="x-form-element">
					        <label id="repayingInterest"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">总充值金额:</label>
					    <div class="x-form-element">
					        <label id="actual_amt"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">已提现金额:</label>
					    <div class="x-form-element">
					        <label id="amt"></label>
					    </div>
					</div>
				</form>
		 	</div>
		 	<div id="financialInfo" title="标的统计" style="padding:20px;">
				<form id="loanCountForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">总借款项目:</label>
					    <div class="x-form-element">
					        <label id="totalLoan"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">已发布借款项目:</label>
					    <div class="x-form-element">
					        <label id="publishLoan"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">未发布借款项目:</label>
					    <div class="x-form-element">
					        <label id="unpublishedLoan"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">正在筹款的项目:</label>
					    <div class="x-form-element">
					        <label id="fundingLoan"></label>
					    </div>
					</div> 
					<div class="x-form-item">
					    <label class="x-form-item-label">还款完成的项目:</label>
					    <div class="x-form-element">
					        <label id="repayedLoan"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">账单逾期次数:</label>
					    <div class="x-form-element">
					        <label id="overdue_times"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">坏账标数:</label>
					    <div class="x-form-element">
					        <label id="baddebtLoan"></label>
					    </div>
					</div>
				</form>
		 	</div>
		 	<div id="financialInfo" title="借款统计" style="padding:20px;">
				<form id="loanMoneyForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">借款总额:</label>
					    <div class="x-form-element">
					        <label id="loan_loanAmt"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">总已还利息:</label>
					    <div class="x-form-element">
					        <label id="loan_repayedActualRepayInterest"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">总待还本金:</label>
					    <div class="x-form-element">
					        <label id="loan_repyingActualRepayPrincipal"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">总待还利息:</label>
					    <div class="x-form-element">
					        <label id="loan_repyingActualRepayInterest"></label>
					    </div>
					</div> 
				</form>
		 	</div>
		</div> 					
	</div>
	<div class="psb" style="margin-top:5px;">
		<a id="returnBtn" href="javascript:history.go(-1);" class="easyui-linkbutton searchBtn">返回</a>
	</div>
	