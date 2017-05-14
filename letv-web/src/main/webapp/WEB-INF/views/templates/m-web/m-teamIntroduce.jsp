<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String photoUrl = session.getAttribute("userphoto") == null ? null
			: session.getAttribute("userphoto").toString(); 
	String level = session.getAttribute("investorLevel")==null?"0":session.getAttribute("investorLevel").toString();
	String userLevel = session.getAttribute("userLevel")==null?null:session.getAttribute("userLevel").toString();
%>

<script type="text/javascript">
	var siteUserId = "<%=userId%>";
</script>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" href="<%=path %>/style/web.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<title></title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var userPhoto = "<%=photoUrl%>";
	var investorLevel = "<%=level%>";
	var userLevel = "<%=userLevel%>";
</script>
<style>
body{margin-bottom:100px;}
</style>
</head>


<body>
<div class="head_top">
    <p>详情经历</p>
    <div class="back"></div>
</div>
<div style="line-height: 35px; border-bottom: 1px solid #ccc;"><span style="padding-left: 10px;" id="name"></span><span style="padding-left: 10px;" id="position"></span></div>
<div class="teamIntroduce" id="founderCenter" style="border-bottom: 10px solid #efeef4;">
	<h3 class="tm_title">工作经历</h3>
	<div class="founderTit clearfix"><span class="s1">公司</span><span class="s2">岗位</span><span class="s3">开始时间</span><span class="s4">离职时间</span></div>
	<div class="list"></div>
</div>
<div class="teamIntroduce" id="BusinessCenter">
	<h3 class="tm_title">创业经历</h3>
	<div style="padding:0 10px;"></div>
</div>
<div class="teamIntroduce" id="EducationsCenter">
	<h3 class="tm_title">教育背景</h3>
	<div style="padding:0 10px;"></div>
</div>

<script type="text/javascript">
var id = getQueryString("id");
$(function(){
	$(".back").click(function(){
		history.go(-1);
	});
	//创始人列表
	$.ajax({
		url: path + "/crowdfundingFounder/selectFounderById.html",
		type: "post",
		dataType: "json",
		data: {"id": id},
		success: function(data){
			$("#name").text(data["info"]["name"]);
			$("#position").text(data["info"]["position"]);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	/* $("#name").text(name);
	$("#position").text(position); */
	$.ajax({
		url: path + "/crowdfundingFounder/getFounderWorksList.html",
		type: "post",
		dataType: "json",
		data: {"founderId": id},
		success: function(data){
			
			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
			for(var i = 0;i<l;i++){
				pArr.push('<div class="founderList clearfix"><span class="s1" title="'+data[i]["company"]+'">'+data[i]["company"]+'</span><span class="s2" title="'+data[i]["position"]+'">'+data[i]["position"]+'</span><span class="s3">'+data[i]["startTime"].substring(0,10)+'</span><span class="s4">'+data[i]["endTime"].substring(0,10)+'</span></div>');
			}
			pStr = pArr.join("");
			$("#founderCenter>div.list").html(pStr);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	$.ajax({
		url: path + "/crowdfundingFounder/getFounderBusinessList.html",
		type: "post",
		dataType: "json",
		data: {"founderId": id},
		success: function(data){
			
			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
			for(var i = 0;i<l;i++){
				pArr.push('<p>'+data[i]["description"]+'</p>');
			}
			pStr = pArr.join("");
			$("#BusinessCenter>div").html(pStr);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	$.ajax({
		url: path + "/crowdfundingFounder/getFounderEducationsList.html",
		type: "post",
		dataType: "json",
		data: {"founderId": id},
		success: function(data){
			
			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
			for(var i = 0;i<l;i++){
				pArr.push('<p>'+data[i]["createTime"].substring(0,10)+'~'+data[i]["endTime"].substring(0,10)+'，'+data[i]["school"]+'</p>');
			}
			pStr = pArr.join("");
			$("#EducationsCenter>div").html(pStr);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
});
</script>
</body>
</html>