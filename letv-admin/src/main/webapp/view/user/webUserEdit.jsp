<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var id = '${id}';
var userId = '${userId}';
</script>
<style type="text/css">
.tab_table{border-collapse:collapse;width:100%;}
.tab_table tr th,td{border:1px solid rgb(149, 184, 231);min-height:40px;line-height:40px;padding:0 10px;}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/user/webUserEdit.js"></script>
	<div id="add" style="display: block; overflow: auto; width: 100%;">
		<div id="tt">  
			<div id="baseInfo" title="基本信息" style="padding:20px;">  
			<form id="userForm" method="post" style="margin-top:20px;">
				
				<div class="x-form-item">
					<label class="x-form-item-label">用户名:</label>
					<div class="x-form-element">
						<input id="userId" name="userId" type="text" disabled="disabled"/>
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
			<div style="text-align:center;width:360px;">
						<a  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveBtn">保存</a>
					</div>
		</div>
		<div id="authFileInfo" title="公司认证资料"  style="padding:20px;">
				<div class="x-form-item">
						<label class="x-form-item-label">法人代表身份证:</label>
						<input type="hidden" name="legal_user_card"/>
						<div class="x-form-element">
							<div id="photoDiv1" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn1" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
					<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('legal_user_card')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">法人代表个人信用报告:</label>
						<input type="hidden" name="credit_report"/>
						<div class="x-form-element">
							<div id="photoDiv2" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn2" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('credit_report')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">营业执照:</label>
						<input type="hidden" name="busi_licence"/>
						<div class="x-form-element">
							<div id="photoDiv3" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn3" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('busi_licence')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">营业执照副本:</label>
						<input type="hidden" name="busi_licence_copy"/>
						<div class="x-form-element">
							<div id="photoDiv4" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn4" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('busi_licence_copy')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">税务登记证:</label>
						<input type="hidden" name="tax_reg"/>
						<div class="x-form-element">
							<div id="photoDiv5" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn5" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('tax_reg')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">税务登记副本:</label>
						<input type="hidden" name="tax_reg_copy"/>
						<div class="x-form-element">
							<div id="photoDiv6" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn6" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('tax_reg_copy')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				
				<div class="x-form-item">
						<label class="x-form-item-label">组织机构代码证:</label>
						<input type="hidden" name="org_code"/>
						<div class="x-form-element">
							<div id="photoDiv7" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn7" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('org_code')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">组织机构代码证副本:</label>
						<input type="hidden" name="org_code_copy"/>
						<div class="x-form-element">
							<div id="photoDiv8" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn8" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('org_code_copy')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">公司照片:</label>
						<input type="hidden" name="company_photo"/>
						<div class="x-form-element">
							<div id="photoDiv9" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn9" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('company_photo')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				<div class="x-form-item">
						<label class="x-form-item-label">场地租凭合同:</label>
						<input type="hidden" name="tenancy_contract"/>
						<div class="x-form-element">
							<div id="photoDiv10" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn10" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('tenancy_contract')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">财务报表:</label>
						<input type="hidden" name="finance_report"/>
						<div class="x-form-element">
							<div id="photoDiv11" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn11" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('finance_report')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
				<div class="x-form-item">
						<label class="x-form-item-label">企业资质证明:</label>
						<input type="hidden" name="hygienic_license"/>
						<div class="x-form-element">
							<div id="photoDiv12" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn12" style="margin-left: 16px;">选择图片</div>
							</div>
						</div>
						<div style="text-align:center;width:360px;">
						<a onclick="updateCompany('hygienic_license')"  class="easyui-linkbutton c7" style="width:120px;margin-top:5px;" id="saveCompanyBtn">保存</a>
					</div>
				</div>
				
		    </div> 
		
		</div>
	</div>
	<div class="psb" style="margin-top:30px;">
		<a id="returnBtn" href="javascript:history.go(-1);" class="easyui-linkbutton searchBtn">返回</a>
	</div>
	