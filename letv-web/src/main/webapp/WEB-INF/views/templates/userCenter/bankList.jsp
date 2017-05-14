<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<div class="back_colh">
	 <div class="box clearfix">
	  <div class="fl xt_news">
		   <div class="cont_xq">
		    <%-- <a href="<%=path %>/common/modifyData.html"><img id="centerUserPhoto" class="rightImg"></a> --%>
		    <div class="tx" id="image_file">
	    		<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
	        	<a href="javascript:void(0);" class="photo"><img id="imgheadPhoto" src="" width="113px" height="113px" style="border-radius:50%;"/></a>
	            <div class="cover" nullMessage="请上传项目封面" style="height:3px"></div>
	            <input type="hidden" id="loan_logo_url"/>
	        </div>
			<p class="mt15 ft16" id="userRealName"></p>
			<p class="rzh_cz"><a class="rzh1 cur" href="<%=path %>/common/accountSecurity.html"></a><a class="rzh2" id="realNameRZ"></a><a class="rzh3" id="bankCardRZ"></a></p>
		   </div>
		   <ul class="lf_nav">
			   <li class="bor_top"><a href="<%=path %>/common/myCenter.html">账户总览</a></li>
			   <li class="rel hg225"><a id="rightProjectList">项目管理</a>
			    <div class="abs_div">
				 <p class="bor_top"><a href="<%=path %>/common/administrationProduct.html">产品众筹</a></p>
				 <p><a href="<%=path %>/common/administrationPublic.html">非公开融资</a></p>
				 <p><a href="<%=path %>/common/administrationStock.html">公益众筹</a></p>
				</div>
			   </li>
			   <li><a href="<%=path %>/common/myTrade.html">交易记录</a></li>
			   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
			   <li class="cur"><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
			   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
			   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
			   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
		   </ul>
		</div>
	  <div class="fr rg_side clearfix" style="height:787px;">
	   
	   <h4 class="tit_xt clearfix mb20">银行卡列表</h4>
	   <div class="clearfix" id="bankAccount" style="width:900px;">
<!--        <div class="clearfix yh_num fl hg_88 cur"> -->
<!-- 	    <div class="fl"> -->
<!-- 		 <img src="<%=path %>/images/letv/yh-img.png"> -->
<!-- 		 <p class="mt25 col9 ft_normal"> 持卡人姓名：*美</p> -->
<!-- 		</div> -->
<!-- 		<div class="fr"> -->
<!-- 		 <span>尾号 5902</span> -->
<!-- 		 <p class="mt25 tr ft_normal"><a class="col_blue">删除</a></p> -->
<!-- 	    </div> -->
<!-- 	   </div> -->
<!-- 	   <div class="clearfix yh_num fl hg_88"> -->
<!-- 	    <div class="fl"> -->
<!-- 		 <img src="<%=path %>/images/letv/yh-img.png"> -->
<!-- 		 <p class="mt25 col9 ft_normal"> 持卡人姓名：*美</p> -->
<!-- 		</div> -->
<!-- 		<div class="fr"> -->
<!-- 		 <span>尾号 5902</span> -->
<!-- 		 <p class="mt25 tr ft_normal"><a class="col9">删除</a></p> -->
<!-- 	    </div> -->
<!-- 	   </div> -->
<!-- 	   <div class="clearfix yh_num fl hg_88" style="margin-right:0"> -->
<!-- 	    <div class="fl"> -->
<!-- 		 <img src="<%=path %>/images/letv/yh-img2.png"> -->
<!-- 		 <p class="mt25 col9 ft_normal"> 持卡人姓名：*美</p> -->
<!-- 		</div> -->
<!-- 		<div class="fr"> -->
<!-- 		 <span>尾号 6299</span> -->
<!-- 		 <p class="mt25 tr ft_normal"><a class="col9">删除</a></p> -->
<!-- 	    </div> -->
<!-- 	   </div> -->
<!-- 	   <div class="clearfix yh_num fl hg_88 tc mt52" style="position: relative;overflow:hidden;"> -->
<!-- 	    <img src="<%=path %>/images/letv/jia5.png" class="mt10"> -->
<!-- 	    <a class="col9" href="<%=path%>/common/bankAdd.html" style="display: block;width:100%; height:100%;top:0;left:0;position:absolute;padding-top:50px;">添加银行卡</a> -->
<!-- 	   </div> -->
	   </div>
	  </div>
	 </div>
</div>
<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/bankList.js"></script>