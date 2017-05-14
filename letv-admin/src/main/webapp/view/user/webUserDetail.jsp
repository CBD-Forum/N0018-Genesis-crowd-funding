<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var id = '${id}';
</script>
<style type="text/css">
.tab_table{border-collapse:collapse;width:100%;}
.tab_table tr th,td{border:1px solid rgb(149, 184, 231);min-height:40px;line-height:40px;padding:0 10px;}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/user/webUserDetail.js"></script>
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
						<label class="x-form-item-label">注册时间:</label>
						<div class="x-form-element">
							<label id="createTime"></label>
						</div>
					</div>
				</form>
		    </div>  
			<div id="financialInfo" title="作为投资人认证资料" style="padding:20px;">
				<form id="investorForm">
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">真实姓名:</label>
					    <div class="x-form-element">
					        <label id="realName"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">身份证号码:</label>
					    <div class="x-form-element">
					        <label id="certNo"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">公司名称:</label>
					    <div class="x-form-element">
					        <label id="companyName"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">职位:</label>
					    <div class="x-form-element">
					        <label id="position"></label>
					    </div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label" style="width:258px">名片:</label>
						<input type="hidden" name="cardPhoto"/>
						<div class="x-form-element">
							<div id="cardPhotoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							</div>
						</div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">感兴趣的领域:</label>
					    <div class="x-form-element">
					        <label id="concernIndustry"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">感兴趣的城市:</label>
					    <div class="x-form-element">
					        <label id="concernCity"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">准备使用多少资金进行投资:</label>
					    <div class="x-form-element">
					        <label id="investRatio"></label>
					    </div>
					</div>
					<!-- <div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">是否投资过:</label>
					    <div class="x-form-element">
					        <label id="isInvest"></label>
					    </div>
					</div> -->
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">投资经历:</label>
					    <div class="x-form-element">
					        <label id="investExperience"></label>
					    </div>
					</div>
					<!-- <div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年是否有投资计划:</label>
					    <div class="x-form-element">
					        <label id="hasInvestPlan"></label>
					    </div>
					</div> -->
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年计划投资多少个项目:</label>
					    <div class="x-form-element">
					        <label id="investPlanNum"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年计划投资金额:</label>
					    <div class="x-form-element">
					        <label id="investPlanAmt"></label>
					    </div>
					</div>
				</form>
		 	</div>
		 	<div id="financialInfo" title="作为领投人认证资料" style="padding:20px;">
				<form id="leadInvestorForm">
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">真实姓名:</label>
					    <div class="x-form-element">
					        <label id="realName"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">身份证号码:</label>
					    <div class="x-form-element">
					        <label id="certNo"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">公司名称:</label>
					    <div class="x-form-element">
					        <label id="companyName"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">职位:</label>
					    <div class="x-form-element">
					        <label id="position"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label" style="width:258px">名片:</label>
						<input type="hidden" name="cardPhoto"/>
						<div class="x-form-element">
							<div id="cardPhotoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							</div>
						</div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">感兴趣的领域:</label>
					    <div class="x-form-element">
					        <label id="concernIndustry"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">感兴趣的城市:</label>
					    <div class="x-form-element">
					        <label id="concernCity"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">准备使用多少资金进行投资:</label>
					    <div class="x-form-element">
					        <label id="investRatio"></label>
					    </div>
					</div>
					<!-- <div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">是否投资过:</label>
					    <div class="x-form-element">
					        <label id="isInvest"></label>
					    </div>
					</div> -->
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">投资经历:</label>
					    <div class="x-form-element">
					        <label id="investExperience"></label>
					    </div>
					</div>
					<!-- <div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年是否有投资计划:</label>
					    <div class="x-form-element">
					        <label id="hasInvestPlan"></label>
					    </div>
					</div> -->
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年计划投资多少个项目:</label>
					    <div class="x-form-element">
					        <label id="investPlanNum"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年计划投资金额:</label>
					    <div class="x-form-element">
					        <label id="investPlanAmt"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">个人资产认证资料（股票、房产、银行资产等）:</label>
					    <div class="x-form-element" id="assetsCredentialsDiv">
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">历史投资资料（投资合同等）:</label>
					    <div class="x-form-element" id="historicalInvestmentDataDiv">
					    </div>
					</div>
					
					
				</form>
		 	</div>
		 	
		 	<div id="financialInfo" title="跟投机构认证资料" style="padding:20px;">
				<form id="orgInvestorForm">
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构性质:</label>
					    <div class="x-form-element">
					        <label id="institution"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构名称:</label>
					    <div class="x-form-element">
					        <label id="realName"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构证件号码:</label>
					    <div class="x-form-element">
					        <label id="certNo"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构证件:</label>
					    <div class="x-form-element" id="cardPhotoDiv">
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">感兴趣的领域:</label>
					    <div class="x-form-element">
					        <label id="concernIndustry"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">感兴趣的城市:</label>
					    <div class="x-form-element">
					        <label id="concernCity"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">准备使用多少资金进行投资:</label>
					    <div class="x-form-element">
					        <label id="investRatio"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">投资经历:</label>
					    <div class="x-form-element">
					        <label id="investExperience"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年计划投资多少个项目:</label>
					    <div class="x-form-element">
					        <label id="investPlanNum"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年计划投资金额:</label>
					    <div class="x-form-element">
					        <label id="investPlanAmt"></label>
					    </div>
					</div>
				</form>
		 	</div>
		 	
		 	<div id="financialInfo" title="领投机构认证资料" style="padding:20px;">
				<form id="orgLeadInvestorForm">
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构性质:</label>
					    <div class="x-form-element">
					        <label id="institution"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构名称:</label>
					    <div class="x-form-element">
					        <label id="realName"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构证件号码:</label>
					    <div class="x-form-element">
					        <label id="certNo"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构证件:</label>
					    <div class="x-form-element" id="cardPhotoDiv">
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">感兴趣的领域:</label>
					    <div class="x-form-element">
					        <label id="concernIndustry"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">感兴趣的城市:</label>
					    <div class="x-form-element">
					        <label id="concernCity"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">准备使用多少资金进行投资:</label>
					    <div class="x-form-element">
					        <label id="investRatio"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">投资经历:</label>
					    <div class="x-form-element">
					        <label id="investExperience"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年计划投资多少个项目:</label>
					    <div class="x-form-element">
					        <label id="investPlanNum"></label>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">未来一年计划投资金额:</label>
					    <div class="x-form-element">
					        <label id="investPlanAmt"></label>
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">机构资产认证资料（股票、房产、银行资产等）:</label>
					    <div class="x-form-element" id="assetsCredentialsDiv">
					    </div>
					</div>
					
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:258px">历史投资资料（投资合同等）:</label>
					    <div class="x-form-element" id="historicalInvestmentDataDiv">
					    </div>
					</div>
				</form>
		 	</div>
		</div> 					
	</div>
	<div class="psb" style="margin-top:5px;">
		<a id="returnBtn" href="javascript:history.go(-1);" class="easyui-linkbutton searchBtn">返回</a>
	</div>
	