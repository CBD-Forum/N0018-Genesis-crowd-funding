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
<script type="text/javascript" src="<%=path%>/js/user/webCrowdfundUserEdit.js"></script>
	<div id="add" style="display: block; overflow: auto; width: 100%;">
		<form id="userForm" method="post" style="margin-top:20px;">
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">用户编号:</label>
					    <div class="x-form-element">
					        <input  id="userId" name="userId" type="text" disabled="disabled"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">真实姓名/机构名称:</label>
					    <div class="x-form-element">
					        <input name="companyName" id="companyName" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label" style="width:150px">身份证(公司营业执照):</label>
						<input type="hidden" name="handCardFront"/>
						<div class="x-form-element">
							<div id="photoDiv" style="margin-left: 10px;">
							    <!--用来存放item-->
							    <div class="filelist"></div>
							    <div id="photoBtn"  style="margin-left: 60px;">选择图片</div>
							</div>
						</div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">感兴趣的城市:</label>
					    <div class="x-form-element">
					        <input name="concernCity" id="concernCity" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">感兴趣的投资领域:</label>
					    <div class="x-form-element">
					        <input name="concernIndustry" id="concernIndustry" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">准备使用总资产的多少比例进行投资:</label>
					    <div class="x-form-element">
					        <select id="investRatio" name="investRatio" style="width:167px;height:42px;">
		                        		<option value="5">5</option>
		                        		<option value="10">10</option>
		                        		<option value="15">15</option>
		                        		<option value="20">20</option>
		                        		<option value="25">25</option>
		                        		<option value="30">30</option>
		                        		<option value="35">35</option>
		                        		<option value="40">40</option>
		                        		<option value="45">45</option>
		                        		<option value="50">50</option>
		                        	</select>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">是否投资过:</label>
					    <input type="radio" name="hi" value="1" class="input" >我投资过项目&nbsp; 
					    <input name="hi" class="input" type="radio" value="0">我还没投资过项目&nbsp; 
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">投资经历:</label>
					    <div class="x-form-element">
					        <input name="investExperience" id="investExperience" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
					
						<label class="x-form-item-label" style="width:150px">计划投资:</label>
					    <input type="radio" name="hj" value="1" class="input" >未来一年有投资计划&nbsp;
					    <input name="hj" class="input" type="radio" value="0">未来一年暂无投资计划&nbsp; 
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">计划投资项目数:</label>
					    <div class="x-form-element">
					        <input name="investPlanNum" id="investPlanNum" type="text"/>个
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">总投资额:</label>
					    <div class="x-form-element">
					        <input name="investPlanAmt" id="investPlanAmt" type="text"/>万元
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label" style="width:150px">投资人类型:</label>
					        <input type="radio" name="igj" value="person"  class="input">个人投资人&nbsp;
					        <input class="input" name="igj" type="radio" value="institution">机构投资人&nbsp; 
					</div>
					<div class="x-form-item" id="institutionIndustry_div">
					    <label class="x-form-item-label" style="width:150px">所属行业:</label>
					    <div class="x-form-element">
					        <input name="institutionIndustry" id="institutionIndustry" type="text"/>
					    </div>
					</div>
					<div class="x-form-item" id = "companyAddress_div">
					    <label class="x-form-item-label" style="width:150px">公司地址:</label>
					    <div class="x-form-element">
					        <input name="companyAddress" id="companyAddress" type="text"/>
					    </div>
					</div>
					<div class="x-form-item" id="capitalDes_div">
					    <label class="x-form-item-label" style="width:150px">资产状况说明:</label>
					    <div class="x-form-element">
					        <input name="capitalDes" id="capitalDes" type="text"/>
					    </div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label" style="width:150px">用户等级:</label>
						<div class="x-form-element">
							 <select id="userLevel" name="userLevel" style="width:230px">
							 <option value="common">普通会员</option>
							  <option value="lead">领投人</option>
							   <option value="authed">认证会员</option>
							 </select>
						</div>
					</div>
			
		</form>
	</div>
	<div class="psb" style="margin-top:30px;">
		<a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a>
		<a id="returnBtn" href="javascript:history.go(-1);" class="easyui-linkbutton searchBtn">返回</a>
	</div>
	