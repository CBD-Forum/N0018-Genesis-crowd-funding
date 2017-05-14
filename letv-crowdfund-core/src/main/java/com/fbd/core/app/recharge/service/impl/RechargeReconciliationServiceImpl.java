package com.fbd.core.app.recharge.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.recharge.service.IRechargeReconciliationService;
import com.fbd.core.app.reconciliation.dao.IReconciliationRechargeDao;
import com.fbd.core.app.reconciliation.model.ReconciliationRechargeModel;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;

@Service(value="rechargeReconciliationService")
public class RechargeReconciliationServiceImpl implements
		IRechargeReconciliationService {
	
	
    private static final Logger logger = LoggerFactory.getLogger(RechargeReconciliationServiceImpl.class);

    
    @Resource
    private IRechargeDao rechargeDao;
    @Resource
    private IReconciliationRechargeDao reconciliationRechargeDao;

	@Override
	public void rechargeReconciliation(String orderDate,String payPlatform) {
		String resultStr = "";
        try {
            
            Map<String,String> params = new HashMap<String,String>();
            String requestUrl = SxyPayConstants.Config.SXY_FILE_DOWNLOAD_URL
                    +SxyPayConstants.Config.SXY_SHBH+"_"+SxyPayConstants.Config.SXY_FILE_DOWNLOAD_RANDOMNUM+"/"+orderDate+".xml";
            resultStr =  HttpRequestUtils.callHttpPOST(requestUrl, params,"gb2312");
            System.out.println(resultStr);
            
            //创建一个新的字符串
            StringReader read = new StringReader(resultStr);
            //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            //创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Namespace ns = root.getNamespace();
            Element et = null;
            String reconciliationOrderId = PKGenarator.getOrderId();
            List<ReconciliationRechargeModel> rechargeList = new ArrayList<ReconciliationRechargeModel>();
            for(int i=0;i<jiedian.size();i++){
                if(true){
                    continue;
                }
                et = (Element) jiedian.get(i);//循环依次得到子元素
                String oid = et.getChild("oid",ns).getText();
                String amount = et.getChild("amount",ns).getText();
                String pmode = et.getChild("pmode",ns).getText();
                String pstring = et.getChild("pstring",ns).getText();
                String pdesc = et.getChild("pdesc",ns).getText();
                String mac = et.getChild("mac",ns).getText();
                
                ReconciliationRechargeModel reconciliationRecharge = new ReconciliationRechargeModel();
                reconciliationRecharge.setId(PKGenarator.getId());
                reconciliationRecharge.setOrderDate(orderDate);
                reconciliationRecharge.setReconciliationOrderId(reconciliationOrderId);
                reconciliationRecharge.setOrderId(oid);
                reconciliationRecharge.setAmount(Double.parseDouble(amount));
                reconciliationRecharge.setCardMode(pmode);
                reconciliationRecharge.setOrderState(pstring);
                reconciliationRecharge.setOrderDesc(pdesc);
                reconciliationRecharge.setMac(mac);
                reconciliationRecharge.setPayPlatform(payPlatform);
                reconciliationRecharge.setCreateTime(new Date());
                
                //根据订单号查询充值信息
                RechargeModel recharge = this.rechargeDao.selectByOrderId(oid);
                if(recharge!=null){
                    reconciliationRecharge.setLocalAmount(recharge.getRechargeAmt());
                    reconciliationRecharge.setLocalOrderState(recharge.getState());
                    if("1".equals(pstring) && !"1".equals(recharge.getReconciliationState())){
                        recharge.setReconciliationState("1"); 
                        recharge.setReconciliationTime(new Date());
                        this.rechargeDao.update(recharge);
                    }
                }
                rechargeList.add(reconciliationRecharge);
                
            }
            if(rechargeList!=null && rechargeList.size()>0){
                this.reconciliationRechargeDao.batchInsert(rechargeList);
            }
            
             
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        
	}
	
	public static void main(String[] args) {
	    try {
//            Map<String,String> params = new HashMap<String,String>();
//            String requestUrl = "http://210.73.90.22/merchant/download/11538_6f017643/20161031.xml";
//            String resultStr =  HttpRequestUtils.callHttpPOST(requestUrl, params,"gb2312");
//            System.out.println(resultStr);
	        
	        
	        
	        
	        
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
	 
	
	
	
	
	

}
