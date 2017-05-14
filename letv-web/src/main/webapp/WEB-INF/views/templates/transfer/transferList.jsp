<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
<div class="box pt30" style='overflow: initial;'>
	<h4 class="transH4"><a class="fr rule" id="transferRuleBlock">转让规则</a>转让市场</h4>
	<div class="transBox">
		<div class="transTit">
			<p class="p1">项目名称 / 未来回报</p>
			<p style="width:131px;height:30px;" class="fr"> </p>
			<p class="fr">转让剩余天数</p>
			<p class="fr">项目发货时间</p>
			<p class="fr">转让人</p>
			<p class="fr">转让总金额(含运费)</p>
		</div>
		<div id="projectList">
		<ul class="transList" >
		</ul>
		</div>
		
		<div class="letvPage" style="text-align: center;">
        </div>
	</div>
</div>
</div>
<div class="prompt_box prompt_phone" id="transferDiv" style="left:50%; top: 100px;margin-left: -250px;z-index: 9999;">
	<a class="prompt_close">关闭</a>
	<h4>转让规则</h4>
	<div class="prompt_conter" id="transfer_conter">
		
	</div>
</div>
<div class="bgpop"></div>

<input id="indexFor" type="hidden" namefor="gua"/>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/transfer/transferList.js"></script>
