<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<div class="">
    <div class="box jlzcDetail_box">
        <div class="jlzcDetail">
            <div class="right jlzc_leftimg">
                <div style="position:relative;"><img style="width:470px;height:354px;" id="loanCover"/></div>
                
                <div style="position:relative;">
                <div class="jlzcDetail_seep">
                <p class="clearfix">
                    <span class="left">已筹资<span class="orgJ">￥<i id="approveAmt"></i></span></span>
                    <span class="right">目标 <span class="orgJ">¥<i id="fundAmt"></i></span></span>
                </p>
                <!--<p><i>已筹资</i><b>￥30,989</b></p>-->
                <p class="jlzc_jdt" class="clearfix">
                    <span id="pBar" style="width:0%;"></span>
                </p>
                <p class="jlzc_jdtsj" class="clearfix">
                    <span class="left">已完成 <span class="orgJ"><i id="supportRatio1"></i></span></span>
                    <span class="right">剩余天数<span class="orgJ" id="remainDay"></span>/<i id="fundDays" ></i>天</span>
                </p>
                </div>
                </div>
                
                <p class="jlzc_div3_gz left" id="gzimg"><i>关注</i> <img src="<%=path%>/images/xin1.png" class="jlzcimg" /></p>
                <span class="jlzc_div3_gz_fx right">
                	<!-- 分享：<img src="<%=path%>/images/kj.png" />
                    <img src="<%=path%>/images/xlwb.png" />
                    <img src="<%=path%>/images/txwb.png" />
                    <img src="<%=path%>/images/wx1.png" />
                    <img src="<%=path%>/images/add.png" /> -->
                    <div class="bdsharebuttonbox">
                    	<a href="#" class="bds_more" data-cmd="more"></a>
                    	<a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                    	<a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                    	<a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
                    	<a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
                    	<a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                    </div>
					<script>
						window._bd_share_config={
								"common":{
									"bdSnsKey":{},
									"bdText":"123456",
									"bdMini":"2",
									"bdMiniList":false,
									"bdPic":"http://www.5aichou.com/images/logo.png",
									"bdStyle":"0",
									"bdSize":"24"
									},
								"share":{"bdText": "爱筹网，真众筹，好平台"}};
						with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
                 </span>
            </div>
            <div class="left jlzc_right">
                <div class="jlzc_div1 clearfix">
                    <p class="jlzc_div1_tit" id="loanName"></p>
                    <p class="jlzc_zcmb" id="loanDesc"></p>
                </div>
                <div id="topBackSetList"></div>
                <div class="jlzc_more">
                	<a href="javascript:void(0);" onclick="showBackSetDiv();">更多∨</a>
                </div>
            </div>
        </div>
        <div class="lt_ko"></div>
        <div class="lt_ko"></div>
    </div>
</div>

