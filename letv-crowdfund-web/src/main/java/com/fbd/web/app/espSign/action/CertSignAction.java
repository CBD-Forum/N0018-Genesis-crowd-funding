package com.fbd.web.app.espSign.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.itrus.raapi.UserInfo;

import com.fbd.core.app.signature.service.ICertSignService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;


@Controller
@Scope("prototype")
@RequestMapping("/certSign")
public class CertSignAction extends BaseAction {

	
	private static final long serialVersionUID = 1L;
	@Resource
	private ICertSignService certSignService;
	
	
    @RequestMapping(value = "/testApplyCert.html", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> sendCreateUserVerifyCode(HttpServletRequest request,String usage,HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	UserInfo userInfo = new UserInfo();
        	userInfo.setUserName("zhangweilong");
        	userInfo.setUserEmail("1240836457@qq.com");
        	String certReqBuf = "MIICXzCCAUcCAQAwGjEYMBYGA1UEAxMPQ049aXRydXNfZW5yb2xsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtSaSADrTi10519vqzl6ukAVtIIh6+Z5Km43VDw6OWXggK74N0/hsQIrOJ2J193QkQQmwoqofbAypuh7eNgv3L3yKO7GwDpXF8vVOAzlTNb5YsPa5kx377kXXiToPyC5s3ZauGRV4cyic3R9wbx8fFlg9XBIL3ocANPANeY6WLfFdzLXt6YJtVRgo5I7mc4bwaZ8wNFLzZo9WqSui/lpmy7pKYP833rv2hfIDhX2nO8Z3H/gL/dIoB1VLtrDhddhe7T3AoHznXQK6jUpM8PcW5KfcONN9aEGtRKx0iSrbKReffx8A7r2FIpJMlC12KvXRhFbNrEyTmjlYRXQlWsD2OQIDAQABoAAwDQYJKoZIhvcNAQEFBQADggEBACqIaN3hZa9DkM2+qfVDYV/JGKDRkWZ7WIbVQWaGx+53TJUy4P8PHQJ7eaUDFyQf+fu+LagIJXpDIobfqcZpatn3qVe5xaYB4OeHO3Lp2dWjqWB8jv9TDT4qYs1ynOxfmMpaIvMxjffVPg2KhqqFDgx1+Yu02eiQn1cNgqYL7WIjDXr+6iXovmSK0NFYK28TFMetJflPx443TzPCecEei3t3K53wwHmA9usDSCare0/ieV6ds/q6j1ltFw3nQvyutKBU00i80JCCj2yOAzBYkNMtmrVAM0IQGJjtbuN64DJrApG8J0Y+Ub2o78vQyit0Y29fMpiEQ4uDhvcKtX7wp2A=";
        	String certSign = this.certSignService.applyCertAA(userInfo, certReqBuf, FbdCoreConstants.RA_AccountHash, FbdCoreConstants.RA_Password);
            resultMap.put(SUCCESS,true);
            resultMap.put("certSign",certSign);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }	
	
	
	
	
	
	
	
}
