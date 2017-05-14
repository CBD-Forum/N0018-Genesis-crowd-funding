<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var userId = '${id}';
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/user/loanUserEdit.js"></script>
	<div id="add" style="display: block; overflow: auto; width: 100%;">
		<div id="tt">  
			<div id="baseInfo" title="基本信息" style="padding:20px;">  
				<form id="userForm" method="post">
					<input type="hidden" name="id">
		        	<div class="x-form-item">
						<label class="x-form-item-label">用户名:</label>
						<div class="x-form-element">
							<input name="userId" type="text" disabled="disabled"/>
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
							<input name="email" type="text" maxlength="100"/>
						</div>
					</div>
					
					
					<div class="x-form-item">
						<label class="x-form-item-label">真实姓名:</label>
						<div class="x-form-element">
							<input name="realName" type="text" maxlength="32"/>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">昵称:</label>
						<div class="x-form-element">
							<input name="nickName" type="text" maxlength="32"/>
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
							<input name="qqNo" type="text" maxlength="32" />
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">微博账号:</label>
						<div class="x-form-element">
							<input name="weiboNo" type="text" maxlength="64"/>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">手机号:</label>
						<div class="x-form-element">
							<input name="mobile" type="text" maxlength="11"/>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">电话号:</label>
						<div class="x-form-element">
							<input name="tel" type="text" maxlength="32"/>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">身份证号:</label>
						<div class="x-form-element">
							<input name="certificateNo" type="text" maxlength="18"/>
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
							<input name="homeAddress" type="text" maxlength="100"/>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">家庭住址邮编:</label>
						<div class="x-form-element">
							<input name="postCode" type="text" maxlength="6"/>
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
							<input name="emergencyContact" type="text" maxlength="64"/>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">紧急联系人电话:</label>
						<div class="x-form-element">
							<input name="emergencyPhone" type="text" maxlength="64"/>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">紧急联系人关系:</label>
						<div class="x-form-element">
							<input name="emergencyRelation" type="text" maxlength="32"/>
						</div>
					</div>
					
				</form>
		    </div>  
		    <div id="detailInfo" title="个人信息" style="padding:20px;">
		    	<form id="detailForm" method="post">
					<div class="x-form-item">
					    <label class="x-form-item-label">用户编号:</label>
					    <div class="x-form-element">
					        <input name="userId" type="text" disabled="disabled"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">籍贯:</label>
					    <div class="x-form-element">
					        <input name="nativePlace" type="text" maxlength="100"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">户口所在地:</label>
					    <div class="x-form-element">
					        <input name="huKouPlace" type="text" maxlength="100"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">学历:</label>
					    <div class="x-form-element">
					        <input id="degree" name="degree" class="easyui-combobox" url="<%=path %>/dictionary/user_degree.html" panelHeight="auto"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">毕业院校:</label>
					    <div class="x-form-element">
					        <input name="school" type="text" maxlength="100"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">入学年份:</label>
					    <div class="x-form-element">
					        <input name="schoolYear" type="text" maxlength="4"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">婚姻状况:</label>
					    <div class="x-form-element">
					        <input id="marriageInfo" name="marriageInfo" class="easyui-combobox" url="<%=path %>/dictionary/user_marriage.html" panelHeight="auto"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有孩子:</label>
					    <div class="x-form-element">
					        <select name="childrenInfo">
					        	<option value="">-=请选择=-</option>
					        	<option value="0">无</option>
					        	<option value="1">有</option>
					        </select>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有房子:</label>
					    <div class="x-form-element">
					        <select name="hasHouse">
					        	<option value="">-=请选择=-</option>
					        	<option value="0">无</option>
					        	<option value="1">有</option>
					        </select>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有房贷:</label>
					    <div class="x-form-element">
					        <select name="hasHousingLoan">
					        	<option value="">-=请选择=-</option>
					        	<option value="0">无</option>
					        	<option value="1">有</option>
					        </select>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有车:</label>
					    <div class="x-form-element">
					        <select name="hasCar">
					        	<option value="">-=请选择=-</option>
					        	<option value="0">无</option>
					        	<option value="1">有</option>
					        </select>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">是否有车贷:</label>
					    <div class="x-form-element">
					        <select name="hasCarLoan">
					        	<option value="">-=请选择=-</option>
					        	<option value="0">无</option>
					        	<option value="1">有</option>
					        </select>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">直属亲属:</label>
					    <div class="x-form-element">
					        <input name="relativeName" type="text" maxlength="32"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">直系亲属关系:</label>
					    <div class="x-form-element">
					        <input name="relativeRelation" type="text" maxlength="32"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">直系亲属联系方式:</label>
					    <div class="x-form-element">
					        <input name="relativePhone" type="text" maxlength="32"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他联系人:</label>
					    <div class="x-form-element">
					        <input name="otherRelative" type="text" maxlength="32"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他联系人关系:</label>
					    <div class="x-form-element">
					        <input name="otherRelation" type="text" maxlength="32"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他联系人联系方式:</label>
					    <div class="x-form-element">
					        <input name="otherContactPhone" type="text" maxlength="32"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">信用等级:</label>
					    <div class="x-form-element">
					        <input id="creditLevel" name="creditLevel" class="easyui-combobox" url="<%=path %>/dictionary/user_credit_level.html" panelHeight="auto"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">信用额度:</label>
					    <div class="x-form-element">
					        <input name="creditLimit" type="text" onkeypress="return checkNum(event,this)"/>元
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">风险等级:</label>
					    <div class="x-form-element">
					        <input id="riskLevel" name="riskLevel" class="easyui-combobox" url="<%=path %>/dictionary/user_risk_level.html" panelHeight="auto"/>
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
					        <input name="userId" type="text" disabled="disabled"/>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label">手持身份证照片:</label>
					    <input name="idCardPhoto" type="hidden"/>
					    <div class="x-form-element">
					        <div id="idCardPhotoDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="idCardPhotoBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">身份证扫描件:</label>
					    <input name="idCardScan" type="hidden"/>
					    <div class="x-form-element">
					        <div id="idCardScanDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="idCardScanBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">银行征信报告:</label>
					    <input name="bankCreditReport" type="hidden"/>
					    <div class="x-form-element">
					        <div id="bankCreditReportDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="bankCreditReportBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">户口卡:</label>
					    <input name="huKouScan" type="hidden"/>
					    <div class="x-form-element">
					        <div id="huKouScanDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="huKouScanBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">手持户口卡照片:</label>
					    <input name="huKouPhoto" type="hidden"/>
					    <div class="x-form-element">
					        <div id="huKouPhotoDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="huKouPhotoBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">学历证书扫描件:</label>
					    <input name="educationScan" type="hidden"/>
					    <div class="x-form-element">
					        <div id="educationScanDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="educationScanBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">收入证明:</label>
					    <input name="incomeProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="incomeProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="incomeProofBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">账户流水扫描件:</label>
					    <input name="accountFlow" type="hidden"/>
					    <div class="x-form-element">
					        <div id="accountFlowDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="accountFlowBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">工作证件扫描件:</label>
					    <input name="workCertificate" type="hidden"/>
					    <div class="x-form-element">
					        <div id="workCertificateDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="workCertificateBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">学生证:</label>
					    <input name="studentCard" type="hidden"/>
					    <div class="x-form-element">
					        <div id="studentCardDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="studentCardBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">职称证书:</label>
					    <input name="titlesCertificate" type="hidden"/>
					    <div class="x-form-element">
					        <div id="titlesCertificateDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="titlesCertificateBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">房产证明:</label>
					    <input name="houseProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="houseProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="houseProofBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">车辆证明:</label>
					    <input name="carProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="carProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="carProofBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">结婚证:</label>
					    <input name="marriageCertificate" type="hidden"/>
					    <div class="x-form-element">
					        <div id="marriageCertificateDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="marriageCertificateBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他财产证明:</label>
					    <input name="otherPropertyPoof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="otherPropertyPoofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="otherPropertyPoofBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他居住地证明:</label>
					    <input name="otherHouseProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="otherHouseProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="otherHouseProofBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他可确认身份的证件:</label>
					    <input name="otherIdentityCard" type="hidden"/>
					    <div class="x-form-element">
					        <div id="otherIdentityCardDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="otherIdentityCardBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">其他能证明稳定收入的材料:</label>
					    <input name="otherIncomeProof" type="hidden"/>
					    <div class="x-form-element">
					        <div id="otherIncomeProofDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="otherIncomeProofBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">企业营业执照:</label>
					    <input name="companyLicense" type="hidden"/>
					    <div class="x-form-element">
					        <div id="companyLicenseDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="companyLicenseBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">企业流水账户信息:</label>
					    <input name="companyAcctFlow" type="hidden"/>
					    <div class="x-form-element">
					        <div id="companyAcctFlowDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="companyAcctFlowBtn" class="uploadBtn">上传图片</a>
					         </div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">微博认证:</label>
					    <input name="microblogInfo" type="hidden"/>
					    <div class="x-form-element">
					        <div id="microblogInfoDiv" style="margin-left: 10px;">
					            <div class="filelist"></div>
					            <a href="javascript:void(0)" id="microblogInfoBtn" class="uploadBtn">上传图片</a>
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
					        <input name="userId" type="text" disabled="disabled"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">公司规模:</label>
					    <div class="x-form-element">
					        <input id="companySize" name="companySize" class="easyui-combobox" url="<%=path %>/dictionary/company_size.html" panelHeight="auto"/>
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
					        <input name="workPosition" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">工作邮箱:</label>
					    <div class="x-form-element">
					        <input name="workEmail" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">月收入:</label>
					    <div class="x-form-element">
					        <input id="monthlyIncome" name="monthlyIncome" class="easyui-combobox" url="<%=path %>/dictionary/monthly_income.html" panelHeight="auto"/>
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
					        <input id="companyIndustry" name="companyIndustry" class="easyui-combobox" url="<%=path %>/dictionary/company_industry.html" panelHeight="auto"/>
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
					        <input name="companyName" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">单位地址:</label>
					    <div class="x-form-element">
					        <input name="companyAddress" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">现单位工作年限:</label>
					    <div class="x-form-element">
					        <input name="currentWorkYeas" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">单位电话:</label>
					    <div class="x-form-element">
					        <input name="companyPhone" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">单位类型:</label>
					    <div class="x-form-element">
					        <input name="companyType" type="text"/>
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
					        <input name="workYeas" type="text"/>
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
		</div> 					
	</div>
	<div class="psb" style="margin-top:5px;">
		<a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a>
		<a id="returnBtn" href="<%=path %>//user.loanUser.html" class="easyui-linkbutton searchBtn">返回</a>
	</div>