<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<div class="box">
	<div class="menu_tit"><a href="<%=path%>/common/index.html">首页</a> > <a>项目列表</a></div>
</div>

<div class="bgColorWh xmlb_box">
<div class="box">
	<ul class="xmlb-sx" id="searchType">
    	<li class="searchDiv">
            <div class="search">
            	<input type="text" id="listingSearch" placeholder="搜索项目" onkeydown= "if(event.keyCode==13)$('#topSearchBtn').click()"/>
	            <span id="listingSearchBtn">&nbsp;&nbsp;&nbsp;</span>
            </div>
        </li>
    	<li>
        	<p class="lx">项目类型：</p>
            <div class="nr" id="loanProcess">
            	<a href="javascript:void(0);" class="cur" code="">全部</a>
            	<a href="javascript:void(0);" class="red" code="preheat">挂牌中</a>
            	<a href="javascript:void(0);" code="success">挂牌结束</a>
            </div>
        </li>
    </ul>
</div>
</div>
<div class="box">
	<div class="indexMain clearfix">
    	<div class="main_list" id="projectList">
            <ul class="main_list_ul">
            </ul>
        </div>
        
        <div class="page" style="text-align: right;">
        </div>
        
    </div>
    
    
</div>


<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/listingService.js"></script>
