package com.fbd.core.app.signature.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import com.fbd.core.app.contract.dao.IContractTemplateDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.dao.impl.CrowdfundingSupportDaoImpl;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.signature.service.IContractCreateService;
import com.fbd.core.app.signature.service.IEspSignApiService;
import com.fbd.core.app.signature.util.Base64Encoder;
import com.fbd.core.app.signature.util.EspHttpClient;
import com.fbd.core.app.signature.util.EspHttpClientBuilder;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserSignatureDao;
import com.fbd.core.app.user.dao.IUserSignatureOrgDao;
import com.fbd.core.app.user.dao.IUserSignaturePersonDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSignatureModel;
import com.fbd.core.app.user.model.UserSignatureOrgModel;
import com.fbd.core.app.user.model.UserSignaturePersonModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.HttpServletUtils;
import com.fbd.core.util.spring.SpringUtil;


@Service(value="espSignApiService")
public class EspSignApiServiceImpl implements IEspSignApiService {
    
    
    
    private static final Logger logger = LoggerFactory.getLogger(EspSignApiServiceImpl.class);
	
	@Resource
	private IUserDao userDao;
	@Resource
	private IUserSignatureDao userSignatureDao;
	@Resource
	private IUserSignatureOrgDao userSignatureOrgDao;
	@Resource
	private IUserSignaturePersonDao userSignaturePersonDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IContractTemplateDao contractTemplateDao;
    @Resource
    private IContractCreateService contractCreateService;
	@Override
	public String sendVerifyCode(String userId, String mobile, String usage,HttpServletRequest request) {
		UserModel user = null;
		String sendMsgMobile = ""; //接收短信手机号码
		String sign_user_id = "";//签章服务器用户ID
		String authCode = "";
		try{
		    //查询用户签约信息
            UserSignatureModel userSignature = this.userSignatureDao.selectByUserId(userId);
            if(userSignature!=null){  //已经创建用户
                if("user/create".equals(usage)){
                    throw new ApplicationException("用户印章已创建！");
                }
                sign_user_id = userSignature.getSignUserId();
                if("0".equals(userSignature.getType())){ //个人用户
                    UserSignaturePersonModel quserPerson = new UserSignaturePersonModel();
                    quserPerson.setUserId(userId);
                    UserSignaturePersonModel person = this.userSignaturePersonDao.selectByModel(quserPerson);
                    sendMsgMobile = person.getMobile();
                }else if("1".equals(userSignature.getType())){//企业用户
                    UserSignatureOrgModel quserOrg = new UserSignatureOrgModel();
                    quserOrg.setUserId(userId);
                    UserSignatureOrgModel org = this.userSignatureOrgDao.selectByModel(quserOrg);
                    sendMsgMobile = org.getTransactorMobile();
                }
            }else{
                user = this.userDao.findByUserId(userId);//查询用户信息
                sendMsgMobile = user.getMobile();
            }
			String url = FbdCoreConstants.esp_baseUrl + "/authCode/send";
			logger.info("------------>天威诚信发送短信验证码-url:"+url);
			String apiId = FbdCoreConstants.esp_apiId;
			String apiSecret = FbdCoreConstants.esp_apiSecret;
			Map<String, Object> reqData = new HashMap<String, Object>();
			reqData.put("apiId", apiId);
			reqData.put("timestamp", System.currentTimeMillis());
			reqData.put("usage", usage);
			reqData.put("mobile", sendMsgMobile);
			if (sign_user_id != null && !"".equals(sign_user_id)) {
				reqData.put("userId", sign_user_id);
			}
			logger.info("------------>天威诚信发送短信验证码请求参数-reqData:"+reqData);
			EspHttpClient espHttpClient = EspHttpClientBuilder.getHttpClient(FbdCoreConstants.esp_HttpClientProvider);
			String strJsonRespData = espHttpClient.postSignedJson(url, new JSONObject(reqData), apiSecret);
			logger.info("------------>天威诚信发送短信验证码返回参数-strJsonRespData:"+strJsonRespData);
			JSONObject jsonResp = new JSONObject(strJsonRespData);

			if (jsonResp.getBoolean("isOk")) {
//				authCode = jsonResp.getString("authCode");
				System.out.println("/authCode/send Ok:\nauthCode=" + authCode);
				return authCode;
			} else {
				System.err.println("/authCode/send Error:\n" + strJsonRespData);
				throw new ApplicationException(strJsonRespData);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
	}

	
	/**
	 * 创建签章用户 
	 */
	@Override
	public void saveCreateSignUser(String userId, String type,String authCode, UserSignaturePersonModel userPerson,
			UserSignatureOrgModel userOrg,Certificate cert, HttpServletRequest request) {
		try{
		   //查询用户签约信息
            UserSignatureModel userSignature = this.userSignatureDao.selectByUserId(userId);
            if(userSignature!=null){
                throw new ApplicationException("用户印章信息已存在！");
            }
			String url = FbdCoreConstants.esp_baseUrl + "/user/create";
			String apiId = FbdCoreConstants.esp_apiId;
			String apiSecret = FbdCoreConstants.esp_apiSecret;
	        String clientSignCert = null;
	        if (cert != null) {
	            clientSignCert = Base64.encodeBase64String(cert.getEncoded());
	        }
			Map<String, Object> reqData = new HashMap<String, Object>();
	        reqData.put("apiId", apiId);
	        reqData.put("timestamp", System.currentTimeMillis());
	        reqData.put("authCode", authCode);
	        Map<String, Object> user = new HashMap<String, Object>();
	        
	        UserSignatureOrgModel org = null;
	        UserSignaturePersonModel person = null;
			if("0".equals(type)){  //个人用户
				//查询个人用户信息
				UserSignaturePersonModel quserPerson = new UserSignaturePersonModel();
				quserPerson.setUserId(userId);
			    person = this.userSignaturePersonDao.selectByModel(quserPerson);
				if(person!=null){ //更新
					person.setPersonalName(userPerson.getPersonalName());
					person.setIdCardNum(userPerson.getIdCardNum());
					userPerson.setUpdateTime(new Date());
					this.userSignaturePersonDao.update(person);
				}else{
					//保存个人用户信息
					userPerson.setId(PKGenarator.getId());
					userPerson.setCreateTime(new Date());
					userPerson.setUpdateTime(new Date());
					userPerson.setUserId(userId);
					userPerson.setSignImgState("0");
					this.userSignaturePersonDao.save(userPerson);
				}
				user.put("userType", 0);
				user.put("identifyField","idCardNum");
	            user.put("fullname", userPerson.getPersonalName());
	            user.put("idCardNum", userPerson.getIdCardNum());
	            user.put("mobile", userPerson.getMobile());
			}else if("1".equals(type)){  //企业用户
				UserSignatureOrgModel quserOrg = new UserSignatureOrgModel();
				quserOrg.setUserId(userId);
				org = this.userSignatureOrgDao.selectByModel(quserOrg);
				if(org!=null){ //更新
					org.setOrgName(userOrg.getOrgName());
					org.setOrgCode(userOrg.getOrgCode());
					org.setUpdateTime(new Date());
					org.setLegalPersonName(userOrg.getLegalPersonName());
					org.setTransactorMobile(userOrg.getTransactorMobile());
					org.setTransactorName(userOrg.getTransactorName());
					org.setTransactoridCardNum(userOrg.getTransactoridCardNum());
					this.userSignatureOrgDao.update(org);
				}else{
					//保存企业用户信息
					userOrg.setId(PKGenarator.getId());
					userOrg.setCreateTime(new Date());
					userOrg.setUpdateTime(new Date());
					userOrg.setUserId(userId);
					userPerson.setSignImgState("0");
					this.userSignatureOrgDao.save(userOrg);
				}
				user.put("userType", 1);
	            user.put("fullname", userOrg.getOrgName());
	            user.put("orgCode", userOrg.getOrgCode());
	            user.put("identifyField","orgCode");
	            user.put("legalPersonName", userOrg.getLegalPersonName());
	            user.put("transactorName", userOrg.getTransactorName());
	            user.put("transactorIdCardNum", userOrg.getTransactoridCardNum());
	            user.put("transactorMobile", userOrg.getTransactorMobile());
			}
			if (clientSignCert != null){
				user.put("clientSignCert", clientSignCert);
			}
	        reqData.put("user", user);
	        logger.info("------->天威诚信创建用户请求参数-reqData："+reqData);
	        EspHttpClient espHttpClient = EspHttpClientBuilder.getHttpClient(FbdCoreConstants.esp_HttpClientProvider);
	        String strJsonRespData = espHttpClient.postSignedJson(url, new JSONObject(reqData), apiSecret);
	        logger.info("------->天威诚信创建用户相应参数-strJsonRespData："+strJsonRespData);
	        JSONObject jsonResp = new JSONObject(strJsonRespData);
	        if (jsonResp.getBoolean("isOk")) {
	            String sign_user_Id = jsonResp.getString("userId");
	            System.out.println("/user/create Ok:\nuserId=" + sign_user_Id);
	            //更新信息
	            if("0".equals(type)){  //个人用户
	            	UserSignaturePersonModel quserPerson = new UserSignaturePersonModel();
					quserPerson.setUserId(userId);
				    person = this.userSignaturePersonDao.selectByModel(quserPerson);
				    person.setSignUserId(sign_user_Id);
				    person.setUpdateTime(new Date());
				    this.userSignaturePersonDao.update(person);
	            }else if("1".equals(type)){
	            	UserSignatureOrgModel quserOrg = new UserSignatureOrgModel();
					quserOrg.setUserId(userId);
					org = this.userSignatureOrgDao.selectByModel(quserOrg);
					org.setSignUserId(sign_user_Id);
					org.setUpdateTime(new Date());
					this.userSignatureOrgDao.update(org);
	            }
	            //添加签约信息
	            UserSignatureModel userSign = new UserSignatureModel();
	            userSign.setId(PKGenarator.getId());
	            userSign.setUserId(userId);
	            userSign.setSignUserId(sign_user_Id);
	            userSign.setType(type);
	            userSign.setCreateTime(new Date());
	            userSign.setUpdateTime(new Date());
	            this.userSignatureDao.save(userSign);
	            //更新用户
	            UserModel userModel = this.userDao.findByUserId(userId);//查询用户信息
	            userModel.setIsSetSignature("1");
	            this.userDao.updateBySelective(userModel);
	            
	        } else {
	            logger.error("/user/create Error:\n" + strJsonRespData);
	            //消息
	            String message = jsonResp.getString("message");
	            if(message.indexOf("orgCode")>0){
	                throw new ApplicationException("组织机构证代码不合法");
	            }else{
	                throw new ApplicationException(jsonResp.getString("message"));
	            }
	        } 
		}catch(Exception e){
		    e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
        String str = "组织机构代码证[user.orgCode=adsfasdf]参数不合法";
        
        System.out.println(str.indexOf("orgCode3"));
    }
	 
	
	/**
	 * 查询用户签章
	 * @param userId
	 */
	public List<Map<String,Object>> querySignList(String userId){
		
		List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
		try{
			UserSignatureModel userSign = this.userSignatureDao.selectByUserId(userId);
			String url = FbdCoreConstants.esp_baseUrl + "/seal/getListByUser";
			String apiId = FbdCoreConstants.esp_apiId;
			String apiSecret = FbdCoreConstants.esp_apiSecret;
			Map<String, Object> reqData = new HashMap<String, Object>();
	        reqData.put("apiId", apiId);
	        reqData.put("timestamp", System.currentTimeMillis());
	        reqData.put("userId", userSign.getSignUserId());
	        EspHttpClient espHttpClient = EspHttpClientBuilder.getHttpClient(FbdCoreConstants.esp_HttpClientProvider);
	        String strJsonRespData = espHttpClient.postSignedJson(url, new JSONObject(reqData), apiSecret);
	        JSONObject jsonResp = new JSONObject(strJsonRespData);
	        if (jsonResp.getBoolean("isOk")) {
	        	JSONObject seals = jsonResp.getJSONObject("seals");
	        	System.out.println("seals:"+seals);

	        	int total = seals.getInt("total");
	        	if(total>0){
	        		JSONArray listArray =seals.getJSONArray("list");
		        	System.out.println("jsonArray:"+listArray);
		        	Map<String,Object> item = null;
		        	for(int i=0;i<listArray.length();i++){
		        		JSONObject seal = listArray.getJSONObject(i);
		        		System.out.println("seal:"+seal);
		        		item = new HashMap<String,Object>();
		        		item.put("sealName", seal.getString("sealName"));
		        		item.put("sealImage", seal.getString("sealImage"));
		        		item.put("sealImageSize", seal.get("sealImageSize"));
		        		item.put("sealDesc", seal.get("sealDesc"));
		        		item.put("sealId", seal.getString("sealId"));
		        		rows.add(item);
		        	}
	        	}
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return rows;
	}
	
	
    /**
     * 创建合同(返回合同编号)
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-17 上午11:44:09
     */
    public String createContract(String loanNo,String investNo,String userId2,HttpServletRequest request,HttpServletResponse response){
        String contractId = "";
        String inFile = "";
        String outFile =  "";
        try{
            String basePath = request.getSession().getServletContext().getRealPath("/")+"tempFile";
            File file = new File(basePath);
            if(!file.exists()){
                file.mkdirs();
            }
            inFile = basePath+File.separator+PKGenarator.getId()+".pdf";
            outFile = basePath+File.separator+PKGenarator.getId()+".pdf";
            //创建合同使用时间戳创建合同章(返回 PDF的总页数)
            logger.info("===============>使用时间戳创建合同章返回 PDF的总页数");
            int pageNum = this.contractCreateService.createCrontractPdf(loanNo,investNo, inFile, outFile, request, response);
            logger.info("===============>项目总页数-pageNum："+pageNum);
            //根据投资编号查询合同是否已经创建，如果创建直接返回合同编号
            File filePdf = new File(outFile);
            InputStream is = null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                is = new FileInputStream(filePdf);
                byte[] b = new byte[1024];
                int n;
                while ((n = is.read(b)) != -1) {
                    out.write(b, 0, n);
                }
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception ignore) {
                    }
                }
            }
            byte[] bytesPdf = out.toByteArray();
            String url = FbdCoreConstants.esp_baseUrl + "/contract/create";
            String apiId = FbdCoreConstants.esp_apiId;
            String apiSecret = FbdCoreConstants.esp_apiSecret;
            Map<String, Object> reqData = new HashMap<String, Object>();
            reqData.put("apiId", apiId);
            reqData.put("timestamp", System.currentTimeMillis());
            String doc =  new String(new Base64Encoder().encode(bytesPdf));
            //获取合同内容
            String contractTitle = "合同【"+loanNo+"_"+investNo+"】";  //合同标题
            String contractFileName =filePdf.getName(); //合同名称
            //随机数
            Random random = new Random();
            
            String contractNo1 = loanNo+"_"+investNo+"_"+random.nextInt(1000);  //合同编号(项目编号+投资编号)
            Map<String, Object> mapContract = new HashMap<String, Object>();
            mapContract.put("docPath", loanNo);
            mapContract.put("title", contractTitle);
            mapContract.put("fileName",contractFileName);
            mapContract.put("docNum", contractNo1);
            mapContract.put("doc",doc);
            mapContract.put("docType", "pdf"); //文档类型
            
            //定义签章列表
            List<Map<String, Object>> listSignatures = new ArrayList<Map<String, Object>>();
            //定义项目方签章
            CrowdfundingModel crowdfunding = this.crowdfundingDao.getByloanNo(loanNo); //查询项目信息
            
            String loanUser = crowdfunding.getLoanUser(); //查询项目方信息
//            loanUser = "w4vy907685";
            UserSignatureModel loanUserSign = this.userSignatureDao.selectByUserId(loanUser);
            if(loanUserSign==null){
                throw new ApplicationException("创建合同失败,项目方未创建签章！");
            }        
            
            Map<String, Object> projectSideSignatureMap = new HashMap<String, Object>();
            projectSideSignatureMap.put("userId",loanUserSign.getSignUserId());
            
//            projectSideSignatureMap.put("page",pageNum-1);//签章所在页数
//            projectSideSignatureMap.put("positionX", 40 + 100*1);
//            projectSideSignatureMap.put("positionY", 50);
            
            //关键字签章
            projectSideSignatureMap.put("positionKeyword", "甲方签字/盖章");  //关键字
            projectSideSignatureMap.put("offsetx", 0);
            projectSideSignatureMap.put("offsety", 50);
            //查询投资信息
            CrowdfundingSupportModel support = this.crowdfundingSupportDao.getByOrderId(investNo);
            String supportUser = support.getSupportUser();
//            supportUser = "zwlyzzfd";
            UserSignatureModel supportUserSign = this.userSignatureDao.selectByUserId(supportUser);//查询投资用户信息
            if(supportUserSign == null){
                throw new ApplicationException("创建合同失败,投资用户未创建签章！");
            }
            //定义投资方签章
            Map<String, Object> investorSignatureMap = new HashMap<String, Object>();
            investorSignatureMap.put("userId", supportUserSign.getSignUserId());
            
            //坐标位置签章
//            investorSignatureMap.put("page",pageNum-1); //签章所在页数
//            investorSignatureMap.put("positionX",50);
//            investorSignatureMap.put("positionY",50);
            //关键字签章
            investorSignatureMap.put("positionKeyword", "乙方签字/盖章");  //关键字
            investorSignatureMap.put("offsetx", 0);
            investorSignatureMap.put("offsety", -50);
            
            listSignatures.add(projectSideSignatureMap);
            listSignatures.add(investorSignatureMap);
            
            mapContract.put("signatures", listSignatures);//所有要签署的合同签章
            reqData.put("contract", mapContract);
            reqData.put("docRequired", false);
            //开始创建合同
            logger.info("------------------->开始创建合同-----------------------------");
            EspHttpClient espHttpClient = EspHttpClientBuilder.getHttpClient(FbdCoreConstants.esp_HttpClientProvider);
            logger.info("------------------>创建合同请求参数-reqData："+reqData);
            String strJsonRespData = espHttpClient.postSignedJson(url, new JSONObject(reqData), apiSecret);
            logger.info("------------------>创建合同响应参数-strJsonRespData："+strJsonRespData);
            JSONObject jsonResp = new JSONObject(strJsonRespData);
            if (jsonResp.getBoolean("isOk")) {  //创建合同
                //删除合同？上传到ftp?——>还是删除吧，并没有什么卵用
                JSONObject contract = jsonResp.getJSONObject("contract");
                contractId = contract.getString("contractId");
                //更新支持列表中的合同编号
                support.setContractId(contractId);
                support.setContractState("signing");
                
                //上传到ftp；
                //已经签上平台章的合同(异步上传)
/*                String baseContractPath =  request.getSession().getServletContext().getRealPath("/");
                UpdateThread thread = new UpdateThread(doc, baseContractPath, investNo);
                thread.start();*/
                //同步上传
                String fileName = uploadPDFToFileSystem(doc, request);
                support.setContractUrl(fileName);
                this.crowdfundingSupportDao.updateBySelective(support);
                return contractId;
            }else{
                logger.info(strJsonRespData);
                throw new ApplicationException(jsonResp.getString("message"));
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }finally{
          //下载完合同删除临时文件
            File file1 = new File(inFile);
            if(file1.exists()){
                file1.delete();
            }
            File file2 = new File(outFile);
            if(file2.exists()){
                file2.delete();
            }
        }
    }
 
    /**
     * 签署合同
     */
    @Override
    public void signContract(String loanNo, String investNo, String userId,String contractId,String authCode, HttpServletRequest request) {
        
        String sign_user_id = "";//签章服务器用户ID
        try{
            //查询投资支持信息
            CrowdfundingSupportModel support = this.crowdfundingSupportDao.getByOrderId(investNo);
            //查询用户签约信息
            UserSignatureModel userSignature = this.userSignatureDao.selectByUserId(userId);
            if(userSignature!=null){
                sign_user_id = userSignature.getSignUserId();
            }else{
                throw new ApplicationException("用户未创建签章用户信息！");
            }
            String url = FbdCoreConstants.esp_baseUrl + "/contract/sign";
            String apiId = FbdCoreConstants.esp_apiId;
            String apiSecret = FbdCoreConstants.esp_apiSecret; 
            Map<String, Object> reqData = new HashMap<String, Object>();
            reqData.put("apiId", apiId);
            reqData.put("timestamp", System.currentTimeMillis());
            reqData.put("userId",sign_user_id);
            reqData.put("contractId", contractId);
            reqData.put("docRequired", false);
            reqData.put("authCode", authCode);
            EspHttpClient espHttpClient = EspHttpClientBuilder.getHttpClient(FbdCoreConstants.esp_HttpClientProvider);
            logger.info("-------------->签署合同请求参数-reqData:"+reqData);
            String strJsonRespData = espHttpClient.postSignedJson(url, new JSONObject(reqData), apiSecret);
            logger.info("-------------->签署合同响应参数-strJsonRespData:"+strJsonRespData);
            JSONObject jsonResp = new JSONObject(strJsonRespData);
            if (jsonResp.getBoolean("isOk")) {
                //更新合同签约状态
                support.setContractState("signing");  //设置合同状态为签署中
                
                if(userId.equals(support.getSupportUser())){  //投资人 签署
                    support.setContractInvestSignState("1");  //投资人签署完成
                    support.setContractInvestSignTime(new Date());
                    if("complete".equals(support.getContractProjectSignState())){
                        support.setContractState("complete");
                    }
                }else{  //项目方签署
                    support.setContractProjectSignState("1");
                    support.setContractProjectSignTime(new Date());
                    if("complete".equals(support.getContractInvestSignState())){
                        support.setContractState("complete");
                    }
                }
                //把文件上传到
                String fileName = this.getContract(investNo, request);
                support.setContractUrl(fileName);
                this.crowdfundingSupportDao.updateBySelective(support);
            } else {
                logger.error("/contract/create Error:\n" + strJsonRespData);
                throw new ApplicationException(jsonResp.getString("message"));
            }
        }catch(Exception e){
            logger.info("签约合同失败："+e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }
    
 
    @Override
    public String getContract(String investNo, HttpServletRequest request) {
        String path = "";
        try{
            //查询投资信息
            CrowdfundingSupportModel support = this.crowdfundingSupportDao.getByOrderId(investNo);
            String url = FbdCoreConstants.esp_baseUrl + "/contract/get";
            String apiId = FbdCoreConstants.esp_apiId;
            String apiSecret = FbdCoreConstants.esp_apiSecret; 
            Map<String, Object> reqData = new HashMap<String, Object>();
            reqData.put("apiId", apiId);
            reqData.put("timestamp", System.currentTimeMillis());
            reqData.put("contractId",support.getContractId());
            reqData.put("docRequired", true);
            EspHttpClient espHttpClient = EspHttpClientBuilder.getHttpClient(FbdCoreConstants.esp_HttpClientProvider);
            String strJsonRespData = espHttpClient.postSignedJson(url, new JSONObject(reqData), apiSecret);
            JSONObject jsonResp = new JSONObject(strJsonRespData);
            if (jsonResp.getBoolean("isOk")) {
                //更新合同签约状态
                JSONObject contract = jsonResp.getJSONObject("contract");
                String doc = contract.getString("doc");
                //异步上传
               /* String basePath =  request.getSession().getServletContext().getRealPath("/");
                UpdateThread thread = new UpdateThread(doc, basePath, investNo);
                thread.start();*/
                String fileName = uploadPDFToFileSystem(doc, request);
                return fileName;
            } else {
                logger.error("/contract/get Error:\n" + strJsonRespData);
                throw new ApplicationException(jsonResp.getString("message"));
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
    
 
    
    
    /**
     * 将合同上传到FTP文件服务器
     * Description: 
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-29 下午6:17:47
     */
    @SuppressWarnings("restriction")
    public String uploadPDFToFileSystem(String doc,HttpServletRequest request){
        String filePathName = "";
        try{
            String path = "contract/"+DateUtil.date2Str(new Date(), "yyyyMMdd");   //合同保存路劲
            String basePath =  request.getSession().getServletContext().getRealPath(path);
            BASE64Decoder decoder = new sun.misc.BASE64Decoder();   
            //将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(doc);
            String fileName=PKGenarator.getId()+".pdf";
            File targetFile = new File(basePath, fileName);  
            if(!targetFile.exists()){  
                targetFile.mkdirs();  
            }  
            //保存在文件服务器上
            InputStream is = new ByteArrayInputStream(bytes); 
            boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, fileName,is);
            if(uploadResult){ //上传成功
                filePathName =  path+"/"+fileName;
            }else{
                throw new ApplicationException("文件上传失败！");
            }
        }catch(Exception e){
            throw new ApplicationException(e.getMessage());
        }
        return filePathName;
    }
   
    
    private class UpdateThread extends Thread{
        private String doc;
        private String basePath;
        private String investNo;
        public UpdateThread(String doc,String basePath,String intestNo){
            this.doc = doc;
            this.basePath = basePath;
            this.investNo = intestNo;
        }
        @SuppressWarnings("restriction")
        @Override
        public void run() {
            try{
                String path = "contract/"+DateUtil.date2Str(new Date(), "yyyyMMdd");   //合同保存路劲
                BASE64Decoder decoder = new sun.misc.BASE64Decoder();   
                //将base64编码的字符串解码成字节数组
                byte[] bytes = decoder.decodeBuffer(doc);
                String fileName=PKGenarator.getId()+".pdf";
                String filePath = basePath+ path;
                File targetFile = new File(filePath, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存在文件服务器上
                InputStream is = new ByteArrayInputStream(bytes); 
                boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, fileName,is);
                if(uploadResult){ //上传成功
                    CrowdfundingSupportDaoImpl crowdfundingSupportDao = (CrowdfundingSupportDaoImpl) SpringUtil.getBean("crowdfundingSupportDao");
                    String contractPath = path+fileName;
                    CrowdfundingSupportModel support = crowdfundingSupportDao.getByOrderId(investNo);
                    support.setContractUrl(contractPath);
                    crowdfundingSupportDao.updateBySelective(support);
                }
            }catch(Exception e){
                throw new ApplicationException(e.getMessage());
            }
        }
    }    
    
    /**
    * 批量签署合同
    */
    @Override
    public void batchSignContract(String loanNo,String userId,String authCode, HttpServletRequest request) {
        String sign_user_id = "";
        try{
//           //查询用户签约信息
           UserSignatureModel userSignature = this.userSignatureDao.selectByUserId(userId);
           if(userSignature!=null){
               sign_user_id = userSignature.getSignUserId();
           }else{
               throw new ApplicationException("用户未创建签章用户信息！");
           }
           String url = FbdCoreConstants.esp_baseUrl + "/contract/batchSign";
           String apiId = FbdCoreConstants.esp_apiId;
           String apiSecret = FbdCoreConstants.esp_apiSecret; 
           Map<String, Object> reqData = new HashMap<String, Object>();
           reqData.put("apiId", apiId);
           reqData.put("timestamp", System.currentTimeMillis());
           reqData.put("userId",sign_user_id);  //签约用户
           reqData.put("docPath", loanNo); //同一项目下的所有合同
           reqData.put("clientIp", HttpServletUtils.getIpAddress(request));
           reqData.put("authCode", authCode);
           EspHttpClient espHttpClient = EspHttpClientBuilder.getHttpClient(FbdCoreConstants.esp_HttpClientProvider);
           String strJsonRespData = espHttpClient.postSignedJson(url, new JSONObject(reqData), apiSecret);
           System.out.println("strJsonRespData:"+strJsonRespData);
           JSONObject jsonResp = new JSONObject(strJsonRespData);
           if (jsonResp.getBoolean("isOk")) {
               //更新合同签约状态
               
               
               
           } else {
               logger.error("/contract/batchSign Error:\n" + strJsonRespData);
               throw new ApplicationException(strJsonRespData);
           }
       }catch(Exception e){
           logger.info("签约合同失败："+e.getMessage());
           throw new ApplicationException(e.getMessage());
       }
    }
    
    @SuppressWarnings("restriction")
    public void createPDF(String doc,String fileName){
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try { 
            BASE64Decoder decoder = new sun.misc.BASE64Decoder();   
            //将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(doc);
            //apache公司的API
            //byte[] bytes = Base64.decodeBase64(base64sString);
            //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            //创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            //指定输出的文件 
            File file = new File(fileName);
            //创建到指定文件的输出流
            fout  = new FileOutputStream(file);
            //为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while(len != -1){
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
         } catch (IOException e) {
                e.printStackTrace();
         }finally{
            try { 
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                 e.printStackTrace();
            } 
        }
    }
    
    
    
    /**
     * 查询用户的签章信息
     * Description: 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-1 上午11:02:26
     */
    public Map<String,Object> queryUserSignInfo(String userId){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        UserSignatureModel userSignature = this.userSignatureDao.selectByUserId(userId);
        if(userSignature!=null){  //已经创建用户
            if("0".equals(userSignature.getType())){ //个人用户
                UserSignaturePersonModel quserPerson = new UserSignaturePersonModel();
                quserPerson.setUserId(userId);
                UserSignaturePersonModel person = this.userSignaturePersonDao.selectByModel(quserPerson);
                String signImgState = person.getSignImgState();
                if("0".equals(signImgState) || signImgState==null ){ //如果是0去获取图章信息
                    try{
                        String signImg = this.getUserDefaultSign(userSignature.getSignUserId());
                        System.out.println("signImg:"+signImg);
                        person.setSignImg(signImg);
                        person.setSignImgState("1");
                        this.userSignaturePersonDao.update(person);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                resultMap.put("signature", person);
            }else if("1".equals(userSignature.getType())){//企业用户
                UserSignatureOrgModel quserOrg = new UserSignatureOrgModel();
                quserOrg.setUserId(userId);
                UserSignatureOrgModel org = this.userSignatureOrgDao.selectByModel(quserOrg);
                String signImgState = org.getSignImgState();
                if("0".equals(signImgState)|| signImgState==null){ //如果是0去获取图章信息
                    try{
                        String signImg = this.getUserDefaultSign(userSignature.getSignUserId());
                        System.out.println("signImg:"+signImg);
                        org.setSignImg(signImg);
                        org.setSignImgState("1");
                        this.userSignatureOrgDao.update(org);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                resultMap.put("signature", org);
            }
        }
        return resultMap;
        
    }
    
    /**
     * 获取用户的印章信息
     * Description: 
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-1 上午11:50:55
     */
    public String getUserDefaultSign(String signUserId){
        
        try{
            String url = FbdCoreConstants.esp_baseUrl + "/seal/getDefaultByUser";
            String apiId = FbdCoreConstants.esp_apiId;
            String apiSecret = FbdCoreConstants.esp_apiSecret; 
            Map<String, Object> reqData = new HashMap<String, Object>();
            reqData.put("apiId", apiId);
            reqData.put("timestamp", System.currentTimeMillis());
            reqData.put("userId",signUserId);
            EspHttpClient espHttpClient = EspHttpClientBuilder.getHttpClient(FbdCoreConstants.esp_HttpClientProvider);
            String strJsonRespData = espHttpClient.postSignedJson(url, new JSONObject(reqData), apiSecret);
            JSONObject jsonResp = new JSONObject(strJsonRespData);
            if (jsonResp.getBoolean("isOk")) {
                System.out.println("strJsonRespData:"+strJsonRespData);
                
                JSONObject seal = jsonResp.getJSONObject("seal");
                String userId = seal.getString("userId");
                String sealId = seal.getString("sealId");
                boolean isDefault = seal.getBoolean("isDefault");
                String sealName = seal.getString("sealName");
                String sealImage = seal.getString("sealImage");
                return sealImage;
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("获取用户图章失败！");
        }
        return null;
    }
}
