<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String thirdNo = (String)session.getAttribute("thirdNo");
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript">
	var thirdNo = "<%=thirdNo%>"; //是否需要绑定用户
	/*if(siteUserId == "null"){
		window.location.href = path + "/common/login.html";
	}else{
		if(!thirdNo){//需要绑定用户
			$("body").append($('<div class="bgpop"></div>'));
			AlertDialog.tip("绑定用户后才可以发起众筹");
			setTimeout(function(){
				window.location.href = path + "/common/realBindUser.html?type=b";
			},1800);
		}
	}*/
</script>
<div class="box">
	<div class="navDiv">
        <a href="<%=path%>/common/index.html">首页</a> > 发起经营型项目 
    </div>
	<div class="publicProject">
    	<div class="pTitle">填写发起信息<span>(*为必填)</span></div>
        <div class="zcTitle clearfix">
        	<ul>
                <li><a class="ico4" href="<%=path %>/common/enterPubBenefit.html">筹爱心</a></li>
                <li><a class="ico1" href="<%=path %>/common/enterReward.html">筹好货</a></li>
                <li><a class="ico2" href="<%=path %>/common/enterStock.html">筹好业</a></li>
                <li class="cur"><a class="ico3" href="javascript:void(0);">筹好房</a></li>
            </ul>
        </div>
        
        <div class="stepDiv equity" id="stepDiv">
        	<hr />
            <dl class="dl1 cur">
            	<dt><span>1</span></dt>
                <dd>项目基本信息</dd>
            </dl>
            <dl class="dl2">
            	<dt><span>2</span></dt>
                <dd>发起人基本信息</dd>
            </dl>
            <dl class="dl3" style="left:360px!important;">
            	<dt><span>3</span></dt>
                <dd>公司认证</dd>
            </dl>
            <dl class="dl4" style="left:380px!important;">
            	<dt><span>4</span></dt>
                <dd>回报设置</dd>
            </dl>
            <dl class="dl5" style="right:180px!important;">
            	<dt><span>5</span></dt>
                <dd>项目资料</dd>
            </dl>
            <!-- <dl class="dl6">
            	<dt><span>6</span></dt>
                <dd>支付账号注册并了解融后管理</dd>
            </dl> -->
            <dl class="dl7">
            	<dt><span>6</span></dt>
                <dd>提交审核</dd>
            </dl>
        </div>
        
        <!--项目基本信息-->
        <div class="pInfo houseInfo">
        	<div class="title">
        		<p class="enter_save_tip" id="dataPauseTip"></p>
            	<p><i>*</i>项目基本信息</p>
                <a href="javascript:void(0);" name="dataPause" type="pause" pause="1"><span>收起</span></a>
            </div>
            <ul id="dataPauseBox">
            	<li>
                	<span class="name"><i>*</i>项目名称：</span>
                    <input type="text" id="projectName" maxlength="50"/>
                </li>
                <li>
                	<span class="name"><i>*</i>目标金额：</span>
                    <input type="text" id="raiseAmt" nullMessage="输入金额应该为数字" logicMessage="筹资金额不能低于500元" logicMessage2="筹资金额不能大于1000000000元" maxlength="10" logicMessage="筹资金额不能低于500元" maxlength="10"onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />&nbsp;&nbsp;元
                </li>               
                <li class="zc">
                	<span class="name"><i>*</i>众筹周期：</span>
                    <input class="cz" type="text" id="raiseDay"/>&nbsp;&nbsp;天
                </li>
                <li class="pAddress clearfix">
                	<span class="name"><i>*</i>行业类别：</span>
                    <div class="fl">
                    	<div class="clearfix">
                            <dl>
                                <select id="ptrade_one" nullMessage="请选择行业类别">
		                    		<option value="">请选择</option>
		                    	</select>
                            </dl>
                            <dl style="display:none;">
                                <select id="ptrade_two" nullMessage="请选择">
		                    		<option value="">请选择</option>
		                    	</select>
                            </dl>
                    </div>
                </li>
                
                <li class="pAddress clearfix">
                	<span class="name"><i>*</i>项目所在地：</span>
                    <div class="fl">
                    	<div class="clearfix">
                            <dl>
                                <select id="province" nullMessage="请选择省份">
		                    		<option value="">请选择省</option>
		                    	</select>
                            </dl>
                            <dl>
                                <select id="city" nullMessage="请选择城市">
									<option value="">请选择市</option>
								</select>
                            </dl>
                            <dl>
                                <select id="county">
		                    		<option value="">请选择县</option>
		                    	</select>
                            </dl>
                        </div>
                    </div>
                </li>
                <li class="pAddress clearfix">
                	<span class="fl name"><i>*</i>项目简介：</span>
                	<textarea id="ProjectDption"  maxlength="200" placeholder="最大长度不可超过200" nullMessage="项目简介不能为空"></textarea>
                </li>
                <li class="pAddress clearfix">
                	<span class="fl name">详细地址：</span>
                	<textarea id="address" maxlength="200" placeholder="最大长度不可超过200"></textarea>
                </li>
                <li class="coverDiv clearfix">
                    <span class="fl name"><a style="color:#fd3c3c;">*</a>项目封面：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="cover" id="image_file" nullMessage="请上传项目封面"><a href="javascript:void(0);">请选择</a></div>
                    <input type="hidden" id="loan_logo_url"/>
                    <i>支持jpg、jpeg、png、gif格式，大小不超过2M。最佳尺寸：589px × 370px</i>
                </li>
                <li style="padding-left:326px;display:none;" id="imgheadLi"><img id="imghead" width="230" height="175"/></li>
                <li style="text-align:center;">
                	<span class="button" id="saveDataBtn">保存项目信息</span>
                </li>
            </ul>
        </div>
        
      <div class="pInfo sponsor Housesponsor" style="margin-top:40px;">
        	<div class="title">
        		<p class="enter_save_tip" id="enterPauseTip"></p>
            	<p><i>*</i>发起人基本信息</p>
                <a href="javascript:void(0);" name="enterPause" type="pause"><span>展开</span></a>
            </div>
        	<ul id="enterPauseBox" style="display:none;">
            	<li>
                	<span class="name"><i>*</i>真实姓名/公司名称：</span>
                    <input type="text" id="enterProUser" nullMessage="真实姓名/公司名称不能为空"/>
                </li>
                <li>
                	<span class="name"><i>*</i>身份证号码/组织机构代码：</span>
                    <input type="text" id="cardCode" nullMessage="身份证号码/组织机构代码不能为空"/>
                </li>
                <li class="uploadPhoto clearfix">
                	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                	<span class="name fl"><i>*</i>上传身份证(营业执照)正面照片：</span>
                    <div id="licenceFrontBtn" nullMessage="请上传证件的正面照片"><span>发起人身份名片、职位证照片<br />或扫描件(大小不超过2M)</span></div>
                    <input type="hidden" id="licenceFront_url"/>
                    <div style="width:322px;height:202px;line-height:202px;background:#ECECEC;border:none;display:none;" id="licenceFrontDiv"><img style="max-width:322px;max-height:202px;" id="licenceFrontImage"/></div>
                </li>
                <li class="uploadPhoto clearfix">
                	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                	<span class="name fl"><i>*</i>上传身份证(营业执照)反面照片：</span>
                    <div id="licenceReveseBtn" nullMessage="请上传证件的反面照片"><span>发起人身份名片、职位证照片<br />或扫描件(大小不超过2M)</span></div>
                    <input type="hidden" id="licenceRevese_url"/>
                    <div style="width:322px;height:202px;line-height:202px;background:#ECECEC;border:none;display:none;" id="licenceReveseDiv"><img style="max-width:322px;max-height:202px;" id="licenceReveseImage"/></div>
                </li>
                <li>
                	<span class="name"><i>*</i>手机号码/联系电话：</span>
                    <input type="text" id="mobile"/>
                </li>
                <li style="text-align:center;">
                	<span class="button" id="saveEnterBtn">保存发起人信息</span>
                	<span class="button none" id="overEnterBtn" style="margin-left:20px;">完成填写</span>
                </li>
            </ul>
        </div>
        
         <div class="pInfo companyDiv" style="margin-top:40px;">
        	<div class="title">
        		<p class="enter_save_tip" id="companyRZPauseTip"></p>
            	<p>公司认证</p>
                <a href="javascript:void(0);" name="companyRZPause" type="pause"><span>展开</span></a>
            </div>
            <ul id="companyRZPauseBox" style="display:none;">
	            <p class="write">上传您的部分证件，这些证件只做风控和审核使用。更多的证件通过审核会大大增加投资人对您的项目的信任<br/><br/>支持jpg、jpeg、png、gif格式，大小不超过2M。</p>
            	<li class="clearfix">
                	<span class="name fl">法定代表人身份证：</span>
                	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="frsfzBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="frsfzBtn_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="frsfzLi">
                	<img width="100%" height="100%" id="frsfz_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">法人代表个人信用报告：</span>
                	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="frgrxxbgBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="frgrxxbg_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="frgrxxbgLi">
                	<img width="100%" height="100%" id="frgrxxbg_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">营业执照：</span>
                	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="yyzzBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="yyzz_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="yyzzLi">
                	<img width="100%" height="100%" id="yyzz_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">营业执照副本：</span>
                	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="yyzzfbBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="yyzzfb_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="yyzzfbLi">
                	<img width="100%" height="100%" id="yyzzfb_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">税务登记证：</span>
                	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="swdjzBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="swdjz_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="swdjzLi">
                	<img width="100%" height="100%" id="swdjz_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">税务登记副本：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="swdjzfbBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="swdjzfb_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="swdjzfbLi">
                	<img width="100%" height="100%" id="swdjzfb_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">组织机构代码证：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="zzjgdmzBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="zzjgdmz_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="zzjgdmzLi">
                	<img width="100%" height="100%" id="zzjgdmz_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">组织机构代码证副本：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="zzjgdmzfbBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="zzjgdmzfb_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="zzjgdmzfbLi">
                	<img width="100%" height="100%" id="zzjgdmzfb_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">公司照片：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="gszpphotoBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="gszpphoto_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="gszpphotoLi">
                	<img width="100%" height="100%" id="gszpphoto_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">场地租赁合同：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="cdzlhtBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="cdzlht_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="cdzlhtLi">
                	<img width="100%" height="100%" id="cdzlht_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">财务报表：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="cwbbPhotoBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="cwbbPhoto_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="cwbbPhotoLi">
                	<img width="100%" height="100%" id="cwbbPhoto_image"/>
                </li>
                <li class="clearfix">
                	<span class="name fl">卫生许可证：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="wsxkzBtn"><a href="javascript:void(0);">选择文件</a></div>
                    <input type="hidden" id="wsxkz_url"/>
                </li>
                <li style="width:80px;height:80px!important;margin-left:326px;display:none;" id="wsxkzLi">
                	<img width="100%" height="100%" id="wsxkz_image"/>
                </li>
                <li class="clearfix" style="text-align:center;">
                	<span class="button" id="saveCompanyRZ">保存公司认证</span>
                </li>
            </ul>
        </div>
        
        <div class="pInfo eReportSet" style="margin-top:40px;">
        	<div class="title">
        		<p class="enter_save_tip" id="backPauseTip"></p>
            	<p><i class="imp">*</i>回报设置</p>
                <a href="javascript:void(0);" name="backSetPause" type="pause"><span>展开</span></a>
            </div>
            <div id="backSetPauseBox" style="display:none;">
	        	<ul style="float:left;width:600px;">
	            	<li>
	                	<span class="name"><i>*</i>融资总额￥：</span>
	                    <input type="text" readonly="readonly" id="totalF" nullMessage="请输入融资总额"/>
	                </li>
	                <li>
	                	<span class="name"><i>*</i>项目方出资及比例￥：</span>
	                    <input type="text" id="fp" nullMessage="请输入项目方出资金额" logicMessage="筹资金额不能低于500元" maxlength="10"onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
	                    <em id="fpr_text">20</em>%
	                </li>
	                <li>
	                	<span class="name"><i>*</i>投资人出资及比例￥：</span>
	                    <span class="tzr" id="ip_text"></span>
	                    <em id="ipr_text">80</em>%
	                </li>
	                <li>
	                	<span class="name"><i>*</i>项目方分红比例：</span>
	                    <input type="text" id="xmr" nullMessage="请输入项目方分红比例"/>&nbsp;&nbsp;%
	                </li>
	                <li>
	                	<span class="name">投资方分红比例：</span>
	                    <span class="tzr" id="tmr_text">0</span>&nbsp;&nbsp;%
	                </li>
	                <li>
	                	<span class="name"><i>*</i>认购份数：</span>
	                    <input type="text" id="fraction" nullMessage="请输入认购份数"/>
	                    <em>(最多199份)</em>
	                </li>
	                <li>
	                	<span class="name">单笔投资人最低投资金额￥:</span>
	                    <span class="tzr" id="lessF_text"></span>
	                </li>
	                <li class="clearfix" style="text-align:center;">
	                	<span class="button" id="saveBackSet">保存回报设置</span>
	                </li>
	            </ul>
	            <div class="recom_count_box">
	            	<p>份数推荐</p>
	            	<ul id="recomCount" class="recom_count"></ul>
	            </div>
	            <div class="clear"></div>
	        </div>
        </div>
        
        <div class="pInfo projectData eProjectData" style="margin-top:40px;">
        	<div class="title">
        		<p class="enter_save_tip" id="projectPauseTip"></p>
            	<p>项目资料详情</p>
                <a href="javascript:void(0);" name="proDataPause" type="pause"><span>展开</span></a>
            </div>
        	<ul id="proDataPauseBox" style="display:none;">
            	<li>
                	<span class="name"><i>*</i>创始人姓名：</span>
                    <input type="text" id="csrName"/>
                </li>
                <li class="clearfix pImg">
                	<span class="name fl"><i>*</i>创始人头像：</span>
                    <input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                    <div class="fl" id="csrPhotoBtn" nullMessage="创始人头像不能为空"></div>
                    <input type="hidden" id="csrPhoto_url"/>
                    <div class="crs_photo_div" id="csrPhoto_div">
                    	<img width="100%" height="100%" id="csrPhoto_img"/>
                    </div>
                </li>
                <li class="clearfix companyStatus">
                	<span class="name fl">公司现状：</span><br/>
                    <div class="fl info">
                    	<div class="clearfix foundData">
                        	<span class="fl" style="width:124px;">成立日期：</span>
                            <div class="fl">
                                <input id="compDate" class="datetime" type="text" style="margin-left:20px;" 
					onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"/>
                            </div>
                        </div>
                        <div>
                        	<span>上季度营业收入(元)：</span>
                            <input type="text" id="lastQuarterIncome"/>
                        </div>
                        <div>
                        	<span>上季度营业利润(元)：</span>
                            <input type="text" id="lastQuarterProfit"/>
                        </div>
                        <div>
                        	<span>上年度营业收入(元)：</span>
                            <input type="text" id="lastYearIncome"/>
                        </div>
                        <div>
                        	<span>上年度营业利润(元)：</span>
                            <input type="text" id="lastYearProfit"/>
                        </div>
                    </div>
                </li>
                <li class="clearfix introduce">
                	<span class="name fl"><i>*</i>项目介绍：</span>
                    <div class="fl" name="ueditor" style="width:498px;height:486px;overflow:auto;" id="proDetailNull"nullMessage="项目介绍不能为空">
                    	<script id="proDetail" type="text/plain" style="width:478px;height:456px;"></script>
                    </div>
                </li>
                <li class="rzBudget clearfix">
                	<span class="name fl">融资预算：</span>
                    <textarea id="financeBudget"></textarea>
                </li>
                <li class="clearfix pVideo">
                	<span class="name fl"><!-- <i>*</i> -->项目视频：<em>(网站链接)</em></span>
                    <div class="fl">
                    	<input type="text" id="loanVideo" placeholder="(在pc端显示)" nullMessage="项目视频不能为空" logicMessage="请输入swf格式的链接"/><br />
                        <span><!-- <i>*</i> -->视频简介：</span><br />
                        <textarea id="videoDes" nullMessage="视频简介不能为空"></textarea>
                    </div>
                </li>
                <li class="clearfix pVideo">
                	<span class="name fl"><!-- <i>*</i> -->项目视频(手机端)：<em>(网站链接)</em></span>
                    <div class="fl">
                    	<input type="text" id="mobileVideo" placeholder="(在手机端显示)" nullMessage="项目视频不能为空" logicMessage="请输入swf格式的链接"/><br />
                    </div>
                </li>
                <li class="clearfix pImg">
                	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
                	<span class="name fl"><i>*</i>项目展示图片：<em>(最多上传5张)</em></span>
                    <div class="fl" id="proPhotoBtn" nullMessage="请上传项目展示图片"></div>
                    <input type="hidden" id="proPhoto_url"/>
                </li>
                <li class="pro_img" id="proImageLi" style="display:none;">
                	<div class="pimg">
                		<img width="100%" height="100%"/>
                		<div class="del_pro_image" id="proImage1">×</div>
                	</div>
                	<div class="pimg">
                		<img width="100%" height="100%"/>
                		<div class="del_pro_image" id="proImage2">×</div>
                	</div>
                	<div class="pimg">
                		<img width="100%" height="100%"/>
                		<div class="del_pro_image" id="proImage3">×</div>
                	</div>
                	<div class="pimg">
                		<img width="100%" height="100%"/>
                		<div class="del_pro_image" id="proImage4">×</div>
                	</div>
                	<div class="pimg">
                		<img width="100%" height="100%"/>
                		<div class="del_pro_image" id="proImage5">×</div>
                	</div>
                </li>
                <li style="text-align:center;margin-top:50px;">
                	<span class="button" id="svaeProData">保存项目资料</span>
                </li>
            </ul>
        </div>
        
        <div class="pInfo knowDiv" style="margin-top:40px;">
