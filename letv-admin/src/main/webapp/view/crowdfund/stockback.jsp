<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="stockback">
		<form id="stockbackform" method="post" style="margin-top:20px;">
			<input id="loanNo" name="loanNo" type="hidden"/>
			<div class="x-form-item" id="sDate">
				<label class="x-form-item-label" style="width:150px">项目方出资总额:</label>
				<div class="x-form-element">
					<label id="projectFinanceAmt"></label>元
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">投资人出资金额:</label>
				<div class="x-form-element">
					<label id="investFinanceAmt"></label>元
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">项目方分红比例:</label>
				<div class="x-form-element">
					<label id="projectBonusRatio"></label>%
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">投资方分红比例:</label>
				<div class="x-form-element">
					<label id="investBonusRatio"></label>%
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">融资总份数:</label>
				<div class="x-form-element">
					<label id="financeNum"></label>份
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">单笔投资人最低投资金额:</label>
				<div class="x-form-element">
					<label id="miniInvestAmt"></label>元
				</div>
			</div>
		</form>
	</div>