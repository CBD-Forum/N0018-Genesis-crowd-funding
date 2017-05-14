<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/crowdfundDetail.js"></script>
	<div id="add" style="display: block; overflow: hidden; padding: 0;">
		<div id="tt"> 
			<div id="baseInfo" title="基本信息" style="padding:20px;">  
				<form id="baseForm" method="post">
		        	<div class="x-form-item">
						<label class="x-form-item-label">项目名称:</label>
						<div class="x-form-element">
							<label id="loanName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">房产建筑名称:</label>
						<div class="x-form-element">
							<label id="houseArea"></label>平方米
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">房产期限:</label>
						<div class="x-form-element">
							<label id="houseLimit"></label>年
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">发起人:</label>
						<div class="x-form-element">
							<label id="loanUser"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">发起人真实姓名:</label>
						<div class="x-form-element">
							<label id="loanUserName"></label>
						</div>
					</div>
					
					
					<div class="x-form-item">
						<label class="x-form-item-label">筹资金额:</label>
						<div class="x-form-element">
							<label id="fundAmt"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">筹资天数:</label>
						<div class="x-form-element">
							<label id="fundDays"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">收取服务费比例:</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<label id="chargeFee"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">项目类型:</label>
						<div class="x-form-element">
							<label id="loanTypeName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">项目负责人:</label>
						<div class="x-form-element">
							<label id="loanManager"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">项目等级:</label>
						<div class="x-form-element">
							<label id="loanLevel"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">物业所在省:</label>
						<div class="x-form-element">
							<label id="loanProvinceName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">物业所在市:</label>
						<div class="x-form-element">
							<label id=loanCityName></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">物业所在县:</label>
						<div class="x-form-element">
							<label id=loanCountyName></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">申请时间:</label>
						<div class="x-form-element">
							<label id="createTime"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">预热截止时间:</label>
						<div class="x-form-element">
							<label id="preheatEndTime"></label>
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">融资截止时间:</label>
						<div class="x-form-element">
							<label id="fundEndTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">状态:</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<label id="loanStateName"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">发布时间:</label>
						<input type="hidden" name="photo"/>
						<div class="x-form-element">
							<label id="releaseTime"></label>
						</div>
					</div>
					
					<div class="x-form-item">
						<label class="x-form-item-label">放款时间:</label>
						<div class="x-form-element">
							<label id="cashTime"></label>
						</div>
					</div>
					
				</form>
		    </div>  
		    <div id="detailInfo" title="项目简介" style="padding:20px;">
		    	<form id="detailForm" method="post">
					<div class="x-form-item">
					    <label class="x-form-item-label">项目简介:</label>
					    <div class="x-form-element">
					        <div id="loanDes"></div>
					    </div>
					</div>
				</form>
		     </div>  
		     
		     <div id="loanLogo" title="项目封面" style="padding:20px;">
				  
		 	 </div>
		 	 <div id="loanStuff" title="项目资料" style="padding:20px;">
					<div class="x-form-item">
						<label class="x-form-item-label">房屋类型:</label>
						<div class="x-form-element">
							<label id="houseTypeName"></label>
						</div>
					</div>
		        	<div class="x-form-item">
						<label class="x-form-item-label">房产原价格/租金:</label>
						<div class="x-form-element">
							<label id="houseOldPrice"></label>元/平方米(不含税)
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">乐会员价格/租金:</label>
						<div class="x-form-element">
							<label id="housePresentPrice"></label>元/平方米(不含税)
						</div>
					</div>
					<div class="x-form-item">
						<label class="x-form-item-label">原开发商:</label>
						<div class="x-form-element">
							<label id="houseDeveloper"></label>
						</div>
					</div>
		    </div>
		    <div id="detailInfo" title="房产介绍" style="padding:20px;">
					<div class="x-form-item">
					    <label class="x-form-item-label">项目详情:</label>
				    	 <div class="x-form-element">
					        <div id="loanDetail"></div>
					    </div>
				    </div>
		     </div> 
		     <div id="d_priceCompare" title="价格对比" style="padding:20px;">
					<div class="x-form-item">
					    <label class="x-form-item-label">价格对比:</label>
				    	  <div class="x-form-element">
					        <div id="priceCompare"></div>
					      </div>
				    </div>
		     </div>  
		     <div id="d_house_manager" title="管理方" style="padding:20px;">
					<div class="x-form-item">
					    <label class="x-form-item-label">管理方:</label>
				    	 <div class="x-form-element">
					        <div id="houseManager"></div>
					      </div>
				    </div>
		     </div>   
			<div id="loanVideo1" title="项目视频" style="padding:20px;">
				<form id="financialForm">
					<div class="x-form-item">
					    <label class="x-form-item-label">视频简介:</label>
					    <div class="x-form-element">
					    	<div id="videoDes"></div>
					    </div>
					</div>
					<div class="x-form-item">
					    <label class="x-form-item-label">项目视频:</label>
					    <div class="x-form-element">
					     <div id="loanVideo"></div>
					    </div>
					</div>
				</form>
		 	</div>
		 	
		</div> 					
	</div>
	<!-- 
	<div class="psb" style="margin-top:30px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a><a id="returnBtn" class="easyui-linkbutton searchBtn">返回</a></div>
	 -->