<!--         	<div class="title"> -->
<!--         		<p class="enter_save_tip" id="projectSubTip"></p> -->
<!--             	<p> 了解支付账号注册与融后管理</p> -->
<!--                 <a href="javascript:void(0);" name="projectSubPause" type="pause"><span>展开</span></a> -->
<!--             </div> -->
            <div id="projectSubDiv" style="display:none;">
<!-- 	            <div class="info"> 
	            	<dl>
	                	<dt>1.支付帐号注册</dt>
	                    <dd>爱筹网为了保障投资资金安全与第三方支付平台合作，进行项目融资资金的托管。在此可以注册第三方支付平台帐号，为以后在线融资做好准备。</dd>
	                </dl>
	                <dl>
	                	<dt>2.为什么要注册第三方支付平台账号</dt>
	                    <dd>1、线上付款至第三方支付平台后，融资款在所有投资人同意后可提出，增强资金安全保障。</dd>
	                    <dd>2、避免线下交易，因操作不当，造成不必要的经济损失。</dd>
	                    <dd>3、众筹方式可能投资人会有多个，线上付款简化操作流程。</dd>
	                </dl>
	                <dl>
	                	<dt>3.了解融后管理</dt>
	                    <dd><span>融资后，我们建议融资方和投资人一起成立有限合伙企业来管理融资后的项目。由项目发起方充当该合伙企业的普通合伙人，其他投资人为有限合伙人</span></dd>
	                </dl>
	            </div>-->
	            <ul>
	            	<li>
	                	<div class="uploadData">
	                    	<a href="<%=path%>/htmlforpdf/getContractView.html?nodeType=enterHouse">下载《筹好房服务协议》</a>
	                    </div>
	                </li>
	                <li class="submitBtn">
	                	<a class="btn" href="javascript:void(0);" id="subLoan">提交项目，等待审批</a>
	                    <div>
	                        <label><input id="checkLoanView" class="fl" type="checkbox" checked="checked"/>阅读并同意的爱筹网的《筹好房服务协议》</label>
	                        <span>工作人员会在24小时内审核，如有问题将和您联系 </span>
	                    </div>
	                </li>
	            </ul>
            </div>
            
        </div>
    </div>
</div>
       
<input type="hidden" id="projectLoanNo"/>

<input id="indexFor" type="hidden" namefor="enter"/>
<!-- 背景遮盖层 -->
	<div id="bgpop" class="bgpop" name="bigp"></div>
	<!-- 协议范文 -->
	<div class="agree_box">
		<div style="text-align: center;margin-bottom:-5px;margin-top:5px;font-size:16px;width: 555px;float: left;">用户协议</div>
		<div class="agree_close"></div>
		<div id="agreeContent" class="agree_con"></div>
		<p id="agreeTime" class="agree_date"></p>
	</div>
	
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ueditor/ueditorDo.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/enterHouse.js"></script>