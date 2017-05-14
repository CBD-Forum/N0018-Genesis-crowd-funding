<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path =  request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <title> 乐视网站页面</title>
<style type="text/css">
</style>
<link href="css/style.css" rel="stylesheet">
  <script type="text/javascript">
		// 最小化客服窗口
		function minimumWindow(){
			 document.getElementById("chatBox").style.display='block';
			 document.getElementById("chatMain").style.display='none';
		}
  		//关闭客服窗口
		 function closeWindow(){
			  document.getElementById("chatBox").style.display='block';
			  document.getElementById("chatMain").style.display='none';
		}
		// 打开客服窗口
		function openChatWindow(){
				 document.getElementById("chatBox").style.display='none';
				 document.getElementById("chatMain").style.display='block';
				if(typeof(exec_obj)=='undefined'){
						exec_obj = document.createElement('iframe');
						exec_obj.name = 'tmp_frame';
						exec_obj.src = 'http://10.75.164.32:9080/webchat/new/openChatWind.html';
						exec_obj.style.display = 'none';
						document.body.appendChild(exec_obj);
				}else{
						exec_obj.src = 'http://10.75.164.32:9080/webchat/new/openChatWind.html?' + Math.random();
				}
		}
		// 页面加载完成调用
		function initChatDiv(){
				var parent = document.getElementsByTagName("body")[0];
	            var outerDiv = document.createElement("div");
				outerDiv.setAttribute("id", "chatMain");
				outerDiv.setAttribute("class", "chat");
				outerDiv.setAttribute("style", "display: none");
				parent.appendChild(outerDiv);
				chatIframe=document.createElement("iframe");
				chatIframe.setAttribute("src","http://10.75.164.32:9080/webchat/index?custId=custId&custName=custName&mobile=mobile&channe=channe");
				chatIframe.setAttribute("name","myframe");
				chatIframe.setAttribute("style","width:320px;height:540px;border: 0px");
				var chatMain = document.getElementById("chatMain");
				chatMain.appendChild(chatIframe);
				var chatBox = document.createElement("div");
				chatBox.setAttribute("id","chatBox");
				chatBox.setAttribute("class","chatbox");
				parent.appendChild(chatBox);
				var chatBoxInnerA=document.createElement("a");
				chatBoxInnerA.setAttribute("href","javascript::void;");
				chatBoxInnerA.setAttribute("onclick","openChatWindow()");
				chatBox.appendChild(chatBoxInnerA)
				var chatBoxInnerSpan=document.createElement("span");
				chatBoxInnerA.setAttribute("class","count");
				chatBox.appendChild(chatBoxInnerSpan)
				var chatBoxInnerAInnerImg=document.createElement("img");
				chatBoxInnerAInnerImg.setAttribute("src","images/chatbtn.png");
				chatBoxInnerA.appendChild(chatBoxInnerAInnerImg);
				var chatBoxInnerAInnerSpan=document.createElement("span");
				chatBoxInnerAInnerSpan.setAttribute("class","images/chatbtn.png");
				chatBoxInnerA.appendChild(chatBoxInnerAInnerSpan);
				chatBoxInnerAInnerSpan.innerHTML="咨询在线客服";
		}
  </script>
 </head>
 <body>
	
</body>
<script type="text/javascript">
		initChatDiv();
</script>
</html>