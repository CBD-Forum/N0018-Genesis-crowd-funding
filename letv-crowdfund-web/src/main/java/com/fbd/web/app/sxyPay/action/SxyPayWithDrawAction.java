package com.fbd.web.app.sxyPay.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fbd.core.app.sxyPay.common.ResultHandler;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.thirdtrusteeship.common.TrusteeshipConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.HttpServletUtils;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.sxyPay.service.ISxyPayRechargeService;
import com.fbd.web.app.user.service.IUserService;


@Controller
@Scope("prototype")
@RequestMapping("/sxyPay/withDraw")
public class SxyPayWithDrawAction extends BaseAction {
	
    /**
     * 
     */
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory.getLogger(SxyPayWithDrawAction.class);
    @Resource
    private ISxyPayRechargeService sxyPayRechargeService;
    @Resource
    private IUserService userService;
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;

	@RequestMapping(value = "/withDrawNotify.html")
    public  void withDrawNotify(HttpServletRequest request,HttpServletResponse res) throws Exception{
		
	    logger.info("---------->首信易支付充值接口同步回调开始调用============");
        String result = this.receiveRechargeCallback(request);
        logger.info("---------->首信易支付充值接口同步回调调用结束，返回的结果为：:" + result);
		
		HttpServletUtils.outString(res, "sent");
    }
	private String receiveRechargeCallback(HttpServletRequest request) {
		String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			 //获取参数
			  String v_oid = request.getParameter("v_oid");//订单编号
			  logger.info("-----------首易信充值异步通知订单号-orderId：-->"+v_oid);
			  String v_pmode = request.getParameter("v_pmode");//支付方式
			  String v_pstatus = request.getParameter("v_pstatus");//支付结果 1支付成功 3 支付失败
			  logger.info("-----------首易信充值异步通知支付状态-orderId：-->"+v_oid);
			  String v_pstring = request.getParameter("v_pstring");//支付结果信息说明
			  logger.info("-----------首易信充值异步通知支付结果信息-pstring：-->"+v_pstring);
			  String v_count = request.getParameter("v_count");//订单个数
			  logger.info("-----------首易信充值异步通知订单个数-count：-->"+v_count);
			  String v_amount = request.getParameter("v_amount");//订单金额
			  logger.info("-----------首易信充值异步通知订单金额-amount：-->"+v_amount);
			  String v_moneytype = request.getParameter("v_moneytype");//币种
			  logger.info("-----------首易信充值异步通知币种-moneytype：-->"+v_moneytype);
			  String v_md5money = request.getParameter("v_md5money");//数字指纹
			  logger.info("-----------首易信充值异步通知数字指纹-md5money：-->"+v_md5money);
			  String v_mac = request.getParameter("v_mac");//数字指纹
			  logger.info("-----------首易信充值异步通知数字指纹-v_mac：-->"+v_mac);
			  String v_sign = request.getParameter("v_sign");//RSA数字指纹
			  logger.info("-----------首易信充值异步通知数字指纹-v_sign：-->"+v_sign);
//			  v_pstring = new String(v_pstring.getBytes("utf-8"));
//			  v_pmode = new String(v_pmode.getBytes("utf-8"));
			  v_pstring = java.net.URLEncoder.encode(v_pstring,"utf-8");
			  v_pmode = java.net.URLEncoder.encode(v_pmode,"utf-8");
			  //拆分参数
			  String[] resultoid = v_oid.split("[|][_][|]");
			  String[] resultpmode = v_pmode.split("[|][_][|]");
			  String[] resultstatus = v_pstatus.split("[|][_][|]");
			  String[] resultpstring = v_pstring.split("[|][_][|]");
			  String[] resultamount = v_amount.split("[|][_][|]");
			  String[] resultmoneytype = v_moneytype.split("[|][_][|]");

			String source1 = v_oid + v_pmode + v_pstatus + v_pstring + v_count;
			String source2 = v_amount +v_moneytype;
			logger.info("----------------->source1:"+source1);
			logger.info("----------------->source2:"+source2);
			logger.info("----------------->source1Md5:"+SxyPayMd5.createMd5(source1));
			logger.info("----------------->source2Md5:"+SxyPayMd5.createMd5(source2));
			if (v_mac.equals(SxyPayMd5.createMd5(source1)) && 
                    v_md5money.equals(SxyPayMd5.createMd5(source2))) {
				logger.info("---------------->验签通过-------------");
				String status = "";
				if(v_pstatus.equals("1")){//充值成功
	                status = TrusteeshipConstants.Status.PASSED;
	            }else{
	                status = TrusteeshipConstants.Status.REFUSED;
	            }
				trusteeshipOperationService.updateOperationModel(request.getParameter(SyxParam.v_oid), 
	                    SxyPayConstants.BusiType.recharge, 
	                    null, SxyPayConstants.THIRD_ID, status,v_pstring);
				
				if(v_pstatus.equals("1")){//充值成功
	                Map<String,String> resultMap = this.getMap(request);
	                sxyPayRechargeService.updateRechargSucess(resultMap);
	            }else{
	            	logger.info("---------------->充值失败-------------");
	            }
			}else{
				logger.info("---------------->验签失败------------");
			}
			
        } catch (ApplicationException e) {
            handlerResult = e.getMessage();
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return handlerResult;
	}
	
	
	
	
	
	
	
	
	
	private String receiveRechargeCallback1(HttpServletRequest request) {
		String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			Map<String, String> handerResultMap = ResultHandler.convert2Map(request);
			logger.info("------------------->充值异步通知参数："+handerResultMap);
			String resultCode = handerResultMap.get("respCode");
			logger.info("------------------->resultCode:"+resultCode);
            String respDesc = handerResultMap.get("respDesc");
            String result = resultCode+";"+respDesc;
            String status = "";
            if(resultCode.equals("20")){//充值成功
                status = TrusteeshipConstants.Status.PASSED;
            }else{
                status = TrusteeshipConstants.Status.REFUSED;
            }
            trusteeshipOperationService.updateOperationModel(request.getParameter(SyxParam.v_oid), 
                    SxyPayConstants.BusiType.recharge, 
                    null, SxyPayConstants.THIRD_ID, status, result);
            if(resultCode.equals("20")){//充值成功
                Map<String,String> resultMap = this.getMap(request);
                sxyPayRechargeService.updateRechargSucess(resultMap);
            }else{
                if(StringUtils.isEmpty(respDesc)){
                    respDesc = "充值失败,请联系客服！";
                }
                throw new ApplicationException(respDesc);
            }
            
            logger.info("汇付宝满标投资接口回调返回的结果为：:" + result);
        } catch (ApplicationException e) {
            handlerResult = e.getMessage();
            logger.error(e.getMessage());
        } catch (Exception e) {
            handlerResult = "满标投资失败,请联系客服处理";
            logger.error(e.getMessage());
        }
        return handlerResult;
	}
	
	private Map<String, String> getMap(HttpServletRequest request) {
		Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put(SyxParam.v_oid, request.getParameter(SyxParam.v_oid));
        return paramMap;
	}

}
