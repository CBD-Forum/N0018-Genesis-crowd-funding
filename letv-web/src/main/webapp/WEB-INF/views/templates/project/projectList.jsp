<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>

<div class="box projectNav1" id="searchType">
	<div id="loanType"><span class="fl">众筹类型：</span><a class="wid cur" code="">全部</a><a code="product">产品众筹</a><a code="stock">非公开融资</a><a code="public">公益众筹</a></div>
	<div id="loanProcess"><span class="fl">项目状态：</span><a class="wid cur" code="">全部</a><a code="preheat">预热</a><a code="funding">众筹中</a><a code="success">众筹成功</a></div>
	<div id="sort"><span class="fl">排列方式：</span><a class="wid cur" code="">默认</a><a code="releaseTime">最新上线<i></i></a><a code="fundAmt">目标金额<i></i></a><a code="supportNum">支持人数<i></i></a><a code="approveAmt">筹资额<i></i></a></div>
</div>
<div class="box clearfix projectList"  style="overflow: inherit;"  id="projectList">
	<ul>
	</ul>
</div>
<div class="letvPage"></div>
<div id="bgpop" class="bgpop" style="z-index:100;"></div>


<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/common/jPagesList.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/projectList.js"></script>