<div class="box jlzcBox">
	<div class="jlzc_rig_ clearfix">
    	<div class="jlzc_rig_1 left">
        	<span class="jlzc_butsx" id="privateSale">私信</span><br />
            <span class="jlzc_fqr"><i>发起人</i><br/><i id="loanUser"></i></span>
        </div>
        <p class="fl jlzc_rig_6"><img id="userPhone" style="width:80px;height:80px;border-radius:50%;"/></p>
        <p class="fl jlzc_rig_7">项目地点：<img src="<%=path%>/images/zuob.png" />&nbsp;&nbsp;<i id="loanAddress"></i></p>
        <div class="jlzc_rig_2 fl">
            <p id="selfDetail"><i>个人简介</i>&nbsp;&nbsp;&nbsp;&nbsp;</p>
        </div>
        
    </div>
    <div class="left">
        <div class="jlzc_list" id="loanDetailTab">
            <ul id="detailTab">
                <li class="on"><a href="javascript:void(0);" name="proInfo" url="">项目介绍</a></li>
                <li><a href="javascript:void(0);" name="proEvolve" url="">项目进展</a></li>
                <li><a href="javascript:void(0);" name="proRecord" url="/crowdfunding/getSupportList.html">众筹记录(<i id="supportNum1"></i>)</a></li>
                <li><a href="javascript:void(0);" name="proComment" url="/crowdfunding/getCommentList.html" id="freshComment">评论(<i id="commentNum"></i>)</a></li>
            </ul>
        </div>
        <div id="detailBody">
	        <div class="jlzc_art" id="proInfoContent" h="1" style="width:840px;padding:15px;"></div>
	        <!-- 项目进展 -->
	        <div class="jlzc_art" id="proEvolveContent" style="display:none;" h="1">
	        	<div class="clear pro_jz" style="height:500px;">
		            <p class="jl"><img src="<%=path %>/images/jl.png" />这里记录了发起人为梦想努力的一些成果，快来看看吧~</p>
		            <ul id="loanProgress"></ul>
		        </div>
	        </div>
	        <div class="jlzc_art" id="proRecordContent" style="display:none;">
	        	<!-- <div class="clear">
		            <div class="left pro_mb" style="margin-left:100px;">
		                <p>众筹目标：￥<i id="fundAmt"></i> </p>
		                <p style="padding:10px 0;">已获得：￥<i id="approveAmt1"></i> </p>
		            </div>
		            <div class="left pro_mb">
		                <p>参与众筹人次：<i id="supportNum"></i> 人次</p>
		                <p style="padding:10px 0;">离众筹完成还剩下：<i id="remainDay1"></i> 天</p>
		            </div>
		        </div> -->
		        <div class="clear pro_zctab">
		        	<table cellspacing="0" cellpadding="0" style="width:870px;padding-bottom:0;">
		        		<tr class="odd">
	                        <td style="width:120px;font-size:14px;">序号</td>
	                        <td style="width:165px;font-size:14px;">参与人</td>
	                        <td style="width:180px;font-size:14px;">众筹金额</td>
	                        <td style="width:300px;font-size:14px;">众筹时间</td>
	                        <td style="width:130px;font-size:14px;">状态</td>
	                    </tr>
		        	</table>
		            <table id="suportTable" cellspacing="0" cellpadding="0" style="width:870px;"></table>
		        </div>
		        <div class="page" id="suportPage"></div>
	        </div>
	        <div class="jlzc_art" id="proCommentContent" style="display:none;padding-top:30px;">
	        	<div class="clear pro_pl" style="margin-left:35px;">
		            <div class="pro_pl_are">
		            	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
		                <!-- <div id="image_file" class="pro_pl_sc" style="top:190px;"><a href="javascript:void(0);">上传图片</a></div> -->
		                <input type="hidden" id="loan_logo_url"/>
		                <textarea id="comVal" placeholder="评论不能少于5个字哦！"></textarea>
		                <div style="display:none;" id="imgheadLi"><img id="imghead" width="110" height="110"/></div>
		                <div class="pro_pl_butdiv"><a href="javascript:void(0);" class="pro_pl_but" id="subComment">发表评论</a></div>
		            </div>
		            <div class="pro_pl_list">
<!-- 		                <ul> -->
<!-- 		                    <li><a href="javascript:vodi(0);" class="on">全部</a></li> -->
<!-- 		                    <li><a href="javascript:vodi(0);">只看发起人</a></li> -->
<!-- 		                    <li><a href="javascript:vodi(0);">只看投资人</a></li> -->
<!-- 		                </ul> -->
		            </div>
		            <div class="pro_pl_tiao">
		                <ul id="commentUl"></ul>
		            </div>
		            <div class="page" id="commonPage"></div>
		        </div>
	        </div>
        </div>
    </div>
    <div class="right" style="  width: 300px;">
        <div class="jlzc_rig_4">支持此项目</div>
        <div id="bottomBackSetList"></div>
        
        <div class="jlzc_rig_4">项目动态</div>
        <div class="jlzc_rig_3">
            <ul id="proDynBox"></ul> 
            <div class="xian"></div>
            <div class="lt_ko"></div>
        </div>
        
    </div>
</div>
<!-- 弹出更多回报设置 -->
<div class="site_tip_div" id="dialogTipDiv" style="width:660px;height:400px;overflow-y:auto;">
	<div class="head">
		<span class="w">回报设置</span>
	</div>
	<div id="dialogBackSet" style="padding:3px;"></div>
</div>

<!-- 私信弹出框 -->
<div class="site_tip_div" id="priSaleDiv" style="height:240px;">
	<div class="head">
		<span class="w">私信</span>
	</div>
	<div style="padding:20px;">
		<textarea class="talkArea" id="saleArea" placeholder="请输入私信内容" nullMessage="请输入私信内容"></textarea>
	</div>
	<p style="text-align:center;"><span class="button" id="saleBtn">确定</span></p>
</div>



<!--背景遮盖层 -->
<div id="bgpop2" class="bgpop2" name="bigp"></div>	
      <!-- 项目相关大图片展示-->
<div id="loanBig" class="big_pingbox" name="bigp"></div>
<!--定位大图片的1个按钮 -->
<div id="big_close" class="big_pigc" name="bigp"></div> 
        
    

<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/loanDetail-entity.js"></script>
